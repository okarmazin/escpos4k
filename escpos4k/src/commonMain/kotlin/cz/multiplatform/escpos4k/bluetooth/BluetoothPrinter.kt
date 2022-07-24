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

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

public class BluetoothPrinter(device: BluetoothDevice) {
  public val name: String = device.name ?: "Bluetooth device"
  public val address: String = device.address

  private val _isConnecting = MutableStateFlow(false)
  public val isConnecting: StateFlow<Boolean> = _isConnecting.asStateFlow()

  //  override suspend fun printRaw(bytes: ByteArray): PrintError? {
  //    val connection = connection
  //    if (connection == null || !connection.isConnected()) {
  //      return PrintError.NotConnected
  //    }
  //    return withContext(Dispatchers.Default) {
  //      connection.write(bytes)
  //      null
  //    }
  //  }

  //  override suspend fun connect(): ConnectionError? {
  //    return withContext(Dispatchers.Default) {
  //      connectionCreateMutex.withLock {
  //        try {
  //          _isConnecting.value = true
  //          if (isConnected()) {
  //            return@withContext null
  //          }
  //          val conn = device.newConnection() ?: return@withContext
  // ConnectionError.FailedToOpenDevice
  //          val connectJob = async(Dispatchers.Default) { conn.connect() }
  //          connection = conn
  //          if (connectJob.await()) {
  //            return@withContext null
  //          } else {
  //            return@withContext ConnectionError.FailedToOpenDevice
  //          }
  //        } finally {
  //          _isConnecting.value = false
  //        }
  //      }
  //    }
  //  }
}
