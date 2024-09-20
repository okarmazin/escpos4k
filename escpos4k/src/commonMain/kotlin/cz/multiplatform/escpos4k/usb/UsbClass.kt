/*
 *    Copyright 2024 escpos4k authors
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

package cz.multiplatform.escpos4k.usb

public sealed interface DeviceClass

public sealed interface InterfaceClass

/** USB classes as defined by [https://www.usb.org/defined-class-codes](https://www.usb.org/defined-class-codes). */
public sealed class UsbClass(internal val raw: UByte) {

  internal companion object {
    private val intToClass: Map<Int, UsbClass> =
        mapOf(
            0x00 to DefinedByInterface,
            0x01 to Audio,
            0x02 to CommAndCdc,
            0x03 to Hid,
            0x05 to Physical,
            0x06 to Image,
            0x07 to Printer,
            0x08 to MassStorage,
            0x09 to Hub,
            0x0A to CdcData,
            0x0B to SmartCard,
            0x0D to ContentSecurity,
            0x0E to Video,
            0x0F to PersonalHealthcare,
            0x10 to AudioVideo,
            0x11 to Billboard,
            0xDC to DiagnosticDevice,
            0xE0 to WirelessController,
            0xEF to Miscellaneous,
            0xFE to ApplicationSpecific,
            0xFF to VendorSpecific,
        )

    // Called with the value obtained from a platform device. We assume it is valid.
    fun fromInt(clazz: Int): UsbClass = intToClass[clazz]!!
  }

  /**
   * Base Class 00h (Device)
   *
   * This base class is defined to be used in Device Descriptors to indicate that class information should be determined
   * from the Interface Descriptors in the device. There is one class code definition in this base class. All other
   * values are reserved.
   *
   * This value is also used in Interface Descriptors to indicate a null class code triple.
   */
  public object DefinedByInterface : UsbClass(0x00u), DeviceClass

  /**
   * Base Class 01h (Audio)
   *
   * This base class is defined for Audio capable devices that conform to the Audio Device Class Specification found on
   * the USB-IF website. That specification defines the usable set of SubClass and Protocol values. Values outside that
   * defined spec are reserved. These class codes may only be used in Interface Descriptors.
   */
  public object Audio : UsbClass(0x01u), InterfaceClass

  /**
   * Base Class 02h (Communications and CDC Control)
   *
   * This base class is defined for devices that conform to the Communications Device Class Specification found on the
   * USB-IF website. That specification defines the usable set of SubClass and Protocol values. Values outside of that
   * defined spec are reserved. Note that the Communication Device Class spec requires some class code values (triples)
   * to be used in Device Descriptors and some to be used in Interface Descriptors.
   */
  public object CommAndCdc : UsbClass(0x02u), InterfaceClass, DeviceClass

  /**
   * Base Class 03h (HID – Human Interface Device)
   *
   * This base class is defined for devices that conform to the HID Device Class Specification found on the USB-IF
   * website. That specification defines the usable set of SubClass and Protocol values. Values outside of that defined
   * spec are reserved. These class codes can only be used in Interface Descriptors.
   */
  public object Hid : UsbClass(0x03u), InterfaceClass

  /**
   * Base Class 05h (Physical)
   *
   * This base class is defined for devices that conform to the Physical Device Class Specification found on the USB-IF
   * website. That specification defines the usable set of SubClass and Protocol values. Values outside of that defined
   * spec are reserved. These class codes can only be used in Interface Descriptors.
   */
  public object Physical : UsbClass(0x05u), InterfaceClass

  /**
   * Base Class 06h (Still Imaging)
   *
   * This base class is defined for devices that conform to the Imaging Device Class Specification found on the USB-IF
   * website. That specification defines the usable set of SubClass and Protocol values. Values outside of that defined
   * spec are reserved.
   */
  public object Image : UsbClass(0x06u), InterfaceClass

  /**
   * Base Class 07h (Printer)
   *
   * This base class is defined for devices that conform to the Printer Device Class Specification found on the USB-IF
   * website. That specification defines the usable set of SubClass and Protocol values. Values outside of that defined
   * spec are reserved. These class codes can only be used in Interface Descriptors.
   */
  public object Printer : UsbClass(0x07u), InterfaceClass

  /**
   * Base Class 08h (Mass Storage)
   *
   * This base class is defined for devices that conform to the Mass Storage Device Class Specification found on the
   * USB-IF website. That specification defines the usable set of SubClass and Protocol values. Values outside of that
   * defined spec are reserved. These class codes can only be used in Interface Descriptors.
   */
  public object MassStorage : UsbClass(0x08u), InterfaceClass

  /**
   * Base Class 09h (Hub)
   *
   * This base class is defined for devices that are USB hubs and conform to the definition in the USB specification.
   * That specification defines the complete triples as shown below. All other values are reserved. These class codes
   * can only be used in Device Descriptors.
   */
  public object Hub : UsbClass(0x09u), DeviceClass

  /**
   * Base Class 0Ah (CDC-Data)
   *
   * This base class is defined for devices that conform to the Communications Device Class Specification found on the
   * USB-IF website. That specification defines the usable set of SubClass and Protocol values. Values outside of that
   * defined spec are reserved. These class codes can only be used in Interface Descriptors.
   */
  public object CdcData : UsbClass(0x0Au), InterfaceClass

  /**
   * Base Class 0Bh (Smart Card)
   *
   * This base class is defined for devices that conform to the Smart Card Device Class Specification found on the
   * USB-IF website. That specification defines the usable set of SubClass and Protocol values.Values outside of that
   * defined spec are reserved. These class codes can only be used in Interface Descriptors.
   */
  public object SmartCard : UsbClass(0x0Bu), InterfaceClass

  /**
   * Base Class 0Dh (Content Security)
   *
   * This base class is defined for devices that conform to the Content Security Device Class Specification found on the
   * USB-IF website. That specification defines the usable set of SubClass and Protocol values. Values outside of that
   * defined spec are reserved. These class codes can only be used in Interface Descriptors.
   */
  public object ContentSecurity : UsbClass(0x0Du), InterfaceClass

  /**
   * Base Class 0Eh (Video)
   *
   * This base class is defined for devices that conform to the Video Device Class Specification found on the USB-IF
   * website. That specification defines the usable set of SubClass and Protocol values. Values outside of that defined
   * spec are reserved. These class codes can only be used in Interface Descriptors.
   */
  public object Video : UsbClass(0x0Eu), InterfaceClass

  /**
   * Base Class 0Fh (Personal Healthcare)
   *
   * This base class is defined for devices that conform to the Personal Healthcare Device Class Specification found on
   * the USB-IF website. That specification defines the usable set of SubClass and Protocol values. Values outside of
   * that defined spec are reserved. These class codes should only be used in Interface Descriptors.
   */
  public object PersonalHealthcare : UsbClass(0x0Fu), InterfaceClass

  /**
   * Base Class 10h (Audio/Video Devices)
   *
   * The USB Audio/Video (AV) Device Class Definition describes the methods used to communicate with devices or
   * functions embedded in composite devices that are used to manipulate audio, video, voice, and all image- and
   * sound-related functionality. That specification defines the usable set of SubClass and Protocol values. Values
   * outside of that defined spec are reserved. These class codes can only be used in Interface Descriptors.
   */
  public object AudioVideo : UsbClass(0x10u), InterfaceClass

  /**
   * Base Class 11h (Billboard Device)
   *
   * This base class is defined for devices that conform to the Billboard Device Class Specification found on the USB-IF
   * website. That specification defines the usable set of SubClass and Protocol values. Values outside of that defined
   * spec are reserved. These class codes can only be used in Device Descriptors.
   */
  public object Billboard : UsbClass(0x11u), DeviceClass, InterfaceClass

  /**
   * Base Class 12h (USB Type-C Bridge Device)
   *
   * This base class is defined for devices that conform to the USB Type-C Bridge Device Class Specification found on
   * the USB-IF website. That specification defines the usable set of SubClass and Protocol values. Values outside of
   * that defined spec are reserved. These class codes can only be used in Interface Descriptors.
   */
  public object UsbCBridge : UsbClass(0x12u), InterfaceClass

  /**
   * Base Class 3Ch (I3C Device Class)
   *
   * This base class is defined for devices that conform to the USB I3C Device Class Specification found on this USB-IF
   * website. That specification defines the usable set of SubClass and Protocol values. Values outside of that defined
   * spec are reserved. These class codes can only be used in Interface Descriptions.
   */
  public object I3C : UsbClass(0x3Cu), InterfaceClass

  /**
   * Base Class DCh (Diagnostic Device)
   *
   * This base class is defined for devices that diagnostic devices. This class code can be used in Device or Interface
   * Descriptors. Trace is a form of debugging where processor or system activity is made externally visible in
   * real-time or stored and later retrieved for viewing by an applications developer, applications program, or,
   * external equipment specializing observing system activity. Design for Debug or Test (Dfx). This refers to a logic
   * block that provides debug or test support (E.g. via Test Access Port (TAP)). DvC: Debug Capability on the USB
   * device (Device Capability)
   */
  public object DiagnosticDevice : UsbClass(0xDCu), InterfaceClass, DeviceClass

  /**
   * Base Class E0h (Wireless Controller)
   *
   * This base class is defined for devices that are Wireless controllers. Values not shown in the table below are
   * reserved. These class codes are to be used in Interface Descriptors, with the exception of the Bluetooth class code
   * which can also be used in a Device Descriptor.
   */
  public object WirelessController : UsbClass(0xE0u), InterfaceClass

  /**
   * Base Class EFh (Miscellaneous)
   *
   * This base class is defined for miscellaneous device definitions. Values not shown in the table below are reserved.
   * The use of these class codes (Device or Interface descriptor) are specifically annotated in each entry below.
   */
  public object Miscellaneous : UsbClass(0xEFu), InterfaceClass, DeviceClass

  /**
   * Base Class FEh (Application Specific)
   *
   * This base class is defined for devices that conform to several class specifications found on the USB-IF website.
   * That specification defines the usable set of SubClass and Protocol values. Values outside of that defined spec are
   * reserved. These class codes can only be used in Interface Descriptors.
   */
  public object ApplicationSpecific : UsbClass(0xFEu), InterfaceClass

  /**
   * Base Class FFh (Vendor Specific)
   *
   * This base class is defined for vendors to use as they please. These class codes can be used in both Device and
   * Interface Descriptors.
   */
  public object VendorSpecific : UsbClass(0xFFu), InterfaceClass, DeviceClass
}
