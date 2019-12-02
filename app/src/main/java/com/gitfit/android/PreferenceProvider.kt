package com.gitfit.android

import android.content.Context
import android.content.SharedPreferences
import com.gitfit.android.model.User

class PreferenceProvider(
    context: Context
) {
    companion object {
        const val PREFERENCES_NAME = "user_details"
        const val PREFERENCES_MODE = Context.MODE_PRIVATE

        const val PREFERENCE_USERNAME = "username"
        const val PREFERENCE_TOKEN = "token"
    }

    private val appContext = context.applicationContext
    private val preferences: SharedPreferences
        get() = appContext.getSharedPreferences(PREFERENCES_NAME, PREFERENCES_MODE)

    fun saveUser(user: User) {
        withPreferences {
            putString(PREFERENCE_USERNAME, user.username)
            putString(PREFERENCE_TOKEN, user.token)
        }
    }

    fun getUser(): User {
        val username = preferences.getString(PREFERENCE_USERNAME, null)
        val token = preferences.getString(PREFERENCE_TOKEN, null)

        return User(username, token)
    }

    fun removeUser() {
        withPreferences {
            remove(PREFERENCE_USERNAME)
            remove(PREFERENCE_TOKEN)
        }
    }

    fun userExists(): Boolean {
        return preferences.contains(PREFERENCE_USERNAME)
                && preferences.contains(PREFERENCE_TOKEN)
    }

    private inline fun withPreferences(prefs: SharedPreferences.Editor.() -> Unit) {
        val editor = preferences.edit()
        prefs.invoke(editor)
        editor.apply()
    }
}