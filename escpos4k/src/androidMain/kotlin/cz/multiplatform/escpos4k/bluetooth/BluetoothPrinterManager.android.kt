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

import android.annotation.SuppressLint
import android.bluetooth.BluetoothManager
import android.content.Context
import java.io.IOException
import java.util.UUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

public fun BluetoothPrinterManager(context: Context): BluetoothPrinterManager =
    AndroidBluetoothPrinterManager(context)

private class AndroidBluetoothPrinterManager(context: Context) : AbstractBluetoothPrinterManager() {
  private val context: Context = context.applicationContext

  @SuppressLint("MissingPermission")
  override fun allPairedDevices(): List<BluetoothDevice> {
    return context.bluetoothManager().adapter.bondedDevices.map { platformDevice ->
      BluetoothDevice(
          platformDevice.address,
          platformDevice.name,
          platformDevice.type.toBtType(),
          platformDevice.uuids.orEmpty().map { it.uuid.toString() },
          platformDevice.bluetoothClass.majorDeviceClass,
          platformDevice.bluetoothClass.deviceClass)
    }
  }

  @SuppressLint("MissingPermission")
  override suspend fun openDeviceConnection(printer: BluetoothDevice): BluetoothDeviceConnection? {
    return withContext(Dispatchers.IO) {
      try {
        val serviceUUID = printer.uuids.firstOrNull()?.let(UUID::fromString) ?: UUID.randomUUID()
        val socket =
            context.findPlatformDevice(printer)?.createRfcommSocketToServiceRecord(serviceUUID)
                ?: return@withContext null

        socket.connect()

        AndroidBluetoothDeviceConnection(socket, printer)
      } catch (e: IOException) {
        null
      }
    }
  }
}

private fun Int.toBtType(): BluetoothType =
    when (this) {
      android.bluetooth.BluetoothDevice.DEVICE_TYPE_CLASSIC -> BluetoothType.Classic
      android.bluetooth.BluetoothDevice.DEVICE_TYPE_LE -> BluetoothType.LowEnergy
      android.bluetooth.BluetoothDevice.DEVICE_TYPE_DUAL -> BluetoothType.Dual
      android.bluetooth.BluetoothDevice.DEVICE_TYPE_UNKNOWN -> BluetoothType.Unknown
      else -> BluetoothType.Unknown
    }

internal fun Context.bluetoothManager(): BluetoothManager =
    getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager

@SuppressLint("MissingPermission")
internal fun Context.findPlatformDevice(
    device: BluetoothDevice
): android.bluetooth.BluetoothDevice? {
  return bluetoothManager().adapter.bondedDevices.firstOrNull { it.address == device.address }
}
