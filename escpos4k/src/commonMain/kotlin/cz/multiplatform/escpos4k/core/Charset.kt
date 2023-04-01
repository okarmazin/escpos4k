/*
 *    Copyright 2023 Ondřej Karmazín
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

public enum class Charset(

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
    public val ianaName: String,
) {
  /** Page 0 [CP437: USA, Standard Europe] */
  CP437(0, "IBM437"),

  //  /** Page 1 [Katakana] */
  //  Katakana(1, TODO()),

  /** Page 2 [CP850: Multilingual] */
  CP850(2, "IBM850"),

  /** Page 3 [CP860: Portuguese] */
  CP860(3, "IBM860"),

  /** Page 4 [CP863: Canadian-French] */
  CP863(4, "IBM863"),

  /** Page 5 [CP865: Nordic] */
  CP865(5, "IBM865"),

  //  /** Page 6 [Hiragana] */
  //  Hiragana(6, TODO()),

  //  /** Page 7 [One-pass printing Kanji characters] */
  //  Kanji_1(7, TODO()),

  //  /** Page 8 [One-pass printing Kanji characters] */
  //  Kanji_2(8, TODO()),

  /** Page 11 [CP851: Greek] */
  CP851(11, "IBM851"),

  /** Page 12 [CP853: Turkish] */
  CP853(12, "IBM853"),

  //  /** Page 13 [CP857: Turkish] */
  //  CP857(13, TODO()),

  //  /** Page 14 [CP737: Greek] */
  //  CP737(14, TODO()),

  /** Page 15 [ISO8859-7: Greek] */
  ISO_8859_7(15, "ISO-8859-7"),

  /** Page 16 [Windows-1252] */
  Windows1252(16, "windows-1252"),

  /** Page 17 [CP866: Cyrillic #2] */
  CP866(17, "IBM866"),

  /** Page 18 [CP852: Latin 2] */
  CP852(18, "IBM852"),

  /** Page 19 [CP858: Euro] */
  CP858(19, "IBM00858"), // Note: two leading zeros

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

  /** Page 33 [CP775: Baltic Rim] */
  CP775(33, "IBM775"),

  /** Page 34 [CP855: Cyrillic] */
  CP855(34, "IBM855"),

  /** Page 35 [CP861: Icelandic] */
  CP861(35, "IBM861"),

  /** Page 36 [CP862: Hebrew] */
  CP862(36, "IBM862"),

  /** Page 37 [CP864: Arabic] */
  CP864(37, "IBM864"),

  /** Page 38 [CP869: Greek] */
  CP869(38, "IBM869"),

  /** Page 39 [ISO8859-2: Latin 2] */
  ISO8859_2(39, "ISO-8859-2"),

  /** Page 40 [ISO8859-15: Latin 9] */
  ISO8859_15(40, "ISO-8859-15"),

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
  Windows1250(45, "windows-1250"),

  /** Page 46 [Windows-1251] */
  Windows1251(46, "windows-1251"),

  /** Page 47 [Windows-1253] */
  Windows1253(47, "windows-1253"),

  /** Page 48 [Windows-1254] */
  Windows1254(48, "windows-1254"),

  /** Page 49 [Windows-1255] */
  Windows1255(49, "windows-1255"),

  /** Page 50 [Windows-1256] */
  Windows1256(50, "windows-1256"),

  /** Page 51 [Windows-1257] */
  Windows1257(51, "windows-1257"),

  /** Page 52 [Windows-1258] */
  Windows1258(52, "windows-1258"),

  /** Page 53 [KZ-1048: Kazakhstan] */
  KZ1048(52, "KZ-1048");

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

  //  TODO
  //  public fun encode(text: String): ByteArray = encode(text, this)

  public companion object {
    internal val default = values().first()
  }
}
