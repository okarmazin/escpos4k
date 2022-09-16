package cz.multiplatform.escpos4k.core

import io.kotest.core.spec.style.FunSpec

class BarcodeSpecTest : FunSpec() {
  init {
    context("QRCodeSpec") {
      test("factory enforces length limits") {
        BarcodeSpec.QRCodeSpec.create("", QrCorrectionLevel.Q)
            .shouldBeLeft(BarcodeSpec.QRCodeSpec.QrCodeError.EmptyContent)

        val longText = buildString { repeat(10_000) { append("1") } }
        BarcodeSpec.QRCodeSpec.create(longText, QrCorrectionLevel.Q)
            .shouldBeLeft(BarcodeSpec.QRCodeSpec.QrCodeError.TooLong)
      }
    }
  }
}
