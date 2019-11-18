package com.gitfit.android.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gitfit.android.repos.AuthRepository


class HomeViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private var tokenLiveData: LiveData<String?>? = null

    fun getToken(url: String): LiveData<String?> {
        // todo do normal logic
        tokenLiveData = authRepository.getTokenFromUrl(url)
        return tokenLiveData as LiveData<String?>
    }
}