/*
 *    Copyright 2023 Ondřej Karmazín
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
                direction =
                    UsbEndpoint.Direction.values().first { it.intValue == platformEp.direction },
                bEndpointAddress = platformEp.address,
                bmAttributes = platformEp.attributes,
                endpointNumber = platformEp.endpointNumber)
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
        interfaces = platformDevice.interfaces().map(::toCommonInterface))
