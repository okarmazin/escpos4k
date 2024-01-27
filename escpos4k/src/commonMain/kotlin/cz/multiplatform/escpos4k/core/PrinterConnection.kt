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

public interface PrinterConnection {
  public val name: String
  public val isOpen: Boolean

  /**
   * Send raw bytes to the printer. It is expected that you use the [CommandBuilder] to generate the
   * bytes.
   *
   * If the printer is not connected, this function returns an error. If successful, returns `null`.
   */
  public suspend fun printRaw(bytes: ByteArray): PrintError?
}

/** Build and print the [content]. */
public suspend fun PrinterConnection.print(
    config: PrinterConfiguration,
    content: CommandBuilder.() -> Unit
): PrintError? {
  val builder = CommandBuilder(config, content)
  return printRaw(builder.bytes())
}

public sealed class ConnectionError {
  public data object NotAPrinter : ConnectionError()
  public data object FailedToOpenDevice : ConnectionError()
}

public sealed class PrintError {
  public data object NotConnected : PrintError()
  public data object NotAPrinter : PrintError()
}
