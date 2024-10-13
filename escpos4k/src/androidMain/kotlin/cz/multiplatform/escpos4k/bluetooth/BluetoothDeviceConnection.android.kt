// SPDX-License-Identifier: Apache-2.0

package cz.multiplatform.escpos4k.bluetooth

import android.bluetooth.BluetoothSocket
import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

internal class AndroidBluetoothDeviceConnection(private val connection: BluetoothSocket, device: BluetoothDevice) :
    BluetoothDeviceConnection(device) {
  private val writeMutex = Mutex()

  override fun closeInternal() {
    try {
      connection.close()
    } catch (e: IOException) {
      // Ignore
    }
  }

  override suspend fun write(data: ByteArray): BluetoothError? {
    return withContext(Dispatchers.Default) {
      writeMutex.withLock {
        try {
          connection.outputStream.write(data)
          null
        } catch (e: IOException) {
          BluetoothError.Unknown(e)
        }
      }
    }
  }
}
