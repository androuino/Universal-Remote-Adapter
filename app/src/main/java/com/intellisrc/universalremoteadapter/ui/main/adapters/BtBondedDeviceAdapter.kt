package com.intellisrc.universalremoteadapter.ui.main.adapters

import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.intellisrc.universalremoteadapter.R
import com.intellisrc.universalremoteadapter.ui.main.BluetoothConnectionFragmentViewModel
import com.intellisrc.universalremoteadapter.utils.CustomRecyclerView
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.lang.IllegalArgumentException

class BtBondedDeviceAdapter internal constructor(
    private val btBondedDevices: MutableList<BluetoothDevice>,
    private val viewModel: BluetoothConnectionFragmentViewModel?
) : CustomRecyclerView() {
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        this.context = parent.context
        return when (viewType) {
            R.layout.fragment_bluetooth_bonded_devices_items -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_bluetooth_bonded_devices_items, parent, false)
                ViewHolder(view)
            }
            else -> throw IllegalArgumentException(parent.context.getString(R.string.illegal_argument_exception))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.fragment_bluetooth_bonded_devices_items -> (holder as ViewHolder).bind(btBondedDevices, position)
        }
    }

    override fun getItemCount(): Int = btBondedDevices.size

    override fun getItemViewType(position: Int): Int {
        return R.layout.fragment_bluetooth_bonded_devices_items
    }

    fun updatesBtBondedDevicesList(newList: MutableList<BluetoothDevice>, activity: Activity?) {
        val callback = BondedDevicesCallback(this.btBondedDevices, newList)
        val diff = getDiff(callback)
            .subscribeOn(Schedulers.newThread())
            .subscribe { data ->
                this.btBondedDevices.clear()
                this.btBondedDevices.addAll(newList)
                activity?.runOnUiThread { data.dispatchUpdatesTo(this@BtBondedDeviceAdapter) }
            }
    }

    private fun getDiff(callback: BondedDevicesCallback): Observable<DiffUtil.DiffResult> {
        return Observable.fromCallable { DiffUtil.calculateDiff(callback) }
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvBtBondedDeviceName: TextView = itemView.findViewById(R.id.tvBtBondedDeviceName)

        fun bind(list: MutableList<BluetoothDevice>, position: Int) {
            val bluetoothDevice = list[position]
            if (bluetoothDevice.name.isNullOrEmpty())
                tvBtBondedDeviceName.text = bluetoothDevice.address
            else
                tvBtBondedDeviceName.text = bluetoothDevice.name

            tvBtBondedDeviceName.setOnClickListener {
                viewModel?.connectToBTDevice(bluetoothDevice)
            }
        }
    }

    companion object {
        const val TAG = "BtBondedDeviceAdapter"
    }
}