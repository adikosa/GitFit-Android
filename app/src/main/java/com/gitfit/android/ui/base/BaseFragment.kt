package com.gitfit.android.ui.base

import android.view.View
import androidx.fragment.app.Fragment
import com.gitfit.android.ui.MainActivity

abstract class BaseFragment : Fragment() {

    fun setLoading(isLoading: Boolean) {
        val activity = (activity as MainActivity)

        if (isLoading) {
            activity.mProgressBarVisibility.value = View.VISIBLE
        } else {
            activity.mProgressBarVisibility.value = View.GONE
        }
    }
}