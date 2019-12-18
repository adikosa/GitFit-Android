package com.gitfit.android.ui.home.journal.editActivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gitfit.android.data.local.db.entity.Activity
import com.gitfit.android.data.local.prefs.PreferenceProvider
import com.gitfit.android.data.remote.request.PatchActivityRequest
import com.gitfit.android.repos.ActivityRepository
import com.gitfit.android.repos.GitFitAPIRepository
import com.gitfit.android.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class EditActivityViewModel(
    activityId: Long,
    private val gitFitAPIRepository: GitFitAPIRepository,
    private val activityRepository: ActivityRepository,
    private val preferenceProvider: PreferenceProvider

) :
    BaseViewModel<EditActivityNavigator>() {

    private val currentUser by lazy { preferenceProvider.getUser() }

    var activity = runBlocking(Dispatchers.IO) { activityRepository.get(activityId) }

    val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
    val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)

    var activityType = MutableLiveData<String>(activity.type)
    var dateTime = MutableLiveData<LocalDateTime>(activity.timestamp)
    var value = MutableLiveData<Int>(activity.duration)

    fun onDateLayoutClick(hasFocus: Boolean) {
        if (hasFocus) {
            navigator()?.showDatePicker(
                dateTime.value!!.year,
                dateTime.value!!.monthValue - 1,
                dateTime.value!!.dayOfMonth
            )
        }
    }

    fun onTimeLayoutClick(hasFocus: Boolean) {
        if (hasFocus) {
            navigator()?.showTimePicker(
                dateTime.value!!.hour,
                dateTime.value!!.minute
            )
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
        navigator()?.closeDialog()
    }

    fun onSaveButtonClick() = viewModelScope.launch {
        patchActivity()
        navigator()?.closeDialog()
    }

    private suspend fun patchActivity() =
        withContext(Dispatchers.IO) {
            val patchActivityRequest = PatchActivityRequest(
                activity.id,
                activity.user,
                activityType.value!!,
                dateTime.value!!,
                value.value!!,
                value.value!!
            )

            val patchActivityResponse = gitFitAPIRepository.patchUserActivity(
                currentUser.username,
                currentUser.token,
                activity.id,
                patchActivityRequest
            )

            val activity = Activity.fromPatchActivityResponse(patchActivityResponse)
            activityRepository.insert(activity)
        }

    fun onDeleteButtonClick() {
        navigator()?.showDeleteDialog()
    }

    fun onConfirmDeleteClick() = viewModelScope.launch {
        deleteActivity()
        navigator()?.closeDialog()
    }

    private suspend fun deleteActivity() = withContext(Dispatchers.IO) {
        gitFitAPIRepository.deleteUserActivity(
            currentUser.username,
            currentUser.token,
            activity.id
        )
        activityRepository.delete(activity)
    }
}