package com.gitfit.android.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.gitfit.android.R
import com.gitfit.android.ui.login.LoginFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.parent!!.id == R.id.nav_graph_main)
                bottom_nav_view.visibility = View.VISIBLE
            else
                bottom_nav_view.visibility = View.GONE
        }

        bottom_nav_view.setupWithNavController(navController)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        val code = intent?.data?.getQueryParameter("code")

        code?.let {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

            val fragmentOnTop = navHostFragment.childFragmentManager.fragments[0]

            if (fragmentOnTop is LoginFragment) {
                fragmentOnTop.onRedirect(code)
            }
        }
    }
}
