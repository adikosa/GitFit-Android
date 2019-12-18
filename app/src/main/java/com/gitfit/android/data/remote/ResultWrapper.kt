package com.gitfit.android.data.remote

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T): ResultWrapper<T>()
    data class GenericError(val code: Int? = null): ResultWrapper<Nothing>()
    object NetworkConnectivityError: ResultWrapper<Nothing>()
    object NetworkError: ResultWrapper<Nothing>()

    // TODO: 401, 403, 404 etc.
}