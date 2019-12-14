package com.gitfit.android.ui.register

import com.gitfit.android.data.local.prefs.PreferenceProvider
import com.gitfit.android.data.local.prefs.User
import com.gitfit.android.data.remote.request.UserRegisterRequest
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

    val user = User(
        linesOfCodeGoal = "200",
        cupsOfCoffeeGoal = "5"
    )

    fun onRegisterButtonClick() {
        CoroutineScope(Dispatchers.IO).launch {
            val userRegisterRequest = UserRegisterRequest.fromUser(user)
            val userAuthResponse = gitFitAPIRepository.registerUser(userRegisterRequest)
            preferences.saveUser(User.fromAuthResponse(userAuthResponse))

            withContext(Dispatchers.Main) {
                navigator()!!.navigateToHomeFragment()
            }
        }
    }
}
