package cz.multiplatform.escpos4k.usb

import kotlin.test.Test
import kotlin.test.assertNull
import kotlin.test.assertSame

class FindOutputEndpointTest {

  @Test
  fun likelyPrinterEndpoint_printerInterface_withBulkOut() {
    val printerInterface =
        UsbInterface(
            interfaceClass = UsbClass.Printer,
            name = null,
            bInterfaceNumber = 1,
            endpoints = bulkAndControl())
    val device =
        UsbDevice(
            "device1",
            "Printer1",
            "Epson",
            UsbClass.DefinedByInterface,
            1,
            listOf(printerInterface))

    assertSame(printerInterface.endpoints[1], device.findOutputEndpoint().orNull()!!)
  }

  @Test
  fun likelyPrinterEndpoint_printerInterface_withoutBulkOut() {
    val printerInterface =
        UsbInterface(
            interfaceClass = UsbClass.Printer,
            name = null,
            bInterfaceNumber = 1,
            endpoints = control())
    val device =
        UsbDevice(
            "device1",
            "Printer1",
            "Epson",
            UsbClass.DefinedByInterface,
            1,
            listOf(printerInterface))

    assertNull(device.findOutputEndpoint().orNull())
  }

  @Test
  fun likelyPrinterEndpoint_singleVendorSpecificInterface_withBulkOut() {
    val printerInterface =
        UsbInterface(
            interfaceClass = UsbClass.VendorSpecific,
            name = null,
            bInterfaceNumber = 1,
            endpoints = bulkAndControl())
    val device =
        UsbDevice(
            "device1",
            "Printer1",
            "Epson",
            UsbClass.DefinedByInterface,
            1,
            listOf(printerInterface))

    assertSame(printerInterface.endpoints[1], device.findOutputEndpoint().orNull()!!)
  }

  @Test
  fun likelyPrinterEndpoint_singleVendorSpecificInterface_withoutBulkOut() {
    val printerInterface =
        UsbInterface(
            interfaceClass = UsbClass.VendorSpecific,
            name = null,
            bInterfaceNumber = 1,
            endpoints = control())
    val device =
        UsbDevice(
            "device1",
            "Printer1",
            "Epson",
            UsbClass.DefinedByInterface,
            1,
            listOf(printerInterface))

    assertNull(device.findOutputEndpoint().orNull())
  }

  @Test
  fun likelyPrinterEndpoint_multipleVendorSpecificInterface_withBulkOut() {
    val printerInterfaces =
        listOf(
            UsbInterface(
                interfaceClass = UsbClass.VendorSpecific,
                name = null,
                bInterfaceNumber = 1,
                endpoints = control()),
            UsbInterface(
                interfaceClass = UsbClass.VendorSpecific,
                name = null,
                bInterfaceNumber = 2,
                endpoints = bulkAndControl()),
        )
    val device =
        UsbDevice("device1", "Printer1", "Epson", UsbClass.DefinedByInterface, 1, printerInterfaces)

    assertSame(printerInterfaces[1].endpoints[1], device.findOutputEndpoint().orNull()!!)
  }

  @Test
  fun likelyPrinterEndpoint_multipleVendorSpecificInterface_disqualified() {
    val printerInterfaces =
        listOf(
            UsbInterface(
                interfaceClass =
                    UsbClass.Image, // TODO check all of the other disqualifying classes
                name = null,
                bInterfaceNumber = 1,
                endpoints = control()),
            UsbInterface(
                interfaceClass = UsbClass.VendorSpecific,
                name = null,
                bInterfaceNumber = 2,
                endpoints = bulkAndControl()),
        )
    val device =
        UsbDevice("device1", "Printer1", "Epson", UsbClass.DefinedByInterface, 1, printerInterfaces)

    assertNull(device.findOutputEndpoint().orNull())
  }

  private fun bulkAndControl(): List<UsbEndpoint> =
      listOf(
          UsbEndpoint(1, UsbEndpoint.Type.Control, UsbEndpoint.Direction.Out, 1, 1, 1),
          UsbEndpoint(1, UsbEndpoint.Type.Bulk, UsbEndpoint.Direction.Out, 1, 1, 2),
      )

  private fun control(): List<UsbEndpoint> =
      listOf(
          UsbEndpoint(1, UsbEndpoint.Type.Control, UsbEndpoint.Direction.Out, 1, 1, 1),
      )
}
