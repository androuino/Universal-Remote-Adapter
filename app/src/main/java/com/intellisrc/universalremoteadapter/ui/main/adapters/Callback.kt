package com.intellisrc.universalremoteadapter.ui.main.adapters

import android.bluetooth.BluetoothDevice
import androidx.recyclerview.widget.DiffUtil

class Callback(
    private val newList: MutableList<BluetoothDevice>,
    private val oldList: MutableList<BluetoothDevice>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        if (newList.size > 0 && oldList.size > 0) {
            try {
                return oldList[oldItemPosition].name == newList[newItemPosition].name
            } catch (ex: Exception) {
                return false
            }
        }
        return false
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        if (newList.size > 0 && oldList.size > 0) {
            val old = oldList[oldItemPosition]
            val new = newList[newItemPosition]
            return old.name == new.name
        }
        return false
    }
}