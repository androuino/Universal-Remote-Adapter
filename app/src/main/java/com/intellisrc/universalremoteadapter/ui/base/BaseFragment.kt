package com.intellisrc.universalremoteadapter.ui.base

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment<VM> : Fragment() {
    @NonNull
    fun <T : BaseKey<T>> getKey(): T {
        val args = arguments ?: throw IllegalStateException("Fragment cannot have null arguments.")
        return args.getParcelable("KEY") ?: throw IllegalStateException("Fragment cannot have null key")
    }

    abstract fun bindViewModel(viewModel: VM)

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    fun snackbar(message: String, extras: String = "") {
        val snackbar = Snackbar.make(requireActivity().window.decorView.rootView, "", Snackbar.LENGTH_LONG)
        val snackbarView = snackbar.view
        val tv = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView

        when (message) {
            "reset-success" -> {
                //snackbar.setText(getString(R.string.reset_success))
                //tv.setTextColor(Color.WHITE)
                //snackbarView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.snackbarSuccess))
            }
        }
        snackbar.show()
    }
}