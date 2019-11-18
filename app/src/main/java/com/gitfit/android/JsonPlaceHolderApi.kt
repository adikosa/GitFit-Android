package com.gitfit.android

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface JsonPlaceHolderApi {
    @POST("register/oauth/authorize")
    fun registerUser(@Body userRegister: UserRegister): Call<UserResponse>

    @POST("login/oauth/authorize")
    fun loginUser(@Body userLogin: UserLogin): Call<UserResponse>
}