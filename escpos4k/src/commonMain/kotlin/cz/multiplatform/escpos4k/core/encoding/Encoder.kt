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

package cz.multiplatform.escpos4k.core.encoding

import cz.multiplatform.escpos4k.core.encoding.charset.Charset

private const val REPLACEMENT_CHAR = '?'
private const val REPLACEMENT_BYTE = REPLACEMENT_CHAR.code.toByte()

internal fun String.encode(charset: Charset): ByteArray {
  val output = ByteArray(length)

  for ((index, char) in this.withIndex()) {
    @Suppress("MagicNumber")
    val byte: Byte =
        when {
          // 0..31 are control characters, always replaced
          char.code < 32 -> {
            log("Replacing control character '${char.code}' at index $index with '$REPLACEMENT_CHAR'.")
            REPLACEMENT_BYTE
          }

          // Printable ASCII up to 127
          char.code < 128 -> {
            char.code.toByte()
          }

          char == '\uFFFD' -> REPLACEMENT_BYTE

          // Page-specific characters
          else -> {
            val byte = charset.mapping[char]?.toByte()
            if (byte == null) {
              val msg =
                  "[${charset.ianaName}] Replacing unmappable code unit '${char.code}' " +
                      "at index $index with '$REPLACEMENT_CHAR'"
              log(msg)
            }
            byte ?: REPLACEMENT_BYTE
          }
        }

    output[index] = byte
  }

  return output
}

private fun log(msg: String) {
  val debug = false
  if (debug) {
    println(msg)
  }
}
