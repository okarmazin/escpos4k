// SPDX-License-Identifier: Apache-2.0

package cz.multiplatform.escpos4k.core.encoding.charset

/** Page 46 [Windows-1251] */
@Suppress("MagicNumber")
public data object Windows1251 : Charset(46, "windows-1251") {
  override val mapping: Map<Char, Int> =
      mapOf(
          '\u0402' to 0x80,
          '\u0403' to 0x81,
          '\u201A' to 0x82,
          '\u0453' to 0x83,
          '\u201E' to 0x84,
          '\u2026' to 0x85,
          '\u2020' to 0x86,
          '\u2021' to 0x87,
          '\u20AC' to 0x88,
          '\u2030' to 0x89,
          '\u0409' to 0x8A,
          '\u2039' to 0x8B,
          '\u040A' to 0x8C,
          '\u040C' to 0x8D,
          '\u040B' to 0x8E,
          '\u040F' to 0x8F,
          '\u0452' to 0x90,
          '\u2018' to 0x91,
          '\u2019' to 0x92,
          '\u201C' to 0x93,
          '\u201D' to 0x94,
          '\u2022' to 0x95,
          '\u2013' to 0x96,
          '\u2014' to 0x97,
          '\uFFFD' to 0x98,
          '\u2122' to 0x99,
          '\u0459' to 0x9A,
          '\u203A' to 0x9B,
          '\u045A' to 0x9C,
          '\u045C' to 0x9D,
          '\u045B' to 0x9E,
          '\u045F' to 0x9F,
          '\u00A0' to 0xA0,
          '\u040E' to 0xA1,
          '\u045E' to 0xA2,
          '\u0408' to 0xA3,
          '\u00A4' to 0xA4,
          '\u0490' to 0xA5,
          '\u00A6' to 0xA6,
          '\u00A7' to 0xA7,
          '\u0401' to 0xA8,
          '\u00A9' to 0xA9,
          '\u0404' to 0xAA,
          '\u00AB' to 0xAB,
          '\u00AC' to 0xAC,
          '\u00AD' to 0xAD,
          '\u00AE' to 0xAE,
          '\u0407' to 0xAF,
          '\u00B0' to 0xB0,
          '\u00B1' to 0xB1,
          '\u0406' to 0xB2,
          '\u0456' to 0xB3,
          '\u0491' to 0xB4,
          '\u00B5' to 0xB5,
          '\u00B6' to 0xB6,
          '\u00B7' to 0xB7,
          '\u0451' to 0xB8,
          '\u2116' to 0xB9,
          '\u0454' to 0xBA,
          '\u00BB' to 0xBB,
          '\u0458' to 0xBC,
          '\u0405' to 0xBD,
          '\u0455' to 0xBE,
          '\u0457' to 0xBF,
          '\u0410' to 0xC0,
          '\u0411' to 0xC1,
          '\u0412' to 0xC2,
          '\u0413' to 0xC3,
          '\u0414' to 0xC4,
          '\u0415' to 0xC5,
          '\u0416' to 0xC6,
          '\u0417' to 0xC7,
          '\u0418' to 0xC8,
          '\u0419' to 0xC9,
          '\u041A' to 0xCA,
          '\u041B' to 0xCB,
          '\u041C' to 0xCC,
          '\u041D' to 0xCD,
          '\u041E' to 0xCE,
          '\u041F' to 0xCF,
          '\u0420' to 0xD0,
          '\u0421' to 0xD1,
          '\u0422' to 0xD2,
          '\u0423' to 0xD3,
          '\u0424' to 0xD4,
          '\u0425' to 0xD5,
          '\u0426' to 0xD6,
          '\u0427' to 0xD7,
          '\u0428' to 0xD8,
          '\u0429' to 0xD9,
          '\u042A' to 0xDA,
          '\u042B' to 0xDB,
          '\u042C' to 0xDC,
          '\u042D' to 0xDD,
          '\u042E' to 0xDE,
          '\u042F' to 0xDF,
          '\u0430' to 0xE0,
          '\u0431' to 0xE1,
          '\u0432' to 0xE2,
          '\u0433' to 0xE3,
          '\u0434' to 0xE4,
          '\u0435' to 0xE5,
          '\u0436' to 0xE6,
          '\u0437' to 0xE7,
          '\u0438' to 0xE8,
          '\u0439' to 0xE9,
          '\u043A' to 0xEA,
          '\u043B' to 0xEB,
          '\u043C' to 0xEC,
          '\u043D' to 0xED,
          '\u043E' to 0xEE,
          '\u043F' to 0xEF,
          '\u0440' to 0xF0,
          '\u0441' to 0xF1,
          '\u0442' to 0xF2,
          '\u0443' to 0xF3,
          '\u0444' to 0xF4,
          '\u0445' to 0xF5,
          '\u0446' to 0xF6,
          '\u0447' to 0xF7,
          '\u0448' to 0xF8,
          '\u0449' to 0xF9,
          '\u044A' to 0xFA,
          '\u044B' to 0xFB,
          '\u044C' to 0xFC,
          '\u044D' to 0xFD,
          '\u044E' to 0xFE,
          '\u044F' to 0xFF,
      )
}
