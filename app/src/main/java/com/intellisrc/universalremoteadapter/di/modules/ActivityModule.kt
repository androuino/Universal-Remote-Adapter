package com.intellisrc.universalremoteadapter.di.modules

import com.intellisrc.universalremoteadapter.ui.MainActivity
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ActivityModule {
    @Provides
    @Singleton
    fun providesMainActivity(): MainActivity {
        return MainActivity()
    }
}