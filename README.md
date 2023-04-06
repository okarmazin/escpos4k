# ESC/POS for Kotlin Multiplatform

<!-- TOC -->

* [ESC/POS for Kotlin Multiplatform](#escpos-for-kotlin-multiplatform)
    * [Setup](#setup)
    * [TLDR Android Bluetooth example](#tldr-android-bluetooth-example)
    * [What is ESC/POS?](#what-is-escpos)
    * [What does this library do?](#what-does-this-library-do)
    * [Feature/Platform matrix](#featureplatform-matrix)

<!-- TOC -->

#### Setup
The library is available at Maven Central.

```kotlin
repositories {
    mavenCentral()
}
```
```kotlin
implementation("cz.multiplatform.escpos4k:escpos4k:0.3.0")
```

#### TLDR Android Bluetooth example

```kotlin
suspend fun awaitPrint(): MyError? {
  val btManager = BluetoothPrinterManager(requireContext())
  val device = btManager.pairedPrinters().firstOrNull() 
      ?: return MyError.NotFound
  val connection = btManager.openConnection(device)?.takeIf { it.isOpen } // (1)
      ?: return MynError.Disconnected
  
  val config = PrinterConfiguration(charactersPerLine = 32)
  val libraryError: PrintError? = connection.print(configuration) {       // (2)
    // MULTIPLE TEXT ALIGNMENTS PER LINE
    line("Famous bridges:")
    charset(Charset.CP865) // Can encode Ø, but not ů
    segmentedLine(
      LineSegment("Øresundsbroen", TextAlignment.LEFT),
      LineSegment("7845m", TextAlignment.RIGHT),
    )
    charset(Charset.CP852) // Can encode ů, but not Ø
    segmentedLine(
      LineSegment("Karlův most", TextAlignment.LEFT),
      LineSegment("515m", TextAlignment.RIGHT),
    )
    
    // STANDARD TEXT STYLING, CAN BE SCOPED USING
    // with-STYLE BUILDERS
    withTextSize(width = 2, height = 3) {
      line("Me big!")
    }
    line("Me small again!")
    
    bold(true)
    line("Normal and bald. Wait.. I wanted BOLD!")
    bold(false)

    // BARCODES - SUPPORTS A NUMBER OF 1D and 2D CODES
    val qrCode: Either<QRCodeError, QRCodeSpec> = 
        BarcodeSpec.QRCodeSpec("Hello from the QR Code!")
    qrCode
      .onRight(::barcode)
      .onLeft { err ->
        line("Could not construct QR code:")
        line(err.toString())
    }
  }
  
  return libraryError?.let(::mapToMyError)
}

fun mapToMyError(libraryError: PrintError): MyError {
    // ...
}
```

```kotlin
(1) Use the PrinterManager to open a Bluetooth connection
(2) Print using the CommandBuilder
```

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

|                         | Android            | iOS                | macOS              |
|-------------------------|--------------------|--------------------|--------------------|
| **Command builder**     | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: |
| **Bluetooth connector** | :heavy_check_mark: | `N/A`              | :x:                |
| **USB connector**       | :heavy_check_mark: | `N/A`              | :x:                |
| **TCP connector**       | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: |
