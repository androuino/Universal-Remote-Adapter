package com.intellisrc.universalremoteadapter.di.modules

import com.intellisrc.universalremoteadapter.utils.schedulers.BackgroundSchedulers
import com.intellisrc.universalremoteadapter.utils.schedulers.MainThreadScheduler
import com.intellisrc.universalremoteadapter.utils.schedulers.NetworkScheduler
import com.intellisrc.universalremoteadapter.utils.schedulers.Scheduler
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class SchedulerModule {
    @Provides
    @Named("NETWORK")
    fun networkScheduler(networkScheduler: NetworkScheduler): Scheduler {
        return networkScheduler
    }

    @Provides
    @Named("BACKGROUND")
    fun backgroundScheduler(backgroundScheduler: BackgroundSchedulers): Scheduler {
        return backgroundScheduler
    }

    @Provides
    @Named("MAIN_THREAD")
    fun mainThreadScheduler(mainThreadScheduler: MainThreadScheduler): Scheduler {
        return mainThreadScheduler
    }
}