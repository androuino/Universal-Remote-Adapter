package com.intellisrc.universalremoteadapter

import android.app.IntentService
import android.content.Intent
import com.intellisrc.universalremoteadapter.di.Injector
import javax.inject.Inject

class Service @Inject constructor() : IntentService("AppService") {
    init {
        Injector.get().inject(this)
    }

    override fun onCreate() {
        super.onCreate()

    }

    override fun onHandleIntent(p0: Intent?) {

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        const val TAG = "Service"
    }
}