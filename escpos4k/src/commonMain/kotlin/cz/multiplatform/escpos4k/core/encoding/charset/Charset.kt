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

public sealed class Charset(
    /**
     * Code page number according to ESC/POS specification.
     *
     * ESC/POS specifies a number of different code pages from different vendors, including pages
     * from IBM, Windows etc.
     */
    public val escposPageNumber: Byte,
    /**
     * The assigned IANA name of this character set.
     *
     * https://www.iana.org/assignments/character-sets/character-sets.xhtml
     */
    public val ianaName: String
) {
  public companion object {
    internal val default = IBM437
  }

  internal abstract val mapping: Map<Char, Int>

  //  /** Page 1 [Katakana] */
  //  Katakana(1, TODO()),

  //  /** Page 6 [Hiragana] */
  //  Hiragana(6, TODO()),

  //  /** Page 7 [One-pass printing Kanji characters] */
  //  Kanji_1(7, TODO()),

  //  /** Page 8 [One-pass printing Kanji characters] */
  //  Kanji_2(8, TODO()),

  //  /** Page 11 [CP851: Greek] */
  //  CP851(11, "IBM851"),

  //  /** Page 12 [CP853: Turkish] */
  //  CP853(12, "IBM853"),

  //  /** Page 14 [CP737: Greek] */
  //  CP737(14, TODO()),

  //  /** Page 20 [Thai Character Code 42] */
  //  Thai_42(20),
  //
  //  /** Page 21 [Thai Character Code 11] */
  //  Thai_11(21),
  //
  //  /** Page 22 [Thai Character Code 13] */
  //  Thai_13(22),
  //
  //  /** Page 23 [Thai Character Code 14] */
  //  Thai_14(23),
  //
  //  /** Page 24 [Thai Character Code 16] */
  //  Thai_16(24),
  //
  //  /** Page 25 [Thai Character Code 17] */
  //  Thai_17(25),
  //
  //  /** Page 26 [Thai Character Code 18] */
  //  Thai_18(26),

  //  /** Page 30 [TCVN-3: Vietnamese] */
  //  TCVN3_1(30),
  //
  //  /** Page 31 [TCVN-3: Vietnamese] */
  //  TCVN3_2(31),

  //  /** Page 32 [CP720: Arabic] */
  //  CP720(32),

  /** Page 40 [ISO8859-15: Latin 9] */
  public data object ISO_8859_15 : Charset(40, "ISO-8859-15") {
    override val mapping: Map<Char, Int> =
        mapOf(
            '\u0080' to 0x80,
            '\u0081' to 0x81,
            '\u0082' to 0x82,
            '\u0083' to 0x83,
            '\u0084' to 0x84,
            '\u0085' to 0x85,
            '\u0086' to 0x86,
            '\u0087' to 0x87,
            '\u0088' to 0x88,
            '\u0089' to 0x89,
            '\u008A' to 0x8A,
            '\u008B' to 0x8B,
            '\u008C' to 0x8C,
            '\u008D' to 0x8D,
            '\u008E' to 0x8E,
            '\u008F' to 0x8F,
            '\u0090' to 0x90,
            '\u0091' to 0x91,
            '\u0092' to 0x92,
            '\u0093' to 0x93,
            '\u0094' to 0x94,
            '\u0095' to 0x95,
            '\u0096' to 0x96,
            '\u0097' to 0x97,
            '\u0098' to 0x98,
            '\u0099' to 0x99,
            '\u009A' to 0x9A,
            '\u009B' to 0x9B,
            '\u009C' to 0x9C,
            '\u009D' to 0x9D,
            '\u009E' to 0x9E,
            '\u009F' to 0x9F,
            '\u00A0' to 0xA0,
            '\u00A1' to 0xA1,
            '\u00A2' to 0xA2,
            '\u00A3' to 0xA3,
            '\u20AC' to 0xA4,
            '\u00A5' to 0xA5,
            '\u0160' to 0xA6,
            '\u00A7' to 0xA7,
            '\u0161' to 0xA8,
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
            '\u017D' to 0xB4,
            '\u00B5' to 0xB5,
            '\u00B6' to 0xB6,
            '\u00B7' to 0xB7,
            '\u017E' to 0xB8,
            '\u00B9' to 0xB9,
            '\u00BA' to 0xBA,
            '\u00BB' to 0xBB,
            '\u0152' to 0xBC,
            '\u0153' to 0xBD,
            '\u0178' to 0xBE,
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
            '\u00D0' to 0xD0,
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
            '\u00DD' to 0xDD,
            '\u00DE' to 0xDE,
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
            '\u00F0' to 0xF0,
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
            '\u00FD' to 0xFD,
            '\u00FE' to 0xFE,
            '\u00FF' to 0xFF,
        )
  }

  //  /** Page 41 [CP1098: Farsi] */
  //  CP1098(41, TODO()),

  //  /** Page 42 [CP1118: Lithuanian] */
  //  CP1118(42),
  //
  //  /** Page 43 [CP1119: Lithuanian] */
  //  CP1119(43),

  //  /** Page 44 [CP1125: Ukrainian] */
  //  CP1125(44),

  /** Page 45 [Windows-1250] */
  public data object Windows1250 : Charset(45, "windows-1250") {
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
            '\u0160' to 0x8A,
            '\u2039' to 0x8B,
            '\u015A' to 0x8C,
            '\u0164' to 0x8D,
            '\u017D' to 0x8E,
            '\u0179' to 0x8F,
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
            '\u0161' to 0x9A,
            '\u203A' to 0x9B,
            '\u015B' to 0x9C,
            '\u0165' to 0x9D,
            '\u017E' to 0x9E,
            '\u017A' to 0x9F,
            '\u00A0' to 0xA0,
            '\u02C7' to 0xA1,
            '\u02D8' to 0xA2,
            '\u0141' to 0xA3,
            '\u00A4' to 0xA4,
            '\u0104' to 0xA5,
            '\u00A6' to 0xA6,
            '\u00A7' to 0xA7,
            '\u00A8' to 0xA8,
            '\u00A9' to 0xA9,
            '\u015E' to 0xAA,
            '\u00AB' to 0xAB,
            '\u00AC' to 0xAC,
            '\u00AD' to 0xAD,
            '\u00AE' to 0xAE,
            '\u017B' to 0xAF,
            '\u00B0' to 0xB0,
            '\u00B1' to 0xB1,
            '\u02DB' to 0xB2,
            '\u0142' to 0xB3,
            '\u00B4' to 0xB4,
            '\u00B5' to 0xB5,
            '\u00B6' to 0xB6,
            '\u00B7' to 0xB7,
            '\u00B8' to 0xB8,
            '\u0105' to 0xB9,
            '\u015F' to 0xBA,
            '\u00BB' to 0xBB,
            '\u013D' to 0xBC,
            '\u02DD' to 0xBD,
            '\u013E' to 0xBE,
            '\u017C' to 0xBF,
            '\u0154' to 0xC0,
            '\u00C1' to 0xC1,
            '\u00C2' to 0xC2,
            '\u0102' to 0xC3,
            '\u00C4' to 0xC4,
            '\u0139' to 0xC5,
            '\u0106' to 0xC6,
            '\u00C7' to 0xC7,
            '\u010C' to 0xC8,
            '\u00C9' to 0xC9,
            '\u0118' to 0xCA,
            '\u00CB' to 0xCB,
            '\u011A' to 0xCC,
            '\u00CD' to 0xCD,
            '\u00CE' to 0xCE,
            '\u010E' to 0xCF,
            '\u0110' to 0xD0,
            '\u0143' to 0xD1,
            '\u0147' to 0xD2,
            '\u00D3' to 0xD3,
            '\u00D4' to 0xD4,
            '\u0150' to 0xD5,
            '\u00D6' to 0xD6,
            '\u00D7' to 0xD7,
            '\u0158' to 0xD8,
            '\u016E' to 0xD9,
            '\u00DA' to 0xDA,
            '\u0170' to 0xDB,
            '\u00DC' to 0xDC,
            '\u00DD' to 0xDD,
            '\u0162' to 0xDE,
            '\u00DF' to 0xDF,
            '\u0155' to 0xE0,
            '\u00E1' to 0xE1,
            '\u00E2' to 0xE2,
            '\u0103' to 0xE3,
            '\u00E4' to 0xE4,
            '\u013A' to 0xE5,
            '\u0107' to 0xE6,
            '\u00E7' to 0xE7,
            '\u010D' to 0xE8,
            '\u00E9' to 0xE9,
            '\u0119' to 0xEA,
            '\u00EB' to 0xEB,
            '\u011B' to 0xEC,
            '\u00ED' to 0xED,
            '\u00EE' to 0xEE,
            '\u010F' to 0xEF,
            '\u0111' to 0xF0,
            '\u0144' to 0xF1,
            '\u0148' to 0xF2,
            '\u00F3' to 0xF3,
            '\u00F4' to 0xF4,
            '\u0151' to 0xF5,
            '\u00F6' to 0xF6,
            '\u00F7' to 0xF7,
            '\u0159' to 0xF8,
            '\u016F' to 0xF9,
            '\u00FA' to 0xFA,
            '\u0171' to 0xFB,
            '\u00FC' to 0xFC,
            '\u00FD' to 0xFD,
            '\u0163' to 0xFE,
            '\u02D9' to 0xFF,
        )
  }

  /** Page 46 [Windows-1251] */
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

  /** Page 47 [Windows-1253] */
  public data object Windows1253 : Charset(47, "windows-1253") {
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
            '\uFFFD' to 0x88,
            '\u2030' to 0x89,
            '\uFFFD' to 0x8A,
            '\u2039' to 0x8B,
            '\uFFFD' to 0x8C,
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
            '\uFFFD' to 0x98,
            '\u2122' to 0x99,
            '\uFFFD' to 0x9A,
            '\u203A' to 0x9B,
            '\uFFFD' to 0x9C,
            '\uFFFD' to 0x9D,
            '\uFFFD' to 0x9E,
            '\uFFFD' to 0x9F,
            '\u00A0' to 0xA0,
            '\u0385' to 0xA1,
            '\u0386' to 0xA2,
            '\u00A3' to 0xA3,
            '\u00A4' to 0xA4,
            '\u00A5' to 0xA5,
            '\u00A6' to 0xA6,
            '\u00A7' to 0xA7,
            '\u00A8' to 0xA8,
            '\u00A9' to 0xA9,
            '\uFFFD' to 0xAA,
            '\u00AB' to 0xAB,
            '\u00AC' to 0xAC,
            '\u00AD' to 0xAD,
            '\u00AE' to 0xAE,
            '\u2015' to 0xAF,
            '\u00B0' to 0xB0,
            '\u00B1' to 0xB1,
            '\u00B2' to 0xB2,
            '\u00B3' to 0xB3,
            '\u0384' to 0xB4,
            '\u00B5' to 0xB5,
            '\u00B6' to 0xB6,
            '\u00B7' to 0xB7,
            '\u0388' to 0xB8,
            '\u0389' to 0xB9,
            '\u038A' to 0xBA,
            '\u00BB' to 0xBB,
            '\u038C' to 0xBC,
            '\u00BD' to 0xBD,
            '\u038E' to 0xBE,
            '\u038F' to 0xBF,
            '\u0390' to 0xC0,
            '\u0391' to 0xC1,
            '\u0392' to 0xC2,
            '\u0393' to 0xC3,
            '\u0394' to 0xC4,
            '\u0395' to 0xC5,
            '\u0396' to 0xC6,
            '\u0397' to 0xC7,
            '\u0398' to 0xC8,
            '\u0399' to 0xC9,
            '\u039A' to 0xCA,
            '\u039B' to 0xCB,
            '\u039C' to 0xCC,
            '\u039D' to 0xCD,
            '\u039E' to 0xCE,
            '\u039F' to 0xCF,
            '\u03A0' to 0xD0,
            '\u03A1' to 0xD1,
            '\uFFFD' to 0xD2,
            '\u03A3' to 0xD3,
            '\u03A4' to 0xD4,
            '\u03A5' to 0xD5,
            '\u03A6' to 0xD6,
            '\u03A7' to 0xD7,
            '\u03A8' to 0xD8,
            '\u03A9' to 0xD9,
            '\u03AA' to 0xDA,
            '\u03AB' to 0xDB,
            '\u03AC' to 0xDC,
            '\u03AD' to 0xDD,
            '\u03AE' to 0xDE,
            '\u03AF' to 0xDF,
            '\u03B0' to 0xE0,
            '\u03B1' to 0xE1,
            '\u03B2' to 0xE2,
            '\u03B3' to 0xE3,
            '\u03B4' to 0xE4,
            '\u03B5' to 0xE5,
            '\u03B6' to 0xE6,
            '\u03B7' to 0xE7,
            '\u03B8' to 0xE8,
            '\u03B9' to 0xE9,
            '\u03BA' to 0xEA,
            '\u03BB' to 0xEB,
            '\u03BC' to 0xEC,
            '\u03BD' to 0xED,
            '\u03BE' to 0xEE,
            '\u03BF' to 0xEF,
            '\u03C0' to 0xF0,
            '\u03C1' to 0xF1,
            '\u03C2' to 0xF2,
            '\u03C3' to 0xF3,
            '\u03C4' to 0xF4,
            '\u03C5' to 0xF5,
            '\u03C6' to 0xF6,
            '\u03C7' to 0xF7,
            '\u03C8' to 0xF8,
            '\u03C9' to 0xF9,
            '\u03CA' to 0xFA,
            '\u03CB' to 0xFB,
            '\u03CC' to 0xFC,
            '\u03CD' to 0xFD,
            '\u03CE' to 0xFE,
            '\uFFFD' to 0xFF,
        )
  }

  /** Page 48 [Windows-1254] */
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

  /** Page 49 [Windows-1255] */
  public data object Windows1255 : Charset(49, "windows-1255") {
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
            '\uFFFD' to 0x8A,
            '\u2039' to 0x8B,
            '\uFFFD' to 0x8C,
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
            '\uFFFD' to 0x9A,
            '\u203A' to 0x9B,
            '\uFFFD' to 0x9C,
            '\uFFFD' to 0x9D,
            '\uFFFD' to 0x9E,
            '\uFFFD' to 0x9F,
            '\u00A0' to 0xA0,
            '\u00A1' to 0xA1,
            '\u00A2' to 0xA2,
            '\u00A3' to 0xA3,
            '\u20AA' to 0xA4,
            '\u00A5' to 0xA5,
            '\u00A6' to 0xA6,
            '\u00A7' to 0xA7,
            '\u00A8' to 0xA8,
            '\u00A9' to 0xA9,
            '\u00D7' to 0xAA,
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
            '\u00F7' to 0xBA,
            '\u00BB' to 0xBB,
            '\u00BC' to 0xBC,
            '\u00BD' to 0xBD,
            '\u00BE' to 0xBE,
            '\u00BF' to 0xBF,
            '\u05B0' to 0xC0,
            '\u05B1' to 0xC1,
            '\u05B2' to 0xC2,
            '\u05B3' to 0xC3,
            '\u05B4' to 0xC4,
            '\u05B5' to 0xC5,
            '\u05B6' to 0xC6,
            '\u05B7' to 0xC7,
            '\u05B8' to 0xC8,
            '\u05B9' to 0xC9,
            '\uFFFD' to 0xCA,
            '\u05BB' to 0xCB,
            '\u05BC' to 0xCC,
            '\u05BD' to 0xCD,
            '\u05BE' to 0xCE,
            '\u05BF' to 0xCF,
            '\u05C0' to 0xD0,
            '\u05C1' to 0xD1,
            '\u05C2' to 0xD2,
            '\u05C3' to 0xD3,
            '\u05F0' to 0xD4,
            '\u05F1' to 0xD5,
            '\u05F2' to 0xD6,
            '\u05F3' to 0xD7,
            '\u05F4' to 0xD8,
            '\uFFFD' to 0xD9,
            '\uFFFD' to 0xDA,
            '\uFFFD' to 0xDB,
            '\uFFFD' to 0xDC,
            '\uFFFD' to 0xDD,
            '\uFFFD' to 0xDE,
            '\uFFFD' to 0xDF,
            '\u05D0' to 0xE0,
            '\u05D1' to 0xE1,
            '\u05D2' to 0xE2,
            '\u05D3' to 0xE3,
            '\u05D4' to 0xE4,
            '\u05D5' to 0xE5,
            '\u05D6' to 0xE6,
            '\u05D7' to 0xE7,
            '\u05D8' to 0xE8,
            '\u05D9' to 0xE9,
            '\u05DA' to 0xEA,
            '\u05DB' to 0xEB,
            '\u05DC' to 0xEC,
            '\u05DD' to 0xED,
            '\u05DE' to 0xEE,
            '\u05DF' to 0xEF,
            '\u05E0' to 0xF0,
            '\u05E1' to 0xF1,
            '\u05E2' to 0xF2,
            '\u05E3' to 0xF3,
            '\u05E4' to 0xF4,
            '\u05E5' to 0xF5,
            '\u05E6' to 0xF6,
            '\u05E7' to 0xF7,
            '\u05E8' to 0xF8,
            '\u05E9' to 0xF9,
            '\u05EA' to 0xFA,
            '\uFFFD' to 0xFB,
            '\uFFFD' to 0xFC,
            '\u200E' to 0xFD,
            '\u200F' to 0xFE,
            '\uFFFD' to 0xFF,
        )
  }

  /** Page 50 [Windows-1256] */
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

  /** Page 52 [Windows-1258] */
  public data object Windows1258 : Charset(52, "windows-1258") {
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
            '\uFFFD' to 0x8A,
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
            '\uFFFD' to 0x9A,
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
            '\u0102' to 0xC3,
            '\u00C4' to 0xC4,
            '\u00C5' to 0xC5,
            '\u00C6' to 0xC6,
            '\u00C7' to 0xC7,
            '\u00C8' to 0xC8,
            '\u00C9' to 0xC9,
            '\u00CA' to 0xCA,
            '\u00CB' to 0xCB,
            '\u0300' to 0xCC,
            '\u00CD' to 0xCD,
            '\u00CE' to 0xCE,
            '\u00CF' to 0xCF,
            '\u0110' to 0xD0,
            '\u00D1' to 0xD1,
            '\u0309' to 0xD2,
            '\u00D3' to 0xD3,
            '\u00D4' to 0xD4,
            '\u01A0' to 0xD5,
            '\u00D6' to 0xD6,
            '\u00D7' to 0xD7,
            '\u00D8' to 0xD8,
            '\u00D9' to 0xD9,
            '\u00DA' to 0xDA,
            '\u00DB' to 0xDB,
            '\u00DC' to 0xDC,
            '\u01AF' to 0xDD,
            '\u0303' to 0xDE,
            '\u00DF' to 0xDF,
            '\u00E0' to 0xE0,
            '\u00E1' to 0xE1,
            '\u00E2' to 0xE2,
            '\u0103' to 0xE3,
            '\u00E4' to 0xE4,
            '\u00E5' to 0xE5,
            '\u00E6' to 0xE6,
            '\u00E7' to 0xE7,
            '\u00E8' to 0xE8,
            '\u00E9' to 0xE9,
            '\u00EA' to 0xEA,
            '\u00EB' to 0xEB,
            '\u0301' to 0xEC,
            '\u00ED' to 0xED,
            '\u00EE' to 0xEE,
            '\u00EF' to 0xEF,
            '\u0111' to 0xF0,
            '\u00F1' to 0xF1,
            '\u0323' to 0xF2,
            '\u00F3' to 0xF3,
            '\u00F4' to 0xF4,
            '\u01A1' to 0xF5,
            '\u00F6' to 0xF6,
            '\u00F7' to 0xF7,
            '\u00F8' to 0xF8,
            '\u00F9' to 0xF9,
            '\u00FA' to 0xFA,
            '\u00FB' to 0xFB,
            '\u00FC' to 0xFC,
            '\u01B0' to 0xFD,
            '\u20AB' to 0xFE,
            '\u00FF' to 0xFF,
        )
  }

  //  /** Page 53 [KZ-1048: Kazakhstan] */
  //  KZ1048(52, "KZ-1048");

  //  /** Page 66 [Devanagari] */
  //  Devanagari(66),
  //
  //  /** Page 67 [Bengali] */
  //  Bengali(67),
  //
  //  /** Page 68 [Tamil] */
  //  Tamil(68),
  //
  //  /** Page 69 [Telugu] */
  //  Telugu(69),
  //
  //  /** Page 70 [Assamese] */
  //  Assamese(70),
  //
  //  /** Page 71 [Oriya] */
  //  Oriya(71),
  //
  //  /** Page 72 [Kannada] */
  //  Kannada(72),
  //
  //  /** Page 73 [Malayalam] */
  //  Malayalam(73),
  //
  //  /** Page 74 [Gujarati] */
  //  Gujarati(74),
  //
  //  /** Page 75 [Punjabi] */
  //  Punjabi(75),
  //
  //  /** Page 82 [Marathi] */
  //  Marathi(82)
}
