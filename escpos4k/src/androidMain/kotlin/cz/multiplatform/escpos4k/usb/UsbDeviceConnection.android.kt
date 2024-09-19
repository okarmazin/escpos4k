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

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class AndroidUsbDeviceConnection(
    private val platformDevice: android.hardware.usb.UsbDevice,
    platformConnection: android.hardware.usb.UsbDeviceConnection
) : UsbDeviceConnection(toCommonDevice(platformDevice)) {
  private var platformConnection: android.hardware.usb.UsbDeviceConnection? = platformConnection

  override fun claimInterfaceInternal(
      iface: UsbInterface,
      force: Boolean
  ): Either<UsbConnectionError, Unit> {
    val isClaimed =
        platformConnection?.claimInterface(platformDevice.findPlatformInterface(iface), force)
    return if (isClaimed == true) {
      Unit.right()
    } else {
      UsbConnectionError.ConnectionClosed.left()
    }
  }

  override fun releaseInterfaceInternal(iface: UsbInterface): Either<UsbConnectionError, Unit> {
    val isReleased =
        platformConnection?.releaseInterface(platformDevice.findPlatformInterface(iface))
    return if (isReleased == true) {
      Unit.right()
    } else {
      UsbConnectionError.ConnectionClosed.left()
    }
  }

  override suspend fun controlTransfer(setupPacket: SetupPacket, dataBuffer: ByteArray): Int {
    val platformConnection = platformConnection ?: return 0
    val bytesTransferred =
        platformConnection.controlTransfer(
            /* requestType = */ setupPacket.requestType.toInt(),
            /* request = */ setupPacket.request.toInt(),
            /* value = */ setupPacket.argValue.toInt(),
            /* index = */ setupPacket.argIndex.toInt(),
            /* buffer = */ dataBuffer,
            /* length = */ setupPacket.dataLength.toInt(),
            /* timeout = */ 80,
        )

    return bytesTransferred
  }

  override suspend fun bulkTransfer(endpoint: UsbEndpoint, buffer: ByteArray) {
    withContext(Dispatchers.Default) {
      val platformEp = platformDevice.findPlatformEndpoint(endpoint)
      // Earlier Android versions had a bulk transfer limit
      val chunkSize = minOf(16384, platformEp.maxPacketSize)
      val chunks: List<ByteArray> =
          buffer.asSequence().chunked(chunkSize).map { it.toByteArray() }.toList()
      for (chunk in chunks) {
        platformConnection?.bulkTransfer(platformEp, chunk, chunk.size, 100)
      }
    }
  }

  override fun closeInternal() {
    platformConnection?.close()
    platformConnection = null
  }
}
