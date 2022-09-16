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
 *
 * Barcode printing is **not affected** by styles such as `bold`, `underline`, `italics` etc.
 *
 * Barcode printing **is** affected by `textSize`, `upside-down mode`, `text rotation`.
 */
public sealed class BarcodeSpec {

  internal abstract fun asCommand(): Command

  /**
   * Print a QR Code.
   *
   * `text.length` must be in `<1..7089>`, but the upper limit of 7k can only be achieved with fully
   * numeric `text`. The realistic limit for alphanumeric content is about **`2k`**.
   */
  public class QRCodeSpec
  private constructor(
      public val text: String,
      public val errorCorrection: QrCorrectionLevel,
  ) : BarcodeSpec() {

    override fun asCommand(): Command.QrCode {
      return Command.QrCode(text, errorCorrection)
    }

    public sealed class QrCodeError {
      public object EmptyContent : QrCodeError()
      public object TooLong : QrCodeError()
    }

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

    public companion object {
      private const val maxLength = 7089

      /**
       * Print a QR Code.
       *
       * `text.length` must be in `<1..7089>`, but the upper limit of 7k can only be achieved with
       * fully numeric `text`. The realistic limit for random text content is about **`2k`**.
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
