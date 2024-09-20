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

/** Page 50 [Windows-1256] */
@Suppress("MagicNumber")
public data object Windows1256 : Charset(50, "windows-1256") {
  override val mapping: Map<Char, Int> =
      mapOf(
          '\u20AC' to 0x80,
          '\u067E' to 0x81,
          '\u201A' to 0x82,
          '\u0192' to 0x83,
          '\u201E' to 0x84,
          '\u2026' to 0x85,
          '\u2020' to 0x86,
          '\u2021' to 0x87,
          '\u02C6' to 0x88,
          '\u2030' to 0x89,
          '\u0679' to 0x8A,
          '\u2039' to 0x8B,
          '\u0152' to 0x8C,
          '\u0686' to 0x8D,
          '\u0698' to 0x8E,
          '\u0688' to 0x8F,
          '\u06AF' to 0x90,
          '\u2018' to 0x91,
          '\u2019' to 0x92,
          '\u201C' to 0x93,
          '\u201D' to 0x94,
          '\u2022' to 0x95,
          '\u2013' to 0x96,
          '\u2014' to 0x97,
          '\u06A9' to 0x98,
          '\u2122' to 0x99,
          '\u0691' to 0x9A,
          '\u203A' to 0x9B,
          '\u0153' to 0x9C,
          '\u200C' to 0x9D,
          '\u200D' to 0x9E,
          '\u06BA' to 0x9F,
          '\u00A0' to 0xA0,
          '\u060C' to 0xA1,
          '\u00A2' to 0xA2,
          '\u00A3' to 0xA3,
          '\u00A4' to 0xA4,
          '\u00A5' to 0xA5,
          '\u00A6' to 0xA6,
          '\u00A7' to 0xA7,
          '\u00A8' to 0xA8,
          '\u00A9' to 0xA9,
          '\u06BE' to 0xAA,
          '\u00AB' to 0xAB,
          '\u00AC' to 0xAC,
          '\u00AD' to 0xAD,
          '\u00AE' to 0xAE,
          '\u00AF' to 0xAF,
          '\u00B0' to 0xB0,
          '\u00B1' to 0xB1,
          '\u00B2' to 0xB2,
          '\u00B3' to 0xB3,
          '\u00B4' to 0xB4,
          '\u00B5' to 0xB5,
          '\u00B6' to 0xB6,
          '\u00B7' to 0xB7,
          '\u00B8' to 0xB8,
          '\u00B9' to 0xB9,
          '\u061B' to 0xBA,
          '\u00BB' to 0xBB,
          '\u00BC' to 0xBC,
          '\u00BD' to 0xBD,
          '\u00BE' to 0xBE,
          '\u061F' to 0xBF,
          '\u06C1' to 0xC0,
          '\u0621' to 0xC1,
          '\u0622' to 0xC2,
          '\u0623' to 0xC3,
          '\u0624' to 0xC4,
          '\u0625' to 0xC5,
          '\u0626' to 0xC6,
          '\u0627' to 0xC7,
          '\u0628' to 0xC8,
          '\u0629' to 0xC9,
          '\u062A' to 0xCA,
          '\u062B' to 0xCB,
          '\u062C' to 0xCC,
          '\u062D' to 0xCD,
          '\u062E' to 0xCE,
          '\u062F' to 0xCF,
          '\u0630' to 0xD0,
          '\u0631' to 0xD1,
          '\u0632' to 0xD2,
          '\u0633' to 0xD3,
          '\u0634' to 0xD4,
          '\u0635' to 0xD5,
          '\u0636' to 0xD6,
          '\u00D7' to 0xD7,
          '\u0637' to 0xD8,
          '\u0638' to 0xD9,
          '\u0639' to 0xDA,
          '\u063A' to 0xDB,
          '\u0640' to 0xDC,
          '\u0641' to 0xDD,
          '\u0642' to 0xDE,
          '\u0643' to 0xDF,
          '\u00E0' to 0xE0,
          '\u0644' to 0xE1,
          '\u00E2' to 0xE2,
          '\u0645' to 0xE3,
          '\u0646' to 0xE4,
          '\u0647' to 0xE5,
          '\u0648' to 0xE6,
          '\u00E7' to 0xE7,
          '\u00E8' to 0xE8,
          '\u00E9' to 0xE9,
          '\u00EA' to 0xEA,
          '\u00EB' to 0xEB,
          '\u0649' to 0xEC,
          '\u064A' to 0xED,
          '\u00EE' to 0xEE,
          '\u00EF' to 0xEF,
          '\u064B' to 0xF0,
          '\u064C' to 0xF1,
          '\u064D' to 0xF2,
          '\u064E' to 0xF3,
          '\u00F4' to 0xF4,
          '\u064F' to 0xF5,
          '\u0650' to 0xF6,
          '\u00F7' to 0xF7,
          '\u0651' to 0xF8,
          '\u00F9' to 0xF9,
          '\u0652' to 0xFA,
          '\u00FB' to 0xFB,
          '\u00FC' to 0xFC,
          '\u200E' to 0xFD,
          '\u200F' to 0xFE,
          '\u06D2' to 0xFF,
      )
}
