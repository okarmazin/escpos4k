/*
 *    Copyright 2022 Ondřej Karmazín
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package cz.multiplatform.escpos4k.usb

public interface UsbPrinterManager {

  public fun hasPermission(printer: UsbDevice): Boolean

  public suspend fun awaitPermission(printer: UsbDevice): Boolean

  /**
   * Open the device so requests can be made.
   *
   * @return The open connection or `null` if an error occurred.
   */
  public fun openConnection(printer: UsbDevice): UsbPrinterConnection?

  /**
   * The list of all connected USB devices that have been determined to likely be a printer.
   *
   * Due to the fact that many, if not most, ESC/POS printers do not advertise their USB class as
   * `Printer` (which is one of the base USB classes defined by the USB standard), the library must
   * apply heuristics to try and determine if a device is a printer.
   *
   * Therefore, some USB devices listed by this function might be completely unrelated.
   */
  public fun visiblePrinters(): List<UsbDevice>
}

internal abstract class AbstractUsbPrinterManager : UsbPrinterManager {

  override fun openConnection(printer: UsbDevice): UsbPrinterConnection? {
    val deviceConnection = openDeviceConnection(printer) ?: return null
    return UsbPrinterConnection(deviceConnection)
  }

  override fun visiblePrinters(): List<UsbDevice> =
      allVisibleDevices().filter { it.likelyPrinterEndpoint() != null }

  protected abstract fun allVisibleDevices(): List<UsbDevice>

  protected abstract fun openDeviceConnection(printer: UsbDevice): UsbDeviceConnection?
}

/**
 * Try to determine if this device is a printer. If it is, return its BULK OUT endpoint.
 *
 * Otherwise, return `null`.
 *
 * Note: If this function returns `null`, it does not mean that the device is not a printer. It only
 * means that our detection algorithm didn't figure it out.
 */
internal fun UsbDevice.likelyPrinterEndpoint(): UsbEndpoint? {
  when (deviceClass) {
    UsbClass.DefinedByInterface,
    UsbClass.VendorSpecific -> {
      // First check if there is an explicitly defined Printer interface.
      interfaces
          .firstOrNull { it.isPrinter() }
          ?.let { printerIface ->
            // Every Printer-class interface must have a BULK OUT endpoint, otherwise we couldn't
            // send
            // print commands to it. So the bulkOutEp function SHOULD always return a not-null
            // result.
            // If it returns null, something is seriously wrong with the device.
            return printerIface.bulkOutEp()
          }

      // If there are no Printer interfaces, we have to try some sort of heuristic.

      when (interfaces.size) {
        0 -> {
          // Is this even allowed by the USB standard?
          // TODO check the standard if no-interface devices are allowed.
          return null
        }
        1 -> {
          val iface = interfaces[0]
          return when (iface.interfaceClass) {
            UsbClass.VendorSpecific,
            UsbClass.Printer /* In reality cannot be Printer */ -> {
              /*
                THIS IS THE MOST LIKELY PATH TO BE TAKEN WITH ESC/POS PRINTERS!

                ESC/POS printers often do not advertise themselves as printers, but rather as
                Vendor-specific devices with a single Vendor-specific interface. That's this path.
              */

              iface.bulkOutEp()
            }

            // Other classes cannot be printers.
            else -> null
          }
        }
        else -> {
          /*
             The device has multiple interfaces and none of them are Printer.
             We differentiate multi-interface devices from single-interface devices because the
             additional information allows us to perform additional filtering.

             Some USB classes are considered (by us) to be mutually exclusive with printer devices.
             These classes disqualify the device even if the device has other interfaces that we
             accept to be printers.

             For example if any of the interfaces have the WirelessController USB class,
             it is pretty much guaranteed to be something irrelevant.
             When was the last time you saw a printer acting as a Bluetooth dongle??
          */

          fun isDisqualified(iface: UsbInterface) =
              when (iface.interfaceClass) {
                UsbClass.Audio,
                UsbClass.CommAndCdc,
                UsbClass.Hid,
                UsbClass.Physical,
                UsbClass.MassStorage,
                UsbClass.CdcData,
                UsbClass.SmartCard,
                UsbClass.ContentSecurity,
                UsbClass.Video,
                UsbClass.PersonalHealthcare,
                UsbClass.AudioVideo,
                UsbClass.UsbCBridge,
                UsbClass.I3C,
                UsbClass.DiagnosticDevice,
                UsbClass.WirelessController,
                UsbClass.Miscellaneous,
                UsbClass.ApplicationSpecific -> {
                  // All of these classes disqualify this device from being a printer.
                  true
                }
                UsbClass.Image -> {
                  // This class is singled out for visibility. It is true that printers with
                  // embedded scanners will include the Image class for the scanner, but we assume
                  // that our target printers are small thermal printers without any scanning
                  // capabilities. Therefore, we consider the Image class to be disqualifying.
                  // If we wanted to detect big multipurpose printers, this would be incorrect.
                  // On the other hand, it is expected that serious printers (like those with
                  // scanners) properly include a Printer-class interface and have already been
                  // returned from this function based on that fact (we specifically search
                  // for a Printer-class interface as the first operation in this function).
                  true
                }
                UsbClass.Printer,
                UsbClass.VendorSpecific -> {
                  false
                }
              }
          return if (interfaces.any(::isDisqualified)) {
            null
          } else {
            interfaces
                .filter { it.interfaceClass == UsbClass.VendorSpecific }
                .firstNotNullOfOrNull { it.bulkOutEp() }
          }
        }
      }
    }
    else -> {
      // No other Device classes can be printers.
      return null
    }
  }
}

private fun UsbInterface.isPrinter() = interfaceClass == UsbClass.Printer

private fun UsbInterface.bulkOutEp(): UsbEndpoint? =
    endpoints.firstOrNull {
      it.type == UsbEndpoint.Type.Bulk && it.direction == UsbEndpoint.Direction.Out
    }
