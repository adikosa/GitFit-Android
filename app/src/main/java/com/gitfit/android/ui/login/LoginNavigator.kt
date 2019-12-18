package com.gitfit.android.ui.login

import com.gitfit.android.ui.base.BaseNavigator

interface LoginNavigator : BaseNavigator {
    fun openCustomTabsIntent()
    fun navigateToHomeFragment()
    fun navigateToRegisterFragment()
}