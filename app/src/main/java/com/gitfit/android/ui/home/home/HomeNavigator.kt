package com.gitfit.android.ui.home.home

import com.gitfit.android.ui.base.BaseNavigator

interface HomeNavigator : BaseNavigator {
    fun openNewActivityDialog()
    fun navigateToLoginFragment()
}