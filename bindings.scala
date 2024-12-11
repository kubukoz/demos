//> using target.platform native
package libhidapi

import _root_.scala.scalanative.unsafe.*
import _root_.scala.scalanative.unsigned.*
import _root_.scala.scalanative.libc.*
import _root_.scala.scalanative.*

object aliases:
  import _root_.libhidapi.aliases.*
  import _root_.libhidapi.structs.*
  type size_t = libc.stddef.size_t

  object size_t:
    val _tag: Tag[size_t] = summon[Tag[libc.stddef.size_t]]
    inline def apply(inline o: libc.stddef.size_t): size_t = o
    extension (v: size_t) inline def value: libc.stddef.size_t = v

  type wchar_t = libc.stddef.wchar_t

  object wchar_t:
    val _tag: Tag[wchar_t] = summon[Tag[libc.stddef.wchar_t]]
    inline def apply(inline o: libc.stddef.wchar_t): wchar_t = o
    extension (v: wchar_t) inline def value: libc.stddef.wchar_t = v

object structs:
  import _root_.libhidapi.aliases.*
  import _root_.libhidapi.structs.*

  /** [bindgen] header: include/hidapi.h
    */
  opaque type hid_device = CStruct0
  object hid_device:
    given _tag: Tag[hid_device] = Tag.materializeCStruct0Tag

  /** [bindgen] header: include/hidapi.h
    */
  opaque type hid_device_ = CStruct0
  object hid_device_ :
    given _tag: Tag[hid_device_] = Tag.materializeCStruct0Tag

  /** hidapi info structure
    *
    * [bindgen] header: include/hidapi.h
    */
  opaque type hid_device_info = CStruct11[CString, CUnsignedShort, CUnsignedShort, Ptr[
    wchar_t
  ], CUnsignedShort, Ptr[wchar_t], Ptr[wchar_t], CUnsignedShort, CUnsignedShort, CInt, Ptr[Byte]]

  object hid_device_info:

    given _tag: Tag[hid_device_info] = Tag
      .materializeCStruct11Tag[CString, CUnsignedShort, CUnsignedShort, Ptr[
        wchar_t
      ], CUnsignedShort, Ptr[wchar_t], Ptr[wchar_t], CUnsignedShort, CUnsignedShort, CInt, Ptr[
        Byte
      ]]

    def apply(
    )(
      using Zone
    ): Ptr[hid_device_info] = scala.scalanative.unsafe.alloc[hid_device_info](1)

    def apply(
      path: CString,
      vendor_id: CUnsignedShort,
      product_id: CUnsignedShort,
      serial_number: Ptr[wchar_t],
      release_number: CUnsignedShort,
      manufacturer_string: Ptr[wchar_t],
      product_string: Ptr[wchar_t],
      usage_page: CUnsignedShort,
      usage: CUnsignedShort,
      interface_number: CInt,
      next: Ptr[hid_device_info],
    )(
      using Zone
    ): Ptr[hid_device_info] =
      val ____ptr = apply()
      (!____ptr).path = path
      (!____ptr).vendor_id = vendor_id
      (!____ptr).product_id = product_id
      (!____ptr).serial_number = serial_number
      (!____ptr).release_number = release_number
      (!____ptr).manufacturer_string = manufacturer_string
      (!____ptr).product_string = product_string
      (!____ptr).usage_page = usage_page
      (!____ptr).usage = usage
      (!____ptr).interface_number = interface_number
      (!____ptr).next = next
      ____ptr

    extension (struct: hid_device_info)
      def path: CString = struct._1
      def path_=(value: CString): Unit = !struct.at1 = value
      def vendor_id: CUnsignedShort = struct._2
      def vendor_id_=(value: CUnsignedShort): Unit = !struct.at2 = value
      def product_id: CUnsignedShort = struct._3
      def product_id_=(value: CUnsignedShort): Unit = !struct.at3 = value
      def serial_number: Ptr[wchar_t] = struct._4
      def serial_number_=(value: Ptr[wchar_t]): Unit = !struct.at4 = value
      def release_number: CUnsignedShort = struct._5
      def release_number_=(value: CUnsignedShort): Unit = !struct.at5 = value
      def manufacturer_string: Ptr[wchar_t] = struct._6
      def manufacturer_string_=(value: Ptr[wchar_t]): Unit = !struct.at6 = value
      def product_string: Ptr[wchar_t] = struct._7
      def product_string_=(value: Ptr[wchar_t]): Unit = !struct.at7 = value
      def usage_page: CUnsignedShort = struct._8
      def usage_page_=(value: CUnsignedShort): Unit = !struct.at8 = value
      def usage: CUnsignedShort = struct._9
      def usage_=(value: CUnsignedShort): Unit = !struct.at9 = value
      def interface_number: CInt = struct._10
      def interface_number_=(value: CInt): Unit = !struct.at10 = value
      def next: Ptr[hid_device_info] = struct._11.asInstanceOf[Ptr[hid_device_info]]
      def next_=(value: Ptr[hid_device_info]): Unit = !struct.at11 = value.asInstanceOf[Ptr[Byte]]

@extern
private[libhidapi] object extern_functions:
  import _root_.libhidapi.aliases.*
  import _root_.libhidapi.structs.*

  /** Close a HID device.
    *
    * [bindgen] header: include/hidapi.h
    */
  def hid_close(device: Ptr[hid_device]): Unit = extern

  /** Enumerate the HID Devices.
    *
    * [bindgen] header: include/hidapi.h
    */
  def hid_enumerate(vendor_id: CUnsignedShort, product_id: CUnsignedShort): Ptr[hid_device_info] =
    extern

  /** Get a string describing the last error which occurred.
    *
    * [bindgen] header: include/hidapi.h
    */
  def hid_error(device: Ptr[hid_device]): Ptr[wchar_t] = extern

  /** Finalize the HIDAPI library.
    *
    * [bindgen] header: include/hidapi.h
    */
  def hid_exit(): CInt = extern

  /** Free an enumeration Linked List
    *
    * [bindgen] header: include/hidapi.h
    */
  def hid_free_enumeration(devs: Ptr[hid_device_info]): Unit = extern

  /** Get a feature report from a HID device.
    *
    * [bindgen] header: include/hidapi.h
    */
  def hid_get_feature_report(device: Ptr[hid_device], data: Ptr[CUnsignedChar], length: size_t)
    : CInt = extern

  /** Get a string from a HID device, based on its string index.
    *
    * [bindgen] header: include/hidapi.h
    */
  def hid_get_indexed_string(
    device: Ptr[hid_device],
    string_index: CInt,
    string: Ptr[wchar_t],
    maxlen: size_t,
  ): CInt = extern

  /** Get The Manufacturer String from a HID device.
    *
    * [bindgen] header: include/hidapi.h
    */
  def hid_get_manufacturer_string(device: Ptr[hid_device], string: Ptr[wchar_t], maxlen: size_t)
    : CInt = extern

  /** Get The Product String from a HID device.
    *
    * [bindgen] header: include/hidapi.h
    */
  def hid_get_product_string(device: Ptr[hid_device], string: Ptr[wchar_t], maxlen: size_t): CInt =
    extern

  /** Get The Serial Number String from a HID device.
    *
    * [bindgen] header: include/hidapi.h
    */
  def hid_get_serial_number_string(device: Ptr[hid_device], string: Ptr[wchar_t], maxlen: size_t)
    : CInt = extern

  /** Initialize the HIDAPI library.
    *
    * [bindgen] header: include/hidapi.h
    */
  def hid_init(): CInt = extern

  /** Open a HID device using a Vendor ID (VID), Product ID (PID) and optionally a serial number.
    *
    * [bindgen] header: include/hidapi.h
    */
  def hid_open(vendor_id: CUnsignedShort, product_id: CUnsignedShort, serial_number: Ptr[wchar_t])
    : Ptr[hid_device] = extern

  /** Open a HID device by its path name.
    *
    * [bindgen] header: include/hidapi.h
    */
  def hid_open_path(path: CString): Ptr[hid_device] = extern

  /** Read an Input report from a HID device.
    *
    * [bindgen] header: include/hidapi.h
    */
  def hid_read(device: Ptr[hid_device], data: Ptr[CUnsignedChar], length: size_t): CInt = extern

  /** Read an Input report from a HID device with timeout.
    *
    * [bindgen] header: include/hidapi.h
    */
  def hid_read_timeout(
    dev: Ptr[hid_device],
    data: Ptr[CUnsignedChar],
    length: size_t,
    milliseconds: CInt,
  ): CInt = extern

  /** Send a Feature report to the device.
    *
    * [bindgen] header: include/hidapi.h
    */
  def hid_send_feature_report(device: Ptr[hid_device], data: Ptr[CUnsignedChar], length: size_t)
    : CInt = extern

  /** Set the device handle to be non-blocking.
    *
    * [bindgen] header: include/hidapi.h
    */
  def hid_set_nonblocking(device: Ptr[hid_device], nonblock: CInt): CInt = extern

  /** Write an Output report to a HID device.
    *
    * [bindgen] header: include/hidapi.h
    */
  def hid_write(device: Ptr[hid_device], data: Ptr[CUnsignedChar], length: size_t): CInt = extern

object functions:
  import _root_.libhidapi.aliases.*
  import _root_.libhidapi.structs.*
  import extern_functions.*
  export extern_functions.*

object types:
  export _root_.libhidapi.structs.*
  export _root_.libhidapi.aliases.*

object all:
  export _root_.libhidapi.aliases.size_t
  export _root_.libhidapi.aliases.wchar_t
  export _root_.libhidapi.structs.hid_device
  export _root_.libhidapi.structs.hid_device_
  export _root_.libhidapi.structs.hid_device_info
  export _root_.libhidapi.functions.hid_close
  export _root_.libhidapi.functions.hid_enumerate
  export _root_.libhidapi.functions.hid_error
  export _root_.libhidapi.functions.hid_exit
  export _root_.libhidapi.functions.hid_free_enumeration
  export _root_.libhidapi.functions.hid_get_feature_report
  export _root_.libhidapi.functions.hid_get_indexed_string
  export _root_.libhidapi.functions.hid_get_manufacturer_string
  export _root_.libhidapi.functions.hid_get_product_string
  export _root_.libhidapi.functions.hid_get_serial_number_string
  export _root_.libhidapi.functions.hid_init
  export _root_.libhidapi.functions.hid_open
  export _root_.libhidapi.functions.hid_open_path
  export _root_.libhidapi.functions.hid_read
  export _root_.libhidapi.functions.hid_read_timeout
  export _root_.libhidapi.functions.hid_send_feature_report
  export _root_.libhidapi.functions.hid_set_nonblocking
  export _root_.libhidapi.functions.hid_write
