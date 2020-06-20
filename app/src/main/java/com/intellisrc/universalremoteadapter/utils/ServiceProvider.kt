package com.intellisrc.universalremoteadapter.utils

import androidx.annotation.NonNull
import com.zhuinden.simplestack.ScopeKey
import com.zhuinden.simplestack.ScopedServices
import com.zhuinden.simplestack.ServiceBinder

class ServiceProvider : ScopedServices {
    override fun bindServices(serviceBinder: ServiceBinder) {
        val key = serviceBinder.getKey<HasServices>()
        key.bindServices(serviceBinder)
    }

    interface HasServices : ScopeKey {
        fun bindServices(@NonNull serviceBinder: ServiceBinder)
    }
}