// SPDX-License-Identifier: Apache-2.0

package cz.multiplatform.escpos4k.usb

public data class UsbInterface(
    val interfaceClass: InterfaceClass,
    val name: String?,
    val bInterfaceNumber: Int,
    val endpoints: List<UsbEndpoint>,
)
