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

package cz.multiplatform.escpos4k.bluetooth

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice as PlatformBluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import arrow.core.Either
import arrow.core.continuations.either
import arrow.core.identity
import arrow.core.left
import arrow.core.right
import java.io.IOException
import java.util.UUID
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext

public fun BluetoothPrinterManager(context: Context): BluetoothPrinterManager =
    AndroidBluetoothPrinterManager(context)

private class AndroidBluetoothPrinterManager(context: Context) : BluetoothPrinterManager {
  private val context: Context = context.applicationContext

  @SuppressLint("MissingPermission")
  override suspend fun openConnection(
      printer: BluetoothDevice
  ): Either<BluetoothError, BluetoothPrinterConnection> {
    return runCatching {
          withContext(Dispatchers.IO) {
            either {
              val device =
                  context.allPlatformDevices().bind().firstOrNull { it.address == printer.address }
                      ?: shift<Nothing>(BluetoothError.DeviceNotFound(printer))
              val serviceUUID =
                  printer.uuids.firstOrNull()?.let(UUID::fromString) ?: UUID.randomUUID()
              val socket = device.createRfcommSocketToServiceRecord(serviceUUID)

              currentCoroutineContext().ensureActive()
              socket.connect()
              val context = currentCoroutineContext()
              if (!context.isActive) {
                // We were cancelled while opening the connection. Clean up the open connection and
                // bail out!
                try {
                  socket.close()
                } catch (e: IOException) {
                  // Close failure ignored.
                }

                // We use ensureActive instead of throwing CancellationException ourselves since
                // ensureActive provides a better error message.
                context.ensureActive()
              }

              BluetoothPrinterConnection(AndroidBluetoothDeviceConnection(socket, printer))
            }
          }
        }
        .fold(::identity) { cause ->
          when (cause) {
            is CancellationException -> throw cause
            is IOException -> {
              // IOException thrown from createSocket() or connect(). There are multiple reasons
              // for this failure ranging from connection errors to missing permissions.
              // FIXME: Investigate the possible contents of the IOException to provide a more
              //        specific error.
              BluetoothError.Unknown(cause).left()
            }
            else -> {
              BluetoothError.Unknown(cause).left()
            }
          }
        }
  }

  override fun pairedPrinters(): Either<BluetoothError, List<BluetoothDevice>> =
      allPairedDevices().map { allDevices ->
        allDevices.filter { device ->
          val classImaging = 1536
          val classPrinter = 1664
          device.majorDeviceClass == classImaging &&
              (device.deviceClass == classPrinter || device.deviceClass == classImaging)
        }
      }

  @SuppressLint("MissingPermission")
  private fun allPairedDevices(): Either<BluetoothError, List<BluetoothDevice>> {
    return runCatching {
          either.eager {
            val platformDevices = context.allPlatformDevices().bind()
            platformDevices.map { platformDevice ->
              BluetoothDevice(
                  platformDevice.address,
                  platformDevice.name,
                  platformDevice.type.toBtType(),
                  platformDevice.uuids.orEmpty().map { it.uuid.toString() },
                  platformDevice.bluetoothClass.majorDeviceClass,
                  platformDevice.bluetoothClass.deviceClass)
            }
          }
        }
        .fold(::identity, { BluetoothError.Unknown(it).left() })
  }
}

private fun Int.toBtType(): BluetoothType =
    when (this) {
      PlatformBluetoothDevice.DEVICE_TYPE_CLASSIC -> BluetoothType.Classic
      PlatformBluetoothDevice.DEVICE_TYPE_LE -> BluetoothType.LowEnergy
      PlatformBluetoothDevice.DEVICE_TYPE_DUAL -> BluetoothType.Dual
      PlatformBluetoothDevice.DEVICE_TYPE_UNKNOWN -> BluetoothType.Unknown
      else -> BluetoothType.Unknown
    }

private fun Context.bluetoothManager(): Either<BluetoothError, BluetoothManager> {
  val manager = getSystemService(Context.BLUETOOTH_SERVICE) as? BluetoothManager?
  return manager?.right() ?: BluetoothError.BluetoothNotAvailable.left()
}

@SuppressLint("MissingPermission")
private fun Context.allPlatformDevices(): Either<BluetoothError, List<PlatformBluetoothDevice>> {
  return runCatching {
        either.eager {
          val manager = bluetoothManager().bind()
          val adapter = manager.adapter

          if (adapter.state != BluetoothAdapter.STATE_ON) {
            shift<Nothing>(BluetoothError.BluetoothOff)
          }

          if (!hasBondedDevicesPermission()) {
            shift<Nothing>(BluetoothError.AccessDenied)
          }

          adapter.bondedDevices?.toList().orEmpty()
        }
      }
      .fold(::identity, { BluetoothError.Unknown(it).left() })
}

private fun Context.hasBondedDevicesPermission(): Boolean {
  val permission =
      if (Build.VERSION.SDK_INT >= 31) {
        Manifest.permission.BLUETOOTH_CONNECT
      } else {
        Manifest.permission.BLUETOOTH
      }
  return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
}
