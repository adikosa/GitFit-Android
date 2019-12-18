package com.gitfit.android.ui.home.home.addActivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gitfit.android.data.local.db.entity.Activity
import com.gitfit.android.data.local.prefs.PreferenceProvider
import com.gitfit.android.data.remote.ResultWrapper
import com.gitfit.android.data.remote.request.PostActivityRequest
import com.gitfit.android.repos.ActivityRepository
import com.gitfit.android.repos.GitFitAPIRepository
import com.gitfit.android.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class AddActivityViewModel(
    private val gitFitAPIRepository: GitFitAPIRepository,
    private val activityRepository: ActivityRepository,
    private val preferenceProvider: PreferenceProvider
) : BaseViewModel<AddActivityDialogNavigator>() {

    val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
    val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)

    val activityType = MutableLiveData<String>()
    val dateTime = MutableLiveData<LocalDateTime>(LocalDateTime.now())
    val value = MutableLiveData(0)

    fun onDateEditTextClick(hasFocus: Boolean) {
        if (hasFocus) {
            navigator()?.showDatePicker()
        }
    }

    fun onTimeEditTextClick(hasFocus: Boolean) {
        if (hasFocus) {
            navigator()?.showTimePicker()
        }
    }

    fun saveSelectedDate(selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int) {
        val selectedDate = LocalDate.of(selectedYear, selectedMonth + 1, selectedDayOfMonth)
        dateTime.value = LocalDateTime.of(selectedDate, dateTime.value!!.toLocalTime())
    }

    fun saveSelectedTime(selectedHour: Int, selectedMinute: Int) {
        val selectedTime = LocalTime.of(selectedHour, selectedMinute)
        dateTime.value =
            LocalDateTime.of(dateTime.value!!.toLocalDate(), selectedTime)
    }

    fun onCancelButtonClick() {
        navigator()?.dismissDialog()
    }

    fun onSaveButtonClick() = viewModelScope.launch{
        saveActivity()
        navigator()?.closeDialog()
    }

    private suspend fun saveActivity() {
        val user = preferenceProvider.getUser()
        val activity = Activity(
            0, user.username, activityType.value!!,
            dateTime.value!!, value.value!!, value.value!!
        )

        when(gitFitAPIRepository.saveUserActivity(
            user.username, user.token,
            PostActivityRequest.fromActivity(activity)
        )){
            is ResultWrapper.NetworkConnectivityError -> {
                navigator()?.showToast("No network connection.")
            }
            is ResultWrapper.Success -> {
                activityRepository.insert(activity)
            }
            else -> {
                navigator()?.showToast("Server connection error.")
            }
        }
    }

}