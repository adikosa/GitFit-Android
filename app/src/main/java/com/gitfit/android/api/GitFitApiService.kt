package com.gitfit.android.api

import com.gitfit.android.model.UserLogin
import com.gitfit.android.model.UserRegister
import com.gitfit.android.model.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface GitFitApiService {

    @POST("register/oauth/authorize")
    fun registerUser(@Body userRegister: UserRegister): Call<UserResponse>

    @POST("login/oauth/authorize")
    fun loginUser(@Body userLogin: UserLogin): Call<UserResponse>
}