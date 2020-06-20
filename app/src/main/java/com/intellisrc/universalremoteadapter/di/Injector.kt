package com.intellisrc.universalremoteadapter.di

import com.intellisrc.universalremoteadapter.App
import com.intellisrc.universalremoteadapter.di.components.ApplicationComponent

object Injector {
    fun get(): ApplicationComponent {
        return App.get()?.appComponent()!!
    }
}