package com.gitfit.android.ui

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.gitfit.android.data.local.prefs.PreferenceProvider
import com.gitfit.android.R
import com.gitfit.android.databinding.ActivityMainBinding
import com.gitfit.android.repos.GitFitAPIRepository
import com.gitfit.android.ui.login.LoginFragment
import com.gitfit.android.utils.navigateWithoutComingBack
import com.gitfit.android.utils.showToast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val preferences: PreferenceProvider by inject()
    private val gitFitAPIRepository: GitFitAPIRepository by inject()

    val mProgressBarVisibility: MutableLiveData<Int> = MutableLiveData()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(
            this, R.layout.activity_main).apply {
            activity = this@MainActivity
            lifecycleOwner = this@MainActivity
        }

        navController = findNavController(R.id.nav_host_fragment)
        setupBottomNavView()

        if (isNetworkConnected()) {
            checkUser()
        } else {
            showToast("No internet connection")
            // TODO change later
        }
    }

    private fun isNetworkConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }

    private fun setupBottomNavView() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.parent!!.id == R.id.nav_graph_main)
                bottom_nav_view.visibility = View.VISIBLE
            else
                bottom_nav_view.visibility = View.GONE
        }

        bottom_nav_view.setupWithNavController(navController)
    }

    private fun checkUser() {
        if (preferences.userExists()) {
            val user = preferences.getUser()

            val handler = CoroutineExceptionHandler { _, _ ->
                CoroutineScope(Dispatchers.Main).launch {
                    showToast("Server connection error")
                }
            }

            CoroutineScope(Dispatchers.IO).launch(handler) {
                if (gitFitAPIRepository.isTokenValid(user.username, user.token)) {
                    withContext(Dispatchers.Main) {
                        navController.navigateWithoutComingBack(R.id.nav_graph_main)
                    }
                } else {
                    preferences.removeUser()
                }
            }
        }
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
