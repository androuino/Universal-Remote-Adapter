package com.intellisrc.universalremoteadapter.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import butterknife.ButterKnife
import com.intellisrc.universalremoteadapter.R
import com.intellisrc.universalremoteadapter.di.Injector
import com.intellisrc.universalremoteadapter.ui.base.BaseActivity
import com.intellisrc.universalremoteadapter.ui.base.BaseKey
import com.intellisrc.universalremoteadapter.ui.main.BluetoothConnectionFragmentKey
import com.intellisrc.universalremoteadapter.ui.remote_controller.RemoteControllerFragmentKey
import com.intellisrc.universalremoteadapter.utils.BackstackHolder
import com.intellisrc.universalremoteadapter.utils.RxBus
import com.intellisrc.universalremoteadapter.utils.ServiceProvider
import com.zhuinden.simplestack.BackstackDelegate
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.StateChange
import com.zhuinden.simplestack.StateChanger
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : BaseActivity(), StateChanger, LifecycleOwner {
    private lateinit var menuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        backstackDelegate = BackstackDelegate()
        backstackDelegate.setScopedServices(this, ServiceProvider())
        backstackDelegate.onCreate(savedInstanceState, lastCustomNonConfigurationInstance, History.single(
            RemoteControllerFragmentKey.create))
        backstackDelegate.registerForLifecycleCallbacks(this)
        val backstackHolder: BackstackHolder = Injector.get().backstackHolder
        backstackHolder.setBackstack(backstackDelegate.backstack) // <-- make Backstack globally available through Dagger

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(bottomAppBar)

        ButterKnife.bind(this)
        Injector.get().inject(this)

        fragmentStateChanger = FragmentStateChanger(this, supportFragmentManager, R.id.container)
        backstackDelegate.setStateChanger(this)
    }

    private fun setupViewsForKey(key: BaseKey<*>) {
        if (key.shouldShowUp()) {
        } else {
        }
        //val fragment: Fragment? = supportFragmentManager.findFragmentByTag(key.getFragmentTag())
    }

    override fun handleStateChange(
        stateChange: StateChange,
        completionCallback: StateChanger.Callback
    ) {
        if (!stateChange.isTopNewKeyEqualToPrevious) {
            fragmentStateChanger.handleStateChange(stateChange)

            setupViewsForKey(stateChange.topNewKey())
            //val title: String = stateChange.topNewKey<BaseKey<*>>().title(resources)!!
        }
        completionCallback.stateChangeComplete()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.bottom_app_bar_menu, menu)
        getBluetoothConnection()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_bluetooth -> backstack.goTo(BluetoothConnectionFragmentKey.create)
            else -> {}
        }
        return true
    }

    private fun getBluetoothConnection() {
        val menuItem = bottomAppBar.menu.getItem(0)
        RxBus.subscribe((RxBus.BLUETOOTH_CONNECTION_STATE), this) {
            it is String
            if (it.toString() == "CONNECTED")
                menuItem.icon = resources.getDrawable(R.drawable.ic_bluetooth_connected_menu)
            else if (it.toString() == "DISCONNECTED")
                menuItem.icon = resources.getDrawable(R.drawable.ic_bluetooth_menu)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bluetoothConnectionFragmentViewModel.getBluetoothConnectionStatus.removeObservers(this@MainActivity)
        RxBus.unregister(this)
    }

    override fun onBackPressed() {
        if (!backstackDelegate.backstack.goBack())
            super.onBackPressed()
    }

    companion object {
        const val TAG = "MainActivity"
    }
}
