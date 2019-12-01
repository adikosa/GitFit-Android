package com.gitfit.android.ui.login

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.gitfit.android.AppConstants
import com.gitfit.android.R
import com.gitfit.android.databinding.FragmentLoginBinding
import com.gitfit.android.utils.navigateWithoutComingBack
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginViewModel.openCustomTabsIntentEvent.observe(this, Observer {
            openCustomTabsIntent()
        })

        loginViewModel.openHomeActivityEvent.observe(this, Observer {
            openHomeFragment()
        })

        loginViewModel.openRegisterFragmentEvent.observe(this, Observer {
            openRegisterFragment()
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentLoginBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_login, container, false
            )
        binding.viewModel = loginViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    fun onRedirect(code: String) {
        loginViewModel.handleGithubCode(code)
    }

    private fun openCustomTabsIntent() {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(
            context, Uri.parse(AppConstants.GITHUB_CODE_URL)
        )
    }

    private fun openHomeFragment() {
        findNavController().navigateWithoutComingBack(R.id.nav_graph_main, R.id.nav_graph_auth)
    }

    private fun openRegisterFragment() {
        findNavController().navigate(
            R.id.navigation_register, bundleOf(
                "username" to loginViewModel.githubTokenResponse!!.username,
                "githubToken" to loginViewModel.githubTokenResponse!!.githubToken
            )
        )
    }
}
