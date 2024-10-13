// SPDX-License-Identifier: Apache-2.0

package cz.multiplatform.escpos4k.usb

public data class UsbEndpoint(
    /**
     * The interface which owns this endpoint. This integer can be used for looking up this endpoint's owner from
     * UsbDevice. The endpoint does not have a direct reference to the owner interface.
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
    @Suppress("MagicNumber") //
    In(128),
    Out(0),
  }
}
