package com.intellisrc.universalremoteadapter.utils

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.intellisrc.universalremoteadapter.utils.ConsumerIrManager.Companion.getSupportConsumerIrManager
import timber.log.Timber

abstract class ConsumerIrManager {
    open fun hasIrEmitter(): Boolean {
        return false
    }

    open fun transmit(carrierFrequency: Int, pattern: IntArray?) {
        transmit(carrierFrequency, pattern)
    }

    fun transmit(command: IrCommand) {
        transmit(command.frequency, command.pattern)
    }

    open val carrierFrequencies: Array<android.hardware.ConsumerIrManager.CarrierFrequencyRange?>?
        get() = null

    companion object {
        private const val TAG = "ConsumerIrManager"

        @RequiresApi(Build.VERSION_CODES.KITKAT)
        @JvmStatic
        fun getSupportConsumerIrManager(context: Context?): ConsumerIrManager {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                Timber.tag(TAG).d("ConsumerIrManagerCompat")
                return ConsumerIrManagerCompat(context!!)
            }
            val consumerIrManagerSamsung =
                ConsumerIrManagerSamsung.getIrdaManager(context!!)
            if (consumerIrManagerSamsung != null) {
                Timber.tag(TAG).d("ConsumerIrManagerSamsung")
                return consumerIrManagerSamsung
            }
            return object : ConsumerIrManager() {}
        }
    }
}