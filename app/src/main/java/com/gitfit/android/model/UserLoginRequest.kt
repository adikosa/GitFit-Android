package com.gitfit.android.model

data class UserLoginRequest(
    var username: String,
    var githubToken: String
)