package com.gitfit.android.repos

import com.gitfit.android.api.GitFitApiService
import com.gitfit.android.model.UserLogin
import com.gitfit.android.model.UserRegister
import com.gitfit.android.model.UserResponse
import retrofit2.HttpException

class GitFitAPIRepository(private val gitFitApiService: GitFitApiService) {

    suspend fun getGithubToken(code: String) =
        gitFitApiService.getGithubOauthToken(code, "randomState")

    suspend fun getUser(username: String): UserResponse? {
        val user: UserResponse?
        try {
            user = gitFitApiService.getUser(username)
        } catch (httpException: HttpException) {
            val responseCode = httpException.code()
            if (responseCode == 500) {
                return null
            } else {
                throw httpException
            }
        }
        return user
    }

    suspend fun logInUser(userLogin: UserLogin) =
        gitFitApiService.loginUser(userLogin)

    suspend fun registerUser(userRegister: UserRegister) =
        gitFitApiService.registerUser(userRegister)
}