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

package cz.multiplatform.escpos4k.core

import cnames.structs.__CFString
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.CoreFoundation.CFRelease
import platform.CoreFoundation.CFStringConvertEncodingToNSStringEncoding
import platform.CoreFoundation.CFStringConvertIANACharSetNameToEncoding
import platform.CoreFoundation.CFStringRef
import platform.CoreFoundation.kCFStringEncodingInvalidId
import platform.Foundation.CFBridgingRetain
import platform.Foundation.NSData
import platform.Foundation.NSString
import platform.Foundation.dataUsingEncoding
import platform.posix.memcpy

@OptIn(ExperimentalForeignApi::class)
internal actual fun encode(text: String, charset: Charset): ByteArray {
  var cfCharsetName: CPointer<__CFString>? = null
  try {
    // (1) Cast a NSString as CFStringRef for the CF lookup function.
    //     The BridgingRetain function will transfer the ownership to us,
    //     making us responsible for freeing the memory.
    cfCharsetName = CFBridgingRetain(charset.ianaName as NSString) as CFStringRef?

    // (2) Using the prepared CFStringRef, find the CFStringEncoding.
    val cfEncoding = CFStringConvertIANACharSetNameToEncoding(cfCharsetName)
    if (cfEncoding == kCFStringEncodingInvalidId) {
      throw RuntimeException("invalid encoding: ${charset.ianaName}")
    }

    // (3) Using the CF encoding, find the corresponding NSStringEncoding
    val nsEncoding = CFStringConvertEncodingToNSStringEncoding(cfEncoding)

    // (4) Encode the Kotlin String using the NS Encoding. The resulting NSData is
    //     lives in the ARC-enabled runtime and since it does not escape the current
    //     scope, will be collected when scope ends.
    val encodedData: NSData =
        (text as NSString).dataUsingEncoding(nsEncoding, allowLossyConversion = true)
            ?: throw RuntimeException("encoder failed")
    val encodedBytes = encodedData.bytes ?: throw RuntimeException("encoder failed")

    // (5) Create a Kotlin-land ByteArray and copy over the encoded bytes
    // from the ObjC-land NSData.
    //
    // https://github.com/JetBrains/kotlin-native/issues/3172
    return ByteArray(encodedData.length.toInt()).apply {
      usePinned { pinnedByteArray ->
        memcpy(pinnedByteArray.addressOf(0), encodedBytes, encodedData.length)
      }
    }
  } finally {
    // Don't forget to free the unmanaged CF pointer!
    CFRelease(cfCharsetName)
  }
}
