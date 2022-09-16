package cz.multiplatform.escpos4k.core

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.ints.shouldBeExactly

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
  }
}
