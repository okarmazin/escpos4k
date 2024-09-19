/*
 *    Copyright 2024 escpos4k authors
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

package cz.multiplatform.escpos4k.usb

import arrow.core.identity
import cz.multiplatform.escpos4k.core.PrintError
import cz.multiplatform.escpos4k.core.PrinterConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

public class UsbPrinterConnection(
    private val deviceConnection: UsbDeviceConnection,
) : PrinterConnection {
  override val name: String = with(deviceConnection.device) { productName ?: osAssignedName }

  public override val isOpen: Boolean
    get() = deviceConnection.isOpen

  public fun close(): Unit = deviceConnection.close()

  override suspend fun printRaw(bytes: ByteArray): PrintError? {
    return withContext(Dispatchers.Default) {
      if (!isOpen) {
        return@withContext PrintError.NotConnected
      }
      /*
       1. claim interface
       2. bulk transfer
       3. release interface
      */

      val outEndpoint =
          deviceConnection.device
              .findOutputEndpoint()
              .fold(
                  {
                    return@withContext PrintError.NotAPrinter
                  },
                  ::identity)
      val iface =
          deviceConnection.device.interfaces.firstOrNull {
            it.bInterfaceNumber == outEndpoint.ifaceNumber
          }
              ?: return@withContext PrintError.NotAPrinter

      deviceConnection.claimInterface(iface, true).fold(::mapConnectionError) {
        try {
          deviceConnection.bulkTransfer(outEndpoint, bytes)
          null
        } finally {
          deviceConnection.releaseInterface(iface)
        }
      }
    }
  }

  private fun mapConnectionError(connError: UsbConnectionError): PrintError =
      when (connError) {
        UsbConnectionError.ConnectionClosed -> PrintError.NotConnected
      }
}
