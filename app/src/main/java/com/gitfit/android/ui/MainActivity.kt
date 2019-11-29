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

        startActivityAndClearBackStack(LoginActivity::class)
    }
}
