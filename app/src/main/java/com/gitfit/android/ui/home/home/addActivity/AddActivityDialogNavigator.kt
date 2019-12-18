package com.gitfit.android.ui.home.home.addActivity

import com.gitfit.android.ui.base.BaseNavigator

interface AddActivityDialogNavigator : BaseNavigator {
    fun closeDialog()
    fun dismissDialog()
    fun showDatePicker()
    fun showTimePicker()
}