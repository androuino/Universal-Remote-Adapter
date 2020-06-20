package com.intellisrc.universalremoteadapter.ui.main

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.github.ivbaranov.rxbluetooth.RxBluetooth
import com.intellisrc.universalremoteadapter.databinding.FragmentMainBinding
import com.intellisrc.universalremoteadapter.ui.base.BaseFragment
import com.intellisrc.universalremoteadapter.utils.Preconditions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import kotlin.system.exitProcess


class MainFragment : BaseFragment<MainFragmentViewModel>(), LifecycleOwner {
    private var viewModel: MainFragmentViewModel? = null
    private lateinit var viewBinding: FragmentMainBinding
    private var rxBluetooth: RxBluetooth? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentMainBinding.inflate(inflater, container, false)
        viewBinding.viewModel = viewModel
        viewBinding.lifecycleOwner = viewLifecycleOwner
        return viewBinding.root
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rxBluetooth = RxBluetooth(requireContext())

        if (!rxBluetooth?.isBluetoothAvailable!!) {
            Timber.tag(TAG).i("Bluetooth is not available in this device. Exiting")
            exitProcess(0)
        } else {
            if (!rxBluetooth?.isBluetoothEnabled!!) {
                rxBluetooth?.enableBluetooth(requireActivity(), 2)
            } else {
                // do operation here like connect or show the available Bluetooth devices
                rxBluetooth?.observeDevices()?.observeOn(AndroidSchedulers.mainThread())?.subscribeOn(Schedulers.computation())?.subscribe {
                    Timber.tag(TAG).d("${it.name} : ${it.address}")
                }
            }
        }

        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION), 1)
        }
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
                    if (!rxBluetooth?.isBluetoothAvailable!!) {
                        Timber.tag(TAG).i("Bluetooth is not available in this device. Exiting")
                        exitProcess(0)
                    } else {
                        if (!rxBluetooth?.isBluetoothEnabled!!) {
                            rxBluetooth?.enableBluetooth(requireActivity(), 2)
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

    override fun bindViewModel(viewModel: MainFragmentViewModel) {
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