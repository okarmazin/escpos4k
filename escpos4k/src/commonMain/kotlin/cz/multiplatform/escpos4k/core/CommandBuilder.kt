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

  private inline fun <reified T> List<Command>.lastOfType(): T? =
      asReversed().asSequence().filterIsInstance<T>().firstOrNull()

  /**
   * Set character size.
   *
   * Acceptable size range is `1..8` where the size represents multiplication factor.
   *
   * 2 = double size; 3 = triple size ...
   *
   * Values outside this range will be coerced into it.
   *
   * Default size is `1`
   *
   * ```
   * printer.print {
   *   textSize(width = 2, height = 3)
   *   line("Me big!")
   *   textSize() // Utilizing default arguments
   *   line("I'm back to normal size :(")
   * }
   * ```
   * @see withTextSize
   */
  public fun textSize(width: Byte = 1, height: Byte = 1) {
    commands.add(Command.TextSize(width, height))
  }

  /**
   * Sets the text size to [width] and [height] and executes [content]. After the content is
   * executed, the size is restored to the value it had before calling this function.
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
   * @see textSize
   */
  public fun withTextSize(width: Byte, height: Byte, content: CommandBuilder.() -> Unit) {
    val prev = commands.lastOfType<Command.TextSize>()
    textSize(width, height)
    content()
    textSize(prev?.width ?: 1, prev?.height ?: 1)
  }

  /**
   * Print text. The supplied Kotlin String will be encoded to single-byte characters according to
   * the previously selected [charset].
   *
   * ```
   * printer.print {
   *   text("Famous bridges:\n")
   *
   *   charset(Charset.CP865) // Can encode Ø, but not ů
   *   text("Øresundsbroen: 7845m\n")
   *
   *   charset(Charset.CP852) // Can encode ů, but not Ø
   *   text("Karlův most: 515m\n")
   * }
   * ```
   *
   * @see charset
   */
  public fun text(text: String) {
    // TODO maybe keep the current charset as a member variable instead of looking it up every time.
    val currentCharset = commands.lastOfType<Command.SelectCharset>()?.charset ?: Charset.default
    commands.add(Command.Text(text, currentCharset))
  }

  /**
   * Print text followed by a newline. Useful when the whole content is stored in a variable.
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
   */
  public fun line(text: String) {
    text(text + "\n")
  }

  /**
   * Print the two text fragments on a single line in two columns, appending '\n' to the right text.
   *
   * The left text is aligned to the LEFT, the right text is aligned to the RIGHT.
   *
   * @param minSpace The minimum amount of spaces between the two columns, default 1.
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
   * The left text is aligned to the LEFT, the right text is aligned to the RIGHT.
   *
   * @param minSpace The minimum amount of spaces between the two columns, default 1.
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
   * Turn underline ON or OFF.
   * @see withUnderline
   */
  public fun underline(enabled: Boolean) {
    commands.add(Command.Underline(enabled))
  }

  /**
   * Sets `underlined` mode and executes [content]. After the content is executed, the underline
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
    val prev = commands.lastOfType<Command.Underline>()?.enabled ?: false

    underline(enabled)
    content()
    underline(prev)
  }

  /**
   * Turn emphasis mode ON or OFF.
   * @see withBold
   */
  public fun bold(enabled: Boolean) {
    commands.add(Command.Bold(enabled))
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
    val prev = commands.lastOfType<Command.Bold>()?.enabled ?: false

    bold(enabled)
    content()
    bold(prev)
  }

  /**
   * Turn italics ON or OFF.
   * @see withItalics
   */
  public fun italics(enabled: Boolean) {
    commands.add(Command.Italics(enabled))
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
    val prev = commands.lastOfType<Command.Italics>()?.enabled ?: false

    italics(enabled)
    content()
    italics(prev)
  }

  /**
   * Select a [Charset]. Text printed with [text] will be encoded to single-byte characters
   * according to this character set.
   *
   * @see [text]
   * @see [withCharset]
   */
  public fun charset(charset: Charset) {
    commands.add(Command.SelectCharset(charset))
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
    val prev = commands.lastOfType<Command.SelectCharset>()?.charset ?: Charset.default

    charset(charset)
    content()
    charset(prev)
  }

  /**
   * Set the text alignment.
   *
   * @see TextAlignment
   */
  public fun textAlign(alignment: TextAlignment) {
    commands.add(Command.Justify(alignment))
  }

  public fun cut() {
    commands.add(Command.Cut)
  }
}
