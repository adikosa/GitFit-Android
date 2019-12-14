package com.gitfit.android.repos

import com.gitfit.android.data.local.db.entity.Activity
import com.gitfit.android.data.local.db.entity.ActivityType
import com.gitfit.android.data.remote.GitFitApiService
import com.gitfit.android.data.remote.request.PatchUserDtoRequest
import com.gitfit.android.data.remote.request.UserLoginRequest
import com.gitfit.android.data.remote.request.UserRegisterRequest
import com.gitfit.android.data.remote.response.UserResponse
import retrofit2.HttpException

class GitFitAPIRepository(private val gitFitApiService: GitFitApiService) {

    companion object {
        const val AUTH_HEADER_PREFIX = "Bearer "
    }

    suspend fun getGithubToken(code: String) =
        gitFitApiService.getGithubOauthToken(code, "randomState")

    suspend fun getUser(username: String): UserResponse? {
        return try {
            gitFitApiService.getUser(username)
        } catch (httpException: HttpException) {
            val responseCode = httpException.code()
            if (responseCode == 404) {
                null
            } else {
                throw httpException
            }
        }
    }

    suspend fun getActivitites(username: String, token: String): List<Activity>? {
        return try {
            gitFitApiService.getActivities(
                username,
                AUTH_HEADER_PREFIX + token
            )
        } catch (httpException: HttpException) {
            val responseCode = httpException.code()
            if (responseCode == 404) {
                null
            } else {
                throw httpException
            }
        }
    }

    suspend fun getActivityTypes(): List<ActivityType>? {
        return try {
            gitFitApiService.getActivityTypes()
        } catch (httpException: HttpException) {
            val responseCode = httpException.code()
            if (responseCode == 404) {
                null
            } else {
                throw httpException
            }
        }
    }

    suspend fun updateUser(
        username: String,
        patchUserDtoRequest: PatchUserDtoRequest,
        token: String
    ) {
        gitFitApiService.updateUser(
            username, AUTH_HEADER_PREFIX + token, patchUserDtoRequest
        )
    }

    suspend fun isTokenValid(username: String, token: String): Boolean {
        return try {
            gitFitApiService.getUserWithAuthorization(
                username,
                AUTH_HEADER_PREFIX + token
            )
            true
        } catch (httpException: HttpException) {
            false
        }
    }

    suspend fun logInUser(userLoginRequest: UserLoginRequest) =
        gitFitApiService.loginUser(userLoginRequest)

    suspend fun registerUser(userRegisterRequest: UserRegisterRequest) =
        gitFitApiService.registerUser(userRegisterRequest)


}