package com.intellisrc.universalremoteadapter.di.components

import android.app.Application
import android.content.Context
import com.github.ivbaranov.rxbluetooth.RxBluetooth
import com.intellisrc.universalremoteadapter.App
import com.intellisrc.universalremoteadapter.Service
import com.intellisrc.universalremoteadapter.di.ApplicationContext
import com.intellisrc.universalremoteadapter.di.modules.*
import com.intellisrc.universalremoteadapter.ui.MainActivity
import com.intellisrc.universalremoteadapter.ui.base.BaseActivity
import com.intellisrc.universalremoteadapter.ui.base.BaseViewModel
import com.intellisrc.universalremoteadapter.ui.main.MainFragmentViewModel
import com.intellisrc.universalremoteadapter.ui.remote_controller.RemoteControllerFragmentViewModel
import com.intellisrc.universalremoteadapter.utils.BackstackHolder
import com.polidea.rxandroidble2.RxBleClient
import com.zhuinden.simplestack.Backstack
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidModule::class,
    ActivityModule::class,
    FragmentModule::class,
    BackstackModule::class,
    SchedulerModule::class,
    RoomModule::class,
    BluetoothModule::class,
    AndroidSupportInjectionModule::class
])
interface ApplicationComponent {
    @get:ApplicationContext
    val context: Context
    val application: Application
    var backstack: Backstack
    var backstackHolder: BackstackHolder
    var rxBluetooth: RxBluetooth
    var rxBleClient: RxBleClient
    var mainActivity: MainActivity

    // application
    fun inject(app: App)
    // service
    fun inject(service: Service)
    // activities
    fun inject(baseActivity: BaseActivity)
    // viewmodel
    fun inject(baseViewModel: BaseViewModel)
    val mainFragmentViewModel: MainFragmentViewModel
    val remoteControllerFragmentViewModel: RemoteControllerFragmentViewModel
    // repository implementations
    //fun inject(currencyLayerImplRepo: CurrencyLayerImplRepo)
}