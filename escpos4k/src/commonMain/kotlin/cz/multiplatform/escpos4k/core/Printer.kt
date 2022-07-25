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

public interface Printer {
  public val name: String
  /**
   * Build and print the [content]. The builder contains a variety of command functions.
   *
   * If the printer is not connected, this function returns an error. If successful, returns `null`.
   *
   * Before sending the [content] to the printer, the printer will be set to initialized state and
   * [Charset.CP437] (ESC/POS code page 0) will be selected.
   *
   * This is done for consistency since the factory-default charset can vary by region of purchase.
   * E.g. ESC/POS code page 20 (Thai) can be the default charset for printers sold in Thailand.
   *
   * ```
   * printer.print {
   *   // Charsets
   *   line("Famous bridges:")
   *   charset(Charset.CP865) // Can encode Ø, but not ů
   *   line("Øresundsbroen: 7845m")
   *   charset(Charset.CP852) // Can encode ů, but not Ø
   *   line("Karlův most: 515m")
   *
   *   // Text styles
   *   textSize(width = 2, height = 3)
   *   line("Me big!")
   *   textSize() // Utilizing default arguments to reset size
   *
   *   bold(true)
   *   line("Normal and bald. Wait.. I wanted BOLD!")
   *   bold(false)
   * }
   * ```
   */
  public suspend fun print(content: PrintableBuilder.() -> Unit): PrintError? {
    val builder = PrintableBuilder().apply(content)
    val bytes = builder.commands.flatMap { it.bytes() }.toByteArray()
    return printRaw(bytes)
  }

  /**
   * Send raw bytes to the printer. Use at your own risk, for example if you have your own ESC/POS
   * command generator and want to print its output.
   *
   * If you want to print text, use the sibling [print] function.
   * @see print
   */
  public suspend fun printRaw(bytes: ByteArray): PrintError?
}

public sealed class ConnectionError {
  public object NotAPrinter : ConnectionError()
  public object FailedToOpenDevice : ConnectionError()
}

public sealed class PrintError {
  public object NotConnected : PrintError()
  public object NotAPrinter : PrintError()
}
