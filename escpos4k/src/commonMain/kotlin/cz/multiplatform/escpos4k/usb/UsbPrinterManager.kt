/*
 *    Copyright 2024 escpos4k authors
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

import arrow.core.Either
import arrow.core.left
import arrow.core.right

public interface UsbPrinterManager {

  public fun hasPermission(printer: UsbDevice): Boolean

  public suspend fun awaitPermission(printer: UsbDevice): Boolean

  /**
   * Open the device so requests can be made.
   *
   * @return The open connection or `null` if an error occurred.
   */
  public suspend fun openConnection(printer: UsbDevice): UsbPrinterConnection?

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

  override suspend fun openConnection(printer: UsbDevice): UsbPrinterConnection? {
    val deviceConnection = openDeviceConnection(printer) ?: return null
    return UsbPrinterConnection(deviceConnection)
  }

  override fun visiblePrinters(): List<UsbDevice> =
      allVisibleDevices().filter { it.findOutputEndpoint().isRight() }

  protected abstract fun allVisibleDevices(): List<UsbDevice>

  protected abstract suspend fun openDeviceConnection(printer: UsbDevice): UsbDeviceConnection?
}

/**
 * Try to determine if this device is a printer. If it is, return its BULK OUT endpoint.
 *
 * Otherwise, return `null`.
 *
 * Note: If this function returns `null`, it does not mean that the device is not a printer. It only
 * means that our detection algorithm didn't figure it out.
 */
internal fun UsbDevice.findOutputEndpoint(): Either<EndpointSearchError, UsbEndpoint> {
  if (deviceClass !in setOf(UsbClass.DefinedByInterface, UsbClass.VendorSpecific)) {
    return EndpointSearchError.InvalidDeviceClass.left()
  }

  // First check if there is an explicitly defined Printer interface.
  interfaces
      .firstOrNull { it.isPrinter() }
      ?.let { printerIface ->
        // Every Printer-class interface must have a BULK OUT endpoint, otherwise we couldn't
        // send print commands to it. So the bulkOutEp function SHOULD always return a not-null
        // result. If it returns null, something is seriously wrong with the device.
        return printerIface.bulkOutEp()?.right() ?: EndpointSearchError.BulkOutNotFound.left()
      }

  val validClasses = setOf(UsbClass.Printer, UsbClass.VendorSpecific)
  fun isDisqualified(iface: UsbInterface) = iface.interfaceClass !in validClasses

  // If there are no Printer interfaces, we have to try some sort of heuristic.
  // NOTE: The when cases may look redundant at first sight, but they are not. Do not merge the
  // `1` and `else` branches.
  when (interfaces.size) {
    0 -> {
      // Is this even allowed by the USB standard?
      // TODO check the standard if no-interface devices are allowed.
      return EndpointSearchError.BulkOutNotFound.left()
    }
    1 -> {
      val iface = interfaces[0]
      return if (isDisqualified(iface)) {
        EndpointSearchError.DisqualifyingInterfaceFound(iface).left()
      } else {
        /*
          THIS IS THE MOST LIKELY PATH TO BE TAKEN WITH ESC/POS PRINTERS!

          ESC/POS printers often do not advertise themselves as printers, but rather as
          Vendor-specific devices with a single Vendor-specific interface.
        */
        iface.bulkOutEp()?.right() ?: EndpointSearchError.BulkOutNotFound.left()
      }
    }
    else -> {
      /*
         The device has multiple interfaces and none of them are Printer.
         We differentiate multi-interface devices from single-interface devices because the
         additional information allows us to perform additional filtering.

         Some USB classes are considered (by us) to be mutually exclusive with printer devices.
         These classes disqualify the device even if the device has other interfaces that would
         be considered valid.

         For example if any of the interfaces have the WirelessController USB class,
         it is pretty much guaranteed to be something irrelevant.
         When was the last time you saw a printer acting as a Bluetooth dongle?
      */

      interfaces.firstOrNull(::isDisqualified)?.let {
        return EndpointSearchError.DisqualifyingInterfaceFound(it).left()
      }

      return interfaces.firstNotNullOfOrNull { it.bulkOutEp() }?.right()
          ?: EndpointSearchError.BulkOutNotFound.left()
    }
  }
}

internal sealed class EndpointSearchError {
  data object InvalidDeviceClass : EndpointSearchError()
  data object BulkOutNotFound : EndpointSearchError()
  class DisqualifyingInterfaceFound(val cause: UsbInterface) : EndpointSearchError()
}

private fun UsbInterface.isPrinter() = interfaceClass == UsbClass.Printer

private fun UsbInterface.bulkOutEp(): UsbEndpoint? =
    endpoints.firstOrNull {
      it.type == UsbEndpoint.Type.Bulk && it.direction == UsbEndpoint.Direction.Out
    }
