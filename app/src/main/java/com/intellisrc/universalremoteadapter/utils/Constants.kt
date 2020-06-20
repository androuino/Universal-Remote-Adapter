package com.intellisrc.universalremoteadapter.utils

import java.util.*

/**
 * Created by josapedmoreno on 2017/05/27.
 */

interface Constants {
    companion object {
        // Message types sent from the BluetoothChatService Handler
        val MESSAGE_STATE_CHANGE: Int
            get() = 1
        val MESSAGE_READ: Int
            get() = 2
        val MESSAGE_WRITE: Int
            get() = 3
        val MESSAGE_DEVICE_NAME: Int
            get() = 4
        val MESSAGE_TOAST: Int
            get() = 5

        val ANDROID_DEVICE : String
            get() = "ANDROID_DEVICE"
        val OTHER_DEVICE : String
            get() = "OTHER_DEVICE"

        // Key names received from the BluetoothChatService Handler
        val DEVICE_NAME: String
            get() = "device_name"
        val TOAST: String
            get() = "toast"

        // UUIDs
        val ANDROID_UUID : UUID
            get() {
                return UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
            }
        val OTHER_UUID : UUID
            get() {
                return UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66")
            }
    }
}