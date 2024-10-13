// SPDX-License-Identifier: Apache-2.0

@file:Suppress("MemberVisibilityCanBePrivate")

package cz.multiplatform.escpos4k.bluetooth

/** Errors encountered when accessing Bluetooth. */
public sealed interface BluetoothError {

  /**
   * The current device has no Bluetooth capability.
   *
   * Android note: This happens when `getSystemService(BLUETOOTH) == null`.
   */
  public data object BluetoothNotAvailable : BluetoothError

  /**
   * The current device has Bluetooth capabilities but Bluetooth is turned off.
   *
   * Android note: This happens when `BluetoothAdapter.state != STATE_ON`
   */
  public data object BluetoothOff : BluetoothError

  /**
   * The operating system denied access to Bluetooth. The application should request this permission from the user.
   *
   * Android note: Likely the application is running on API 31+ and the `BLUETOOTH_CONNECT` permission was not granted.
   * On lower API levels the access should be granted automatically by the OS.
   */
  public data object AccessDenied : BluetoothError

  /**
   * The device could not be found by the operating system. It may have been disconnected after initial device lookup.
   */
  public class DeviceNotFound(public val searched: BluetoothDevice) : BluetoothError {
    override fun toString(): String {
      return "DeviceNotFound(searched=$searched)"
    }
  }

  /**
   * An unknown error occurred, [cause] likely contains the underlying exception, although it may also be `null`.
   *
   * These errors should be relatively uncommon as they are usually produced by internal unhandled error safety nets.
   *
   * If you find this error to be produced regularly, or with a cause that looks like it should have been mapped to a
   * known error, please consider reporting the situation to the issue tracker.
   */
  public class Unknown(public val cause: Throwable?) : BluetoothError {
    override fun toString(): String {
      return "Unknown(cause=$cause)"
    }
  }
}
