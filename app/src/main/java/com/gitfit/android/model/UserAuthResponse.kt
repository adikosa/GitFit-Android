package com.gitfit.android.model

data class UserAuthResponse(
    val id: Int,
    val createdAt: String,
    val firstName: String,
    val lastName: String,
    val username: String,
    val token: String
)