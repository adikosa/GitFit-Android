package com.gitfit.android.data.remote.request

import org.threeten.bp.LocalDateTime

data class PatchActivityRequest (
    val id: Long,
    val user: String,
    val type: String,
    val timestamp: LocalDateTime,
    val duration: Int?,
    val points: Int
)