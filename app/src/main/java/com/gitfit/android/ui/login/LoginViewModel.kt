package com.gitfit.android.ui.login

import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.gitfit.android.PreferenceProvider
import com.gitfit.android.model.GithubTokenResponse
import com.gitfit.android.model.User
import com.gitfit.android.model.UserAuthResponse
import com.gitfit.android.model.UserLoginRequest
import com.gitfit.android.repos.GitFitAPIRepository
import com.gitfit.android.ui.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val gitFitAPIRepository: GitFitAPIRepository,
    private val preferences: PreferenceProvider
) : BaseViewModel<LoginNavigator>() {

    var githubTokenResponse: GithubTokenResponse? = null
        private set

    fun onLoginButtonClick() {
        navigator()!!.openCustomTabsIntent()
    }

    fun handleGithubCode(code: String) {
        setLoading(true)

        CoroutineScope(Dispatchers.IO).launch {
            githubTokenResponse = gitFitAPIRepository.getGithubToken(code)

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

                preferences.saveUser(User(userAuthResponse.username, userAuthResponse.token))


                withContext(Dispatchers.Main) {
                    navigator()!!.navigateToHomeFragment()
                    setLoading(false)
                }
            }
        }
    }
}