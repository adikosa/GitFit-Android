package com.gitfit.android.utils

import android.util.Log
import com.gitfit.android.BuildConfig
import org.koin.core.KoinApplication

fun Any.debug(message: String) {
    KoinApplication.logger.debug(message)
}

fun Any.info(message: String) {
    KoinApplication.logger.info(message)
}

fun Any.error(message: String) {
    KoinApplication.logger.error(message)
}

fun Any.logd(message: String) {
    if (BuildConfig.DEBUG)
        Log.d(className() + "." + methodName(), message)
}

fun Any.logi(message: String) {
    Log.i(className() + "." + methodName(), message)
}

fun Any.loge(message: String) {
    Log.e(className() + "." + methodName(), message)
}

fun Any.className(): String {
    this::class.java.enclosingClass?.let {
        return it.simpleName
    }
    return ""
}

fun Any.methodName(): String {
    this::class.java.enclosingMethod?.let {
        return it.name
    }
    return ""
}