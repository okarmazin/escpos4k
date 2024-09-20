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

package cz.multiplatform.escpos4k.core

import cz.multiplatform.escpos4k.core.encoding.charset.Charset
import cz.multiplatform.escpos4k.core.encoding.encode

internal sealed class Command {
  abstract fun bytes(): ByteArray

  class Text(private val text: String, charset: Charset) : Command() {
    private val encodedBytes: ByteArray by lazy { text.encode(charset) }

    override fun bytes(): ByteArray = encodedBytes.copyOf()

    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (other == null || this::class != other::class) return false

      other as Text

      if (!encodedBytes.contentEquals(other.encodedBytes)) return false

      return true
    }

    override fun hashCode(): Int {
      return encodedBytes.contentHashCode()
    }

    override fun toString(): String {
      return "Text(text='$text', encodedBytes=${encodedBytes.contentToString()})"
    }
  }

  /**
   * Clears the data in the print buffer and resets the printer modes to the modes that were in
   * effect when the power was turned on.
   *
   * Notes:
   *
   * Print buffer is cleared Data buffer contents are preserved NV graphics (NV bit image)
   * information is maintained. User NV memory data is maintained.
   */
  data object Initialize : Command() {
    override fun bytes(): ByteArray = byteArrayOf(27, 64)
  }

  class Underline(val enabled: Boolean) : Command() {
    private val content = byteArrayOf(27, 45, if (enabled) 1 else 0)

    override fun bytes(): ByteArray = content.copyOf()

    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (other == null || this::class != other::class) return false

      other as Underline

      if (!content.contentEquals(other.content)) return false

      return true
    }

    override fun hashCode(): Int {
      return content.contentHashCode()
    }

    override fun toString(): String {
      return "Underline(enabled=$enabled, content=${content.contentToString()})"
    }
  }

  class Italics(val enabled: Boolean) : Command() {
    private val content = byteArrayOf(27, 52, if (enabled) 1 else 0)

    override fun bytes(): ByteArray = content.copyOf()

    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (other == null || this::class != other::class) return false

      other as Italics

      if (!content.contentEquals(other.content)) return false

      return true
    }

    override fun hashCode(): Int {
      return content.contentHashCode()
    }

    override fun toString(): String {
      return "Italics(enabled=$enabled, content=${content.contentToString()})"
    }
  }

  class Bold(val enabled: Boolean) : Command() {
    private val content = byteArrayOf(27, 69, if (enabled) 1 else 0)

    override fun bytes(): ByteArray = content.copyOf()

    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (other == null || this::class != other::class) return false

      other as Bold

      if (!content.contentEquals(other.content)) return false

      return true
    }

    override fun hashCode(): Int {
      return content.contentHashCode()
    }

    override fun toString(): String {
      return "Bold(enabled=$enabled, content=${content.contentToString()})"
    }
  }

  class SelectCharset(val charset: Charset) : Command() {
    private val content = byteArrayOf(27, 116, charset.escposPageNumber)

    override fun bytes(): ByteArray = content.copyOf()

    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (other == null || this::class != other::class) return false

      other as SelectCharset

      if (!content.contentEquals(other.content)) return false

      return true
    }

    override fun hashCode(): Int {
      return content.contentHashCode()
    }

    override fun toString(): String {
      return "SelectCharset(charset=$charset, content=${content.contentToString()})"
    }
  }

  class Justify(private val alignment: TextAlignment) : Command() {
    private val content = byteArrayOf(27, 97, alignment.value)

    override fun bytes(): ByteArray = content.copyOf()

    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (other == null || this::class != other::class) return false

      other as Justify

      if (!content.contentEquals(other.content)) return false

      return true
    }

    override fun hashCode(): Int {
      return content.contentHashCode()
    }

    override fun toString(): String {
      return "Justify(alignment=$alignment, content=${content.contentToString()})"
    }
  }

  class TextSize(val width: Int, val height: Int) : Command() {
    private val content = byteArrayOf(29, 33, constructSize(width, height))

    override fun bytes(): ByteArray = content.copyOf()

    private fun constructSize(width: Int, height: Int): Byte {
      val w = (width.coerceIn(1, 8) - 1) shl 4
      val h = height.coerceIn(1, 8) - 1

      return (w + h).toByte()
    }

    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (other == null || this::class != other::class) return false

      other as TextSize

      if (!content.contentEquals(other.content)) return false

      return true
    }

    override fun hashCode(): Int {
      return content.contentHashCode()
    }

    override fun toString(): String {
      return "TextSize(width=$width, height=$height, content=${content.contentToString()})"
    }
  }

  data object Cut : Command() {
    override fun bytes(): ByteArray = byteArrayOf(29, 86, 1)
  }

  class QRCode(content: String, errorCorrection: QRCorrectionLevel) : Command() {
    private val content: ByteArray

    init {
      @Suppress("JoinDeclarationAndAssignment") //
      var data: ByteArray

      // 1. Select the EC level
      data = byteArrayOf(29, 40, 107, 3, 0, 49, 69, errorCorrection.level)

      // 2. Send the content to the printer. This does not initiate the print process, it merely
      //    stores the data in the printer's symbol buffer.
      val contentBytes = content.encodeToByteArray()
      val base = contentBytes.size + 3
      val (pL, pH) = base.toByte() to (base shr 8).toByte()
      data += byteArrayOf(29, 40, 107, pL, pH, 49, 80, 48, *contentBytes)

      // 3. Send the print command. This will print the data from step 2.
      data += byteArrayOf(29, 40, 107, 3, 0, 49, 81, 48)
      this.content = data
    }

    override fun bytes(): ByteArray = content.copyOf()

    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (other == null || this::class != other::class) return false

      other as QRCode

      if (!content.contentEquals(other.content)) return false

      return true
    }

    override fun hashCode(): Int {
      return content.contentHashCode()
    }

    override fun toString(): String {
      return "QRCode(content=${content.contentToString()})"
    }
  }

  class AztecCode(content: String, errorCorrection: Int) : Command() {
    private val content: ByteArray

    init {
      @Suppress("JoinDeclarationAndAssignment") //
      var data: ByteArray

      // 1. Set the EC level
      data = byteArrayOf(29, 40, 107, 3, 0, 53, 69, errorCorrection.toByte())

      // 2. Send the content to the printer. This does not initiate the print process, it merely
      //    stores the data in the printer's symbol buffer.
      val contentBytes = content.encodeToByteArray()
      val base = contentBytes.size + 3
      val (pL, pH) = base.toByte() to (base shr 8).toByte()
      data += byteArrayOf(29, 40, 107, pL, pH, 53, 80, 48, *contentBytes)

      // 3. Send the print command. This will print the data from step 2.
      data += byteArrayOf(29, 40, 107, 3, 0, 53, 81, 48)
      this.content = data
    }

    override fun bytes(): ByteArray = content.copyOf()

    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (other == null || this::class != other::class) return false

      other as AztecCode

      if (!content.contentEquals(other.content)) return false

      return true
    }

    override fun hashCode(): Int {
      return content.contentHashCode()
    }

    override fun toString(): String {
      return "AztecCode(content=${content.contentToString()})"
    }
  }

  class DataMatrix(content: String) : Command() {
    private val content: ByteArray

    init {
      var data: ByteArray

      // Note: Unlike other codes, DataMatrix sets its error correction on its own.

      // 1. Send the content to the printer. This does not initiate the print process, it merely
      //    stores the data in the printer's symbol buffer.
      val contentBytes = content.encodeToByteArray()
      val base = contentBytes.size + 3
      val (pL, pH) = base.toByte() to (base shr 8).toByte()
      data = byteArrayOf(29, 40, 107, pL, pH, 54, 80, 48, *contentBytes)

      // 2. Send the print command. This will print the data from step 1.
      data += byteArrayOf(29, 40, 107, 3, 0, 54, 81, 48)
      this.content = data
    }

    override fun bytes(): ByteArray = content.copyOf()

    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (other == null || this::class != other::class) return false

      other as DataMatrix

      if (!content.contentEquals(other.content)) return false

      return true
    }

    override fun hashCode(): Int {
      return content.contentHashCode()
    }

    override fun toString(): String {
      return "DataMatrix(content=${content.contentToString()})"
    }
  }

  class UPCA(content: String, hri: HriPosition) : Command() {
    private val content: ByteArray

    init {
      @Suppress("JoinDeclarationAndAssignment") //
      var data: ByteArray

      // 1. Set the HRI position and select the HRI font.
      //    The last 0 is "Font A". We need to specify this because there is no default value for
      //    the font. If we just set the position, there would be a space for the HRI, but it would
      //    be blank
      data = byteArrayOf(29, 72, hri.position, 29, 102, 0)

      // 2. Print the barcode
      val d = content.map { it.digitToInt().toByte() }.toByteArray()
      data += byteArrayOf(29, 107, 65, 12, *d)

      this.content = data
    }

    override fun bytes(): ByteArray = content.copyOf()

    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (other == null || this::class != other::class) return false

      other as UPCA

      if (!content.contentEquals(other.content)) return false

      return true
    }

    override fun hashCode(): Int {
      return content.contentHashCode()
    }

    override fun toString(): String {
      return "UPCA(content=${content.contentToString()})"
    }
  }

  class EAN13(content: String, hri: HriPosition) : Command() {
    private val content: ByteArray

    init {
      @Suppress("JoinDeclarationAndAssignment") //
      var data: ByteArray

      // 1. Set the HRI position and select the HRI font.
      //    The last 0 is "Font A". We need to specify this because there is no default value for
      //    the font. If we just set the position, there would be a space for the HRI, but it would
      //    be blank
      data = byteArrayOf(29, 72, hri.position, 29, 102, 0)

      // 2. Print the barcode
      val d = content.map { it.digitToInt().toByte() }.toByteArray()
      data += byteArrayOf(29, 107, 67, 13, *d)

      this.content = data
    }

    override fun bytes(): ByteArray = content.copyOf()

    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (other == null || this::class != other::class) return false

      other as EAN13

      if (!content.contentEquals(other.content)) return false

      return true
    }

    override fun hashCode(): Int {
      return content.contentHashCode()
    }

    override fun toString(): String {
      return "EAN13(content=${content.contentToString()})"
    }
  }

  class EAN8(content: String, hri: HriPosition) : Command() {
    private val content: ByteArray

    init {
      @Suppress("JoinDeclarationAndAssignment") //
      var data: ByteArray

      // 1. Set the HRI position and select the HRI font.
      //    The last 0 is "Font A". We need to specify this because there is no default value for
      //    the font. If we just set the position, there would be a space for the HRI, but it would
      //    be blank
      data = byteArrayOf(29, 72, hri.position, 29, 102, 0)

      // 2. Print the barcode
      val d = content.map { it.digitToInt().toByte() }.toByteArray()
      data += byteArrayOf(29, 107, 68, 8, *d)

      this.content = data
    }

    override fun bytes(): ByteArray = content.copyOf()

    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (other == null || this::class != other::class) return false

      other as EAN8

      if (!content.contentEquals(other.content)) return false

      return true
    }

    override fun hashCode(): Int {
      return content.contentHashCode()
    }

    override fun toString(): String {
      return "EAN8(content=${content.contentToString()})"
    }
  }
}

public enum class TextAlignment(internal val value: Byte) {
  LEFT(0),
  CENTER(1),
  RIGHT(2),
}
