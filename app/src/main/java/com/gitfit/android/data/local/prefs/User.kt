package com.gitfit.android.data.local.prefs

import com.gitfit.android.data.remote.response.UserAuthResponse

class User(
    var firstName: String = "",
    var lastName: String = "",
    var username: String = "",
    var token: String = "",
    var linesOfCodeGoal: Int,
    var cupsOfCoffeeGoal: Int) {

    companion object {
        fun fromAuthResponse(userAuthResponse: UserAuthResponse): User {
            return User(
                userAuthResponse.firstName,
                userAuthResponse.lastName,
                userAuthResponse.username,
                userAuthResponse.token,
                userAuthResponse.linesOfCodeGoal,
                userAuthResponse.cupsOfCoffeeGoal
            )
        }
    }

    fun getFullName() = "$firstName $lastName"
}



