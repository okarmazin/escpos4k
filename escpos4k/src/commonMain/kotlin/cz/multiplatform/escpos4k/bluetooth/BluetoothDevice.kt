// SPDX-License-Identifier: Apache-2.0

package cz.multiplatform.escpos4k.bluetooth

public data class BluetoothDevice(
    public val address: String,
    public val name: String?,
    public val type: BluetoothType,
    public val uuids: List<String>,
    public val majorDeviceClass: Int,
    public val deviceClass: Int,
)
