package com.gitfit.android.ui.login

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.gitfit.android.AppConstants
import com.gitfit.android.R
import com.gitfit.android.databinding.FragmentLoginBinding
import com.gitfit.android.ui.base.BaseFragment
import com.gitfit.android.utils.navigateWithoutComingBack
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment(), LoginNavigator {

    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel.setNavigator(this)

        loginViewModel.mIsLoading.observe(this, Observer {
            setLoading(it)
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

    override fun openCustomTabsIntent() {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(
            context, Uri.parse(AppConstants.GITHUB_CODE_URL)
        )
    }

    override fun navigateToHomeFragment() {
        findNavController().navigateWithoutComingBack(R.id.nav_graph_main)
    }

    override fun navigateToRegisterFragment() {
        findNavController().navigate(
            R.id.navigation_register, bundleOf(
                "username" to loginViewModel.githubTokenResponse!!.username,
                "githubToken" to loginViewModel.githubTokenResponse!!.githubToken
            )
        )
    }
}
