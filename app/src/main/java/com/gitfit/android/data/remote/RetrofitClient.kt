package com.gitfit.android.data.remote

import android.content.Context
import com.gitfit.android.AppConstants
import com.gitfit.android.data.remote.adapter.LocalDateTimeAdapter
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitClient {

    fun getGitFitService(context: Context) : GitFitApiService {
        val moshi = Moshi.Builder()
            .add(LocalDateTimeAdapter())
            .build()

        val client = OkHttpClient.Builder()
            .addInterceptor(ConnectivityInterceptor(context))
            .build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(AppConstants.GIT_FIT_API_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return retrofit.create(GitFitApiService::class.java)
    }
}