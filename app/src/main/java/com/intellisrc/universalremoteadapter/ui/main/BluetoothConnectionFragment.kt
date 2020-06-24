package com.intellisrc.universalremoteadapter.ui.main

import android.bluetooth.BluetoothDevice
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.intellisrc.universalremoteadapter.R
import com.intellisrc.universalremoteadapter.databinding.FragmentBluetoothConnectionBinding
import com.intellisrc.universalremoteadapter.ui.CustomLinearLayout
import com.intellisrc.universalremoteadapter.ui.base.BaseFragment
import com.intellisrc.universalremoteadapter.ui.main.adapters.BluetoothDeviceAdapter
import com.intellisrc.universalremoteadapter.utils.Preconditions
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_bluetooth_connection.*
import timber.log.Timber
import kotlin.system.exitProcess

class BluetoothConnectionFragment : BaseFragment<BluetoothConnectionFragmentViewModel>(), LifecycleOwner {
    private var viewModel: BluetoothConnectionFragmentViewModel? = null
    private lateinit var viewBinding: FragmentBluetoothConnectionBinding
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val btDevicesList = ArrayList<BluetoothDevice>()
    private var btDeviceAdapter: BluetoothDeviceAdapter? = null

    init {
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentBluetoothConnectionBinding.inflate(inflater, container, false)
        viewBinding.viewModel = viewModel
        viewBinding.lifecycleOwner = viewLifecycleOwner
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initObservers()

        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION), 1)
        }

        btnScanStop.setOnClickListener {
            if (btnScanStop.text == resources.getString(R.string.scan)) {
                btnScanStop.text = resources.getString(R.string.stop)
                tv1.text = resources.getString(R.string.scanning)
                viewModel?.startScan()
            } else {
                btnScanStop.text = resources.getString(R.string.scan)
                tv1.text = resources.getString(R.string.nearby_devices)
                viewModel?.stopScan()
            }
        }
    }

    private fun initAdapter() {
        val list = ArrayList<BluetoothDevice>(1)
        btDeviceAdapter = BluetoothDeviceAdapter(list, viewModel)
        rvBluetoothDevices.adapter = btDeviceAdapter
        rvBluetoothDevices.layoutManager = CustomLinearLayout(requireActivity())
        btDeviceAdapter?.notifyDataSetChanged()
    }

    private fun initObservers() {
        viewModel?.getBluetoothDevices?.observe(viewLifecycleOwner, Observer {
            if (it.size > 0)
                btDeviceAdapter?.updatesBluetoothDevicesList(it, requireActivity())
        })
        viewModel?.getBluetoothConnectionStatus?.observe(viewLifecycleOwner, Observer {
            val menuItem = viewModel?.mainActivity?.bottomAppBar?.menu?.getItem(0) // FIXME: crashing here
            if (it) {
                viewModel?.stopScan()
                viewModel?.goToRemoteControllerScreen()
                menuItem?.icon = resources.getDrawable(R.drawable.ic_bluetooth_connected_menu)
            } else {
                snackbar("bluetooth-connect-error")
                menuItem?.icon = resources.getDrawable(R.drawable.ic_bluetooth_menu)
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            permissions.forEach {
                if (android.Manifest.permission.ACCESS_COARSE_LOCATION == it) {
                    Timber.tag(TAG).i("Permission Granted")
                    if (!viewModel?.rxBluetooth?.isBluetoothAvailable!!) {
                        Timber.tag(TAG).i("Bluetooth is not available in this device. Exiting")
                        exitProcess(0)
                    } else {
                        if (!viewModel?.rxBluetooth?.isBluetoothEnabled!!) {
                            viewModel?.rxBluetooth?.enableBluetooth(requireActivity(), 2)
                        } else {
                            // do operation here like connect or show the available Bluetooth devices
                        }
                    }
                } else {
                    exitProcess(0)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel?.onDestroy()
        viewModel?.getBluetoothDevices?.removeObservers(viewLifecycleOwner)
    }

    override fun bindViewModel(viewModel: BluetoothConnectionFragmentViewModel) {
        Preconditions.checkNotNull(viewModel)
        if (this.viewModel == viewModel) {
            return
        }
        this.viewModel = viewModel
    }

    companion object {
        const val TAG = "MainFragment"
    }
}