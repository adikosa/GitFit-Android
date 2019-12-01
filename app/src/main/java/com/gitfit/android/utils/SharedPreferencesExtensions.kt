package com.gitfit.android.utils

import android.content.Context
import android.content.SharedPreferences

fun Context.saveTokenAndUsername(token: String, username: String) {
    val sharedPreferences: SharedPreferences =
        this.getSharedPreferences("user_details", Context.MODE_PRIVATE)
    sharedPreferences.edit()
        .putString("token", token)
        .putString("username", username)
        .apply()
}