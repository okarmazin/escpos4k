// SPDX-License-Identifier: Apache-2.0

package cz.multiplatform.escpos4k.core.encoding.charset

/** Page 37 [CP864: Arabic] */
@Suppress("MagicNumber")
public data object IBM864 : Charset(37, "IBM864") {
  override val mapping: Map<Char, Int> =
      mapOf(
          '\u00B0' to 0x80,
          '\u00B7' to 0x81,
          '\u2219' to 0x82,
          '\u221A' to 0x83,
          '\u2592' to 0x84,
          '\u2500' to 0x85,
          '\u2502' to 0x86,
          '\u253C' to 0x87,
          '\u2524' to 0x88,
          '\u252C' to 0x89,
          '\u251C' to 0x8A,
          '\u2534' to 0x8B,
          '\u2510' to 0x8C,
          '\u250C' to 0x8D,
          '\u2514' to 0x8E,
          '\u2518' to 0x8F,
          '\u03B2' to 0x90,
          '\u221E' to 0x91,
          '\u03C6' to 0x92,
          '\u00B1' to 0x93,
          '\u00BD' to 0x94,
          '\u00BC' to 0x95,
          '\u2248' to 0x96,
          '\u00AB' to 0x97,
          '\u00BB' to 0x98,
          '\uFEF7' to 0x99,
          '\uFEF8' to 0x9A,
          '\uFFFD' to 0x9B,
          '\uFFFD' to 0x9C,
          '\uFEFB' to 0x9D,
          '\uFEFC' to 0x9E,
          '\uFFFD' to 0x9F,
          '\u00A0' to 0xA0,
          '\u00AD' to 0xA1,
          '\uFE82' to 0xA2,
          '\u00A3' to 0xA3,
          '\u00A4' to 0xA4,
          '\uFE84' to 0xA5,
          '\uFFFD' to 0xA6,
          '\uFFFD' to 0xA7,
          '\uFE8E' to 0xA8,
          '\uFE8F' to 0xA9,
          '\uFE95' to 0xAA,
          '\uFE99' to 0xAB,
          '\u060C' to 0xAC,
          '\uFE9D' to 0xAD,
          '\uFEA1' to 0xAE,
          '\uFEA5' to 0xAF,
          '\u0660' to 0xB0,
          '\u0661' to 0xB1,
          '\u0662' to 0xB2,
          '\u0663' to 0xB3,
          '\u0664' to 0xB4,
          '\u0665' to 0xB5,
          '\u0666' to 0xB6,
          '\u0667' to 0xB7,
          '\u0668' to 0xB8,
          '\u0669' to 0xB9,
          '\uFED1' to 0xBA,
          '\u061B' to 0xBB,
          '\uFEB1' to 0xBC,
          '\uFEB5' to 0xBD,
          '\uFEB9' to 0xBE,
          '\u061F' to 0xBF,
          '\u00A2' to 0xC0,
          '\uFE80' to 0xC1,
          '\uFE81' to 0xC2,
          '\uFE83' to 0xC3,
          '\uFE85' to 0xC4,
          '\uFECA' to 0xC5,
          '\uFE8B' to 0xC6,
          '\uFE8D' to 0xC7,
          '\uFE91' to 0xC8,
          '\uFE93' to 0xC9,
          '\uFE97' to 0xCA,
          '\uFE9B' to 0xCB,
          '\uFE9F' to 0xCC,
          '\uFEA3' to 0xCD,
          '\uFEA7' to 0xCE,
          '\uFEA9' to 0xCF,
          '\uFEAB' to 0xD0,
          '\uFEAD' to 0xD1,
          '\uFEAF' to 0xD2,
          '\uFEB3' to 0xD3,
          '\uFEB7' to 0xD4,
          '\uFEBB' to 0xD5,
          '\uFEBF' to 0xD6,
          '\uFEC1' to 0xD7,
          '\uFEC5' to 0xD8,
          '\uFECB' to 0xD9,
          '\uFECF' to 0xDA,
          '\u00A6' to 0xDB,
          '\u00AC' to 0xDC,
          '\u00F7' to 0xDD,
          '\u00D7' to 0xDE,
          '\uFEC9' to 0xDF,
          '\u0640' to 0xE0,
          '\uFED3' to 0xE1,
          '\uFED7' to 0xE2,
          '\uFEDB' to 0xE3,
          '\uFEDF' to 0xE4,
          '\uFEE3' to 0xE5,
          '\uFEE7' to 0xE6,
          '\uFEEB' to 0xE7,
          '\uFEED' to 0xE8,
          '\uFEEF' to 0xE9,
          '\uFEF3' to 0xEA,
          '\uFEBD' to 0xEB,
          '\uFECC' to 0xEC,
          '\uFECE' to 0xED,
          '\uFECD' to 0xEE,
          '\uFEE1' to 0xEF,
          '\uFE7D' to 0xF0,
          '\u0651' to 0xF1,
          '\uFEE5' to 0xF2,
          '\uFEE9' to 0xF3,
          '\uFEEC' to 0xF4,
          '\uFEF0' to 0xF5,
          '\uFEF2' to 0xF6,
          '\uFED0' to 0xF7,
          '\uFED5' to 0xF8,
          '\uFEF5' to 0xF9,
          '\uFEF6' to 0xFA,
          '\uFEDD' to 0xFB,
          '\uFED9' to 0xFC,
          '\uFEF1' to 0xFD,
          '\u25A0' to 0xFE,
          '\uFFFD' to 0xFF,
      )
}
