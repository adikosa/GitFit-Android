package com.gitfit.android.data.remote.request

data class UserLoginRequest(
    var username: String,
    var githubToken: String
)