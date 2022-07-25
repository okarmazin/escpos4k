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
  abstract val size: Int

  abstract fun bytes(): Sequence<Byte>

  class Text(private val text: String, charset: Charset) : Command() {
    private val encodedBytes: ByteArray by lazy { encode(text, charset) }

    override val size: Int
      get() = encodedBytes.size

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
    override val size: Int = 2

    override fun bytes(): Sequence<Byte> = sequenceOf(27, 64)
  }

  /**
   * Print and line feed.
   *
   * Prints the data in the print buffer and feeds one line, based on the current line spacing.
   */
  object LF : Command() {
    override val size: Int = 1

    override fun bytes(): Sequence<Byte> = sequenceOf(10)
  }

  class Underline(val enabled: Boolean) : Command() {
    override val size: Int = 3

    override fun bytes(): Sequence<Byte> = sequenceOf(27, 45, if (enabled) 1 else 0)
  }

  class Italics(val enabled: Boolean) : Command() {
    override val size: Int = 3

    override fun bytes(): Sequence<Byte> = sequenceOf(27, 52, if (enabled) 1 else 0)
  }

  class Bold(val enabled: Boolean) : Command() {
    override val size: Int = 3

    override fun bytes(): Sequence<Byte> = sequenceOf(27, 69, if (enabled) 1 else 0)
  }

  class SelectCharset(val charset: Charset) : Command() {
    override val size: Int = 3

    override fun bytes(): Sequence<Byte> = sequenceOf(27, 116, charset.escposPageNumber)
  }

  class Justify(val alignment: TextAlignment) : Command() {
    override val size: Int = 3

    override fun bytes(): Sequence<Byte> = sequenceOf(27, 97, alignment.value)
  }

  class TextSize(widthMagnification: Byte, heightMagification: Byte) : Command() {
    override val size: Int = 3

    private val sizeByte = constructSize(widthMagnification, heightMagification)

    override fun bytes(): Sequence<Byte> = sequenceOf(29, 33, sizeByte)

    private fun constructSize(wMag: Byte, hMag: Byte): Byte {
      val w = (wMag.coerceIn(1, 8) - 1) shl 4
      val h = hMag.coerceIn(1, 8) - 1

      return (w + h).toByte()
    }
  }

  object Cut : Command() {
    override val size: Int = 3

    override fun bytes(): Sequence<Byte> = sequenceOf(29, 86, 1)
  }
}

public enum class TextAlignment(internal val value: Byte) {
  LEFT(0),
  CENTER(1),
  RIGHT(2)
}
