package com.gitfit.android.ui.home.journal.editactivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gitfit.android.data.local.prefs.PreferenceProvider
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

    fun onSaveButtonClick() {
        //TODO implement
    }

    fun onDeleteButtonClick() {
        navigator()?.showDeleteDialog()
    }

    fun deleteActivity() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                gitFitAPIRepository.deleteUserActivity(
                    currentUser.username!!,
                    currentUser.token!!,
                    activity.id
                )
                activityRepository.delete(activity)
            }
            navigator()?.closeDialog()
        }
    }
}