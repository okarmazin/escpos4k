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

public data class UsbEndpoint(
    /**
     * The interface which owns this endpoint. This integer can be used for looking up this
     * endpoint's owner from UsbDevice. The endpoint does not have a direct reference to the owner
     * interface.
     */
    val ifaceNumber: Int,
    val type: Type,
    val direction: Direction,
    val bEndpointAddress: Int,
    val bmAttributes: Int,
    val endpointNumber: Int,
) {

  /** The ordinal can be used as the raw type when dealing with low level APIs */
  public enum class Type {
    Control,
    Isochronous,
    Bulk,
    Interrupt,
  }

  public enum class Direction(internal val intValue: Int) {
    In(128),
    Out(0),
  }
}
