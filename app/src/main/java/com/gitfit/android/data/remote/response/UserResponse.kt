package com.gitfit.android.data.remote.response

data class UserResponse(
    val id: Int,
    val createdAt: String,
    val firstName: String,
    val lastName: String,
    val username: String,
    val linesOfCodeGoal: String,
    val cupsOfCoffeeGoal: String
)