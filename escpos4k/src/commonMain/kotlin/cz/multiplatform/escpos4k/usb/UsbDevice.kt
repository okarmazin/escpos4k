// SPDX-License-Identifier: Apache-2.0

package cz.multiplatform.escpos4k.usb

public data class UsbDevice(
    /**
     * The **transient** device name assigned by the operating system.
     *
     * Android description: "`In the standard implementation, this is the path of the device file for the device in the
     * usbfs file system.`". Something like `/dev/some/path/usb3`
     */
    val osAssignedName: String,
    val productName: String?,
    val manufacturerName: String?,
    val deviceClass: DeviceClass,
    val productId: Int,
    val interfaces: List<UsbInterface>,
)
