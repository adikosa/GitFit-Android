package com.gitfit.android.data.remote.request

import com.gitfit.android.data.local.prefs.User

data class UserRegisterRequest(
    var username: String,
    var githubToken: String,
    var firstName: String,
    var lastName: String,
    var linesOfCodeGoal: Int,
    var cupsOfCoffeeGoal: Int
) {
    companion object {
        fun fromUser(user: User): UserRegisterRequest {
            return UserRegisterRequest(
                user.username!!,
                user.token!!,
                user.firstName!!,
                user.lastName!!,
                user.linesOfCodeGoal!!.toInt(),
                user.cupsOfCoffeeGoal!!.toInt()
            )
        }
    }
}