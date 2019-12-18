package com.gitfit.android.ui.home.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gitfit.android.AppConstants
import com.gitfit.android.data.local.db.entity.Activity
import com.gitfit.android.data.local.prefs.PreferenceProvider
import com.gitfit.android.data.local.prefs.User
import com.gitfit.android.data.remote.ResultWrapper
import com.gitfit.android.repos.ActivityRepository
import com.gitfit.android.repos.GitFitAPIRepository
import com.gitfit.android.ui.base.BaseViewModel
import kotlinx.coroutines.*

class HomeViewModel(
    private val activityRepository: ActivityRepository,
    private val gitFitAPIRepository: GitFitAPIRepository,
    private val preferenceProvider: PreferenceProvider
) : BaseViewModel<HomeNavigator>() {

    private var job: Job
    var user: User = preferenceProvider.getUser()
    var linesOfCode = MutableLiveData(0)
    var cupsOfCoffee = MutableLiveData(0)
    var progress = MutableLiveData(0)

    init {
        if (!preferenceProvider.userExists()) {
            signOut()
        }

        job = viewModelScope.launch {
            setLoading(true)
            loadProgress()
            setLoading(false)
        }
    }

    private suspend fun loadProgress() = withContext(Dispatchers.IO) {
        when(val response = gitFitAPIRepository.getActivities(user.username, user.token)) {
            is ResultWrapper.NetworkConnectivityError -> println('A')
            is ResultWrapper.GenericError -> println('B')
            is ResultWrapper.NetworkError -> println('C')
            is ResultWrapper.Success -> {
                val activitiesResponse = response.value
                val activities = Activity.fromActivitiesResponse(activitiesResponse)

                val totalLinesOfCode = activities
                    .filter { it.type == AppConstants.ACTIVITY_CODE_ADDITION }
                    .sumBy { it.points }.or(0)

                val totalCupsOfCoffee = activities
                    .filter { it.type == AppConstants.ACTIVITY_COFFEE }
                    .sumBy { it.points }.or(0)

                val currentProgress = ((totalLinesOfCode / user.linesOfCodeGoal.toDouble()) +
                        (totalCupsOfCoffee / user.cupsOfCoffeeGoal.toDouble())) * 50

                progress.postValue(currentProgress.toInt())
                linesOfCode.postValue(totalLinesOfCode)
                cupsOfCoffee.postValue(totalCupsOfCoffee)

                activityRepository.deleteAll()
                activityRepository.insertList(activities)
            }
        }
    }

    fun stopLoading() {
        job.cancel()
    }

    fun onAddActivityClick() {
        navigator()?.openNewActivityDialog()
    }

    private fun signOut() {
        preferenceProvider.removeUser()
        viewModelScope.launch {
            activityRepository.deleteAll()
        }

        navigator()!!.navigateToLoginFragment()
    }

}