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

  //  /** Page 41 [CP1098: Farsi] */
  //  CP1098(41, TODO()),

  //  /** Page 42 [CP1118: Lithuanian] */
  //  CP1118(42),
  //
  //  /** Page 43 [CP1119: Lithuanian] */
  //  CP1119(43),

  //  /** Page 44 [CP1125: Ukrainian] */
  //  CP1125(44),

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
