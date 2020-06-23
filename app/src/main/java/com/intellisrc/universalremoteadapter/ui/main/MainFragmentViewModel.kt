package com.intellisrc.universalremoteadapter.ui.main

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import androidx.lifecycle.*
import com.github.ivbaranov.rxbluetooth.RxBluetooth
import com.intellisrc.universalremoteadapter.Constants
import com.intellisrc.universalremoteadapter.ui.MainActivity
import com.intellisrc.universalremoteadapter.ui.base.BaseViewModel
import com.intellisrc.universalremoteadapter.ui.remote_controller.RemoteControllerFragmentKey
import com.polidea.rxandroidble2.RxBleClient
import com.zhuinden.simplestack.Backstack
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class MainFragmentViewModel @Inject constructor(private val backstack: Backstack, val rxBluetooth: RxBluetooth, val rxBleClient: RxBleClient, private val mainActivity: MainActivity) : BaseViewModel(), Observer<String> {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val bluetoothDevicesList = MutableLiveData<ArrayList<BluetoothDevice>>()
    private val bluetoothDevices = ArrayList<BluetoothDevice>()
    private val bluetoothConnectionStatus = MutableLiveData<Boolean>()

    init {
        /**
         * Any coroutine launched in this scope is automatically canceled if the ViewModel is cleared
         */
        viewModelScope.launch {
            if (!rxBluetooth.isBluetoothEnabled) {
                rxBluetooth.enableBluetooth(mainActivity, 2)
            } else {
                compositeDisposable.add(
                    rxBluetooth.observeDevices()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.computation())
                        .subscribe {
                            if (it.name != "null")
                                bluetoothDevices.add(it)
                            bluetoothDevicesList.postValue(bluetoothDevices)
                        }
                )
                rxBluetooth.startDiscovery()
            }
        }
    }

    val getBluetoothDevices = bluetoothDevicesList.switchMap {
        liveData { emit(it) }
    }
    val getBluetoothConnectionStatus = bluetoothConnectionStatus.switchMap {
        liveData { emit(it) }
    }

    /**
     * Go to the remote controller screen
     */
    fun goToRemoteControllerScreen() {
        backstack.goTo(RemoteControllerFragmentKey.create)
    }

    /**
     * Connect to bluetooth classic device as client
     */
    @SuppressLint("CheckResult")
    fun connectToBTDevice(bluetoothDevice: BluetoothDevice) {
        rxBluetooth.connectAsClient(bluetoothDevice, Constants.UUID_SECURE).subscribe(
            {bluetoothConnectionStatus.postValue(it.isConnected)},
            {bluetoothConnectionStatus.postValue(false)})
    }

    /**
     * Connect to bluetooth ble device as client
     */
    fun connectToBleDevice() {

    }

    fun onDestroy() {
        compositeDisposable.dispose()
        rxBluetooth.cancelDiscovery()
    }

    override fun onChanged(t: String?) {

    }

    companion object {
        const val TAG = "MainFragmentViewModel"
    }
}