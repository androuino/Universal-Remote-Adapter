package com.intellisrc.universalremoteadapter.ui.remote_controller

import androidx.lifecycle.Observer
import com.intellisrc.universalremoteadapter.ui.base.BaseViewModel
import com.zhuinden.simplestack.Backstack
import javax.inject.Inject

class RemoteControllerFragmentViewModel @Inject constructor(private val backstack: Backstack) : BaseViewModel(), Observer<String> {



    override fun onChanged(t: String?) {

    }
}