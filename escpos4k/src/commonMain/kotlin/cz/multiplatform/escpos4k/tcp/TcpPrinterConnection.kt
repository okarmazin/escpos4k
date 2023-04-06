/*
 *    Copyright 2023 Ondřej Karmazín
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

package cz.multiplatform.escpos4k.tcp

import cz.multiplatform.escpos4k.core.PrintError
import cz.multiplatform.escpos4k.core.PrinterConnection
import io.ktor.network.sockets.Socket
import io.ktor.network.sockets.isClosed
import io.ktor.network.sockets.openWriteChannel
import io.ktor.util.cio.use
import io.ktor.utils.io.writeFully
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalStdlibApi::class)
public class TcpPrinterConnection
internal constructor(
    override val name: String,
    private val socket: Socket,
    public val address: String,
    public val port: Int,
) : PrinterConnection, AutoCloseable {

  override val isOpen: Boolean
    get() = !socket.isClosed

  override suspend fun printRaw(bytes: ByteArray): PrintError? {
    return withContext(Dispatchers.Default) {
      if (!isOpen) {
        return@withContext PrintError.NotConnected
      }

      try {
        socket.openWriteChannel(true).use { writeFully(bytes) }
      } catch (t: Throwable) {
        return@withContext PrintError.NotConnected
      }

      return@withContext null
    }
  }

  override fun close() {
    try {
      socket.close()
    } catch (t: Throwable) {
      // Ignore close errors.
    }
  }
}
