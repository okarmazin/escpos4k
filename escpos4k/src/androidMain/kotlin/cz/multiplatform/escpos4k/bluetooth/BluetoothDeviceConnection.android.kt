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

import android.bluetooth.BluetoothSocket
import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

internal class AndroidBluetoothDeviceConnection(
    private val connection: BluetoothSocket,
    device: BluetoothDevice
) : BluetoothDeviceConnection(device) {
  private val writeMutex = Mutex()

  override fun closeInternal() {
    try {
      connection.close()
    } catch (e: IOException) {
      // Ignore
    }
  }

  override suspend fun write(data: ByteArray) {
    // TODO maybe we would like to report result?
    withContext(Dispatchers.Default) {
      writeMutex.withLock {
        try {
          connection.outputStream.write(data)
        } catch (e: IOException) {
          // Ignore
        }
      }
    }
  }
}
