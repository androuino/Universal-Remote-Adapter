package com.intellisrc.universalremoteadapter.utils.schedulers

import android.os.Handler
import android.os.Looper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainThreadScheduler @Inject internal constructor() : Scheduler {
    private val handler = Handler(Looper.getMainLooper())
    override fun execute(runnable: Runnable?) {
        handler.post(runnable!!)
    }
}