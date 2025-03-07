// SPDX-License-Identifier: Apache-2.0

package cz.multiplatform.escpos4k.core.encoding.charset

/** Page 48 [Windows-1254] */
@Suppress("MagicNumber")
public data object Windows1254 : Charset(48, "windows-1254") {
  override val mapping: Map<Char, Int> =
      mapOf(
          '\u20AC' to 0x80,
          '\uFFFD' to 0x81,
          '\u201A' to 0x82,
          '\u0192' to 0x83,
          '\u201E' to 0x84,
          '\u2026' to 0x85,
          '\u2020' to 0x86,
          '\u2021' to 0x87,
          '\u02C6' to 0x88,
          '\u2030' to 0x89,
          '\u0160' to 0x8A,
          '\u2039' to 0x8B,
          '\u0152' to 0x8C,
          '\uFFFD' to 0x8D,
          '\uFFFD' to 0x8E,
          '\uFFFD' to 0x8F,
          '\uFFFD' to 0x90,
          '\u2018' to 0x91,
          '\u2019' to 0x92,
          '\u201C' to 0x93,
          '\u201D' to 0x94,
          '\u2022' to 0x95,
          '\u2013' to 0x96,
          '\u2014' to 0x97,
          '\u02DC' to 0x98,
          '\u2122' to 0x99,
          '\u0161' to 0x9A,
          '\u203A' to 0x9B,
          '\u0153' to 0x9C,
          '\uFFFD' to 0x9D,
          '\uFFFD' to 0x9E,
          '\u0178' to 0x9F,
          '\u00A0' to 0xA0,
          '\u00A1' to 0xA1,
          '\u00A2' to 0xA2,
          '\u00A3' to 0xA3,
          '\u00A4' to 0xA4,
          '\u00A5' to 0xA5,
          '\u00A6' to 0xA6,
          '\u00A7' to 0xA7,
          '\u00A8' to 0xA8,
          '\u00A9' to 0xA9,
          '\u00AA' to 0xAA,
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
          '\u00BA' to 0xBA,
          '\u00BB' to 0xBB,
          '\u00BC' to 0xBC,
          '\u00BD' to 0xBD,
          '\u00BE' to 0xBE,
          '\u00BF' to 0xBF,
          '\u00C0' to 0xC0,
          '\u00C1' to 0xC1,
          '\u00C2' to 0xC2,
          '\u00C3' to 0xC3,
          '\u00C4' to 0xC4,
          '\u00C5' to 0xC5,
          '\u00C6' to 0xC6,
          '\u00C7' to 0xC7,
          '\u00C8' to 0xC8,
          '\u00C9' to 0xC9,
          '\u00CA' to 0xCA,
          '\u00CB' to 0xCB,
          '\u00CC' to 0xCC,
          '\u00CD' to 0xCD,
          '\u00CE' to 0xCE,
          '\u00CF' to 0xCF,
          '\u011E' to 0xD0,
          '\u00D1' to 0xD1,
          '\u00D2' to 0xD2,
          '\u00D3' to 0xD3,
          '\u00D4' to 0xD4,
          '\u00D5' to 0xD5,
          '\u00D6' to 0xD6,
          '\u00D7' to 0xD7,
          '\u00D8' to 0xD8,
          '\u00D9' to 0xD9,
          '\u00DA' to 0xDA,
          '\u00DB' to 0xDB,
          '\u00DC' to 0xDC,
          '\u0130' to 0xDD,
          '\u015E' to 0xDE,
          '\u00DF' to 0xDF,
          '\u00E0' to 0xE0,
          '\u00E1' to 0xE1,
          '\u00E2' to 0xE2,
          '\u00E3' to 0xE3,
          '\u00E4' to 0xE4,
          '\u00E5' to 0xE5,
          '\u00E6' to 0xE6,
          '\u00E7' to 0xE7,
          '\u00E8' to 0xE8,
          '\u00E9' to 0xE9,
          '\u00EA' to 0xEA,
          '\u00EB' to 0xEB,
          '\u00EC' to 0xEC,
          '\u00ED' to 0xED,
          '\u00EE' to 0xEE,
          '\u00EF' to 0xEF,
          '\u011F' to 0xF0,
          '\u00F1' to 0xF1,
          '\u00F2' to 0xF2,
          '\u00F3' to 0xF3,
          '\u00F4' to 0xF4,
          '\u00F5' to 0xF5,
          '\u00F6' to 0xF6,
          '\u00F7' to 0xF7,
          '\u00F8' to 0xF8,
          '\u00F9' to 0xF9,
          '\u00FA' to 0xFA,
          '\u00FB' to 0xFB,
          '\u00FC' to 0xFC,
          '\u0131' to 0xFD,
          '\u015F' to 0xFE,
          '\u00FF' to 0xFF,
      )
}
