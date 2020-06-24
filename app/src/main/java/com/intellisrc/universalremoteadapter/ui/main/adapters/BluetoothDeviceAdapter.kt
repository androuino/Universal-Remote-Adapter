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
import io.reactivex.schedulers.Schedulers
import java.lang.IllegalArgumentException
import io.reactivex.Observable as ioObservable

class BluetoothDeviceAdapter internal constructor(
    private val bluetoothDevices: MutableList<BluetoothDevice>,
    private val viewModel: BluetoothConnectionFragmentViewModel?
) : CustomRecyclerView() {
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        this.context = parent.context
        return when (viewType) {
            R.layout.fragment_bluetooth_connection_items -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_bluetooth_connection_items, parent, false)
                ViewHolder(view)
            }
            else -> throw IllegalArgumentException(parent.context.getString(R.string.illegal_argument_exception))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.fragment_bluetooth_connection_items -> (holder as ViewHolder).bind(bluetoothDevices, position)
        }
    }

    override fun getItemCount(): Int = bluetoothDevices.size

    override fun getItemViewType(position: Int): Int {
        return R.layout.fragment_bluetooth_connection_items
    }

    fun updatesBluetoothDevicesList(newList: MutableList<BluetoothDevice>, activity: Activity?) {
        val callback = Callback(this.bluetoothDevices, newList)
        val diff = getDiff(callback)
            .subscribeOn(Schedulers.newThread())
            .subscribe { data ->
                this.bluetoothDevices.clear()
                this.bluetoothDevices.addAll(newList)
                activity?.runOnUiThread { data.dispatchUpdatesTo(this@BluetoothDeviceAdapter) }
            }
    }

    private fun getDiff(callback: Callback): ioObservable<DiffUtil.DiffResult> {
        return ioObservable.fromCallable { DiffUtil.calculateDiff(callback) }
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val clParent: ConstraintLayout = itemView.findViewById(R.id.clParent)
        private val tvBtDeviceName: TextView = itemView.findViewById(R.id.tvBtDeviceName)

        fun bind(list: MutableList<BluetoothDevice>, position: Int) {
            val bluetoothDevice = list[position]
            if (bluetoothDevice.name.isNullOrEmpty())
                tvBtDeviceName.text = bluetoothDevice.address
            else
                tvBtDeviceName.text = bluetoothDevice.name
            clParent.tag = bluetoothDevice.address

            tvBtDeviceName.setOnClickListener {
                viewModel?.connectToBTDevice(bluetoothDevice)
            }
        }
    }

    companion object {
        const val TAG = "BluetoothDeviceAdapter"
    }
}