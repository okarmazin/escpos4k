/*
 *    Copyright 2024 Ondřej Karmazín
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

/** Page 51 [Windows-1257] */
public data object Windows1257 : Charset(51, "windows-1257") {
  override val mapping: Map<Char, Int> =
      mapOf(
          '\u20AC' to 0x80,
          '\uFFFD' to 0x81,
          '\u201A' to 0x82,
          '\uFFFD' to 0x83,
          '\u201E' to 0x84,
          '\u2026' to 0x85,
          '\u2020' to 0x86,
          '\u2021' to 0x87,
          '\uFFFD' to 0x88,
          '\u2030' to 0x89,
          '\uFFFD' to 0x8A,
          '\u2039' to 0x8B,
          '\uFFFD' to 0x8C,
          '\u00A8' to 0x8D,
          '\u02C7' to 0x8E,
          '\u00B8' to 0x8F,
          '\uFFFD' to 0x90,
          '\u2018' to 0x91,
          '\u2019' to 0x92,
          '\u201C' to 0x93,
          '\u201D' to 0x94,
          '\u2022' to 0x95,
          '\u2013' to 0x96,
          '\u2014' to 0x97,
          '\uFFFD' to 0x98,
          '\u2122' to 0x99,
          '\uFFFD' to 0x9A,
          '\u203A' to 0x9B,
          '\uFFFD' to 0x9C,
          '\u00AF' to 0x9D,
          '\u02DB' to 0x9E,
          '\uFFFD' to 0x9F,
          '\u00A0' to 0xA0,
          '\uFFFD' to 0xA1,
          '\u00A2' to 0xA2,
          '\u00A3' to 0xA3,
          '\u00A4' to 0xA4,
          '\uFFFD' to 0xA5,
          '\u00A6' to 0xA6,
          '\u00A7' to 0xA7,
          '\u00D8' to 0xA8,
          '\u00A9' to 0xA9,
          '\u0156' to 0xAA,
          '\u00AB' to 0xAB,
          '\u00AC' to 0xAC,
          '\u00AD' to 0xAD,
          '\u00AE' to 0xAE,
          '\u00C6' to 0xAF,
          '\u00B0' to 0xB0,
          '\u00B1' to 0xB1,
          '\u00B2' to 0xB2,
          '\u00B3' to 0xB3,
          '\u00B4' to 0xB4,
          '\u00B5' to 0xB5,
          '\u00B6' to 0xB6,
          '\u00B7' to 0xB7,
          '\u00F8' to 0xB8,
          '\u00B9' to 0xB9,
          '\u0157' to 0xBA,
          '\u00BB' to 0xBB,
          '\u00BC' to 0xBC,
          '\u00BD' to 0xBD,
          '\u00BE' to 0xBE,
          '\u00E6' to 0xBF,
          '\u0104' to 0xC0,
          '\u012E' to 0xC1,
          '\u0100' to 0xC2,
          '\u0106' to 0xC3,
          '\u00C4' to 0xC4,
          '\u00C5' to 0xC5,
          '\u0118' to 0xC6,
          '\u0112' to 0xC7,
          '\u010C' to 0xC8,
          '\u00C9' to 0xC9,
          '\u0179' to 0xCA,
          '\u0116' to 0xCB,
          '\u0122' to 0xCC,
          '\u0136' to 0xCD,
          '\u012A' to 0xCE,
          '\u013B' to 0xCF,
          '\u0160' to 0xD0,
          '\u0143' to 0xD1,
          '\u0145' to 0xD2,
          '\u00D3' to 0xD3,
          '\u014C' to 0xD4,
          '\u00D5' to 0xD5,
          '\u00D6' to 0xD6,
          '\u00D7' to 0xD7,
          '\u0172' to 0xD8,
          '\u0141' to 0xD9,
          '\u015A' to 0xDA,
          '\u016A' to 0xDB,
          '\u00DC' to 0xDC,
          '\u017B' to 0xDD,
          '\u017D' to 0xDE,
          '\u00DF' to 0xDF,
          '\u0105' to 0xE0,
          '\u012F' to 0xE1,
          '\u0101' to 0xE2,
          '\u0107' to 0xE3,
          '\u00E4' to 0xE4,
          '\u00E5' to 0xE5,
          '\u0119' to 0xE6,
          '\u0113' to 0xE7,
          '\u010D' to 0xE8,
          '\u00E9' to 0xE9,
          '\u017A' to 0xEA,
          '\u0117' to 0xEB,
          '\u0123' to 0xEC,
          '\u0137' to 0xED,
          '\u012B' to 0xEE,
          '\u013C' to 0xEF,
          '\u0161' to 0xF0,
          '\u0144' to 0xF1,
          '\u0146' to 0xF2,
          '\u00F3' to 0xF3,
          '\u014D' to 0xF4,
          '\u00F5' to 0xF5,
          '\u00F6' to 0xF6,
          '\u00F7' to 0xF7,
          '\u0173' to 0xF8,
          '\u0142' to 0xF9,
          '\u015B' to 0xFA,
          '\u016B' to 0xFB,
          '\u00FC' to 0xFC,
          '\u017C' to 0xFD,
          '\u017E' to 0xFE,
          '\u02D9' to 0xFF,
      )
}
