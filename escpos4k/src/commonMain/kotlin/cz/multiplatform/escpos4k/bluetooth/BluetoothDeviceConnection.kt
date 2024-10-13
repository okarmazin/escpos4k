// SPDX-License-Identifier: Apache-2.0

package cz.multiplatform.escpos4k.bluetooth

public abstract class BluetoothDeviceConnection(public val device: BluetoothDevice) {
  public var isOpen: Boolean = true
    private set

  public fun close() {
    isOpen = false
    closeInternal()
  }

  protected abstract fun closeInternal()

  public abstract suspend fun write(data: ByteArray): BluetoothError?
}
