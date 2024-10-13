// SPDX-License-Identifier: Apache-2.0

package cz.multiplatform.escpos4k.core

public interface PrinterConnection {
  public val name: String
  public val isOpen: Boolean

  /**
   * Send raw bytes to the printer. It is expected that you use the [CommandBuilder] to generate the bytes.
   *
   * If the printer is not connected, this function returns an error. If successful, returns `null`.
   */
  public suspend fun printRaw(bytes: ByteArray): PrintError?
}

/** Build and print the [content]. */
@ExperimentalEscPosApi
public suspend fun PrinterConnection.print(
    config: PrinterConfiguration,
    content: CommandBuilder.() -> Unit,
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
