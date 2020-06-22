package com.intellisrc.universalremoteadapter.ui.main

import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.github.ivbaranov.rxbluetooth.RxBluetooth
import com.intellisrc.universalremoteadapter.ui.base.BaseViewModel
import com.polidea.rxandroidble2.RxBleClient
import com.zhuinden.simplestack.Backstack
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class MainFragmentViewModel @Inject constructor(private val backstack: Backstack, val rxBluetooth: RxBluetooth, val rxBleClient: RxBleClient) : BaseViewModel(), Observer<String> {
    init {
        /**
         * Any coroutine launched in this scope is automatically canceled if the ViewModel is cleared
         */
        viewModelScope.launch {
            if (!rxBluetooth.isBluetoothEnabled) {

            }
        }
    }

    override fun onChanged(t: String?) {

    }

    companion object {
        const val TAG = "MainFragmentViewModel"
    }
}