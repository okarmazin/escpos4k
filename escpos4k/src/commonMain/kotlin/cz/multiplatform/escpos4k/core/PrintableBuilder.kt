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

/** @see [Printer.print] */
public class PrintableBuilder internal constructor() {
  internal val commands: MutableList<Command> =
      mutableListOf(Command.Initialize, Command.SelectCharset(Charset.values().first()))

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
   */
  public fun textSize(width: Byte = 1, height: Byte = 1) {
    commands.add(Command.TextSize(width, height))
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
    commands.add(
        Command.Text(
            text, (commands.last { it is Command.SelectCharset } as Command.SelectCharset).charset))
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

  /** Turn underline ON or OFF. */
  public fun underline(enabled: Boolean) {
    commands.add(Command.Underline(enabled))
  }

  /** Turn emphasis mode ON or OFF. */
  public fun bold(enabled: Boolean) {
    commands.add(Command.Bold(enabled))
  }

  /** Turn italics ON or OFF. */
  public fun italics(enabled: Boolean) {
    commands.add(Command.Italics(enabled))
  }

  /**
   * Select a [Charset]. Text printed with [text] will be encoded to single-byte characters
   * according to this character set.
   *
   * Characters outside the character set will be replaced with a replacement character.
   *
   * @see [text]
   */
  public fun charset(charset: Charset) {
    commands.add(Command.SelectCharset(charset))
  }
}
