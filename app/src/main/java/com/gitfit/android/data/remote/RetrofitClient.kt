package com.gitfit.android.data.remote

import com.gitfit.android.AppConstants
import com.gitfit.android.data.remote.adapter.LocalDateTimeAdapter
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitClient {

    fun getGitFitService() : GitFitApiService {
        val moshi = Moshi.Builder()
            .add(LocalDateTimeAdapter())
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(AppConstants.GIT_FIT_API_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return retrofit.create(GitFitApiService::class.java)
    }
}