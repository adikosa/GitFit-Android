package com.gitfit.android.data.remote

import com.gitfit.android.data.local.db.entity.ActivityType
import com.gitfit.android.data.remote.request.PatchUserRequest
import com.gitfit.android.data.remote.request.UserLoginRequest
import com.gitfit.android.data.remote.request.UserRegisterRequest
import com.gitfit.android.data.remote.response.ActivityResponse
import com.gitfit.android.data.remote.response.GithubTokenResponse
import com.gitfit.android.data.remote.response.UserAuthResponse
import com.gitfit.android.data.remote.response.UserResponse
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

    @PATCH("users/{username}")
    suspend fun updateUser(
        @Path("username") username: String,
        @Header("Authorization") authorizationHeader: String,
        @Body patchUserRequest: PatchUserRequest
    ): UserResponse

    @GET("users/{username}")
    suspend fun getUserWithAuthorization(
        @Path("username") username: String,
        @Header("Authorization") authorizationHeader: String
    ): UserResponse

    @GET("/users/{username}/activities")
    suspend fun getActivities(
        @Path("username") username: String,
        @Header("Authorization") authorizationHeader: String
    ): List<ActivityResponse>

    @GET("/activities/types")
    suspend fun getActivityTypes(): List<ActivityType>
}