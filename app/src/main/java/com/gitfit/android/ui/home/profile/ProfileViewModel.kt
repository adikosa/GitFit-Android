package com.gitfit.android.ui.home.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gitfit.android.AppConstants
import com.gitfit.android.data.local.db.entity.Activity
import com.gitfit.android.data.local.prefs.PreferenceProvider
import com.gitfit.android.data.local.prefs.User
import com.gitfit.android.data.remote.ResultWrapper
import com.gitfit.android.data.remote.request.PatchUserRequest
import com.gitfit.android.repos.ActivityRepository
import com.gitfit.android.repos.GitFitAPIRepository
import com.gitfit.android.ui.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(
    private val gitFitAPIRepository: GitFitAPIRepository,
    private val activityRepository: ActivityRepository,
    private val preferenceProvider: PreferenceProvider) : BaseViewModel<ProfileNavigator>() {

    var user = MutableLiveData<User>()

    init {
        user.value = preferenceProvider.getUser()
    }

    fun onProfileDetailsLayoutClick() {
        navigator()!!.openProfileAlertDialog()
    }

    fun update() = viewModelScope.launch {
        val user = user.value!!
        preferenceProvider.saveUser(user)

        when(gitFitAPIRepository.updateUser(
            user.username,
            PatchUserRequest.fromUser(user),
            user.token
        )) {
            is ResultWrapper.NetworkConnectivityError -> withContext(Dispatchers.Main) {
                navigator()?.showToast("No network connection.")
            }
            is ResultWrapper.Success -> { /* ignore result */ }
            else -> withContext(Dispatchers.Main) {
                navigator()?.showToast("Server connection error.")
            }
        }
    }

    fun signOut() {
        preferenceProvider.removeUser()
        viewModelScope.launch {
            activityRepository.deleteAll()
        }

        navigator()!!.navigateToLoginFragment()
    }

    fun onActionInfoMenuClick() {
        navigator()!!.openInfoAlertDialog()
    }

    fun onActionSettingsMenuClick() {
        navigator()!!.openSettings()
    }
}