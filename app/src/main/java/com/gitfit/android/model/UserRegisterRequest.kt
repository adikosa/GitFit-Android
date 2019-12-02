package com.gitfit.android.model

data class UserRegisterRequest(
    var username: String,
    var githubToken: String,
    var firstName: String,
    var lastName: String
)