package com.gitfit.android.data.remote.response

import org.threeten.bp.LocalDateTime

data class ActivityTypeResponse(
    val id: Long,
    val createdAt: LocalDateTime,
    val name: String,
    val points: Int
)