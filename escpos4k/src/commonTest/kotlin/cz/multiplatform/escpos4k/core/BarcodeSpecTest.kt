package cz.multiplatform.escpos4k.core

import kotlin.test.Test
import kotlin.test.assertEquals

class BarcodeSpecTest {
  @Test
  fun `QR - factory enforces length limits`() {
    val emptyResult = assertLeft(BarcodeSpec.QRCodeSpec.create(""))
    assertEquals(BarcodeSpec.QRCodeSpec.QRCodeError.EmptyContent, emptyResult)

    val tooLongResult = assertLeft(BarcodeSpec.QRCodeSpec.create("1".repeat(10_000)))
    assertEquals(BarcodeSpec.QRCodeSpec.QRCodeError.TooLong, tooLongResult)
  }

  @Test
  fun `Aztec - factory enforces text length limits`() {
    val emptyResult = assertLeft(BarcodeSpec.AztecCodeSpec.create(""))
    assertEquals(BarcodeSpec.AztecCodeSpec.AztecCodeError.EmptyContent, emptyResult)

    val tooLongResult = assertLeft(BarcodeSpec.AztecCodeSpec.create("1".repeat(10_000)))
    assertEquals(BarcodeSpec.AztecCodeSpec.AztecCodeError.TooLong, tooLongResult)
  }

  @Test
  fun `Aztec - factory coerces EC percentage into a valid range`() {
    val inputs = listOf(-5 to 5, 100 to 95)
    for ((input, ec) in inputs) {
      val result = assertRight(BarcodeSpec.AztecCodeSpec.create("hello", input))
      assertEquals(ec, result.ecPercent)
    }
  }

  @Test
  fun `DataMatrix - factory enforces text length limits`() {
    val emptyResult = assertLeft(BarcodeSpec.DataMatrixSpec.create(""))
    assertEquals(BarcodeSpec.DataMatrixSpec.DataMatrixError.EmptyContent, emptyResult)

    val tooLongResult = assertLeft(BarcodeSpec.DataMatrixSpec.create("1".repeat(10_000)))
    assertEquals(BarcodeSpec.DataMatrixSpec.DataMatrixError.TooLong, tooLongResult)
  }

  @Test
  fun `UPC-A - factory enforces length limits`() {
    val inputs = listOf("1234567890123", "123456789")
    for (input in inputs) {
      val result = assertLeft(BarcodeSpec.UPCASpec.create(input, HriPosition.BELOW))
      assertEquals(BarcodeSpec.UPCASpec.UPCAError.IncorrectLength, result)
    }
  }

  @Test
  fun `UPC-A - factory calculates correct check digit if length == 11`() {
    val result = assertRight(BarcodeSpec.UPCASpec.create("03600029145", HriPosition.BELOW))
    assertEquals("036000291452", result.text)
  }

  @Test
  fun `UPC-A - factory enforces correct digit if length == 12`() {
    val result = assertLeft(BarcodeSpec.UPCASpec.create("036000291453", HriPosition.BELOW))
    assertEquals(BarcodeSpec.UPCASpec.UPCAError.InvalidCheckDigit(2, 3), result)
  }

  @Test
  fun `UPC-A - factory enforces digits only`() {
    val result = assertLeft(BarcodeSpec.UPCASpec.create("03600029145x", HriPosition.BELOW))
    assertEquals(BarcodeSpec.UPCASpec.UPCAError.IllegalCharacter(11, 'x'), result)
  }

  @Test
  fun `EAN-13 - factory enforces length limits`() {
    val inputs = listOf("12345678901234", "123456789")
    for (input in inputs) {
      val result = assertLeft(BarcodeSpec.EAN13Spec.create(input, HriPosition.BELOW))
      assertEquals(BarcodeSpec.EAN13Spec.EAN13Error.IncorrectLength, result)
    }
  }

  @Test
  fun `EAN-13 - factory calculates correct check digit if length == 12`() {
    val result = assertRight(BarcodeSpec.EAN13Spec.create("400638133393", HriPosition.BELOW))
    assertEquals("4006381333931", result.text)
  }

  @Test
  fun `EAN-13 - factory enforces correct digit if length == 13`() {
    val result = assertLeft(BarcodeSpec.EAN13Spec.create("4006381333935", HriPosition.BELOW))
    assertEquals(BarcodeSpec.EAN13Spec.EAN13Error.InvalidCheckDigit(1, 5), result)
  }

  @Test
  fun `EAN-13 - factory enforces digits only`() {
    val result = assertLeft(BarcodeSpec.EAN13Spec.create("40x6381333931", HriPosition.BELOW))
    assertEquals(BarcodeSpec.EAN13Spec.EAN13Error.IllegalCharacter(2, 'x'), result)
  }

  @Test
  fun `EAN-8 - factory enforces length limits`() {
    val inputs = listOf("123456789", "123456")
    for (input in inputs) {
      val result = assertLeft(BarcodeSpec.EAN8Spec.create(input, HriPosition.BELOW))
      assertEquals(BarcodeSpec.EAN8Spec.EAN8Error.IncorrectLength, result)
    }
  }

  @Test
  fun `EAN-8 - factory calculates correct check digit if length == 7`() {
    val result = assertRight(BarcodeSpec.EAN8Spec.create("7351353", HriPosition.BELOW))
    assertEquals("73513537", result.text)
  }

  @Test
  fun `EAN-8 - factory enforces correct digit if length == 8`() {
    val result = assertLeft(BarcodeSpec.EAN8Spec.create("73513530", HriPosition.BELOW))
    assertEquals(BarcodeSpec.EAN8Spec.EAN8Error.InvalidCheckDigit(7, 0), result)
  }

  @Test
  fun `EAN-8 - factory enforces digits only`() {
    val result = assertLeft(BarcodeSpec.EAN8Spec.create("73513x37", HriPosition.BELOW))
    assertEquals(BarcodeSpec.EAN8Spec.EAN8Error.IllegalCharacter(5, 'x'), result)
  }
}
