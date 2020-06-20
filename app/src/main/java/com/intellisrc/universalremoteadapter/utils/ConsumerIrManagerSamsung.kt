package com.intellisrc.universalremoteadapter.utils

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

class ConsumerIrManagerSamsung private constructor(private val irdaService: Any) :
    ConsumerIrManager() {
    private val writeIrSendMethod: Method?
    private fun rawWrite(code: String) {
        try {
            writeIrSendMethod!!.invoke(irdaService, code)
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
    }

    override fun hasIrEmitter(): Boolean {
        return writeIrSendMethod != null
    }

    override fun transmit(carrierFrequency: Int, pattern: IntArray?) {
        if (!hasIrEmitter()) return
        val stringBuilder = StringBuilder()
        stringBuilder.append(carrierFrequency)
        for (bit in pattern!!) {
            stringBuilder.append(',')
            stringBuilder.append(bit * carrierFrequency / MICRO_SECONDS_IN_A_SECOND)
        }
        rawWrite(stringBuilder.toString())
    }

    // 36khz - 40khz
    override val carrierFrequencies: Array<android.hardware.ConsumerIrManager.CarrierFrequencyRange?>?
        get() = null // 36khz - 40khz

    companion object {
        const val IRDA_SERVICE = "irda"
        const val MICRO_SECONDS_IN_A_SECOND = 1000000

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        fun getIrdaManager(applicationContext: Context): ConsumerIrManagerSamsung? {
            val irdaService =
                applicationContext.getSystemService(Context.CONSUMER_IR_SERVICE)
                    ?: return null
            return ConsumerIrManagerSamsung(irdaService)
        }
    }

    init {
        val irdaServiceClass: Class<*> = irdaService.javaClass
        val reflectedMethod: Method?
        reflectedMethod = try {
            irdaServiceClass.getMethod("write_irsend", String::class.java)
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
            null
        }
        writeIrSendMethod = reflectedMethod
    }
}