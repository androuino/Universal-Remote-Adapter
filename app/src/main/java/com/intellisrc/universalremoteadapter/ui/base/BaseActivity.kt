package com.intellisrc.universalremoteadapter.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import com.intellisrc.universalremoteadapter.Service
import com.intellisrc.universalremoteadapter.di.Injector
import com.intellisrc.universalremoteadapter.ui.FragmentStateChanger
import com.intellisrc.universalremoteadapter.ui.main.BluetoothConnectionFragmentViewModel
import com.zhuinden.simplestack.Backstack
import com.zhuinden.simplestack.BackstackDelegate
import javax.inject.Inject

open class BaseActivity : AppCompatActivity(), LifecycleOwner {
    @Inject
    lateinit var backstack: Backstack
    @Inject
    lateinit var bluetoothConnectionFragmentViewModel: BluetoothConnectionFragmentViewModel

    lateinit var backstackDelegate: BackstackDelegate
    lateinit var fragmentStateChanger: FragmentStateChanger
    private lateinit var service: Service

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.get().inject(this)

        service = Service()
    }
}