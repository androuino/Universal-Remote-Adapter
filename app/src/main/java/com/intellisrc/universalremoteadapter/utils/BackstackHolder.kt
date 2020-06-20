package com.intellisrc.universalremoteadapter.utils

import com.zhuinden.simplestack.Backstack
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BackstackHolder @Inject constructor() {
    private lateinit var backstack: Backstack

    fun getBackstack(): Backstack {
        return this.backstack
    }

    fun setBackstack(backstack: Backstack) {
        this.backstack = backstack
    }
}