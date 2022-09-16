package cz.multiplatform.escpos4k.core

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldBe

class BarcodeSpecTest : FunSpec() {
  init {
    context("QRCodeSpec") {
      test("factory enforces length limits") {
        BarcodeSpec.QRCodeSpec.create("")
            .shouldBeLeft(BarcodeSpec.QRCodeSpec.QrCodeError.EmptyContent)

        val longText = buildString { repeat(10_000) { append("1") } }
        BarcodeSpec.QRCodeSpec.create(longText)
            .shouldBeLeft(BarcodeSpec.QRCodeSpec.QrCodeError.TooLong)
      }
    }

    context("AztecCodeSpec") {
      test("factory enforces text length limits") {
        BarcodeSpec.AztecCodeSpec.create("")
            .shouldBeLeft(BarcodeSpec.AztecCodeSpec.AztecCodeError.EmptyContent)

        val longText = buildString { repeat(10_000) { append("1") } }
        BarcodeSpec.AztecCodeSpec.create(longText)
            .shouldBeLeft(BarcodeSpec.AztecCodeSpec.AztecCodeError.TooLong)
      }

      test("factory coerces EC percentage into a valid range") {
        BarcodeSpec.AztecCodeSpec.create("hello", -5).shouldBeRight().ecPercent shouldBeExactly 5
        BarcodeSpec.AztecCodeSpec.create("hello", 100).shouldBeRight().ecPercent shouldBeExactly 95
      }
    }

    context("DataMatrixSpec") {
      test("factory enforces text length limits") {
        BarcodeSpec.DataMatrixSpec.create("")
            .shouldBeLeft(BarcodeSpec.DataMatrixSpec.DataMatrixError.EmptyContent)

        val longText = buildString { repeat(10_000) { append("1") } }
        BarcodeSpec.DataMatrixSpec.create(longText)
            .shouldBeLeft(BarcodeSpec.DataMatrixSpec.DataMatrixError.TooLong)
      }
    }

    context("UPC-A") {
      test("factory enforces length limits") {
        BarcodeSpec.UpcASpec.create("1234567890123", HriPosition.BELOW)
            .shouldBeLeft(BarcodeSpec.UpcASpec.UpcAError.IncorrectLength)
        BarcodeSpec.UpcASpec.create("123456789", HriPosition.BELOW)
            .shouldBeLeft(BarcodeSpec.UpcASpec.UpcAError.IncorrectLength)
      }

      test("factory calculates correct check digit if length == 11") {
        BarcodeSpec.UpcASpec.create("03600029145", HriPosition.BELOW)
            .shouldBeRight()
            .text
            .shouldBe("036000291452")
      }

      test("factory enforces correct digit if length == 12") {
        BarcodeSpec.UpcASpec.create("036000291453", HriPosition.BELOW)
            .shouldBeLeft(BarcodeSpec.UpcASpec.UpcAError.InvalidCheckDigit(2, 3))
      }

      test("factory enforces digits only") {
        BarcodeSpec.UpcASpec.create("03600029145x", HriPosition.BELOW)
            .shouldBeLeft(BarcodeSpec.UpcASpec.UpcAError.IllegalCharacter(11, 'x'))
      }
    }
  }
}
