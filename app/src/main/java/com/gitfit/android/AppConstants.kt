package com.gitfit.android

class AppConstants {
    companion object {
        const val GIT_FIT_API_URL = "http://localhost:1234"

        const val GITHUB_CODE_URL = "$GIT_FIT_API_URL/github/login/oauth/authorize"

        //ACTIVITY TYPES
        const val ACTIVITY_COFFEE = "COFFEE"
        const val ACTIVITY_TABLE_TENNIS = "TABLE_TENNIS"
        const val ACTIVITY_GAME_CONSOLE_BREAK = "GAME_CONSOLE_BREAK"
        const val ACTIVITY_CODE_ADDITION = "CODE_ADDITION"
        val ACTIVITY_TYPES = setOf(
            ACTIVITY_COFFEE,
            ACTIVITY_TABLE_TENNIS,
            ACTIVITY_GAME_CONSOLE_BREAK,
            ACTIVITY_CODE_ADDITION
        )
    }
}