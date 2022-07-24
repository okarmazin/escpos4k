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

import cz.multiplatform.escpos4k.core.PrintError
import cz.multiplatform.escpos4k.core.Printer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

public class BluetoothPrinterConnection(
    private val deviceConnection: BluetoothDeviceConnection,
) : Printer {
  override val name: String = with(deviceConnection.device) { name ?: address }

  public val isOpen: Boolean
    get() = deviceConnection.isOpen

  public fun close(): Unit = deviceConnection.close()

  override suspend fun printRaw(bytes: ByteArray): PrintError? {
    return withContext(Dispatchers.Default) {
      if (!isOpen) {
        return@withContext PrintError.NotConnected
      }

      deviceConnection.write(bytes)

      null
    }
  }
}
