// SPDX-License-Identifier: Apache-2.0

package cz.multiplatform.escpos4k.core.encoding.charset

/** Page 17 [CP866: Cyrillic #2] */
@Suppress("MagicNumber")
public data object IBM866 : Charset(17, "IBM866") {
  override val mapping: Map<Char, Int> =
      mapOf(
          '\u0410' to 0x80,
          '\u0411' to 0x81,
          '\u0412' to 0x82,
          '\u0413' to 0x83,
          '\u0414' to 0x84,
          '\u0415' to 0x85,
          '\u0416' to 0x86,
          '\u0417' to 0x87,
          '\u0418' to 0x88,
          '\u0419' to 0x89,
          '\u041A' to 0x8A,
          '\u041B' to 0x8B,
          '\u041C' to 0x8C,
          '\u041D' to 0x8D,
          '\u041E' to 0x8E,
          '\u041F' to 0x8F,
          '\u0420' to 0x90,
          '\u0421' to 0x91,
          '\u0422' to 0x92,
          '\u0423' to 0x93,
          '\u0424' to 0x94,
          '\u0425' to 0x95,
          '\u0426' to 0x96,
          '\u0427' to 0x97,
          '\u0428' to 0x98,
          '\u0429' to 0x99,
          '\u042A' to 0x9A,
          '\u042B' to 0x9B,
          '\u042C' to 0x9C,
          '\u042D' to 0x9D,
          '\u042E' to 0x9E,
          '\u042F' to 0x9F,
          '\u0430' to 0xA0,
          '\u0431' to 0xA1,
          '\u0432' to 0xA2,
          '\u0433' to 0xA3,
          '\u0434' to 0xA4,
          '\u0435' to 0xA5,
          '\u0436' to 0xA6,
          '\u0437' to 0xA7,
          '\u0438' to 0xA8,
          '\u0439' to 0xA9,
          '\u043A' to 0xAA,
          '\u043B' to 0xAB,
          '\u043C' to 0xAC,
          '\u043D' to 0xAD,
          '\u043E' to 0xAE,
          '\u043F' to 0xAF,
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
          '\u0440' to 0xE0,
          '\u0441' to 0xE1,
          '\u0442' to 0xE2,
          '\u0443' to 0xE3,
          '\u0444' to 0xE4,
          '\u0445' to 0xE5,
          '\u0446' to 0xE6,
          '\u0447' to 0xE7,
          '\u0448' to 0xE8,
          '\u0449' to 0xE9,
          '\u044A' to 0xEA,
          '\u044B' to 0xEB,
          '\u044C' to 0xEC,
          '\u044D' to 0xED,
          '\u044E' to 0xEE,
          '\u044F' to 0xEF,
          '\u0401' to 0xF0,
          '\u0451' to 0xF1,
          '\u0404' to 0xF2,
          '\u0454' to 0xF3,
          '\u0407' to 0xF4,
          '\u0457' to 0xF5,
          '\u040E' to 0xF6,
          '\u045E' to 0xF7,
          '\u00B0' to 0xF8,
          '\u2219' to 0xF9,
          '\u00B7' to 0xFA,
          '\u221A' to 0xFB,
          '\u2116' to 0xFC,
          '\u00A4' to 0xFD,
          '\u25A0' to 0xFE,
          '\u00A0' to 0xFF,
      )
}
