package cz.multiplatform.escpos4k.core.encoding

import cz.multiplatform.escpos4k.core.asciiToBytes
import cz.multiplatform.escpos4k.core.encoding.charset.Windows1252
import kotlin.test.Test
import kotlin.test.assertContentEquals

class EncoderTest {
  @Test
  fun `unicode replacement char gets mapped to our replacement char`() {
    assertContentEquals("abc?".asciiToBytes(), "abc\uFFFD".encode(Windows1252))
    assertContentEquals("abc?".asciiToBytes(), "abc?".encode(Windows1252))
  }

  @Test
  fun `unknown char gets mapped to our replacement char`() {
    assertContentEquals("abc?".asciiToBytes(), "abcě".encode(Windows1252))
  }
}
