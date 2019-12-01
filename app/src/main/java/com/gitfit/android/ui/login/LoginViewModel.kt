package com.gitfit.android.ui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import com.gitfit.android.model.GithubTokenResponse
import com.gitfit.android.model.UserAuthResponse
import com.gitfit.android.model.UserLogin
import com.gitfit.android.repos.GitFitAPIRepository
import com.gitfit.android.ui.SingleLiveEvent
import com.gitfit.android.utils.saveTokenAndUsername
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginViewModel(
    private val gitFitAPIRepository: GitFitAPIRepository,
    private val context: Context
) : ViewModel() {

    val openCustomTabsIntentEvent = SingleLiveEvent<Void>()
    val openHomeActivityEvent = SingleLiveEvent<Void>()
    val openRegisterFragmentEvent = SingleLiveEvent<Void>()

    var githubTokenResponse: GithubTokenResponse? = null
        private set

    fun onLoginButtonClick() {
        openCustomTabsIntentEvent.call()
    }

    fun handleGithubCode(code: String) {
        CoroutineScope(Dispatchers.IO).launch {
            githubTokenResponse = gitFitAPIRepository.getGithubToken(code)
            val userResponse =
                gitFitAPIRepository.getUser(githubTokenResponse!!.username)
            val userAuthResponse: UserAuthResponse
            if (userResponse == null) {
                withContext(Dispatchers.Main) {
                    openRegisterFragmentEvent.call()
                }
            } else {
                userAuthResponse = gitFitAPIRepository.logInUser(
                    UserLogin(
                        githubTokenResponse!!.username,
                        githubTokenResponse!!.githubToken
                    )
                )
                context.saveTokenAndUsername(userAuthResponse.token, userAuthResponse.username)
                withContext(Dispatchers.Main) {
                    openHomeActivityEvent.call()
                }
            }
        }
    }
}