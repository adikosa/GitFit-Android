package com.gitfit.android.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.ref.WeakReference

abstract class BaseViewModel<N> : ViewModel() {

    lateinit var mNavigator: WeakReference<N>
    var mIsLoading: MutableLiveData<Boolean> = MutableLiveData()
        private set

    init {
        mIsLoading.value = false
    }

    fun navigator() = mNavigator.get()

    fun setNavigator(n: N) {
        this.mNavigator = WeakReference(n)
    }

    fun setLoading(isLoading: Boolean) {
        mIsLoading.value = isLoading
    }
}