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

/** @see [PrinterConnection.print] */
@Suppress("MemberVisibilityCanBePrivate")
public class CommandBuilder
internal constructor(
    internal val charsPerLine: Int,
) {
  internal val commands: MutableList<Command> =
      mutableListOf(Command.Initialize, Command.SelectCharset(Charset.default))

  private inline fun <reified T> List<Command>.lastOfTypeOrNull(): T? =
      asReversed().asSequence().filterIsInstance<T>().firstOrNull()

  /**
   * Print text without terminating the line. The supplied Kotlin `String` will be encoded to
   * single-byte characters according to the currently selected [charset].
   *
   * The behavior when [text] contains characters not belonging to the currently selected charset is
   * platform dependent. Usually the unknown character is replaced with a replacement character.
   *
   * ```
   * printer.print {
   *   text("I am.")
   *   text("I am also.")
   *
   *   // The above prints: "I am.I am also."
   * }
   * ```
   *
   * @see charset
   * @see line
   */
  public fun text(text: String) {
    val currentCharset =
        commands.lastOfTypeOrNull<Command.SelectCharset>()?.charset ?: Charset.default
    commands.add(Command.Text(text, currentCharset))
  }

  /**
   * Print text followed by `\n`. The supplied Kotlin `String` will be encoded to single-byte
   * characters according to the currently selected [charset].
   *
   * The behavior when [text] contains characters not belonging to the currently selected charset is
   * platform dependent. Usually the unknown character is replaced with a replacement character.
   *
   * Literally just `text(text + "\n")`
   *
   * ```
   * printer.print {
   *   val text = "I'm Mr. Meeseeks, look at me!"
   *
   *   text(text + "\n") // Annoying: must remember to add "\n"
   *
   *   line(text) // Much better
   * }
   * ```
   *
   * @see charset
   * @see text
   */
  public fun line(text: String) {
    text(text + "\n")
  }

  /**
   * Print the two text fragments in two columns and terminate the line.
   *
   * The two text fragments are spaced out such that the `left` text is aligned to the start of the
   * line and the `right` text is aligned to the end of the line.
   *
   * @param minSpace The minimum amount of spaces between the two columns, default 1.
   * @see twoColumnText
   */
  public fun twoColumnLine(
      left: String,
      right: String,
      charWidth: Int = 1,
      minSpace: Int = 1,
  ) {
    twoColumnText(left, right + "\n", charWidth, minSpace)
  }

  /**
   * Print the two text fragments in two columns without terminating the line.
   *
   * The two text fragments are spaced out such that the `left` text is aligned to the start of the
   * line and the `right` text is aligned to the end of the line.
   *
   * @param minSpace The minimum amount of spaces between the two columns, default 1.
   * @see twoColumnLine
   */
  public fun twoColumnText(
      left: String,
      right: String,
      charWidth: Int = 1,
      minSpace: Int = 1,
  ) {
    // TODO: Extract the character width from [commands] instead of forcing the caller to supply it.
    val numSpaces =
        (charsPerLine - left.length * charWidth - right.length * charWidth).coerceAtLeast(minSpace)
    val spacer = buildString { repeat(numSpaces) { append(' ') } }

    textAlign(TextAlignment.LEFT)
    text(left)
    text(spacer)
    text(right)
  }

  /**
   * Select a [Charset]. Text printed with [text] will be encoded to single-byte characters
   * according to this character set.
   *
   * ```
   * printer.print {
   *   line("Famous bridges:")
   *
   *   charset(Charset.CP865) // Can encode Ø, but not ů
   *   line("Øresundsbroen: 7845m")
   *
   *   charset(Charset.CP852) // Can encode ů, but not Ø
   *   line("Karlův most: 515m")
   * }
   * ```
   *
   * @see [text]
   * @see [withCharset]
   */
  public fun charset(charset: Charset) {
    val prev =
        commands.lastOfTypeOrNull<Command.SelectCharset>() ?: Command.SelectCharset(Charset.default)
    val new = Command.SelectCharset(charset)

    if (prev != new) {
      commands.add(new)
    }
  }

  /**
   * Selects the [charset] and executes [content]. After the content is executed, the selected
   * charset is restored to the value it had before calling this function.
   *
   * ```
   * printer.print {
   *   line("Famous bridges")
   *
   *   // 865 can encode Ø, but not ů
   *   charset(Charset.CP865)
   *   line("Øresundsbroen: 7845m")
   *
   *   withCharset(Charset.CP852) {
   *     // 852 can encode ů, but not Ø
   *     line("Karlův most: 515m")
   *   }
   *
   *   line("I can encode Ø again!")
   * }
   * ```
   * @see charset
   * @see text
   */
  public fun withCharset(charset: Charset, content: CommandBuilder.() -> Unit) {
    val prev = commands.lastOfTypeOrNull<Command.SelectCharset>()?.charset ?: Charset.default

    charset(charset)
    content()
    charset(prev)
  }

  /**
   * Set character size. Acceptable size range is `1..8`.
   *
   * 2 = double size; 3 = triple size ...
   *
   * Values outside this range will be coerced into it.
   *
   * Default size is `1`.
   *
   * ```
   * printer.print {
   *   textSize(width = 2, height = 3)
   *   line("Me big!")
   *
   *   textSize() // Utilizing default arguments
   *
   *   line("I'm back to normal size.")
   * }
   * ```
   *
   * @see withTextSize
   */
  public fun textSize(width: Int = 1, height: Int = 1) {
    val prev = commands.lastOfTypeOrNull<Command.TextSize>() ?: Command.TextSize(1, 1)
    val new = Command.TextSize(width.coerceIn(1..8), height.coerceIn(1..8))

    if (prev != new) {
      commands.add(new)
    }
  }

  /**
   * Sets the text size to [width] and [height] and executes [content]. After the content is
   * executed, the text size setting is restored to the value it had before calling this function.
   *
   * ```
   * printer.print {
   *   line("I am sized 1x1 by default.")
   *
   *   withTextSize(3, 3) {
   *     line("GIANT 3x3!")
   *   }
   *
   *   line("I am sized 1x1 again!")
   * }
   * ```
   *
   * @see textSize
   */
  public fun withTextSize(width: Int, height: Int, content: CommandBuilder.() -> Unit) {
    val prev = commands.lastOfTypeOrNull<Command.TextSize>() ?: Command.TextSize(1, 1)

    textSize(width, height)
    content()
    textSize(prev.width, prev.height)
  }

  /**
   * Turn `bold` mode ON or OFF.
   *
   * @see withBold
   */
  public fun bold(enabled: Boolean) {
    val prev = commands.lastOfTypeOrNull<Command.Bold>() ?: Command.Bold(false)
    val new = Command.Bold(enabled)

    if (prev != new) {
      commands.add(new)
    }
  }

  /**
   * Sets `bold` mode and executes [content]. After the content is executed, the `bold` setting is
   * restored to the value it had before calling this function.
   *
   * ```
   * printer.print {
   *   bold(true)
   *   line("Bold!")
   *
   *   withBold(false) {
   *     line("Normal.")
   *   }
   *
   *   line("Bold Again!")
   * }
   * ```
   *
   * @see bold
   */
  public fun withBold(enabled: Boolean, content: CommandBuilder.() -> Unit) {
    val prev = commands.lastOfTypeOrNull<Command.Bold>()?.enabled ?: false

    bold(enabled)
    content()
    bold(prev)
  }

  /**
   * Turn `underlined` mode ON or OFF.
   *
   * @see withUnderline
   */
  public fun underline(enabled: Boolean) {
    val prev = commands.lastOfTypeOrNull<Command.Underline>() ?: Command.Underline(false)
    val new = Command.Underline(enabled)

    if (prev != new) {
      commands.add(new)
    }
  }

  /**
   * Sets `underlined` mode and executes [content]. After the content is executed, the `underline`
   * setting is restored to the value it had before calling this function.
   *
   * ```
   * printer.print {
   *   underline(true)
   *   line("I am a wee underlined text.")
   *
   *   withUnderline(false) {
   *     line("Nobody puts a line under me!")
   *   }
   *
   *   line("I am underlined again.")
   * }
   * ```
   * @see underline
   */
  public fun withUnderline(enabled: Boolean, content: CommandBuilder.() -> Unit) {
    val prev = commands.lastOfTypeOrNull<Command.Underline>()?.enabled ?: false

    underline(enabled)
    content()
    underline(prev)
  }

  /**
   * Turn `italics` mode ON or OFF.
   *
   * @see withItalics
   */
  public fun italics(enabled: Boolean) {
    val prev = commands.lastOfTypeOrNull<Command.Italics>() ?: Command.Italics(false)
    val new = Command.Italics(enabled)

    if (prev != new) {
      commands.add(new)
    }
  }

  /**
   * Sets `italics` mode and executes [content]. After the content is executed, the `italics`
   * setting is restored to the value it had before calling this function.
   *
   * ```
   * printer.print {
   *   italics(true)
   *   line("That Pisa tower is my jam.")
   *
   *   withItalics(false) {
   *     line("I don't like slanted things.")
   *   }
   *
   *   line("Italics again!")
   * }
   * ```
   *
   * @see italics
   */
  public fun withItalics(enabled: Boolean, content: CommandBuilder.() -> Unit) {
    val prev = commands.lastOfTypeOrNull<Command.Italics>()?.enabled ?: false

    italics(enabled)
    content()
    italics(prev)
  }

  /**
   * Set the text alignment. Only takes effect if the printer is in the start-of-line state.
   * Therefore, this cannot be used to align multiple pieces of text on the same line.
   *
   * Aligning multiple pieces of text on the same line has to be done manually by adding the
   * appropriate number of spaces in between the fragments and then printing this spaced out text as
   * a single line.
   *
   * @see twoColumnLine
   * @see TextAlignment
   */
  public fun textAlign(alignment: TextAlignment) {
    val prev = commands.lastOfTypeOrNull<Command.Justify>() ?: Command.Justify(TextAlignment.LEFT)
    val new = Command.Justify(alignment)

    if (prev != new) {
      commands.add(new)
    }
  }

  public fun cut() {
    commands.add(Command.Cut)
  }

  /**
   * Print a QR Code containing the [content].
   *
   * QR Code printing is not affected by print mode (bold, double-strike, underline...) except for
   * two settings: 1) Character size 2) Upside-down mode
   *
   * `content` must not be empty.
   */
  public fun qr(content: String, errorCorrection: QrCorrectionLevel = QrCorrectionLevel.L) {
    commands.add(Command.QrCode(content, errorCorrection))
  }
}
