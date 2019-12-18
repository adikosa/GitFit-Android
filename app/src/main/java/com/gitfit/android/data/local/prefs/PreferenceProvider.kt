package com.gitfit.android.data.local.prefs

import android.content.Context
import android.content.SharedPreferences

class PreferenceProvider(
    context: Context
) {
    companion object {
        const val PREFERENCES_NAME = "user_details"
        const val PREFERENCES_MODE = Context.MODE_PRIVATE

        const val PREFERENCE_FIRST_NAME = "first_name"
        const val PREFERENCE_LAST_NAME = "last_name"
        const val PREFERENCE_USERNAME = "username"
        const val PREFERENCE_TOKEN = "token"
        const val PREFERENCE_LINES_OF_CODE_GOAL = "lines_of_code_goal"
        const val PREFERENCE_CUPS_OF_COFFEE_GOAL = "cups_of_coffee_goal"
    }

    private val appContext = context.applicationContext
    private val preferences: SharedPreferences
        get() = appContext.getSharedPreferences(
            PREFERENCES_NAME,
            PREFERENCES_MODE
        )

    fun saveUser(user: User) {
        withPreferences {
            putString(PREFERENCE_FIRST_NAME, user.firstName)
            putString(PREFERENCE_LAST_NAME, user.lastName)
            putString(PREFERENCE_USERNAME, user.username)
            putString(PREFERENCE_TOKEN, user.token)
            putInt(PREFERENCE_LINES_OF_CODE_GOAL, user.linesOfCodeGoal)
            putInt(PREFERENCE_CUPS_OF_COFFEE_GOAL, user.cupsOfCoffeeGoal)
        }
    }

    fun getUser(): User {
        val firstName = preferences.getString(PREFERENCE_FIRST_NAME, "")
        val lastName = preferences.getString(PREFERENCE_LAST_NAME, "")
        val username = preferences.getString(PREFERENCE_USERNAME, "")
        val token = preferences.getString(PREFERENCE_TOKEN, "")
        val linesOfCodeGoal = preferences.getInt(PREFERENCE_LINES_OF_CODE_GOAL, 0)
        val cupsOfCoffeeGoal = preferences.getInt(PREFERENCE_CUPS_OF_COFFEE_GOAL, 0)

        return User(
            firstName!!,
            lastName!!,
            username!!,
            token!!,
            linesOfCodeGoal,
            cupsOfCoffeeGoal
        )
    }

    fun removeUser() {
        withPreferences {
            remove(PREFERENCE_FIRST_NAME)
            remove(PREFERENCE_LAST_NAME)
            remove(PREFERENCE_USERNAME)
            remove(PREFERENCE_TOKEN)
            remove(PREFERENCE_LINES_OF_CODE_GOAL)
            remove(PREFERENCE_CUPS_OF_COFFEE_GOAL)
        }
    }

    fun userExists(): Boolean {
        return preferences.contains(PREFERENCE_FIRST_NAME)
                && preferences.contains(PREFERENCE_LAST_NAME)
                && preferences.contains(PREFERENCE_USERNAME)
                && preferences.contains(PREFERENCE_TOKEN)
    }

    private inline fun withPreferences(prefs: SharedPreferences.Editor.() -> Unit) {
        val editor = preferences.edit()
        prefs.invoke(editor)
        editor.apply()
    }
}