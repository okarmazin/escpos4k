// SPDX-License-Identifier: Apache-2.0

package cz.multiplatform.escpos4k.usb

public sealed class UsbConnectionError {
  /** The connection has been closed and is no longer usable. */
  public data object ConnectionClosed : UsbConnectionError()
}
