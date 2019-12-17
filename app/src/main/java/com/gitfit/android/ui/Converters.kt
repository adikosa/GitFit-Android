package com.gitfit.android.ui

import androidx.databinding.InverseMethod


object Converters {
    @JvmStatic
    fun convertStringToInt(text: String): Int {
        return try {
            text.toInt()
        } catch (e: NumberFormatException) {
            0
        }
    }

    @JvmStatic
    @InverseMethod(value = "convertStringToInt")
    fun convertIntToString(value: Int): String {
        if(value == 0)
            return ""
        return value.toString()
    }
}