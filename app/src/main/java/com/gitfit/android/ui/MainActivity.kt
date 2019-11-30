package com.gitfit.android.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gitfit.android.R
import com.gitfit.android.ui.login.LoginActivity
import com.gitfit.android.utils.startActivityAndClearBackStack

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //todo if token in shared prefs -> check is valid - (if valid home else login)
        // else -> go to login activity
        startActivityAndClearBackStack(LoginActivity::class)
    }
}
