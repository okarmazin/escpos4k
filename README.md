# ESC/POS for Kotlin Multiplatform

<!-- TOC -->
* [ESC/POS for Kotlin Multiplatform](#escpos-for-kotlin-multiplatform)
    * [What is ESC/POS?](#what-is-escpos)
    * [What does this library do?](#what-does-this-library-do)
    * [Feature/Platform matrix](#featureplatform-matrix)
<!-- TOC -->

#### What is ESC/POS?

ESC/POS is a set of printer commands developed by Epson for use in thermal printers.
It has been adopted as a "standard" by virtually all receipt printer manufacturers across the globe.

ESC/POS is similar in this regard to the QR code - QR codes
were [invented by Denso Wave](https://en.wikipedia.org/wiki/QR_code)
for use in their automotive manufacturing plants, but are now used just about everywhere.

Similarly, most thermal printers - at least those intended for printing receipts and similar items -
work with a subset of ESC/POS. The set of _actually supported_ ESC/POS commands varies among
printer OEMs and printer models.

#### What does this library do?

This library aims to provide basic ESC/POS command generator as well as device connection support for
various device connection types: Bluetooth, USB, TCP etc.

Being a programming exercise first and foremost, this library purposefully handles as much work as possible in
Common Kotlin. It is not a wrapper around established ESC/POS libraries, but rather a green field implementation.

#### Feature/Platform matrix

The following table summarizes connector implementation status on each platform.

- :heavy_check_mark: - Implemented
- :x: - Not yet implemented
- `N/A` - Technology not available on that platform

|           | Android            | iOS   |
|-----------|--------------------|-------|
| Bluetooth | :heavy_check_mark: | :x:   |
| USB       | :heavy_check_mark: | `N/A` |
| TCP       | :x:                | :x:   |
