package com.intellisrc.universalremoteadapter.ui.remote_controller

import android.os.Bundle
import android.view.*
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.intellisrc.universalremoteadapter.databinding.FragmentRemoteControllerBinding
import com.intellisrc.universalremoteadapter.room.entities.CodesEntity
import com.intellisrc.universalremoteadapter.ui.CustomLinearLayout
import com.intellisrc.universalremoteadapter.ui.base.BaseFragment
import com.intellisrc.universalremoteadapter.ui.remote_controller.adapter.RfCodesAdapter
import com.intellisrc.universalremoteadapter.utils.Preconditions
import kotlinx.android.synthetic.main.fragment_remote_controller.*

class RemoteControllerFragment : BaseFragment<RemoteControllerFragmentViewModel>(), LifecycleOwner {
    private var viewModel: RemoteControllerFragmentViewModel? = null
    private lateinit var viewBinding: FragmentRemoteControllerBinding
    private var rfCodesAdapter: RfCodesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentRemoteControllerBinding.inflate(inflater, container, false)
        viewBinding.viewModel = viewModel
        viewBinding.lifecycleOwner = viewLifecycleOwner
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initObservers()

        btnClearDb.setOnClickListener {
            viewModel?.clearDb()
        }
    }

    private fun initAdapter() {
        val list = ArrayList<CodesEntity>(1)
        rfCodesAdapter = RfCodesAdapter(list, viewModel)
        rvRfCodes.adapter = rfCodesAdapter
        rvRfCodes.layoutManager = CustomLinearLayout(requireActivity())
        rfCodesAdapter?.notifyDataSetChanged()
    }

    private fun initObservers() {
        viewModel?.getRfCodes?.observe(viewLifecycleOwner, Observer {
            if (it.size <= 0)
                rvRfCodes.removeAllViews()
            else
                rfCodesAdapter?.updatesRfCodeList(it, requireActivity())
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel?.getRfCodes?.removeObservers(viewLifecycleOwner)
    }

    override fun bindViewModel(viewModel: RemoteControllerFragmentViewModel) {
        Preconditions.checkNotNull(viewModel)
        if (this.viewModel == viewModel) {
            return
        }
        this.viewModel = viewModel
    }
}