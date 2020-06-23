package com.intellisrc.universalremoteadapter.ui.remote_controller

import com.google.auto.value.AutoValue
import com.intellisrc.universalremoteadapter.Constants
import com.intellisrc.universalremoteadapter.di.Injector
import com.intellisrc.universalremoteadapter.ui.base.BaseFragment
import com.intellisrc.universalremoteadapter.ui.base.BaseKey

@AutoValue
abstract class RemoteControllerFragmentKey : BaseKey<RemoteControllerFragmentViewModel>() {
    companion object {
        val create: RemoteControllerFragmentKey = AutoValue_RemoteControllerFragmentKey(Constants.REMOTE_CONTROLLER_FRAGMENT)
    }

    override fun shouldShowUp(): Boolean {
        return false
    }

    override fun newViewModel(): RemoteControllerFragmentViewModel {
        return Injector.get().remoteControllerFragmentViewModel
    }

    override fun createFragment(): BaseFragment<RemoteControllerFragmentViewModel> {
        return RemoteControllerFragment()
    }
}