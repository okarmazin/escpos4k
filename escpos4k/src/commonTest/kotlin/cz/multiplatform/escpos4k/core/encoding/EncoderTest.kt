package cz.multiplatform.escpos4k.core.encoding

import cz.multiplatform.escpos4k.core.asciiToBytes
import cz.multiplatform.escpos4k.core.encoding.charset.Windows1252
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class EncoderTest :
    FunSpec({
      context("handling of unicode replacement character") {
        test("unicode replacement char gets mapped to our replacement char") {
          "abc\uFFFD".encode(Windows1252) shouldBe "abc?".asciiToBytes()
          "abc?".encode(Windows1252) shouldBe "abc?".asciiToBytes()
        }
      }

      context("handling of unknown characters") {
        test("unknown char gets mapped to our replacement char") { //
          "abcě".encode(Windows1252) shouldBe "abc?".asciiToBytes()
        }
      }
    })
