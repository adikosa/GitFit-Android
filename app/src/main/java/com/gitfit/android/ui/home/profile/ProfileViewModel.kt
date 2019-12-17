package com.gitfit.android.ui.home.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gitfit.android.data.local.prefs.PreferenceProvider
import com.gitfit.android.data.local.prefs.User
import com.gitfit.android.data.remote.request.PatchUserRequest
import com.gitfit.android.repos.ActivityRepository
import com.gitfit.android.repos.GitFitAPIRepository
import com.gitfit.android.ui.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

    fun update() {
        val user = user.value!!
        preferenceProvider.saveUser(user)

        CoroutineScope(Dispatchers.IO).launch {
            gitFitAPIRepository.updateUser(
                user.username!!,
                PatchUserRequest.fromUser(user),
                user.token!!
            )
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