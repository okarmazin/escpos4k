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

internal object StandardControlRequests {
  object Device {
    private const val getStatus: UByte = 0u
    private const val clearFeature: UByte = 1u
    private const val setFeature: UByte = 3u
    private const val setAddress: UByte = 5u
    private const val getDescriptor: UByte = 6u
    private const val setDescriptor: UByte = 7u
    private const val getConfiguration: UByte = 8u
    private const val setConfiguration: UByte = 9u

    fun getStatus(): SetupPacket =
        SetupPacket(
            requestType = maskDirIn or maskTypeStandard or maskRecipientDevice,
            request = getStatus,
            argValue = 0u,
            argIndex = 0u,
            dataLength = 2u) // Returns the 2-byte Device Status

    fun clearFeature(feature: UShort): SetupPacket =
        SetupPacket(
            requestType = maskDirOut or maskTypeStandard or maskRecipientDevice,
            request = clearFeature,
            argValue = feature,
            argIndex = 0u,
            dataLength = 0u)

    fun setFeature(feature: UShort): SetupPacket =
        SetupPacket(
            requestType = maskDirOut or maskTypeStandard or maskRecipientDevice,
            request = setFeature,
            argValue = feature,
            argIndex = 0u,
            dataLength = 0u)

    /** The address is coerced into the valid range `0..127` */
    fun setAddress(address: UByte): SetupPacket {
      val sanitized = address.coerceAtMost(127u)
      return SetupPacket(
          requestType = maskDirOut or maskTypeStandard or maskRecipientDevice,
          request = setAddress,
          argValue = sanitized.toUShort(),
          argIndex = 0u,
          dataLength = 0u)
    }

    // TODO Implement the descriptor and configuration requests
  }

  object Interface {
    // TODO
  }

  object Endpoint {
    // TODO
  }
}

internal fun SetupPacket.isDirIn(): Boolean = requestType and maskDirIn == maskDirIn

private const val maskDirIn: UByte = 0b1_00_00000u
private const val maskDirOut: UByte = 0b0_00_00000u

private const val maskTypeStandard: UByte = 0b0_00_00000u
private const val maskTypeClass: UByte = 0b0_01_00000u
private const val maskTypeVendor: UByte = 0b0_10_00000u

private const val maskRecipientDevice: UByte = 0b0_00_00000u
private const val maskRecipientInterface: UByte = 0b0_00_00001u
private const val maskRecipientEndpoint: UByte = 0b0_00_00010u
private const val maskRecipientOther: UByte = 0b0_00_00011u
