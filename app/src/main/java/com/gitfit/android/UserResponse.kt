package com.gitfit.android

data class UserResponse(
    val id: Int,
    val createdAt: String,
    val firstName: String,
    val lastName: String,
    val username: String,
    val token: String
)