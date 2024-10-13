// SPDX-License-Identifier: Apache-2.0

package cz.multiplatform.escpos4k.tcp

import cz.multiplatform.escpos4k.core.PrintError
import cz.multiplatform.escpos4k.core.PrinterConnection

public class TcpPrinterConnection
internal constructor(
    //    private val connection: Connection,
    override val name: String,
    public val address: String,
    public val port: Int,
) : PrinterConnection {
  override val isOpen: Boolean
    get() = TODO() // !connection.socket.isClosed

  override suspend fun printRaw(bytes: ByteArray): PrintError? {
    TODO()
    //    return withContext(Dispatchers.Default) {
    //      if (!isOpen) {
    //        return@withContext PrintError.NotConnected
    //      }
    //
    //      try {
    //        with(connection.output) {
    //          writeFully(bytes)
    //          flush()
    //        }
    //      } catch (t: Throwable) {
    //        close()
    //        return@withContext PrintError.NotConnected
    //      }
    //
    //      return@withContext null
    //    }
  }

  public fun close() {
    TODO()
    //    try {
    //      connection.socket.close()
    //    } catch (t: Throwable) {
    //      // Ignore close errors.
    //    }
  }

  override fun toString(): String {
    return "TcpPrinterConnection(name='$name', address='$address', port=$port, isOpen=$isOpen)"
  }
}
