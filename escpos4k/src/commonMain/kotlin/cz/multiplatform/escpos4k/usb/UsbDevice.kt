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
