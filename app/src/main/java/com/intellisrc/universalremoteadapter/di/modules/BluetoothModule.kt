package com.intellisrc.universalremoteadapter.di.modules

import android.app.Application
import com.polidea.rxandroidble2.RxBleClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class BluetoothModule {
    @Provides
    @Singleton
    fun providesRxBleClient(application: Application): RxBleClient {
        return RxBleClient.create(application)
    }
}