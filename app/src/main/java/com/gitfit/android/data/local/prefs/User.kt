package com.gitfit.android.data.local.prefs

import com.gitfit.android.data.remote.response.UserAuthResponse

class User(
    var firstName: String? = null,
    var lastName: String? = null,
    var username: String? = null,
    var token: String? = null,
    var linesOfCodeGoal: String? = null,
    var cupsOfCoffeeGoal: String? = null) {

    companion object {
        fun fromAuthResponse(userAuthResponse: UserAuthResponse): User {
            return User(
                userAuthResponse.firstName,
                userAuthResponse.lastName,
                userAuthResponse.username,
                userAuthResponse.token,
                userAuthResponse.linesOfCodeGoal.toString(),
                userAuthResponse.cupsOfCoffeeGoal.toString()
            )
        }
    }

    fun getFullName() = "$firstName $lastName"
}



