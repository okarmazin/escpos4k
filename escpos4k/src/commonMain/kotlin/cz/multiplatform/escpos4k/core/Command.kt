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

package cz.multiplatform.escpos4k.core

internal sealed class Command {
  abstract fun bytes(): Sequence<Byte>

  class Text(private val text: String, charset: Charset) : Command() {
    private val encodedBytes: ByteArray by lazy { encode(text, charset) }

    override fun bytes(): Sequence<Byte> = encodedBytes.asSequence()
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
  object Initialize : Command() {
    override fun bytes(): Sequence<Byte> = sequenceOf(27, 64)
  }

  /**
   * Print and line feed.
   *
   * Prints the data in the print buffer and feeds one line, based on the current line spacing.
   */
  object LF : Command() {
    override fun bytes(): Sequence<Byte> = sequenceOf(10)
  }

  class Underline(val enabled: Boolean) : Command() {
    override fun bytes(): Sequence<Byte> = sequenceOf(27, 45, if (enabled) 1 else 0)
  }

  class Italics(val enabled: Boolean) : Command() {
    override fun bytes(): Sequence<Byte> = sequenceOf(27, 52, if (enabled) 1 else 0)
  }

  class Bold(val enabled: Boolean) : Command() {
    override fun bytes(): Sequence<Byte> = sequenceOf(27, 69, if (enabled) 1 else 0)
  }

  class SelectCharset(val charset: Charset) : Command() {
    override fun bytes(): Sequence<Byte> = sequenceOf(27, 116, charset.escposPageNumber)
  }

  class Justify(val alignment: TextAlignment) : Command() {
    override fun bytes(): Sequence<Byte> = sequenceOf(27, 97, alignment.value)
  }

  class TextSize(val width: Byte, val height: Byte) : Command() {
    private val sizeByte = constructSize(width, height)

    override fun bytes(): Sequence<Byte> = sequenceOf(29, 33, sizeByte)

    private fun constructSize(wMag: Byte, hMag: Byte): Byte {
      val w = (wMag.coerceIn(1, 8) - 1) shl 4
      val h = hMag.coerceIn(1, 8) - 1

      return (w + h).toByte()
    }
  }

  object Cut : Command() {
    override fun bytes(): Sequence<Byte> = sequenceOf(29, 86, 1)
  }

  class QrCode(
      content: String,
      errorCorrection: QrCorrectionLevel,
  ) : Command() {
    private val content: ByteArray

    init {
      require(content.isNotEmpty()) { "Cannot print QR code with no content." }

      /*
        THE DATA TRANSMISSION FUNCTION:
        - Transmits `k` bytes d1..dk to the printer.
        - Requires at least one byte of data.
        - Important!!! The size information we send with pL and pH is in range 4-7092

        29 40 107 pL pH 49 80 48 d1...dk

        Spec:
        (pL + pH × 256) = 4 – 7092
        d = 0 – 255
        k = (pL + pH × 256) − 3
      */

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

    override fun bytes(): Sequence<Byte> = content.asSequence()
  }
}

public enum class QrCorrectionLevel(internal val level: Byte) {
  /** 7 % (approx.) */
  L(48),
  /** 15 % (approx.) */
  M(49),
  /** 25 % (approx.) */
  Q(50),
  /** 30 % (approx.) */
  H(51)
}

public enum class TextAlignment(internal val value: Byte) {
  LEFT(0),
  CENTER(1),
  RIGHT(2)
}
