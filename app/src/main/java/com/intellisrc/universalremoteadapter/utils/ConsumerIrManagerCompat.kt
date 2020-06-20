package com.intellisrc.universalremoteadapter.utils

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
class ConsumerIrManagerCompat @RequiresApi(api = Build.VERSION_CODES.KITKAT) constructor(
    context: Context
) : ConsumerIrManager() {
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private val service: android.hardware.ConsumerIrManager = context.getSystemService(Context.CONSUMER_IR_SERVICE) as android.hardware.ConsumerIrManager

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    override fun hasIrEmitter(): Boolean {
        return service.hasIrEmitter()
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun transmit(carrierFrequency: Int, pattern: IntArray?) {
        service.transmit(carrierFrequency, pattern)
    }

    @get:RequiresApi(api = Build.VERSION_CODES.KITKAT)
    override val carrierFrequencies: Array<android.hardware.ConsumerIrManager.CarrierFrequencyRange?>?
        get() = service.carrierFrequencies

}