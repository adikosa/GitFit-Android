package com.gitfit.android.ui.home.home.add_activity

import androidx.lifecycle.MutableLiveData
import com.gitfit.android.ui.base.BaseViewModel
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class AddActivityViewModel : BaseViewModel<AddActivityDialogNavigator>() {

    val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
    val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)

    var activityType = MutableLiveData<String>()
    var dateTime = MutableLiveData<LocalDateTime>(LocalDateTime.now())
    var value = MutableLiveData<Int>(0)

    fun onDateLayoutClick(hasFocus: Boolean) {
        if (hasFocus) {
            navigator()?.showDatePicker()
        }
    }

    fun onTimeLayoutClick(hasFocus: Boolean) {
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
        navigator()?.closeDialog()
    }

    fun onSaveButtonClick() {
        //TODO implement
    }
}