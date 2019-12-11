package com.gitfit.android.data.remote

import com.gitfit.android.AppConstants
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitClient {

    fun getGitFitService() : GitFitApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(AppConstants.GIT_FIT_API_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        return retrofit.create(GitFitApiService::class.java)
    }
}