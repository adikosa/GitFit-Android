package com.gitfit.android.repos

import com.gitfit.android.data.local.db.entity.ActivityType
import com.gitfit.android.data.remote.GitFitApiService
import com.gitfit.android.data.remote.NoConnectivityException
import com.gitfit.android.data.remote.ResultWrapper
import com.gitfit.android.data.remote.request.*
import com.gitfit.android.data.remote.response.ActivityResponse
import com.gitfit.android.data.remote.response.GithubTokenResponse
import com.gitfit.android.data.remote.response.UserResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class GitFitAPIRepository(
    private val gitFitApiService: GitFitApiService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    companion object {
        const val AUTH_HEADER_PREFIX = "Bearer "
    }

    private suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): ResultWrapper<T> {
        return withContext(dispatcher) {
            try {
                ResultWrapper.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                throwable.printStackTrace()

                when (throwable) {
                    is NoConnectivityException -> ResultWrapper.NetworkConnectivityError
                    is HttpException -> ResultWrapper.GenericError(throwable.code())
                    is IOException -> ResultWrapper.NetworkError
                    else -> ResultWrapper.GenericError(null)
                }
            }
        }
    }

    suspend fun getGithubToken(code: String): ResultWrapper<GithubTokenResponse> {
        return safeApiCall(dispatcher) {
            gitFitApiService.getGithubOauthToken(code, "randomState")
        }
    }

    suspend fun isTokenValid(username: String, token: String): ResultWrapper<Boolean> {
        return safeApiCall(dispatcher) {
            gitFitApiService.getUserWithAuthorization(
                username,
                AUTH_HEADER_PREFIX + token
            )

            true
        }
    }

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

    suspend fun getActivities(username: String, token: String): ResultWrapper<List<ActivityResponse>> {
        return safeApiCall(dispatcher) {
            gitFitApiService.getActivities(
                username,
                AUTH_HEADER_PREFIX + token
            )
        }
    }

    suspend fun getActivityTypes(): ResultWrapper<List<ActivityType>> {
        return safeApiCall(dispatcher) {
            gitFitApiService.getActivityTypes()
        }
    }

    suspend fun updateUser(
        username: String,
        patchUserRequest: PatchUserRequest,
        token: String): ResultWrapper<UserResponse> {
        return safeApiCall(dispatcher) {
            gitFitApiService.updateUser(
                username, AUTH_HEADER_PREFIX + token, patchUserRequest
            )
        }
    }

    suspend fun logInUser(userLoginRequest: UserLoginRequest) =
        gitFitApiService.loginUser(userLoginRequest)

    suspend fun registerUser(userRegisterRequest: UserRegisterRequest) =
        gitFitApiService.registerUser(userRegisterRequest)

    suspend fun deleteUserActivity(username: String, token: String, activityId: Long) =
        gitFitApiService.deleteUserActivity(username, AUTH_HEADER_PREFIX + token, activityId)

    suspend fun patchUserActivity(username: String, token: String, activityId: Long, patchActivityRequest: PatchActivityRequest) =
        gitFitApiService.patchUserActivity(username, AUTH_HEADER_PREFIX + token, activityId, patchActivityRequest)

    suspend fun saveUserActivity(username: String, token: String, postActivityRequest: PostActivityRequest) {
        gitFitApiService.saveUserActivity(username, AUTH_HEADER_PREFIX + token, postActivityRequest)
    }

}