package com.gitfit.android.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gitfit.android.data.remote.response.ActivityResponse
import org.threeten.bp.LocalDateTime

@Entity(tableName = "activities")
data class Activity(
    @PrimaryKey val id: Long,
    val user: String,
    val type: String,
    val timestamp: LocalDateTime,
    val duration: Int?,
    val points: Int
) {
    companion object {
        fun fromActivitiesResponse(activitiesResponse: List<ActivityResponse>): List<Activity> {
            return activitiesResponse.map { fromActivitiesResponse(it) }
        }

        fun fromActivitiesResponse(activityResponse: ActivityResponse): Activity {
            return Activity(
                activityResponse.id,
                activityResponse.user,
                activityResponse.type,
                activityResponse.timestamp,
                activityResponse.duration,
                activityResponse.points
            )
        }
    }
}