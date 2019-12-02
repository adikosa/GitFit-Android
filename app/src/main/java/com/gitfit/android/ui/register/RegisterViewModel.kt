package com.gitfit.android.ui.register

import com.gitfit.android.PreferenceProvider
import com.gitfit.android.model.User
import com.gitfit.android.model.UserRegisterRequest
import com.gitfit.android.repos.GitFitAPIRepository
import com.gitfit.android.ui.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel(
    private val gitFitAPIRepository: GitFitAPIRepository,
    private val preferences: PreferenceProvider
) : BaseViewModel<RegisterNavigator>() {

    var userRegister = UserRegisterRequest("username", "token", "", "")

    fun onRegisterButtonClick() {
        CoroutineScope(Dispatchers.IO).launch {
            val userAuthResponse = gitFitAPIRepository.registerUser(
                userRegister
            )

            preferences.saveUser(User(userAuthResponse.username, userAuthResponse.token))

            withContext(Dispatchers.Main) {
                navigator()!!.navigateToHomeFragment()
            }
        }
    }
}
