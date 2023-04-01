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

package cz.multiplatform.escpos4k.usb

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import kotlin.experimental.and
import kotlin.jvm.JvmName

public abstract class UsbDeviceConnection(public val device: UsbDevice) {
  public var isOpen: Boolean = true
    private set

  public fun claimInterface(iface: UsbInterface, force: Boolean): Either<UsbConnectionError, Unit> =
      withStateCheck {
        claimInterfaceInternal(iface, force)
      }

  protected abstract fun claimInterfaceInternal(
      iface: UsbInterface,
      force: Boolean
  ): Either<UsbConnectionError, Unit>

  public fun releaseInterface(iface: UsbInterface): Either<UsbConnectionError, Unit> =
      withStateCheck {
        releaseInterfaceInternal(iface)
      }

  protected abstract fun releaseInterfaceInternal(
      iface: UsbInterface
  ): Either<UsbConnectionError, Unit>

  /**
   * Perform a Control Transfer request.
   *
   * @param setupPacket The Control Transfer Setup Packet. Some predefined packets can be found in
   * [StandardControlRequests].
   *
   * @param dataBuffer Depending on the request direction, this is either the outgoing data provided
   * by the caller, OR the empty buffer where incoming response data will be stored.
   */
  public abstract suspend fun controlTransfer(setupPacket: SetupPacket, dataBuffer: ByteArray): Int

  public abstract suspend fun bulkTransfer(endpoint: UsbEndpoint, buffer: ByteArray)

  //  suspend fun interruptTransfer()
  //  suspend fun isochronousTransfer()

  /** Close the device connection. Once the connection is closed, it is no longer usable. */
  public fun close() {
    isOpen = false
    closeInternal()
  }

  /** Close the native connection and release platform resources. */
  protected abstract fun closeInternal()

  @JvmName("withStateCheckEither")
  private inline fun <B> withStateCheck(
      block: () -> Either<UsbConnectionError, B>
  ): Either<UsbConnectionError, B> {
    checkState()?.let {
      return it.left()
    }

    return block()
  }

  private inline fun <B> withStateCheck(block: () -> B): Either<UsbConnectionError, B> =
      withStateCheck {
        block().right()
      }

  private fun checkState(): UsbConnectionError? {
    if (!isOpen) return UsbConnectionError.ConnectionClosed

    return null
  }
}

internal class DeviceStatus(
    /**
     * If true, the device can wake the host up from during suspend.
     *
     * This feature can be toggled with the Clear/SetFeature Control Transfer requests.
     */
    val remoteWakeupEnabled: Boolean,

    /**
     * If `true`, the device is powered externally.
     *
     * If `false`, the device is powered by the bus.
     */
    val isSelfPowered: Boolean
)

/** Access the device status, returns `null` if an error occurred. */
internal suspend fun UsbDeviceConnection.getDeviceStatus(): DeviceStatus? {
  val buffer = ByteArray(2)

  val bytesTransferred = controlTransfer(StandardControlRequests.Device.getStatus(), buffer)

  if (bytesTransferred != 2) return null

  // TODO are we bit mapping the correct byte? USB is little endian, the low byte might actually be
  //  the first byte.
  return DeviceStatus((buffer[1] and 0b10) == 0b10.toByte(), (buffer[1] and 0b01) == 0b01.toByte())
}
