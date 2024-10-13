// SPDX-License-Identifier: Apache-2.0

package cz.multiplatform.escpos4k.usb

import android.content.Context
import android.hardware.usb.UsbManager

internal fun android.hardware.usb.UsbDevice.findPlatformEndpoint(
    endpoint: UsbEndpoint
): android.hardware.usb.UsbEndpoint =
    interfaces()
        .flatMap { it.endpoints() }
        .first { it.address == endpoint.bEndpointAddress && it.attributes == endpoint.bmAttributes }

internal fun android.hardware.usb.UsbDevice.findPlatformInterface(
    iface: UsbInterface
): android.hardware.usb.UsbInterface = interfaces().first { it.id == iface.bInterfaceNumber }

internal fun Context.usbManager(): UsbManager = getSystemService(Context.USB_SERVICE) as UsbManager

internal fun android.hardware.usb.UsbDevice.interfaces(): List<android.hardware.usb.UsbInterface> =
    (0 until interfaceCount).map { index -> getInterface(index) }

internal fun android.hardware.usb.UsbInterface.endpoints(): List<android.hardware.usb.UsbEndpoint> =
    (0 until endpointCount).map { index -> getEndpoint(index) }

// TODO make extension on UsbManager instead
internal fun Context.platformDevice(printer: UsbDevice): android.hardware.usb.UsbDevice? =
    usbManager().deviceList[printer.osAssignedName]

internal fun toCommonInterface(platformInterface: android.hardware.usb.UsbInterface): UsbInterface {
  val endpoints =
      platformInterface
          .endpoints()
          .map { platformEp ->
            UsbEndpoint(
                ifaceNumber = platformInterface.id,
                type = UsbEndpoint.Type.values().first { it.ordinal == platformEp.type },
                direction = UsbEndpoint.Direction.values().first { it.intValue == platformEp.direction },
                bEndpointAddress = platformEp.address,
                bmAttributes = platformEp.attributes,
                endpointNumber = platformEp.endpointNumber,
            )
          }
          .sortedBy { it.endpointNumber }
  return UsbInterface(
      interfaceClass = UsbClass.fromInt(platformInterface.interfaceClass) as InterfaceClass,
      name = platformInterface.name,
      bInterfaceNumber = platformInterface.id,
      endpoints = endpoints,
  )
}

internal fun toCommonDevice(platformDevice: android.hardware.usb.UsbDevice): UsbDevice =
    UsbDevice(
        osAssignedName = platformDevice.deviceName,
        productName = platformDevice.productName,
        manufacturerName = platformDevice.manufacturerName,
        deviceClass = UsbClass.fromInt(platformDevice.deviceClass) as DeviceClass,
        productId = platformDevice.productId,
        interfaces = platformDevice.interfaces().map(::toCommonInterface),
    )
