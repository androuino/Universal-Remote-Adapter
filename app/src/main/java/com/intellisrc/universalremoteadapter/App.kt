package com.intellisrc.universalremoteadapter

import android.app.Application
import android.content.res.Resources
import android.os.CountDownTimer
import com.intellisrc.universalremoteadapter.di.components.ApplicationComponent
import com.intellisrc.universalremoteadapter.di.components.DaggerApplicationComponent
import com.intellisrc.universalremoteadapter.di.modules.AndroidModule
import com.intellisrc.universalremoteadapter.di.modules.RoomModule
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber
import java.util.concurrent.ExecutorService

class App : Application() {
    private var applicationComponent: ApplicationComponent? = null
    private var sExecutor: ExecutorService? = null

    init {
        Timber.plant(Timber.DebugTree())
    }

    override fun onCreate() {
        /*if (BuildConfig.DEBUG) {
            val threadPolicy =
                StrictMode.ThreadPolicy.Builder()
                    .detectCustomSlowCalls()
                    .detectNetwork()
                    .penaltyLog()
            val vmPolicy = StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedRegistrationObjects()
                .detectLeakedClosableObjects()
                .detectActivityLeaks()
                .detectFileUriExposure()
                .penaltyLog()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                sExecutor = Executors.newSingleThreadExecutor()
            }

            StrictMode.setThreadPolicy(threadPolicy.build())
            StrictMode.setVmPolicy(vmPolicy.build())
        }*/
        super.onCreate()
        INSTANCE = this
        // leak canary
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)

        applicationComponent = DaggerApplicationComponent
            .builder()
            .androidModule(AndroidModule(this))
            .roomModule(RoomModule(this))
            .build()
        applicationComponent?.inject(this)

        try {
            object : CountDownTimer(1800000, 1000) {
                override fun onTick(p0: Long) {}

                override fun onFinish() {
                    Timber.tag(TAG).d("Updating the rates")
                    //applicationComponent?.mainFragmentViewModel?.getHistoricalData()
                    start()
                }
            }.start()
        } catch (ex: Exception) {
            Timber.tag(TAG).e("Exception in App class->onCreate->CountDownTimer->${ex.message}")
        }
    }

    fun appComponent(): ApplicationComponent? {
        return applicationComponent
    }

    companion object {
        const val TAG = "App"
        private var INSTANCE: App? = null
        fun get(): App? {
            return INSTANCE
        }

        var res: Resources? = null
            private set
    }
}