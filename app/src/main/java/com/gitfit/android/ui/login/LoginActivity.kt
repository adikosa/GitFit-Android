package com.gitfit.android.ui.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.gitfit.android.AppConstants
import com.gitfit.android.R
import com.gitfit.android.databinding.ActivityLoginBinding
import com.gitfit.android.ui.HomeActivity
import com.gitfit.android.utils.startActivityAndClearBackStack
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityLoginBinding>(
            this, R.layout.activity_login
        ).apply {
            lifecycleOwner = this@LoginActivity
            viewModel = loginViewModel
        }

        loginViewModel.openCustomTabsIntentEvent.observe(this, Observer {
            openCustomTabsIntent()
        })

        loginViewModel.openHomeActivityEvent.observe(this, Observer {
            openHomeActivity()
        })
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        val code = intent?.data?.getQueryParameter("code")
        loginViewModel.handleGithubCode(code)
    }

    private fun openCustomTabsIntent() {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(
                this, Uri.parse(AppConstants.GITHUB_CODE_URL))
    }

    private fun openHomeActivity() {
        startActivityAndClearBackStack(HomeActivity::class)
    }
}
