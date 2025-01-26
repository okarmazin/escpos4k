package cz.multiplatform.escpos4k.core

import cz.multiplatform.escpos4k.core.encoding.charset.Charset
import cz.multiplatform.escpos4k.core.encoding.charset.IBM850
import cz.multiplatform.escpos4k.core.encoding.charset.Windows1251
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

@OptIn(ExperimentalEscPosApi::class)
class CommandBuilderTest {
  private val defaultConfig = PrinterConfiguration(32)
  private val initSequence = listOf(Command.Initialize, Command.SelectCharset(Charset.default))

  /** Bytes without the InitSequence */
  private fun CommandBuilder.contentBytes() = bytes().drop(initSequence.sumOf { it.bytes().size })

  @Test
  fun `fresh CommandBuilder initial sequence`() {
    assertContentEquals(initSequence, CommandBuilder(defaultConfig).commands)
  }

  @Test
  fun `text - empty string does nothing`() {
    val builder = CommandBuilder(defaultConfig) { text("") }
    assertContentEquals(initSequence, builder.commands)
    assertEquals(0, builder.contentBytes().size)
  }

  @Test
  fun ` text - output never contains newlines`() {
    val builder = CommandBuilder(defaultConfig) { text("\na\nbcd\n") }
    assertContentEquals(initSequence + Command.Text("\na\nbcd\n", Charset.default), builder.commands)
    assertContentEquals("?a?bcd?".asciiToBytes().toList(), builder.contentBytes())
  }

  @Test
  fun `line - empty line contains exactly newline`() {
    val builder = CommandBuilder(defaultConfig) { line("") }
    assertContentEquals(initSequence + Command.Newline, builder.commands)
    assertContentEquals("\n".asciiToBytes().toList(), builder.contentBytes())
  }

  @Test
  fun `line - line contains exactly 1 newline at the end`() {
    val builder =
        CommandBuilder(defaultConfig) {
          line("\nab\ncd")
          line("\n")
        }
    val expectedCommands =
        initSequence +
            Command.Text("\nab\ncd", Charset.default) +
            Command.Newline +
            Command.Text("\n", Charset.default) +
            Command.Newline

    assertContentEquals(expectedCommands, builder.commands)
    assertContentEquals("?ab?cd\n?\n".asciiToBytes().toList(), builder.contentBytes())
  }

  @Test
  fun `charset - style is not applied if same as current`() {
    val builder =
        CommandBuilder(defaultConfig) {
          charset(Charset.default)
          charset(IBM850)
          charset(IBM850)
          charset(Windows1251)
        }
    val expectedCommands = initSequence + Command.SelectCharset(IBM850) + Command.SelectCharset(Windows1251)
    assertContentEquals(expectedCommands, builder.commands)
  }

  @Test
  fun `withCharset - style is not applied if same as current`() {
    val builder =
        CommandBuilder(defaultConfig) {
          withCharset(Charset.default) {
            withCharset(IBM850) {
              withCharset(IBM850) {
                withCharset(Windows1251) {
                  text("innermost") //
                }
              }
            }
          }
        }

    val expectedCommands =
        initSequence +
            Command.SelectCharset(IBM850) +
            Command.SelectCharset(Windows1251) +
            Command.Text("innermost", Windows1251) +
            Command.SelectCharset(IBM850) +
            Command.SelectCharset(Charset.default)

    assertContentEquals(expectedCommands, builder.commands)
  }

  @Test
  fun `textSize - style is not applied if same as current`() {
    val builder =
        CommandBuilder(defaultConfig) {
          textSize(1, 1)
          textSize(2, 2)
          textSize(2, 2)
          textSize(3, 3)
        }

    val expectedCommands = initSequence + Command.TextSize(2, 2) + Command.TextSize(3, 3)
    assertContentEquals(expectedCommands, builder.commands)
  }

  @Test
  fun `withTextSize - style is not applied if same as current`() {
    val builder =
        CommandBuilder(defaultConfig) {
          withTextSize(1, 1) {
            withTextSize(2, 2) {
              withTextSize(2, 2) {
                withTextSize(3, 3) {
                  text("innermost") // force br
                }
              }
            }
          }
        }
    val expectedCommands =
        initSequence +
            Command.TextSize(2, 2) +
            Command.TextSize(3, 3) +
            Command.Text("innermost", Charset.default) +
            Command.TextSize(2, 2) +
            Command.TextSize(1, 1)
    assertContentEquals(expectedCommands, builder.commands)
  }

  @Test
  fun `bold - style is not applied if same as current`() {
    val builder =
        CommandBuilder(defaultConfig) {
          bold(false)
          bold(true)
          bold(true)
          bold(false)
        }

    val expectedCommands = initSequence + Command.Bold(true) + Command.Bold(false)
    assertContentEquals(expectedCommands, builder.commands)
  }

  @Test
  fun `withBold - style is not applied if same as current`() {
    val builder =
        CommandBuilder(defaultConfig) {
          withBold(false) {
            withBold(true) {
              withBold(true) {
                withBold(false) {
                  text("innermost") //
                }
              }
            }
          }
        }

    val expectedCommands =
        initSequence +
            Command.Bold(true) +
            Command.Bold(false) +
            Command.Text("innermost", Charset.default) +
            Command.Bold(true) +
            Command.Bold(false)
    assertContentEquals(expectedCommands, builder.commands)
  }

  @Test
  fun `underline - style is not applied if same as current`() {
    val builder =
        CommandBuilder(defaultConfig) {
          underline(false)
          underline(true)
          underline(true)
          underline(false)
        }
    val expectedCommands = initSequence + Command.Underline(true) + Command.Underline(false)
    assertContentEquals(expectedCommands, builder.commands)
  }

  @Test
  fun `withUnderline - style is not applied if same as current`() {
    val builder =
        CommandBuilder(defaultConfig) {
          withUnderline(false) {
            withUnderline(true) {
              withUnderline(true) {
                withUnderline(false) {
                  text("innermost") //
                }
              }
            }
          }
        }

    val expectedCommands =
        initSequence +
            Command.Underline(true) +
            Command.Underline(false) +
            Command.Text("innermost", Charset.default) +
            Command.Underline(true) +
            Command.Underline(false)
    assertContentEquals(expectedCommands, builder.commands)
  }

  @Test
  fun `italics - style is not applied if same as current`() {
    val builder =
        CommandBuilder(defaultConfig) {
          italics(false)
          italics(true)
          italics(true)
          italics(false)
        }
    val expectedCommands = initSequence + Command.Italics(true) + Command.Italics(false)
    assertContentEquals(expectedCommands, builder.commands)
  }

  @Test
  fun `withItalics - style is not applied if same as current`() {
    val builder =
        CommandBuilder(defaultConfig) {
          withItalics(false) {
            withItalics(true) {
              withItalics(true) {
                withItalics(false) {
                  text("innermost") //
                }
              }
            }
          }
        }

    val expectedCommands =
        initSequence +
            Command.Italics(true) +
            Command.Italics(false) +
            Command.Text("innermost", Charset.default) +
            Command.Italics(true) +
            Command.Italics(false)
    assertContentEquals(expectedCommands, builder.commands)
  }

  @Test
  fun `textAlign - style is not applied if same as current`() {
    val builder =
        CommandBuilder(defaultConfig) {
          textAlign(TextAlignment.LEFT)
          textAlign(TextAlignment.CENTER)
          textAlign(TextAlignment.CENTER)
          textAlign(TextAlignment.RIGHT)
        }
    val expectedCommands = initSequence + Command.Justify(TextAlignment.CENTER) + Command.Justify(TextAlignment.RIGHT)
    assertContentEquals(expectedCommands, builder.commands)
  }

  @Test
  fun `segmentedLine - empty segments`() {
    val builder =
        CommandBuilder(defaultConfig) {
          segmentedLine()
          segmentedLine()
        }
    assertContentEquals(initSequence, builder.commands)
  }

  @Test
  fun `segmentedLine - segment content empty`() {
    val builder =
        CommandBuilder(defaultConfig) {
          segmentedLine(LineSegment("", TextAlignment.LEFT), LineSegment("3", TextAlignment.LEFT))
        }

    val expectedCommands =
        initSequence +
            Command.Text("                ", Charset.default) +
            Command.Text("3", Charset.default) +
            Command.Text("               ", Charset.default) +
            Command.Newline
    assertContentEquals(expectedCommands, builder.commands)
  }

  @Test
  fun `segmentedLine -prints at least 1 char per segment`() {
    val builder =
        CommandBuilder(PrinterConfiguration(10)) {
          textSize(8, 1)
          segmentedLine(LineSegment("12", TextAlignment.LEFT), LineSegment("3", TextAlignment.LEFT))
        }
    val expectedCommands =
        initSequence +
            Command.TextSize(8, 1) +
            Command.Text("1", Charset.default) +
            Command.Text("3", Charset.default) +
            Command.Newline +
            Command.Text("2", Charset.default) +
            Command.TextSize(1, 1) +
            Command.Text("     ", Charset.default) +
            Command.TextSize(8, 1) +
            Command.Newline
    assertContentEquals(expectedCommands, builder.commands)
  }
}
