package com.intellisrc.universalremoteadapter.ui.remote_controller

import androidx.lifecycle.*
import com.intellisrc.universalremoteadapter.room.entities.CodesEntity
import com.intellisrc.universalremoteadapter.ui.base.BaseViewModel
import com.zhuinden.simplestack.Backstack
import kotlinx.coroutines.launch
import javax.inject.Inject

class RemoteControllerFragmentViewModel @Inject constructor(private val backstack: Backstack) : BaseViewModel(), Observer<String> {
    private val rfCodes = MutableLiveData<ArrayList<CodesEntity>>()
    private val rfCodesList = ArrayList<CodesEntity>()

    init {
        viewModelScope.launch {
            roomDataSource.codesDao().getCodes().observeForever {
                it.forEach { code ->
                    rfCodesList.add(code)
                }
                rfCodes.postValue(rfCodesList)
            }
        }
    }

    val getRfCodes = rfCodes.switchMap {
        liveData { emit(it) }
    }

    fun clearDb() {
        roomDataSource.codesDao().clear()
        roomDataSource.codesDao().getCodes().observeForever {
            it.forEach { code ->
                rfCodesList.add(code)
            }
            rfCodes.postValue(rfCodesList)
        }
    }

    override fun onChanged(t: String?) {

    }
}