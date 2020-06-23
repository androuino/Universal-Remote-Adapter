package com.intellisrc.universalremoteadapter.ui.remote_controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import com.intellisrc.universalremoteadapter.databinding.FragmentRemoteControllerBinding
import com.intellisrc.universalremoteadapter.ui.base.BaseFragment
import com.intellisrc.universalremoteadapter.utils.Preconditions

class RemoteControllerFragment : BaseFragment<RemoteControllerFragmentViewModel>(), LifecycleOwner {
    private var viewModel: RemoteControllerFragmentViewModel? = null
    private lateinit var viewBinding: FragmentRemoteControllerBinding

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
    }

    override fun bindViewModel(viewModel: RemoteControllerFragmentViewModel) {
        Preconditions.checkNotNull(viewModel)
        if (this.viewModel == viewModel) {
            return
        }
        this.viewModel = viewModel
    }
}