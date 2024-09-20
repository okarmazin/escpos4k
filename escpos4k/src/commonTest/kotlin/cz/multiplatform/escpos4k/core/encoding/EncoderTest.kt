/*
 *    Copyright 2024 escpos4k authors
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

package cz.multiplatform.escpos4k.core.encoding

import cz.multiplatform.escpos4k.core.encoding.charset.Windows1252
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class EncoderTest :
    FunSpec({
      fun String.ascii(): ByteArray = toCharArray().map { it.code.toByte() }.toByteArray()

      context("handling of unicode replacement character") {
        test("unicode replacement char gets mapped to our replacement char") {
          "abc\uFFFD".encode(Windows1252) shouldBe "abc?".ascii()
          "abc?".encode(Windows1252) shouldBe "abc?".ascii()
        }
      }

      context("handling of unknown characters") {
        test("unknown char gets mapped to our replacement char") {
          "abcě".encode(Windows1252) shouldBe "abc?".ascii()
        }
      }
    })
