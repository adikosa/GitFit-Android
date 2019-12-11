package com.gitfit.android.utils

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