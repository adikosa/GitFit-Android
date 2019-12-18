package com.gitfit.android.ui.home.journal

import androidx.lifecycle.viewModelScope
import com.gitfit.android.AppConstants.Companion.ACTIVITY_TYPES
import com.gitfit.android.data.local.db.entity.Activity
import com.gitfit.android.data.local.prefs.PreferenceProvider
import com.gitfit.android.data.local.prefs.User
import com.gitfit.android.data.remote.ResultWrapper
import com.gitfit.android.data.remote.ResultWrapper.NetworkConnectivityError
import com.gitfit.android.data.remote.ResultWrapper.Success
import com.gitfit.android.repos.ActivityRepository
import com.gitfit.android.repos.ActivityTypeRepository
import com.gitfit.android.repos.GitFitAPIRepository
import com.gitfit.android.ui.base.BaseViewModel
import com.gitfit.android.utils.info
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class JournalViewModel(
    private val activityRepository: ActivityRepository,
    private val activityTypeRepository: ActivityTypeRepository,
    private val gitFitAPIRepository: GitFitAPIRepository,
    private val preferenceProvider: PreferenceProvider
) : BaseViewModel<JournalNavigator>() {

    var activities = activityRepository.getLiveData()

    init {
        onActionSyncClick()
    }

    fun onActionSyncClick() {
        loadData()
    }

    private fun loadData() = viewModelScope.launch {
        setLoading(true)
        loadActivityTypes()
        loadActivities(preferenceProvider.getUser())
        setLoading(false)
    }

    private suspend fun loadActivityTypes() {
        when(val response = gitFitAPIRepository.getActivityTypes()) {
            is NetworkConnectivityError -> {
                navigator()?.showToast("No network connection.")
            }
            is Success -> {
                val activityTypes = response.value

                activityTypeRepository.deleteAll()
                val stringActivityTypes = activityTypes.mapTo(HashSet()) { it.name }
                if (stringActivityTypes == ACTIVITY_TYPES) {
                    activityTypeRepository.insertList(activityTypes)
                } else {
                    activityTypeRepository.insertList(activityTypes.filter { ACTIVITY_TYPES.contains(it.name) })
                    info("Server activity types don't match implemented ones. Make sure you have implemented all activity types!")
                }
            }
            else -> {
                navigator()?.showToast("Server connection error.")
            }
        }
    }

    private suspend fun loadActivities(user: User) {
        when(val response = gitFitAPIRepository.getActivities(user.username, user.token)) {
            is NetworkConnectivityError -> {
                navigator()?.showToast("No network connection.")
            }
            is Success -> {
                val activities = response.value
                    .filter { ACTIVITY_TYPES.contains(it.type) }
                    .map { a -> Activity.fromActivityResponse(a) }

                activityRepository.deleteAll()
                activityRepository.insertList(activities)
            } else -> {
                navigator()?.showToast("Server connection error.")
            }
        }
    }

}