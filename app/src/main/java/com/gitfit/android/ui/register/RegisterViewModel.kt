package com.gitfit.android.ui.register

import android.content.Context
import androidx.lifecycle.ViewModel
import com.gitfit.android.model.UserRegister
import com.gitfit.android.repos.GitFitAPIRepository
import com.gitfit.android.ui.SingleLiveEvent
import com.gitfit.android.utils.saveTokenAndUsername
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel(
    private val gitFitAPIRepository: GitFitAPIRepository,
    private val context: Context
) : ViewModel() {

    var userRegister = UserRegister("username", "token", "", "")

    val openHomeActivityEvent = SingleLiveEvent<Void>()

    fun onRegisterButtonClick() {
        CoroutineScope(Dispatchers.IO).launch {
            val userAuthResponse = gitFitAPIRepository.registerUser(
                userRegister
            )
            context.saveTokenAndUsername(userAuthResponse.token, userAuthResponse.username)
            withContext(Dispatchers.Main) {
                openHomeActivityEvent.call()
            }
        }
    }
}
