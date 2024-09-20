package cz.multiplatform.escpos4k.core

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldBe

class BarcodeSpecTest : FunSpec() {
  init {
    context("QRCodeSpec") {
      test("factory enforces length limits") {
        BarcodeSpec.QRCodeSpec.create("").shouldBeLeft(BarcodeSpec.QRCodeSpec.QRCodeError.EmptyContent)

        BarcodeSpec.QRCodeSpec.create("1".repeat(10_000)).shouldBeLeft(BarcodeSpec.QRCodeSpec.QRCodeError.TooLong)
      }
    }

    context("AztecCodeSpec") {
      test("factory enforces text length limits") {
        BarcodeSpec.AztecCodeSpec.create("").shouldBeLeft(BarcodeSpec.AztecCodeSpec.AztecCodeError.EmptyContent)

        BarcodeSpec.AztecCodeSpec.create("1".repeat(10_000))
            .shouldBeLeft(BarcodeSpec.AztecCodeSpec.AztecCodeError.TooLong)
      }

      test("factory coerces EC percentage into a valid range") {
        BarcodeSpec.AztecCodeSpec.create("hello", -5).shouldBeRight().ecPercent shouldBeExactly 5
        BarcodeSpec.AztecCodeSpec.create("hello", 100).shouldBeRight().ecPercent shouldBeExactly 95
      }
    }

    context("DataMatrixSpec") {
      test("factory enforces text length limits") {
        BarcodeSpec.DataMatrixSpec.create("").shouldBeLeft(BarcodeSpec.DataMatrixSpec.DataMatrixError.EmptyContent)

        BarcodeSpec.DataMatrixSpec.create("1".repeat(10_000))
            .shouldBeLeft(BarcodeSpec.DataMatrixSpec.DataMatrixError.TooLong)
      }
    }

    context("UPC-A") {
      test("factory enforces length limits") {
        BarcodeSpec.UPCASpec.create("1234567890123", HriPosition.BELOW)
            .shouldBeLeft(BarcodeSpec.UPCASpec.UPCAError.IncorrectLength)
        BarcodeSpec.UPCASpec.create("123456789", HriPosition.BELOW)
            .shouldBeLeft(BarcodeSpec.UPCASpec.UPCAError.IncorrectLength)
      }

      test("factory calculates correct check digit if length == 11") {
        BarcodeSpec.UPCASpec.create("03600029145", HriPosition.BELOW).shouldBeRight().text.shouldBe("036000291452")
      }

      test("factory enforces correct digit if length == 12") {
        BarcodeSpec.UPCASpec.create("036000291453", HriPosition.BELOW)
            .shouldBeLeft(BarcodeSpec.UPCASpec.UPCAError.InvalidCheckDigit(2, 3))
      }

      test("factory enforces digits only") {
        BarcodeSpec.UPCASpec.create("03600029145x", HriPosition.BELOW)
            .shouldBeLeft(BarcodeSpec.UPCASpec.UPCAError.IllegalCharacter(11, 'x'))
      }
    }

    context("EAN-13") {
      test("factory enforces length limits") {
        BarcodeSpec.EAN13Spec.create("12345678901234", HriPosition.BELOW)
            .shouldBeLeft(BarcodeSpec.EAN13Spec.EAN13Error.IncorrectLength)
        BarcodeSpec.EAN13Spec.create("123456789", HriPosition.BELOW)
            .shouldBeLeft(BarcodeSpec.EAN13Spec.EAN13Error.IncorrectLength)
      }

      test("factory calculates correct check digit if length == 12") {
        BarcodeSpec.EAN13Spec.create("400638133393", HriPosition.BELOW).shouldBeRight().text.shouldBe("4006381333931")
      }

      test("factory enforces correct digit if length == 13") {
        BarcodeSpec.EAN13Spec.create("4006381333935", HriPosition.BELOW)
            .shouldBeLeft(BarcodeSpec.EAN13Spec.EAN13Error.InvalidCheckDigit(1, 5))
      }

      test("factory enforces digits only") {
        BarcodeSpec.EAN13Spec.create("40x6381333931", HriPosition.BELOW)
            .shouldBeLeft(BarcodeSpec.EAN13Spec.EAN13Error.IllegalCharacter(2, 'x'))
      }
    }

    context("EAN-8") {
      test("factory enforces length limits") {
        BarcodeSpec.EAN8Spec.create("123456789", HriPosition.BELOW)
            .shouldBeLeft(BarcodeSpec.EAN8Spec.EAN8Error.IncorrectLength)
        BarcodeSpec.EAN8Spec.create("123456", HriPosition.BELOW)
            .shouldBeLeft(BarcodeSpec.EAN8Spec.EAN8Error.IncorrectLength)
      }

      test("factory calculates correct check digit if length == 7") {
        BarcodeSpec.EAN8Spec.create("7351353", HriPosition.BELOW).shouldBeRight().text.shouldBe("73513537")
      }

      test("factory enforces correct digit if length == 8") {
        BarcodeSpec.EAN8Spec.create("73513530", HriPosition.BELOW)
            .shouldBeLeft(BarcodeSpec.EAN8Spec.EAN8Error.InvalidCheckDigit(7, 0))
      }

      test("factory enforces digits only") {
        BarcodeSpec.EAN8Spec.create("73513x37", HriPosition.BELOW)
            .shouldBeLeft(BarcodeSpec.EAN8Spec.EAN8Error.IllegalCharacter(5, 'x'))
      }
    }
  }
}
