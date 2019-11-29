package com.gitfit.android.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gitfit.android.api.GitFitApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException

class GitFitAPIRepository(val gitFitApiService: GitFitApiService) {

    val tokenLiveData = MutableLiveData<String>()

    fun getTokenFromUrl(url: String): LiveData<String?> {
        val okHttpClient = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                CoroutineScope(Main).launch {
                    tokenLiveData.value = null
                }
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                CoroutineScope(Main).launch {
                    val token = response.body!!.string()
                    tokenLiveData.value = token
                }
            }
        })

        return tokenLiveData
    }
}