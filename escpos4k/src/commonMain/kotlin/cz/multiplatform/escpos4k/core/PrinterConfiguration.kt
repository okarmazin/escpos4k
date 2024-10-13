// SPDX-License-Identifier: Apache-2.0

package cz.multiplatform.escpos4k.core

/** A collection of attributes describing the basic properties of a printer. */
@ExperimentalEscPosApi
public data class PrinterConfiguration(
    //    /**
    //     * Dots Per Inch - resolution of this printer. The typical value is `203 DPI == 8 Dots Per
    // mm`.
    //     *
    //     * **NOTE**: All other dimensions are measured in millimetres.
    //     */
    //    val dpi: Int,

    /**
     * How many single-width characters can the printer put on a single line when using the factory-default font.
     *
     * For printers using the 57mm paper width, this is usually 32 or 42.
     *
     * Used in:
     * - The layout algorithm of column-based command builder. The algorithm needs to know the total length of a line to
     *   be able to distribute the space among the columns.
     */
    val charactersPerLine: Int

    //    /**
    //     * The total printable width in millimetres. In the typical case this number will be about
    //     * approximately `<paper width in mm> - 10` (printer leaves 5 mm margin on each side).
    //     *
    //     * Why is this important?
    //     */
    //    val effectivePrintingWidthMM: Int,
)
