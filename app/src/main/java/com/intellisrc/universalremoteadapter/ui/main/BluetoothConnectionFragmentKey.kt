package com.intellisrc.universalremoteadapter.ui.main

import com.google.auto.value.AutoValue
import com.intellisrc.universalremoteadapter.Constants
import com.intellisrc.universalremoteadapter.di.Injector
import com.intellisrc.universalremoteadapter.ui.base.BaseFragment
import com.intellisrc.universalremoteadapter.ui.base.BaseKey

@AutoValue
abstract class BluetoothConnectionFragmentKey : BaseKey<BluetoothConnectionFragmentViewModel>() {
    companion object {
        val create: BluetoothConnectionFragmentKey = AutoValue_BluetoothConnectionFragmentKey(Constants.MAIN_FRAGMENT)
    }

    override fun shouldShowUp(): Boolean {
        return false
    }

    override fun newViewModel(): BluetoothConnectionFragmentViewModel {
        return Injector.get().bluetoothConnectionFragmentViewModel
    }

    override fun createFragment(): BaseFragment<BluetoothConnectionFragmentViewModel> {
        return BluetoothConnectionFragment()
    }
}