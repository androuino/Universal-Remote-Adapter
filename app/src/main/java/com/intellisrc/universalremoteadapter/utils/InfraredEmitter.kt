package com.intellisrc.universalremoteadapter.utils

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.intellisrc.universalremoteadapter.utils.ConsumerIrManager.Companion.getSupportConsumerIrManager
import com.intellisrc.universalremoteadapter.utils.IrCommand.DISH.buildDISH
import com.intellisrc.universalremoteadapter.utils.IrCommand.JVC.buildJVC
import com.intellisrc.universalremoteadapter.utils.IrCommand.NEC.buildNEC
import com.intellisrc.universalremoteadapter.utils.IrCommand.Panasonic.buildPanasonic
import com.intellisrc.universalremoteadapter.utils.IrCommand.Pronto.buildPronto
import com.intellisrc.universalremoteadapter.utils.IrCommand.RC5.buildRC5
import com.intellisrc.universalremoteadapter.utils.IrCommand.RC6.buildRC6
import com.intellisrc.universalremoteadapter.utils.IrCommand.Sharp.buildSharp
import com.intellisrc.universalremoteadapter.utils.IrCommand.Sony.buildSony
import org.androidannotations.annotations.AfterInject
import org.androidannotations.annotations.EBean
import org.androidannotations.annotations.RootContext
import timber.log.Timber

@EBean(scope = EBean.Scope.Singleton)
open class InfraredEmitter {
    //@RootContext
    lateinit var context: Context
    private var manager: ConsumerIrManager? = null

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    @AfterInject
    protected fun afterInjects() {
        manager = getSupportConsumerIrManager(context)
    }

    fun hasIrEmitter(): Boolean {
        return manager!!.hasIrEmitter()
    }

    private fun logAction(type: String, data: String) {
        Timber.tag(IR_COMMAND).i("$type: $data")
    }

    private fun logAction(type: String, size: Int, data: Long) {
        Timber.tag(IR_COMMAND).i("[$type]($size): $data")
    }

    private fun logActionWithAddress(
        type: String,
        address: Int,
        data: Long
    ) {
        Timber.tag(IR_COMMAND).i("[$type]: Address: $address Data: $data")
    }

    fun NEC(size: Int, data: Long) {
        logAction("NEC", size, data)
        manager!!.transmit(buildNEC(size, data.toInt()))
    }

    fun Sony(size: Int, data: Long) {
        logAction("Sony", size, data)
        manager!!.transmit(buildSony(size, data))
    }

    fun RC5(size: Int, data: Long) {
        logAction("RC", size, data)
        manager!!.transmit(buildRC5(size, data))
    }

    fun RC6(size: Int, data: Long) {
        logAction("RC", size, data)
        manager!!.transmit(buildRC6(size, data))
    }

    fun DISH(size: Int, data: Long) {
        logAction("DISH", size, data)
        manager!!.transmit(buildDISH(size, data.toInt()))
    }

    fun Sharp(size: Int, data: Long) {
        logAction("Sharp", size, data)
        manager!!.transmit(buildSharp(size, data.toInt()))
    }

    fun Panasonic(address: Int, data: Long) {
        logActionWithAddress("Panasonic", address, data)
        manager!!.transmit(buildPanasonic(address, data.toInt()))
    }

    fun JVC(size: Int, data: Long) {
        logAction("JVC", size, data)
        manager!!.transmit(buildJVC(size, data, false))
    }

    fun Proton(data: String) {
        logAction("Pronto", data)
        manager!!.transmit(buildPronto(data))
    }

    companion object {
        const val IR_COMMAND = "IRCommand"
    }
}