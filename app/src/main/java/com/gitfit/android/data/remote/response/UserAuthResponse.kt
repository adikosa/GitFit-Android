package com.gitfit.android.data.remote.response

data class UserAuthResponse(
    val id: Int,
    val createdAt: String,
    val firstName: String,
    val lastName: String,
    val username: String,
    val token: String,
    val linesOfCodeGoal: Int,
    val cupsOfCoffeeGoal: Int
)