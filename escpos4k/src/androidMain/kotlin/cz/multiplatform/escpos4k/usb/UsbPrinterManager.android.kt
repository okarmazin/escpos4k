// SPDX-License-Identifier: Apache-2.0

package cz.multiplatform.escpos4k.usb

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbManager
import kotlin.coroutines.resume
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.suspendCancellableCoroutine

private const val ACTION_USB_PERMISSION = "cz.multiplatform.escpos4k.USB_PERMISSION"

public fun UsbPrinterManager(context: Context): UsbPrinterManager = AndroidUsbPrinterManager(context)

internal class AndroidUsbPrinterManager(context: Context) : AbstractUsbPrinterManager() {
  private val context = context.applicationContext

  /** Check whether we are allowed to establish a connection to this device. */
  override fun hasPermission(printer: UsbDevice): Boolean {
    val device = context.platformDevice(printer) ?: return false
    return context.usbManager().hasPermission(device)
  }

  /**
   * Request access permission from the system. What happens is specific to the OS.
   *
   * For example on Android, the system will display a dialog to the user.
   *
   * @return Whether the permission has been granted by the user
   */
  override suspend fun awaitPermission(printer: UsbDevice): Boolean {
    if (hasPermission(printer)) {
      return true
    }

    return suspendCancellableCoroutine { cont ->
      val permissionReceiver =
          object : BroadcastReceiver() {

            override fun onReceive(context: Context, intent: Intent) {
              context.unregisterReceiver(this)
              if (!cont.isActive) {
                throw CancellationException()
              }

              if (ACTION_USB_PERMISSION == intent.action) {
                val result = intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)
                cont.resume(result)
              }
            }
          }

      context.registerReceiver(permissionReceiver, IntentFilter(ACTION_USB_PERMISSION))

      val permissionIntent =
          PendingIntent.getBroadcast(context, 0, Intent(ACTION_USB_PERMISSION), PendingIntent.FLAG_IMMUTABLE)

      context.usbManager().requestPermission(context.platformDevice(printer), permissionIntent)
    }
  }

  override fun allVisibleDevices(): List<UsbDevice> {
    return context.usbManager().deviceList.values.map(::toCommonDevice)
  }

  override suspend fun openDeviceConnection(printer: UsbDevice): UsbDeviceConnection? {
    val platformDevice = context.platformDevice(printer) ?: return null

    return if (awaitPermission(printer)) {
      context.usbManager().openDevice(platformDevice)?.let { conn -> AndroidUsbDeviceConnection(platformDevice, conn) }
    } else {
      null
    }
  }
}
