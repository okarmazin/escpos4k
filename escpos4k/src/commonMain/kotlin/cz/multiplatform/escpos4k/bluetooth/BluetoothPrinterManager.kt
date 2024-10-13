// SPDX-License-Identifier: Apache-2.0

package cz.multiplatform.escpos4k.bluetooth

import arrow.core.Either

public interface BluetoothPrinterManager {
  /**
   * Open the device so requests can be made. You are responsible for closing the connection when you're done with it.
   */
  public suspend fun openConnection(printer: BluetoothDevice): Either<BluetoothError, BluetoothPrinterConnection>

  /** Returns the list of paired Bluetooth devices with Printer device class. */
  public fun pairedPrinters(): Either<BluetoothError, List<BluetoothDevice>>
}
