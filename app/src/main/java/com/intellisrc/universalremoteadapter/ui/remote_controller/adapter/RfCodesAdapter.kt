package com.intellisrc.universalremoteadapter.ui.remote_controller.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.intellisrc.universalremoteadapter.R
import com.intellisrc.universalremoteadapter.room.entities.CodesEntity
import com.intellisrc.universalremoteadapter.ui.remote_controller.RemoteControllerFragmentViewModel
import com.intellisrc.universalremoteadapter.utils.CustomRecyclerView
import io.reactivex.schedulers.Schedulers
import java.lang.IllegalArgumentException
import io.reactivex.Observable as ioObservable

class RfCodesAdapter internal constructor(
    private val codeList: MutableList<CodesEntity>,
    private val viewModel: RemoteControllerFragmentViewModel?
) : CustomRecyclerView() {
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        this.context = parent.context
        return when (viewType) {
            R.layout.fragment_remote_controller_items -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_remote_controller_items, parent, false)
                ViewHolder(view)
            }
            else -> throw IllegalArgumentException(parent.context.getString(R.string.illegal_argument_exception))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.fragment_remote_controller_items -> (holder as ViewHolder).bind(codeList, position)
        }
    }

    override fun getItemCount(): Int = codeList.size

    override fun getItemViewType(position: Int): Int {
        return R.layout.fragment_remote_controller_items
    }

    fun updatesRfCodeList(newList: MutableList<CodesEntity>, activity: Activity?) {
        val callback = Callback(this.codeList, newList)
        val diff = getDiff(callback)
            .subscribeOn(Schedulers.newThread())
            .subscribe { data ->
                this.codeList.clear()
                this.codeList.addAll(newList)
                activity?.runOnUiThread { data.dispatchUpdatesTo(this@RfCodesAdapter) }
            }
    }

    private fun getDiff(callback: Callback): ioObservable<DiffUtil.DiffResult> {
        return ioObservable.fromCallable { DiffUtil.calculateDiff(callback) }
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val codeLayout: ConstraintLayout = itemView.findViewById(R.id.frequencyLayout)
        private val tvFrequency: TextView = itemView.findViewById(R.id.tvFrequency)

        fun bind(list: MutableList<CodesEntity>, position: Int) {
            val code = list[position]
            if (code.name.isNullOrEmpty())
                tvFrequency.text = code.code
            else
                tvFrequency.text = code.name
            codeLayout.tag = code.code

            tvFrequency.setOnClickListener {

            }
        }
    }
}