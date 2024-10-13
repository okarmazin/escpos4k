// SPDX-License-Identifier: Apache-2.0

package cz.multiplatform.escpos4k.bluetooth

import cz.multiplatform.escpos4k.core.PrintError
import cz.multiplatform.escpos4k.core.PrinterConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

public class BluetoothPrinterConnection(private val deviceConnection: BluetoothDeviceConnection) : PrinterConnection {
  override val name: String = with(deviceConnection.device) { name ?: address }

  public override val isOpen: Boolean
    get() = deviceConnection.isOpen

  public fun close(): Unit = deviceConnection.close()

  override suspend fun printRaw(bytes: ByteArray): PrintError? {
    return withContext(Dispatchers.Default) {
      if (!isOpen) {
        return@withContext PrintError.NotConnected
      }

      deviceConnection.write(bytes)?.let(::mapBluetoothError)
    }
  }

  private fun mapBluetoothError(error: BluetoothError): PrintError {
    // TODO Do we have any other mappings than NotConnected?
    return when (error) {
      BluetoothError.AccessDenied,
      BluetoothError.BluetoothNotAvailable,
      BluetoothError.BluetoothOff,
      is BluetoothError.DeviceNotFound,
      is BluetoothError.Unknown -> PrintError.NotConnected
    }
  }
}
