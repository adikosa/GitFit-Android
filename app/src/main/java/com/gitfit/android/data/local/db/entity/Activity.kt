package com.gitfit.android.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "activities")
data class Activity(
    @PrimaryKey val id: Long,
    val user: String,
    val type: String,
    val timestamp: LocalDateTime,
    val duration: Int,
    val points: Int
)