package cz.multiplatform.escpos4k.core

import cz.multiplatform.escpos4k.core.encoding.charset.Charset
import kotlin.test.Test
import kotlin.test.assertContentEquals

class CommandTest {
  @Test
  fun `text output never contains control characters`() {
    val ctrl = buildString { (0.toChar()..32.toChar()).forEach(::append) }
    val output = Command.Text(ctrl, Charset.default).bytes()
    assertContentEquals(("?".repeat(32) + " ").asciiToBytes(), output)
  }
}
