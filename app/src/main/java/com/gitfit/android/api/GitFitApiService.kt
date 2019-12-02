package com.gitfit.android.api

import com.gitfit.android.model.*
import retrofit2.http.*

interface GitFitApiService {

    @POST("register/oauth/authorize")
    suspend fun registerUser(@Body userRegisterRequest: UserRegisterRequest): UserAuthResponse

    @POST("login/oauth/authorize")
    suspend fun loginUser(@Body userLoginRequest: UserLoginRequest): UserAuthResponse

    @GET("github/login/oauth/access_token")
    suspend fun getGithubOauthToken(@Query("code") code: String, @Query("state") state: String): GithubTokenResponse

    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String): UserResponse

    @GET("users/{username}")
    suspend fun getUserWithAuthorization(@Path("username") username: String,
                                         @Header("Authorization") authorizationHeader: String): UserResponse
}