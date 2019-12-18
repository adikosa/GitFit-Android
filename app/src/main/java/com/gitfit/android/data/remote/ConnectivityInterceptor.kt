package com.gitfit.android.data.remote

import android.content.Context
import com.gitfit.android.utils.isNetworkConnected
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ConnectivityInterceptor(context: Context) : Interceptor {

    private val appContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!appContext.isNetworkConnected()) {
            throw NoConnectivityException()
        }

        val builder: Request.Builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }
}