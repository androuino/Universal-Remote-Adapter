package com.intellisrc.universalremoteadapter.ui.main

import com.google.auto.value.AutoValue
import com.intellisrc.universalremoteadapter.Constants
import com.intellisrc.universalremoteadapter.di.Injector
import com.intellisrc.universalremoteadapter.ui.base.BaseFragment
import com.intellisrc.universalremoteadapter.ui.base.BaseKey

@AutoValue
abstract class MainFragmentKey : BaseKey<MainFragmentViewModel>() {
    companion object {
        val create: MainFragmentKey = AutoValue_MainFragmentKey(Constants.MAIN_FRAGMENT)
    }

    override fun shouldShowUp(): Boolean {
        return false
    }

    override fun newViewModel(): MainFragmentViewModel {
        return Injector.get().mainFragmentViewModel
    }

    override fun createFragment(): BaseFragment<MainFragmentViewModel> {
        return MainFragment()
    }
}