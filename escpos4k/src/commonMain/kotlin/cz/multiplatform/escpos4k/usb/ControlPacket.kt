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

/**
 * The 8-byte Setup Packet for a USB Control Transfer request.
 *
 * It is composed of the following information:
 *
 * 1. REQUEST TYPE (1 byte)
 *
 *    Bit-mapped byte describing transfer direction, type of request, and recipient.
 *
 *    The bit mask is as follows:
 *    ```
 *    | bit range |    description     | values                   |
 *    |:---------:|:------------------:|:------------------------:|
 *    |     7     | Transfer direction | 0 = OUT (Host -> Device) |
 *    |           |                    | 1 = IN (Device -> Host)  |
 *    |-----------|--------------------|--------------------------|
 *    |   6 - 5   |        Type        | 0 = Standard             |
 *    |           |                    | 1 = Class                |
 *    |           |                    | 2 = Vendor               |
 *    |           |                    | 3 = Reserved             |
 *    |-----------|--------------------|--------------------------|
 *    |   4 - 0   |     Recipient      | 0 = Device               |
 *    |           |                    | 1 = Interface            |
 *    |           |                    | 2 = Endpoint             |
 *    |           |                    | 3 = Other                |
 *    |           |                    | 4..31 = Reserved         |
 *    ```
 *
 * 2. REQUEST (1 byte)
 *
 *    The request being made. The meaning of this value is based on the declared recipient from
 *    request type.
 *
 * 3. ARGUMENT: VALUE (2 bytes)
 *
 *    Requests can have two arguments: Value and Index (used both independently and together, based
 *    on the request definition.)
 *
 *    E.g. Feature Selector for a SET_FEATURE request; Device Address for a SET_ADDRESS etc.
 *
 * 4. ARGUMENT: INDEX (2 bytes)
 *
 *    Requests can have two arguments: Value and Index (used both independently and together, based
 *    on the request definition.)
 *
 *    E.g. Interface index for requests directed at an Interface.
 *
 * 5. DATA LENGTH (2 bytes)
 *
 * The length of data to be received in the optional Data phase of the Control Transfer.
 *
 * The length is well-defined for Control Transfer requests.
 */
public class SetupPacket(
    /**
     * Bit-mapped byte describing transfer direction, type of request, and recipient.
     *
     * The bit mask is as follows:
     * ```
     * | bit range |    description     | values                   |
     * |:---------:|:------------------:|:------------------------:|
     * |     7     | Transfer direction | 0 = OUT (Host -> Device) |
     * |           |                    | 1 = IN (Device -> Host)  |
     * |-----------|--------------------|--------------------------|
     * |   6 - 5   |        Type        | 0 = Standard             |
     * |           |                    | 1 = Class                |
     * |           |                    | 2 = Vendor               |
     * |           |                    | 3 = Reserved             |
     * |-----------|--------------------|--------------------------|
     * |   4 - 0   |     Recipient      | 0 = Device               |
     * |           |                    | 1 = Interface            |
     * |           |                    | 2 = Endpoint             |
     * |           |                    | 3 = Other                |
     * |           |                    | 4..31 = Reserved         |
     * ```
     */
    public val requestType: UByte,

    /**
     * The request being made. The meaning of this value is based on the declared recipient from
     * [requestType].
     */
    public val request: UByte,

    /**
     * Request argument: Value
     *
     * Requests can have two arguments: Value and Index (used both independently and together, based
     * on the request definition.)
     *
     * E.g. Feature Selector for a SET_FEATURE request; Device Address for a SET_ADDRESS etc.
     *
     * @see argIndex
     */
    public val argValue: UShort,

    /**
     * Request argument: Index
     *
     * Requests can have two arguments: Value and Index (used both independently and together, based
     * on the request definition.)
     *
     * E.g. Interface index for requests directed at an Interface.
     *
     * @see argValue
     */
    public val argIndex: UShort,

    /**
     * The length of data to be received in the optional Data phase of the Control Transfer.
     *
     * The length is well-defined for Control Transfer requests.
     */
    public val dataLength: UShort,
)
