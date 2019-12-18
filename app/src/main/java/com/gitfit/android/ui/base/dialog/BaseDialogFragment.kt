package com.gitfit.android.ui.base.dialog

import androidx.fragment.app.DialogFragment
import com.gitfit.android.utils.showToast

abstract class BaseDialogFragment : DialogFragment() {
    fun showToast(message: String) {
        context?.showToast(message)
    }
}