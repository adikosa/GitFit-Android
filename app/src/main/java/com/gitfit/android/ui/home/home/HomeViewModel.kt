package com.gitfit.android.ui.home.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gitfit.android.AppConstants.Companion.ACTIVITY_CODE_ADDITION
import com.gitfit.android.AppConstants.Companion.ACTIVITY_COFFEE
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

    private lateinit var job: Job
    var user: User = preferenceProvider.getUser()
    var linesOfCode = MutableLiveData(0)
    var cupsOfCoffee = MutableLiveData(0)
    var progress = MutableLiveData(0)

    fun init() {
        if (!preferenceProvider.userExists()) {
            signOut()
        }

        job = viewModelScope.launch {
            countProgress()
            setLoading(true)
            loadProgress()
            setLoading(false)
        }
    }

    private suspend fun loadProgress() {
        when(val response = gitFitAPIRepository.getActivities(user.username, user.token)) {
            is ResultWrapper.NetworkConnectivityError -> {
                navigator()?.showToast("No network connection.")
            }
            is ResultWrapper.Success -> {
                val activitiesResponse = response.value
                val activities = Activity.fromActivitiesResponse(activitiesResponse)

                activityRepository.deleteAll()
                activityRepository.insertList(activities)
            }
            else -> {
                navigator()?.showToast("Server connection error.")
            }
        }

        countProgress()
    }

    private suspend fun countProgress() {
        countLinesOfCode()
        countCupsOfCoffee()

        val linesOfCodeProgress = countPercentage(linesOfCode.value!!, user.linesOfCodeGoal)
        val cupsOfCoffeeProgress = countPercentage(cupsOfCoffee.value!!, user.cupsOfCoffeeGoal)

        val currentProgress = (linesOfCodeProgress + cupsOfCoffeeProgress) * 50
        progress.postValue(currentProgress.toInt())
    }

    private suspend fun countLinesOfCode() {
        val activities = activityRepository.getAllByActivityType(ACTIVITY_CODE_ADDITION)
        val points = activities.sumBy { it.points }.or(0)
        linesOfCode.value = points
    }

    private suspend fun countCupsOfCoffee() {
        val activities = activityRepository.getAllByActivityType(ACTIVITY_COFFEE)
        val points = activities.sumBy { it.points }.or(0)
        cupsOfCoffee.value = points
    }

    private fun countPercentage(a: Int, b: Int): Double {
        var percentage = a.toDouble() / b.toDouble()
        if (percentage > 1.0) {
            percentage = 1.0
        }

        return percentage
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