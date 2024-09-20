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
