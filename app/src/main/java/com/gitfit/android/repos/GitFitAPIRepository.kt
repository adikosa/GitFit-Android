package com.gitfit.android.repos

import com.gitfit.android.api.GitFitApiService
import com.gitfit.android.model.UserLoginRequest
import com.gitfit.android.model.UserRegisterRequest
import com.gitfit.android.model.UserResponse
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

    suspend fun isTokenValid(username: String, token: String): Boolean {
        return try {
            gitFitApiService.getUserWithAuthorization(username,
                AUTH_HEADER_PREFIX + token)
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