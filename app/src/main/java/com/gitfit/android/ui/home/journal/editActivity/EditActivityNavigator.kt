package com.gitfit.android.ui.home.journal.editActivity

import com.gitfit.android.ui.base.BaseNavigator

interface EditActivityNavigator: BaseNavigator {
    fun showDatePicker(year: Int, month: Int, dayOfMonth: Int)
    fun showTimePicker(hour: Int, minute: Int)
    fun closeDialog()
    fun showDeleteDialog()
}