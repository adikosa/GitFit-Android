package com.gitfit.android.data.remote.request

import com.gitfit.android.data.local.prefs.User

data class PatchUserRequest (
    val firstName: String,
    val lastName: String,
    val linesOfCodeGoal: String,
    val cupsOfCoffeeGoal: String
) {
    companion object {
        fun fromUser(user: User): PatchUserRequest {
            return PatchUserRequest(
                user.firstName!!,
                user.lastName!!,
                user.linesOfCodeGoal!!,
                user.cupsOfCoffeeGoal!!
            )
        }
    }
}