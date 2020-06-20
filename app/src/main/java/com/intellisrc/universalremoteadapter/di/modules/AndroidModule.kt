package com.intellisrc.universalremoteadapter.di.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import com.intellisrc.universalremoteadapter.di.ApplicationContext
import dagger.Module
import dagger.Provides

@Module
class AndroidModule(val application: Application) {
    @Provides
    @ApplicationContext
    fun provideContext(): Context {
        return application
    }

    @Provides
    fun provideResources(): Resources {
        return application.resources
    }

    @Provides
    fun provideApplication(): Application {
        return application
    }

    @Provides
    fun provideSharedPrefs(): SharedPreferences {
        return application.getSharedPreferences("talk", Context.MODE_PRIVATE)
    }
}