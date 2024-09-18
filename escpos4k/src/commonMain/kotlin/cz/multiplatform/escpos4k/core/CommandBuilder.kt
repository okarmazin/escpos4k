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

import arrow.core.Nel
import arrow.core.nonEmptyListOf
import arrow.core.toNonEmptyListOrNull
import cz.multiplatform.escpos4k.core.LineDistributionStrategy.Companion.SpaceEvenly
import cz.multiplatform.escpos4k.core.encoding.charset.Charset

/**
 * The central class for building the content to send to the printer. The class provides a variety
 * of functions called "builders" representing the common styling you need for defining the printed
 * content. This includes settings such as text size, text alignment, bold, italics, underline etc.
 *
 * Most builders offer two "variants".
 * 1. Standard setting - `textSize(2, 2)`, `bold(true)` - When you set these styles, they remain set
 *    until changed.
 * 2. Temporary setting - `withTextSize(2, 2)`, `withBold(true)`. These have an additional `content`
 *    parameter of type `CommandBuilder.() -> Unit` and allow you to apply a certain style only to
 *    the content.
 *
 * The builder is also capable of building common barcodes, both 2D and 1D such as `QR`, `EAN`,
 * `UPC`. It offers rudimentary safety mechanisms where applicable, mainly around the hard limits of
 * barcode capacity or shape (content too long/short, content must be all-digits etc.).
 *
 * General usage:
 * ```
 *
 * val builder = CommandBuilder(defaultConfig) {
 *   // Charsets
 *   line("Famous bridges:")
 *   charset(IBM865) // Can encode Ø, but not ů
 *   line("Øresundsbroen: 7845m")
 *   charset(IBM852) // Can encode ů, but not Ø
 *   line("Karlův most: 515m")
 *
 *   // Text style - temporary style builder
 *   withTextSize(width = 2, height = 3) {
 *     line("Me big!")
 *   }
 *   line("Me small again!")
 *
 *   bold(true)
 *   line("Normal and bald. Wait.. I wanted BOLD!")
 *   bold(false)
 *
 *   val qrCode: Either<QRCodeError, QRCodeSpec> = BarcodeSpec.QRCodeSpec("Hello from the QR Code!")
 *   // Print the QR code or an error:
 *   qrCode
 *     .tap(::barcode)
 *     .tapLeft { err ->
 *       line("Could not construct QR code:")
 *       line(err.toString())
 *   }
 * }
 *
 * // Obtain and print the commands we built:
 * printer.printRaw(builder.bytes())
 * ```
 *
 * **NOTE**: This class is **not** thread safe.
 */
@ExperimentalEscPosApi
@Suppress("MemberVisibilityCanBePrivate")
public class CommandBuilder(
    public val config: PrinterConfiguration,
    content: CommandBuilder.() -> Unit = {},
) {
  internal val commands: MutableList<Command> =
      mutableListOf(Command.Initialize, Command.SelectCharset(Charset.default))

  init {
    content()
  }

  /**
   * Prepare the raw ESC/POS commands. The resulting ByteArray can be sent directly to a printer.
   *
   * Use this function to obtain the bytes for a printer after you build your content using the
   * builders.
   */
  public fun bytes(): ByteArray {
    return commands.flatMap { it.bytes().asSequence() }.toByteArray()
  }

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
    if (text.isEmpty()) {
      return
    }
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
   * Select a [Charset]. Text printed with [text] will be encoded to single-byte characters
   * according to this character set.
   *
   * ```
   * printer.print {
   *   line("Famous bridges:")
   *
   *   charset(IBM865) // Can encode Ø, but not ů
   *   line("Øresundsbroen: 7845m")
   *
   *   charset(IBM852) // Can encode ů, but not Ø
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
   *   charset(IBM865)
   *   line("Øresundsbroen: 7845m")
   *
   *   withCharset(IBM852) {
   *     // 852 can encode ů, but not Ø
   *     line("Karlův most: 515m")
   *   }
   *
   *   line("I can encode Ø again!")
   * }
   * ```
   *
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
   *
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
   * @see segmentedLine
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
   * Print a barcode. Multiple barcodes are supported, both 1D and 2D. Please see the sealed
   * [BarcodeSpec] class for information on the supported barcode types.
   *
   * @see BarcodeSpec
   */
  public fun barcode(spec: BarcodeSpec) {
    commands.add(spec.asCommand()) //
  }

  /**
   * Print multiple independent segments on a single line. The available space is distributed by the
   * provided [distributionStrategy]. By default, available space is distributed evenly.
   *
   * **Segment Overflow**: If a segment is too long for its allotted space, the text overflows onto
   * the next line or lines. The overflown line keeps the text alignment.
   *
   * In the following example, each segment is given 8 characters to work with. The second segment
   * is too long to fit, so it breaks downwards.
   *
   * ```
   * // Let the line length be 16. Each segment gets 8 single-width spaces to work with.
   * segmentedLine(
   *   LineSegment("seg1", TextAlignment.LEFT),
   *   LineSegment("seg2_overflow", TextAlignment.RIGHT),
   * )
   *
   * textSize(2, 1) // Double character width
   * segmentedLine(
   *   LineSegment("seg3", TextAlignment.LEFT),
   *   LineSegment("seg4_overflow", TextAlignment.RIGHT),
   * )
   *
   * // Printed output (the paper/segment bounds are outlined with '|' and '_'):
   *
   * -------------------
   * |seg1    |seg2_ove|
   * |        |   rflow|
   * -------------------
   *
   * -----------
   * |seg3|seg4|         <-- Only 8 total characters fit per line now
   * |    |_ove|             since they are double width.
   * |    |rflo|
   * |    |   w|
   * -----------
   * ```
   */
  public fun segmentedLine(
      segments: List<LineSegment>,
      distributionStrategy: LineDistributionStrategy = SpaceEvenly,
  ) {
    @Suppress("NAME_SHADOWING") // force br
    val segments = segments.toNonEmptyListOrNull() ?: return
    val charWidth = commands.lastOfTypeOrNull<Command.TextSize>()?.width ?: 1
    val sizedSegments =
        distributionStrategy.distributeLine(config.charactersPerLine, charWidth, segments)
    // Partition the segment text into parts that fit in the allotted space while taking the
    // character width into consideration.
    val splitSegments: Nel<Nel<Pair<LineSegment, Int>>> =
        sizedSegments.map { (segment, space) ->
          val chunkSize = (space / charWidth).coerceAtLeast(1)
          if (segment.text.isEmpty()) {
            // Empty segment text needs to be special cased, because `chunked`
            // produces an empty list of chunks when faced with an empty string. This is
            // because it treats the string as an empty iterable of characters.
            // If we didn't special case this, the `Nel.fromListUnsafe` would throw due to an
            // empty list.
            nonEmptyListOf(segment to space)
          } else {
            segment.text
                .chunked(chunkSize) { chunk ->
                  LineSegment(chunk.toString(), segment.alignment) to space
                }
                .toNonEmptyListOrNull()!!
          }
        }

    // Each original segment in the list is now split into multiple segments if it was longer
    // than its allotted space. Iterate row wise, each row in the 2D structure is a segmented
    // line to be laid out.
    val numRows = splitSegments.maxOf { it.size }
    (0 until numRows)
        .map { rowIdx ->
          splitSegments.map { column ->
            column.elementAtOrElse(rowIdx) {
              LineSegment("", TextAlignment.LEFT) to column.head.second
            }
          }
        }
        .forEach(::renderSegmentedLine)
  }

  /** Please see the sibling `segmentedLine(segments)` function for full info. */
  public fun segmentedLine(
      vararg segments: LineSegment,
      distributionStrategy: LineDistributionStrategy = SpaceEvenly,
  ) {
    segmentedLine(segments.asList(), distributionStrategy)
  }

  private fun renderSegmentedLine(segments: Nel<Pair<LineSegment, Int>>) {
    val textSize = commands.lastOfTypeOrNull<Command.TextSize>() ?: Command.TextSize(1, 1)
    for ((segment, availableSpace) in segments) {
      val remainingSpaces = availableSpace - segment.text.length * textSize.width
      var direction = segment.alignment == TextAlignment.RIGHT
      var leftSpacer = 0
      var rightSpacer = 0

      for (i in 0 until remainingSpaces) {
        if (direction) leftSpacer++ else rightSpacer++
        if (segment.alignment == TextAlignment.CENTER) {
          direction = !direction
        }
      }

      if (leftSpacer > 0) {
        withTextSize(1, textSize.height) {
          val spacerString = buildString { repeat(leftSpacer) { append(" ") } }
          text(spacerString)
        }
      }

      text(segment.text)

      if (rightSpacer > 0) {
        withTextSize(1, textSize.height) {
          val spacerString = buildString { repeat(rightSpacer) { append(" ") } }
          text(spacerString)
        }
      }
    }
    text("\n")
  }
}

public data class LineSegment(
    val text: String,
    /** Alignment of text within the segment. */
    val alignment: TextAlignment
)

/** A named tuple of [LineSegment] and its `allottedSpace` by a [LineDistributionStrategy]. */
public data class SizedSegment(val segment: LineSegment, val allottedSpace: Int)

/**
 * Distributes the available space among `LineSegment`s.
 *
 * Used by [CommandBuilder.segmentedLine].
 *
 * A default strategy [SpaceEvenly] is provided for uniform space distribution.
 */
public fun interface LineDistributionStrategy {
  public fun distributeLine(
      charsPerLine: Int,
      charWidth: Int,
      segments: Nel<LineSegment>
  ): Nel<SizedSegment>

  public companion object {
    /**
     * Available space is distributed evenly among the segments, with left bias. i.e. the remainder
     * of `numChars / numSegments` is applied from left to right.
     *
     * Example: A line of size `8` is distributed among `3` segments as follows: `3-3-2`
     */
    public val SpaceEvenly: LineDistributionStrategy =
        LineDistributionStrategy { charsPerLine, _, segments ->
          val basicSize = charsPerLine / segments.size
          val rem = charsPerLine % segments.size
          val sizedSegments = segments.mapTo(mutableListOf()) { SizedSegment(it, basicSize) }

          for (i in 0 until rem) {
            val segment = sizedSegments[i]
            sizedSegments[i] = segment.copy(allottedSpace = segment.allottedSpace + 1)
          }

          sizedSegments.toNonEmptyListOrNull()!!
        }
  }
}
