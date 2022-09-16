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

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import cz.multiplatform.escpos4k.core.BarcodeSpec.AztecCodeSpec.Companion.create
import cz.multiplatform.escpos4k.core.BarcodeSpec.DataMatrixSpec.Companion.create
import cz.multiplatform.escpos4k.core.BarcodeSpec.QRCodeSpec.Companion.create
import cz.multiplatform.escpos4k.core.BarcodeSpec.UpcASpec.Companion.create

/**
 * A sealed hierarchy of supported barcodes. Each `BarcodeSpec` is instantiated via a factory
 * function that validates the input arguments and returns an `Either<SpecificError, BarcodeSpec>`.
 * The factory function ensures that only valid instances can be created.
 *
 * Example:
 * ```
 * printer.print {
 *   val qrCode: Either<QrCodeError, QRCodeSpec> = BarcodeSpec.QRCodeSpec("hello")
 *
 *   // Print the QR code or an error:
 *   qrCode
 *     .tap(::barcode)
 *     .tapLeft { err ->
 *       line("Could not construct QR code:")
 *       line(err.toString())
 *     }
 * }
 * ```
 */
public sealed class BarcodeSpec {

  internal abstract fun asCommand(): Command

  /**
   * Print a QR Code.
   *
   * Please see [create] for full info.
   *
   * @see create
   */
  public class QRCodeSpec
  private constructor(
      public val text: String,
      public val errorCorrection: QrCorrectionLevel,
  ) : BarcodeSpec() {

    override fun asCommand(): Command.QrCode = Command.QrCode(text, errorCorrection)

    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (other == null || this::class != other::class) return false

      other as QRCodeSpec

      if (text != other.text) return false
      if (errorCorrection != other.errorCorrection) return false

      return true
    }

    override fun hashCode(): Int {
      var result = text.hashCode()
      result = 31 * result + errorCorrection.hashCode()
      return result
    }

    override fun toString(): String {
      return "QRCodeSpec(text='$text', errorCorrection=$errorCorrection)"
    }

    public sealed class QrCodeError {
      public object EmptyContent : QrCodeError()
      public object TooLong : QrCodeError()
    }

    public companion object {
      private const val maxLength = 7089

      /**
       * Print a QR Code.
       *
       * `text.length` must be in `1..7089`, but the upper limit of can only be achieved with fully
       * numeric `text`. The realistic limit for random text content is about **`2k`**.
       */
      public fun create(
          text: String,
          errorCorrection: QrCorrectionLevel = QrCorrectionLevel.L
      ): Either<QrCodeError, QRCodeSpec> {
        if (text.isEmpty()) {
          return QrCodeError.EmptyContent.left()
        }

        if (text.length > maxLength) {
          return QrCodeError.TooLong.left()
        }

        return QRCodeSpec(text, errorCorrection).right()
      }
    }
  }

  /**
   * Print an Aztec Code.
   *
   * Please see [create] for full info.
   *
   * @see create
   */
  public class AztecCodeSpec
  private constructor(
      public val text: String,
      public val ecPercent: Int,
  ) : BarcodeSpec() {
    override fun asCommand(): Command = Command.AztecCode(text, ecPercent)

    public sealed class AztecCodeError {
      public object EmptyContent : AztecCodeError()
      public object TooLong : AztecCodeError()
    }

    public companion object {
      private const val maxLength = 3832

      /**
       * Print an Aztec Code.
       *
       * **IMPORTANT NOTICE**
       *
       * `Some printers will print a QR code instead of an Aztec Code. The behavior is unknown ahead
       * of time.`
       *
       * `text.length` must be in `1..3832`, but the upper limit can only be achieved with fully
       * numeric `text`. The realistic limit for random text is about **`1.9k`**.
       *
       * `ecPercent` must be in `5..95`. Values outside this range are coerced into it. The
       * recommended value (which is also the default) is 23.
       *
       * **NOTE**: If the resulting symbol size exceeds the print area, the printer should feed the
       * paper as much as the symbol's height, without printing the symbol. This is different from
       * e.g. a QR Code which does not feed the paper.
       */
      public fun create(text: String, ecPercent: Int = 23): Either<AztecCodeError, AztecCodeSpec> {
        if (text.isEmpty()) {
          return AztecCodeError.EmptyContent.left()
        }
        if (text.length > maxLength) {
          return AztecCodeError.TooLong.left()
        }

        return AztecCodeSpec(text, ecPercent.coerceIn(5..95)).right()
      }
    }
  }

  /**
   * Print a Data Matrix.
   *
   * Please see [create] for full info.
   *
   * @see create
   */
  public class DataMatrixSpec private constructor(public val text: String) : BarcodeSpec() {

    override fun asCommand(): Command = Command.DataMatrix(text)

    public sealed class DataMatrixError {
      public object EmptyContent : DataMatrixError()
      public object TooLong : DataMatrixError()
    }

    public companion object {
      private const val maxLength = 3116
      /**
       * Print a Data Matrix.
       *
       * **IMPORTANT NOTICE**
       *
       * `Some printers will print a QR code instead of an Aztec Code. The behavior is unknown ahead
       * of time.`
       *
       * `text.length` must be in `1..3116`, but the upper limit can only be achieved with fully
       * numeric text. The realistic limit for random text is lower.
       */
      public fun create(text: String): Either<DataMatrixError, DataMatrixSpec> {
        if (text.isEmpty()) {
          return DataMatrixError.EmptyContent.left()
        }
        if (text.length > maxLength) {
          return DataMatrixError.TooLong.left()
        }

        return DataMatrixSpec(text).right()
      }
    }
  }

  /**
   * Print a UPC-A barcode.
   *
   * Please see [create] for full information.
   *
   * @see create
   */
  public class UpcASpec private constructor(public val text: String, public val hri: HriPosition) :
      BarcodeSpec() {
    override fun asCommand(): Command = Command.UPC_A(text, hri)

    public sealed class UpcAError {
      public data class IllegalCharacter(val index: Int, val character: Char) : UpcAError() {
        public val message: String = "Illegal character '$character' at index $index"

        override fun toString(): String {
          return "IllegalCharacter(message='$message')"
        }
      }
      public object IncorrectLength : UpcAError()
      public data class InvalidCheckDigit(val expected: Int, val actual: Int) : UpcAError()
    }

    public companion object {

      /**
       * Print the 12-digit UPC-A barcode.
       *
       * The UPC-A standard is enforced by this function. The argument to this function must only
       * contain digits and the input must be of certain length.
       *
       * The following input is accepted:
       *
       * 1) 11-digit string. This function will calculate and add the 12th check digit.
       *
       * 2) 12-digit string. This function will check whether the 12th digit is a valid check digit,
       * returning an error if not.
       */
      public fun create(text: String, hri: HriPosition): Either<UpcAError, UpcASpec> {
        text.forEachIndexed { index, c ->
          if (!c.isDigit()) {
            return UpcAError.IllegalCharacter(index, c).left()
          }
        }

        when (text.length) {
          11 -> {
            val codeWithCheckDigit = text + calculateCheckDigit(text)
            return UpcASpec(codeWithCheckDigit, hri).right()
          }
          12 -> {
            val checkDigit = calculateCheckDigit(text.take(11))
            val candidate = text.last().digitToInt()
            return if (candidate == checkDigit) {
              UpcASpec(text, hri).right()
            } else {
              UpcAError.InvalidCheckDigit(checkDigit, candidate).left()
            }
          }
          else -> {
            return UpcAError.IncorrectLength.left()
          }
        }
      }

      private fun calculateCheckDigit(data: String): Int {
        val sum =
            data.foldIndexed(0) { index, acc, c ->
              if (index % 2 == 0) {
                acc + 3 * c.digitToInt()
              } else {
                acc + c.digitToInt()
              }
            }

        val rem = sum % 10
        return if (rem == 0) rem else 10 - rem
      }
    }
  }
}

/**
 * The HRI (Human Readable Interpretation) position when printing barcodes. These are the readable
 * digits you can find on barcodes in the shop.
 */
public enum class HriPosition(internal val position: Byte) {
  NONE(0),
  ABOVE(1),
  BELOW(2),
  ABOVE_AND_BELOW(3),
}

public enum class QrCorrectionLevel(internal val level: Byte) {
  /** 7 % (approx.) */
  L(48),
  /** 15 % (approx.) */
  M(49),
  /** 25 % (approx.) */
  Q(50),
  /** 30 % (approx.) */
  H(51)
}
