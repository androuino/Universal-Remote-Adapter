package com.intellisrc.universalremoteadapter.ui.main

import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.intellisrc.universalremoteadapter.ui.base.BaseViewModel
import com.zhuinden.simplestack.Backstack
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragmentViewModel @Inject constructor(private val backstack: Backstack) : BaseViewModel(), Observer<String> {
    init {
        /**
         * Any coroutine launched in this scope is automatically canceled if the ViewModel is cleared
         */
        viewModelScope.launch {
        }
    }
    override fun onChanged(t: String?) {

    }

    companion object {
        const val TAG = "MainFragmentViewModel"
    }
}