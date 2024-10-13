// SPDX-License-Identifier: Apache-2.0

package cz.multiplatform.escpos4k.tcp

import arrow.core.Either
import cz.multiplatform.escpos4k.core.ExperimentalEscPosApi
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@ExperimentalEscPosApi
public interface TcpPrinterManager {
  /**
   * Open a TCP connection to the specified address. You are responsible for closing the connection when you're done
   * with it.
   *
   * @param connectTimeout Connection open timeout.
   * @param readWriteTimeout Write timeout.
   */
  public suspend fun openConnection(
      name: String,
      ipAddress: String,
      port: Int,
      connectTimeout: Duration = 10.seconds,
      readWriteTimeout: Duration = 5.seconds,
  ): Either<TcpError, TcpPrinterConnection>
}

// @ExperimentalEscPosApi //
// public fun TcpPrinterManager(): TcpPrinterManager = TcpPrinterManagerImpl()

@OptIn(ExperimentalEscPosApi::class)
@Suppress("UnusedPrivateClass")
private class TcpPrinterManagerImpl : TcpPrinterManager {
  override suspend fun openConnection(
      name: String,
      ipAddress: String,
      port: Int,
      connectTimeout: Duration,
      readWriteTimeout: Duration,
  ): Either<TcpError, TcpPrinterConnection> {
    TODO()
    //    return withContext(Dispatchers.Default) {
    //      var socket: Socket? = null
    //      try {
    //        withTimeout(connectTimeout) {
    //          socket = aSocket(SelectorManager(Dispatchers.Default)).tcp().connect(ipAddress,
    // port)
    //        }
    //        currentCoroutineContext().ensureActive()
    //        return@withContext TcpPrinterConnection(socket!!.connection(), name, ipAddress,
    // port).right()
    //      } catch (cause: Throwable) {
    //        socket?.close()
    //
    //        when (cause) {
    //          is TimeoutCancellationException -> {
    //            return@withContext TcpError.ConnectionTimeout.left()
    //          }
    //          is CancellationException -> {
    //            throw cause
    //          }
    //          else -> {
    //            return@withContext TcpError.Unknown(cause).left()
    //          }
    //        }
    //      }
    //    }
  }
}
