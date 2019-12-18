package com.gitfit.android.ui.login

import androidx.lifecycle.viewModelScope
import com.gitfit.android.data.local.prefs.PreferenceProvider
import com.gitfit.android.data.remote.response.GithubTokenResponse
import com.gitfit.android.data.local.prefs.User
import com.gitfit.android.data.remote.ResultWrapper
import com.gitfit.android.data.remote.ResultWrapper.*
import com.gitfit.android.data.remote.response.UserAuthResponse
import com.gitfit.android.data.remote.request.UserLoginRequest
import com.gitfit.android.repos.GitFitAPIRepository
import com.gitfit.android.ui.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class LoginViewModel(
    private val gitFitAPIRepository: GitFitAPIRepository,
    private val preferences: PreferenceProvider
) : BaseViewModel<LoginNavigator>() {

    var githubTokenResponse: GithubTokenResponse? = null
        private set

    fun onLoginButtonClick() {
        navigator()!!.openCustomTabsIntent()
    }

    fun handleGithubCode(code: String) = viewModelScope.launch {
        setLoading(true)

        when (val response = gitFitAPIRepository.getGithubToken(code)) {
            is NetworkConnectivityError -> withContext(Dispatchers.Main) {
                navigator()?.showToast("No internet connection.")
            }
            is GenericError -> println("code = $code")
            is Success -> {
                githubTokenResponse = response.value
                authorizeUser()
            }
            else -> withContext(Dispatchers.Main) {
                navigator()?.showToast("Server connection error.")
            }
        }
    }

    private suspend fun authorizeUser() {
        val userResponse =
            gitFitAPIRepository.getUser(githubTokenResponse!!.username)

        val userAuthResponse: UserAuthResponse

        if (userResponse == null) {
            withContext(Dispatchers.Main) {
                navigator()!!.navigateToRegisterFragment()
                setLoading(false)
            }

        } else {
            userAuthResponse = gitFitAPIRepository.logInUser(
                UserLoginRequest(
                    githubTokenResponse!!.username,
                    githubTokenResponse!!.githubToken
                )
            )

            preferences.saveUser(User.fromAuthResponse(userAuthResponse))

            withContext(Dispatchers.Main) {
                navigator()!!.navigateToHomeFragment()
                setLoading(false)
            }
        }
    }
}