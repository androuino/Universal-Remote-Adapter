package com.intellisrc.universalremoteadapter.ui.base

import android.content.res.Resources
import android.os.Bundle
import android.os.Parcelable
import com.intellisrc.universalremoteadapter.utils.ServiceProvider
import com.zhuinden.simplestack.ServiceBinder

abstract class BaseKey<T> : Parcelable, ServiceProvider.HasServices {
    override fun getScopeTag(): String {
        return fragmentTag
    }

    override fun bindServices(serviceBinder: ServiceBinder) {
        serviceBinder.addService(viewModelTag, newViewModel()!!)
    }

    val fragmentTag: String
        get() = toString()

    val viewModelTag: String
        get() = fragmentTag + "_VIEW_MODEL"

    abstract fun newViewModel(): T

    fun newFragment(): BaseFragment<*> {
        val fragment = createFragment()
        var bundle = fragment.arguments
        if (bundle == null) {
            bundle = Bundle()
        }
        bundle.putParcelable("KEY", this)
        fragment.arguments = bundle
        return fragment
    }

    protected abstract fun createFragment(): BaseFragment<*>
    fun title(resources: Resources?): String? {
        return null
    }

    abstract fun navigationViewId(): Int
    abstract fun shouldShowUp(): Boolean
}