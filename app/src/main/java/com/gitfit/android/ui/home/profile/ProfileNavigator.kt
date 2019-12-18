package com.gitfit.android.ui.home.profile

import com.gitfit.android.ui.base.BaseNavigator

interface ProfileNavigator : BaseNavigator {
    fun openProfileAlertDialog()
    fun openInfoAlertDialog()
    fun openSettings()
    fun navigateToLoginFragment()
}