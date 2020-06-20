package com.intellisrc.universalremoteadapter.ui.base

import androidx.lifecycle.ViewModel
import com.intellisrc.universalremoteadapter.di.Injector
import com.intellisrc.universalremoteadapter.room.RoomDataSource
import com.zhuinden.simplestack.Bundleable
import com.zhuinden.statebundle.StateBundle
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

open class BaseViewModel @Inject constructor() : Bundleable, ViewModel() {
    @Inject
    lateinit var roomDataSource: RoomDataSource

    var retrofit: Retrofit? = null


    init {
        Injector.get().inject(this) // FIXME: leaking this

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .followRedirects(true)
            .followSslRedirects(true)
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", UUID.randomUUID().toString())
                    .build()
                chain.proceed(newRequest)
            }
        val client: OkHttpClient = httpClient.build()
    }

    override fun toBundle(): StateBundle {
        val stateBundle = StateBundle()
        stateBundle.putString("filterType", "")
        return stateBundle
    }

    override fun fromBundle(bundle: StateBundle?) {
        bundle?.getString("filterType")
    }
}