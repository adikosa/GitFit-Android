package com.gitfit.android.data.remote.request

import com.gitfit.android.data.local.db.entity.Activity
import org.threeten.bp.LocalDateTime

data class PostActivityRequest(
    private var user: String,
    private var type: String,
    private var timestamp: LocalDateTime,
    private var duration: Int,
    private var points: Int
) {
    companion object {
        fun fromActivity(activity: Activity): PostActivityRequest {
            return PostActivityRequest(
                activity.user,
                activity.type,
                activity.timestamp,
                activity.duration,
                activity.points
            )
        }
    }
}