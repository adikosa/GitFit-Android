package com.gitfit.android.data.remote.response

import org.threeten.bp.LocalDateTime

data class ActivityResponse(
    val id: Long,
    val user: String,
    val type: String,
    val timestamp: LocalDateTime,
    val duration: Int,
    val points: Int
)