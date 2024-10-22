package com.gitfit.android.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun NavController.navigateWithoutComingBack(@IdRes resIdDestination: Int) {
    val navOptions = NavOptions.Builder()
        .setPopUpTo(this.graph.id, false)
        .build()

    this.navigate(resIdDestination, null, navOptions)
}

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

suspend fun Context.showToastOnUIThread(text: String) {
    val context = this
    withContext(Dispatchers.Main) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }
}

fun Context.showToast(@StringRes resId: Int) {
    Toast.makeText(this, resId, Toast.LENGTH_LONG).show()
}

fun Fragment.showToast(text: String) {
    context?.showToast(text)
}

fun Fragment.showToast(@StringRes resId: Int) {
    context?.showToast(resId)
}