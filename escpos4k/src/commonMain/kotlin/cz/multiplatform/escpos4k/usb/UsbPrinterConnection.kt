// SPDX-License-Identifier: Apache-2.0

package cz.multiplatform.escpos4k.usb

import arrow.core.identity
import cz.multiplatform.escpos4k.core.PrintError
import cz.multiplatform.escpos4k.core.PrinterConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

public class UsbPrinterConnection(private val deviceConnection: UsbDeviceConnection) : PrinterConnection {
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
                  ::identity,
              )
      val iface =
          deviceConnection.device.interfaces.firstOrNull { it.bInterfaceNumber == outEndpoint.ifaceNumber }
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
