package com.gitfit.android.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gitfit.android.model.UserAuthResponse
import com.gitfit.android.model.UserLogin
import com.gitfit.android.model.UserRegister
import com.gitfit.android.repos.GitFitAPIRepository
import com.gitfit.android.ui.SingleLiveEvent
import com.gitfit.android.utils.logi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val gitFitAPIRepository: GitFitAPIRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is login activity"
    }
    val text: LiveData<String> = _text

    val openCustomTabsIntentEvent = SingleLiveEvent<Void>()
    val openHomeActivityEvent = SingleLiveEvent<Void>()

    fun onLoginButtonClick() {
        openCustomTabsIntentEvent.call()
    }

    fun handleGithubCode(code: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val retrievedGithubTokenResponse = gitFitAPIRepository.getGithubToken(code)
            val userResponse =
                gitFitAPIRepository.getUser(retrievedGithubTokenResponse.username)
            val userAuthResponse: UserAuthResponse
            if (userResponse == null) {
                //todo launch register fragment
                userAuthResponse = gitFitAPIRepository.registerUser(
                    UserRegister(
                        retrievedGithubTokenResponse.username,
                        retrievedGithubTokenResponse.githubToken,
                        "YourName",
                        "YourSurname"
                    )
                )
            } else {
                //todo launch log in fragment
                userAuthResponse = gitFitAPIRepository.logInUser(
                    UserLogin(
                        retrievedGithubTokenResponse.username,
                        retrievedGithubTokenResponse.githubToken
                    )
                )
            }
            logi(userAuthResponse.toString())
        }


    }
}