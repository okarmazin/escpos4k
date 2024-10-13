package cz.multiplatform.escpos4k.core

import cz.multiplatform.escpos4k.core.encoding.charset.Charset
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class CommandTest :
    FunSpec({
      context("Text") {
        test("output never contains control characters") {
          val ctrl = buildString { (0.toChar()..32.toChar()).forEach(::append) }
          Command.Text(ctrl, Charset.default).bytes() shouldBe ("?".repeat(32) + " ").asciiToBytes()
        }
      }
    })
