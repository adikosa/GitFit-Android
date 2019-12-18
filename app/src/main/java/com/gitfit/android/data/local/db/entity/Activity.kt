package com.gitfit.android.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gitfit.android.data.remote.response.ActivityResponse
import com.gitfit.android.data.remote.response.PatchActivityResponse
import org.threeten.bp.LocalDateTime

@Entity(tableName = "activities")
data class Activity(
    @PrimaryKey val id: Long,
    val user: String,
    val type: String,
    val timestamp: LocalDateTime,
    val duration: Int,
    val points: Int
) {
    companion object {
        fun fromActivitiesResponse(activitiesResponse: List<ActivityResponse>): List<Activity> {
            return activitiesResponse.map { fromActivityResponse(it) }
        }

        fun fromActivityResponse(activityResponse: ActivityResponse): Activity {
            return Activity(
                activityResponse.id,
                activityResponse.user,
                activityResponse.type,
                activityResponse.timestamp,
                activityResponse.duration,
                activityResponse.points
            )
        }

        fun fromPatchActivityResponse(patchActivityResponse: PatchActivityResponse): Activity {
            return Activity(
                patchActivityResponse.id,
                patchActivityResponse.user,
                patchActivityResponse.type,
                patchActivityResponse.timestamp,
                patchActivityResponse.duration,
                patchActivityResponse.points
            )
        }
    }
}