package com.gitfit.android.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.gitfit.android.JsonPlaceHolderApi
import com.gitfit.android.UserRegister
import com.gitfit.android.UserResponse
import com.gitfit.android.utils.logi
import com.gitfit.android.utils.showToast
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeFragment : Fragment() {
    companion object {
        const val START_URL = "http://localhost:1234/github/login/oauth/authorize"
        const val REDIRECT_URL_PREFIX = "http://localhost:1234/github/login/oauth/access_token"
    }

    val viewModel: HomeViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(com.gitfit.android.R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        web_view.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                url?.let {
                    if (it.startsWith(REDIRECT_URL_PREFIX)) {
                        viewModel.getToken(url).observe(this@HomeFragment, Observer { token ->
                            token?.let {
                                showToast("Verification successful $token")
                                logi(token)
                                //todo move everything out of here
                                val retrofit = Retrofit.Builder()
                                    .baseUrl("http://localhost:1234/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build()
                                val jsonPlaceHolderApi =
                                    retrofit.create(JsonPlaceHolderApi::class.java)
                                val myCall = jsonPlaceHolderApi.registerUser(
                                    UserRegister(
                                        "username",
                                        token,
                                        "name",
                                        "surname"
                                    )
                                )

                                myCall.enqueue(object : Callback<UserResponse> {
                                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                                        t.printStackTrace()
                                    }

                                    override fun onResponse(
                                        call: Call<UserResponse>,
                                        response: Response<UserResponse>
                                    ) {
                                        logi(response.body().toString())
                                    }

                                })

                            }
                        })
                        return true
                    }
                }
                return false
            }

        }
        web_view.loadUrl(START_URL)
    }


}