package com.intellisrc.universalremoteadapter.ui

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import java.lang.IndexOutOfBoundsException

class CustomLinearLayout(context: Context?) : LinearLayoutManager(context) {
    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        try {
            super.onLayoutChildren(recycler, state)
        } catch (e: IndexOutOfBoundsException) {
            Timber.tag(TAG).e("Error on $TAG->onLayoutChildren")
        }
    }

    companion object {
        const val TAG = "CustomLinearLayout"
    }
}