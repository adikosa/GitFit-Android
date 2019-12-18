package com.gitfit.android.data.remote

import java.io.IOException

class NoConnectivityException : IOException() {

    override val message: String?
        get() = "No internet connection"
}