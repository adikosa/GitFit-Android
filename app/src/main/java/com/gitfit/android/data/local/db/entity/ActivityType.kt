package com.gitfit.android.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDateTime

@Entity(tableName = "activity_types")
data class ActivityType (
    @PrimaryKey val id: Long,
    val createdAt: LocalDateTime,
    val name: String,
    val points: Int
)