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

/** Page 38 [CP869: Greek] */
public data object IBM869 : Charset(38, "IBM869") {
  override val mapping: Map<Char, Int> =
      mapOf(
          '\uFFFD' to 0x80,
          '\uFFFD' to 0x81,
          '\uFFFD' to 0x82,
          '\uFFFD' to 0x83,
          '\uFFFD' to 0x84,
          '\uFFFD' to 0x85,
          '\u0386' to 0x86,
          '\uFFFD' to 0x87,
          '\u00B7' to 0x88,
          '\u00AC' to 0x89,
          '\u00A6' to 0x8A,
          '\u2018' to 0x8B,
          '\u2019' to 0x8C,
          '\u0388' to 0x8D,
          '\u2015' to 0x8E,
          '\u0389' to 0x8F,
          '\u038A' to 0x90,
          '\u03AA' to 0x91,
          '\u038C' to 0x92,
          '\uFFFD' to 0x93,
          '\uFFFD' to 0x94,
          '\u038E' to 0x95,
          '\u03AB' to 0x96,
          '\u00A9' to 0x97,
          '\u038F' to 0x98,
          '\u00B2' to 0x99,
          '\u00B3' to 0x9A,
          '\u03AC' to 0x9B,
          '\u00A3' to 0x9C,
          '\u03AD' to 0x9D,
          '\u03AE' to 0x9E,
          '\u03AF' to 0x9F,
          '\u03CA' to 0xA0,
          '\u0390' to 0xA1,
          '\u03CC' to 0xA2,
          '\u03CD' to 0xA3,
          '\u0391' to 0xA4,
          '\u0392' to 0xA5,
          '\u0393' to 0xA6,
          '\u0394' to 0xA7,
          '\u0395' to 0xA8,
          '\u0396' to 0xA9,
          '\u0397' to 0xAA,
          '\u00BD' to 0xAB,
          '\u0398' to 0xAC,
          '\u0399' to 0xAD,
          '\u00AB' to 0xAE,
          '\u00BB' to 0xAF,
          '\u2591' to 0xB0,
          '\u2592' to 0xB1,
          '\u2593' to 0xB2,
          '\u2502' to 0xB3,
          '\u2524' to 0xB4,
          '\u039A' to 0xB5,
          '\u039B' to 0xB6,
          '\u039C' to 0xB7,
          '\u039D' to 0xB8,
          '\u2563' to 0xB9,
          '\u2551' to 0xBA,
          '\u2557' to 0xBB,
          '\u255D' to 0xBC,
          '\u039E' to 0xBD,
          '\u039F' to 0xBE,
          '\u2510' to 0xBF,
          '\u2514' to 0xC0,
          '\u2534' to 0xC1,
          '\u252C' to 0xC2,
          '\u251C' to 0xC3,
          '\u2500' to 0xC4,
          '\u253C' to 0xC5,
          '\u03A0' to 0xC6,
          '\u03A1' to 0xC7,
          '\u255A' to 0xC8,
          '\u2554' to 0xC9,
          '\u2569' to 0xCA,
          '\u2566' to 0xCB,
          '\u2560' to 0xCC,
          '\u2550' to 0xCD,
          '\u256C' to 0xCE,
          '\u03A3' to 0xCF,
          '\u03A4' to 0xD0,
          '\u03A5' to 0xD1,
          '\u03A6' to 0xD2,
          '\u03A7' to 0xD3,
          '\u03A8' to 0xD4,
          '\u03A9' to 0xD5,
          '\u03B1' to 0xD6,
          '\u03B2' to 0xD7,
          '\u03B3' to 0xD8,
          '\u2518' to 0xD9,
          '\u250C' to 0xDA,
          '\u2588' to 0xDB,
          '\u2584' to 0xDC,
          '\u03B4' to 0xDD,
          '\u03B5' to 0xDE,
          '\u2580' to 0xDF,
          '\u03B6' to 0xE0,
          '\u03B7' to 0xE1,
          '\u03B8' to 0xE2,
          '\u03B9' to 0xE3,
          '\u03BA' to 0xE4,
          '\u03BB' to 0xE5,
          '\u03BC' to 0xE6,
          '\u03BD' to 0xE7,
          '\u03BE' to 0xE8,
          '\u03BF' to 0xE9,
          '\u03C0' to 0xEA,
          '\u03C1' to 0xEB,
          '\u03C3' to 0xEC,
          '\u03C2' to 0xED,
          '\u03C4' to 0xEE,
          '\u0384' to 0xEF,
          '\u00AD' to 0xF0,
          '\u00B1' to 0xF1,
          '\u03C5' to 0xF2,
          '\u03C6' to 0xF3,
          '\u03C7' to 0xF4,
          '\u00A7' to 0xF5,
          '\u03C8' to 0xF6,
          '\u0385' to 0xF7,
          '\u00B0' to 0xF8,
          '\u00A8' to 0xF9,
          '\u03C9' to 0xFA,
          '\u03CB' to 0xFB,
          '\u03B0' to 0xFC,
          '\u03CE' to 0xFD,
          '\u25A0' to 0xFE,
          '\u00A0' to 0xFF,
      )
}
