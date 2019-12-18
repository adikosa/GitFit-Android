package com.gitfit.android.ui.home.journal.editActivity

interface EditActivityNavigator {
    fun showDatePicker(year: Int, month: Int, dayOfMonth: Int)
    fun showTimePicker(hour: Int, minute: Int)
    fun closeDialog()
    fun showDeleteDialog()
}