/*
 *    Copyright 2022 Ondřej Karmazín
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

package cz.multiplatform.escpos4k.bluetooth

public interface BluetoothPrinterManager {
  /**
   * Open the device so requests can be made.
   *
   * @return The open connection or `null` if an error occurred.
   */
  public suspend fun openConnection(printer: BluetoothDevice): BluetoothPrinterConnection?

  /** Returns the list of paired Bluetooth devices with Printer device class. */
  public fun pairedPrinters(): List<BluetoothDevice>

}

internal abstract class AbstractBluetoothPrinterManager : BluetoothPrinterManager {

  override suspend fun openConnection(printer: BluetoothDevice): BluetoothPrinterConnection? {
    val connection = openDeviceConnection(printer) ?: return null
    return BluetoothPrinterConnection(connection)
  }

  protected abstract fun allPairedDevices(): List<BluetoothDevice>

  /** Returns the list of paired Bluetooth devices with Printer device class. */
  override fun pairedPrinters(): List<BluetoothDevice> {

    return allPairedDevices()
        .filter { device ->
          val classImaging = 1536
          val classPrinter = 1664
          device.majorDeviceClass == classImaging &&
              (device.deviceClass == classPrinter || device.deviceClass == classImaging)
        }
  }

  protected abstract suspend fun openDeviceConnection(printer: BluetoothDevice): BluetoothDeviceConnection?
}
