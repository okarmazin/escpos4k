package cz.multiplatform.escpos4k.core

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly

@OptIn(ExperimentalEscPosApi::class)
class CommandBuilderTest : FunSpec() {
  private val defaultConfig = PrinterConfiguration(32)
  private val initSequence = listOf(Command.Initialize, Command.SelectCharset(Charset.default))

  init {
    test("fresh CommandBuilder initial sequence") {
      CommandBuilder(defaultConfig).commands shouldContainExactly initSequence
    }

    context("text") {
      test("empty string does nothing") {
        CommandBuilder(defaultConfig) { text("") }.commands shouldContainExactly initSequence
      }
    }

    // todo text

    // todo line

    // todo twoColumnLine

    // todo twoColumnText

    context("charset") {
      test("style is not applied if same as current") {
        val builder =
            CommandBuilder(defaultConfig) {
              charset(Charset.default)
              charset(Charset.CP850)
              charset(Charset.CP850)
              charset(Charset.Windows1251)
            }
        builder.commands shouldContainExactly
            initSequence +
                Command.SelectCharset(Charset.CP850) +
                Command.SelectCharset(Charset.Windows1251)
      }
    }

    context("withCharset") {
      test("style is not applied if same as current") {
        val builder =
            CommandBuilder(defaultConfig) {
              withCharset(Charset.default) {
                withCharset(Charset.CP850) {
                  withCharset(Charset.CP850) {
                    withCharset(Charset.Windows1251) {
                      text("innermost") //
                    }
                  }
                }
              }
            }

        builder.commands shouldContainExactly
            initSequence +
                Command.SelectCharset(Charset.CP850) +
                Command.SelectCharset(Charset.Windows1251) +
                Command.Text("innermost", Charset.Windows1251) +
                Command.SelectCharset(Charset.CP850) +
                Command.SelectCharset(Charset.default)
      }
    }

    context("textSize") {
      test("style is not applied if same as current") {
        val builder =
            CommandBuilder(defaultConfig) {
              textSize(1, 1)
              textSize(2, 2)
              textSize(2, 2)
              textSize(3, 3)
            }
        builder.commands shouldContainExactly
            initSequence + Command.TextSize(2, 2) + Command.TextSize(3, 3)
      }
    }

    context("withTextSize") {
      test("style is not applied if same as current") {
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
        builder.commands shouldContainExactly
            initSequence +
                Command.TextSize(2, 2) +
                Command.TextSize(3, 3) +
                Command.Text("innermost", Charset.default) +
                Command.TextSize(2, 2) +
                Command.TextSize(1, 1)
      }
    }

    context("bold") {
      test("style is not applied if same as current") {
        val builder =
            CommandBuilder(defaultConfig) {
              bold(false)
              bold(true)
              bold(true)
              bold(false)
            }

        builder.commands shouldContainExactly
            initSequence + Command.Bold(true) + Command.Bold(false)
      }
    }

    context("withBold") {
      test("style is not applied if same as current") {
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

        builder.commands shouldContainExactly
            initSequence +
                Command.Bold(true) +
                Command.Bold(false) +
                Command.Text("innermost", Charset.default) +
                Command.Bold(true) +
                Command.Bold(false)
      }
    }

    context("underline") {
      test("style is not applied if same as current") {
        val builder =
            CommandBuilder(defaultConfig) {
              underline(false)
              underline(true)
              underline(true)
              underline(false)
            }
        builder.commands shouldContainExactly
            initSequence + Command.Underline(true) + Command.Underline(false)
      }
    }

    context("withUnderline") {
      test("style is not applied if same as current") {
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

        builder.commands shouldContainExactly
            initSequence +
                Command.Underline(true) +
                Command.Underline(false) +
                Command.Text("innermost", Charset.default) +
                Command.Underline(true) +
                Command.Underline(false)
      }
    }

    context("italics") {
      test("style is not applied if same as current") {
        val builder =
            CommandBuilder(defaultConfig) {
              italics(false)
              italics(true)
              italics(true)
              italics(false)
            }
        builder.commands shouldContainExactly
            initSequence + Command.Italics(true) + Command.Italics(false)
      }
    }

    context("withItalics") {
      test("style is not applied if same as current") {
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

        builder.commands shouldContainExactly
            initSequence +
                Command.Italics(true) +
                Command.Italics(false) +
                Command.Text("innermost", Charset.default) +
                Command.Italics(true) +
                Command.Italics(false)
      }
    }

    context("textAlign") {
      test("style is not applied if same as current") {
        val builder =
            CommandBuilder(defaultConfig) {
              textAlign(TextAlignment.LEFT)
              textAlign(TextAlignment.CENTER)
              textAlign(TextAlignment.CENTER)
              textAlign(TextAlignment.RIGHT)
            }
        builder.commands shouldContainExactly
            initSequence +
                Command.Justify(TextAlignment.CENTER) +
                Command.Justify(TextAlignment.RIGHT)
      }
    }

    context("segmentedLine") {
      test("empty segments") {
        val builder =
            CommandBuilder(defaultConfig) {
              segmentedLine()
              segmentedLine()
            }
        builder.commands shouldContainExactly initSequence
      }

      test("segment content empty") {
        val builder =
            CommandBuilder(defaultConfig) {
              segmentedLine(
                  LineSegment("", TextAlignment.LEFT), LineSegment("3", TextAlignment.LEFT))
            }

        builder.commands shouldContainExactly
            initSequence +
                Command.Text("                ", Charset.default) +
                Command.Text("3", Charset.default) +
                Command.Text("               ", Charset.default) +
                Command.Text("\n", Charset.default)
      }

      test("prints at least 1 char per segment") {
        val builder =
            CommandBuilder(PrinterConfiguration(10)) {
              textSize(8, 1)
              segmentedLine(
                  LineSegment("12", TextAlignment.LEFT), LineSegment("3", TextAlignment.LEFT))
            }
        builder.commands shouldContainExactly
            initSequence +
                Command.TextSize(8, 1) +
                Command.Text("1", Charset.default) +
                Command.Text("3", Charset.default) +
                Command.Text("\n", Charset.default) +
                Command.Text("2", Charset.default) +
                Command.TextSize(1, 1) +
                Command.Text("     ", Charset.default) +
                Command.TextSize(8, 1) +
                Command.Text("\n", Charset.default)
      }
    }
  }
}
