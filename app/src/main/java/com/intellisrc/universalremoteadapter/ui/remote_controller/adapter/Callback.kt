package com.intellisrc.universalremoteadapter.ui.remote_controller.adapter

import androidx.recyclerview.widget.DiffUtil
import com.intellisrc.universalremoteadapter.room.entities.CodesEntity
import kotlin.math.sign

class Callback(
    private val newList: MutableList<CodesEntity>,
    private val oldList: MutableList<CodesEntity>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        if (newList.size > 0 && oldList.size > 0) {
            return try {
                oldList[oldItemPosition].code == newList[newItemPosition].code
            } catch (ex: Exception) {
                false
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
            return old.code == new.code
        }
        return false
    }
}