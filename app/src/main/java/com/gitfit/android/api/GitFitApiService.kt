package com.gitfit.android.api

import com.gitfit.android.model.*
import retrofit2.http.*

interface GitFitApiService {

    @POST("register/oauth/authorize")
    suspend fun registerUser(@Body userRegister: UserRegister): UserAuthResponse

    @POST("login/oauth/authorize")
    suspend fun loginUser(@Body userLogin: UserLogin): UserAuthResponse

    @GET("github/login/oauth/access_token")
    suspend fun getGithubOauthToken(@Query("code") code: String, @Query("state") state: String): GithubTokenResponse

    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String): UserResponse
}