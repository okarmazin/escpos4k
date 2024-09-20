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

package cz.multiplatform.escpos4k.core.encoding.charset

/** Page 0 [CP437: USA, Standard Europe] */
public data object IBM437 : Charset(0, "IBM437") {
  override val mapping: Map<Char, Int> =
      mapOf(
          '\u00C7' to 0x80,
          '\u00FC' to 0x81,
          '\u00E9' to 0x82,
          '\u00E2' to 0x83,
          '\u00E4' to 0x84,
          '\u00E0' to 0x85,
          '\u00E5' to 0x86,
          '\u00E7' to 0x87,
          '\u00EA' to 0x88,
          '\u00EB' to 0x89,
          '\u00E8' to 0x8A,
          '\u00EF' to 0x8B,
          '\u00EE' to 0x8C,
          '\u00EC' to 0x8D,
          '\u00C4' to 0x8E,
          '\u00C5' to 0x8F,
          '\u00C9' to 0x90,
          '\u00E6' to 0x91,
          '\u00C6' to 0x92,
          '\u00F4' to 0x93,
          '\u00F6' to 0x94,
          '\u00F2' to 0x95,
          '\u00FB' to 0x96,
          '\u00F9' to 0x97,
          '\u00FF' to 0x98,
          '\u00D6' to 0x99,
          '\u00DC' to 0x9A,
          '\u00A2' to 0x9B,
          '\u00A3' to 0x9C,
          '\u00A5' to 0x9D,
          '\u20A7' to 0x9E,
          '\u0192' to 0x9F,
          '\u00E1' to 0xA0,
          '\u00ED' to 0xA1,
          '\u00F3' to 0xA2,
          '\u00FA' to 0xA3,
          '\u00F1' to 0xA4,
          '\u00D1' to 0xA5,
          '\u00AA' to 0xA6,
          '\u00BA' to 0xA7,
          '\u00BF' to 0xA8,
          '\u2310' to 0xA9,
          '\u00AC' to 0xAA,
          '\u00BD' to 0xAB,
          '\u00BC' to 0xAC,
          '\u00A1' to 0xAD,
          '\u00AB' to 0xAE,
          '\u00BB' to 0xAF,
          '\u2591' to 0xB0,
          '\u2592' to 0xB1,
          '\u2593' to 0xB2,
          '\u2502' to 0xB3,
          '\u2524' to 0xB4,
          '\u2561' to 0xB5,
          '\u2562' to 0xB6,
          '\u2556' to 0xB7,
          '\u2555' to 0xB8,
          '\u2563' to 0xB9,
          '\u2551' to 0xBA,
          '\u2557' to 0xBB,
          '\u255D' to 0xBC,
          '\u255C' to 0xBD,
          '\u255B' to 0xBE,
          '\u2510' to 0xBF,
          '\u2514' to 0xC0,
          '\u2534' to 0xC1,
          '\u252C' to 0xC2,
          '\u251C' to 0xC3,
          '\u2500' to 0xC4,
          '\u253C' to 0xC5,
          '\u255E' to 0xC6,
          '\u255F' to 0xC7,
          '\u255A' to 0xC8,
          '\u2554' to 0xC9,
          '\u2569' to 0xCA,
          '\u2566' to 0xCB,
          '\u2560' to 0xCC,
          '\u2550' to 0xCD,
          '\u256C' to 0xCE,
          '\u2567' to 0xCF,
          '\u2568' to 0xD0,
          '\u2564' to 0xD1,
          '\u2565' to 0xD2,
          '\u2559' to 0xD3,
          '\u2558' to 0xD4,
          '\u2552' to 0xD5,
          '\u2553' to 0xD6,
          '\u256B' to 0xD7,
          '\u256A' to 0xD8,
          '\u2518' to 0xD9,
          '\u250C' to 0xDA,
          '\u2588' to 0xDB,
          '\u2584' to 0xDC,
          '\u258C' to 0xDD,
          '\u2590' to 0xDE,
          '\u2580' to 0xDF,
          '\u03B1' to 0xE0,
          '\u00DF' to 0xE1,
          '\u0393' to 0xE2,
          '\u03C0' to 0xE3,
          '\u03A3' to 0xE4,
          '\u03C3' to 0xE5,
          '\u00B5' to 0xE6,
          '\u03C4' to 0xE7,
          '\u03A6' to 0xE8,
          '\u0398' to 0xE9,
          '\u03A9' to 0xEA,
          '\u03B4' to 0xEB,
          '\u221E' to 0xEC,
          '\u03C6' to 0xED,
          '\u03B5' to 0xEE,
          '\u2229' to 0xEF,
          '\u2261' to 0xF0,
          '\u00B1' to 0xF1,
          '\u2265' to 0xF2,
          '\u2264' to 0xF3,
          '\u2320' to 0xF4,
          '\u2321' to 0xF5,
          '\u00F7' to 0xF6,
          '\u2248' to 0xF7,
          '\u00B0' to 0xF8,
          '\u2219' to 0xF9,
          '\u00B7' to 0xFA,
          '\u221A' to 0xFB,
          '\u207F' to 0xFC,
          '\u00B2' to 0xFD,
          '\u25A0' to 0xFE,
          '\u00A0' to 0xFF,
      )
}
