package com.gitfit.android.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gitfit.android.ui.SingleLiveEvent

class LoginViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is login activity"
    }
    val text: LiveData<String> = _text

    val openCustomTabsIntentEvent = SingleLiveEvent<Void>()
    val openHomeActivityEvent = SingleLiveEvent<Void>()

    fun onLoginButtonClick() {
        openCustomTabsIntentEvent.call()
    }

    fun handleGithubCode(code: String?) {
        // TODO code logic

        openHomeActivityEvent.call()
    }
}