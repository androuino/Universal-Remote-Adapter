package com.intellisrc.universalremoteadapter.di.modules

import com.intellisrc.universalremoteadapter.utils.BackstackHolder
import com.zhuinden.simplestack.Backstack
import dagger.Module
import dagger.Provides

@Module
class BackstackModule {
    @Provides
    fun backstack(backstackHolder: BackstackHolder): Backstack {
        return backstackHolder.getBackstack()
    }
}