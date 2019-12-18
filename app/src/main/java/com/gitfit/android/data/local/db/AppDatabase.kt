package com.gitfit.android.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gitfit.android.data.local.db.converter.RoomConverters
import com.gitfit.android.data.local.db.dao.ActivityDao
import com.gitfit.android.data.local.db.dao.ActivityTypeDao
import com.gitfit.android.data.local.db.entity.Activity
import com.gitfit.android.data.local.db.entity.ActivityType


@Database(
    entities = [Activity::class, ActivityType::class],
    exportSchema = false,
    version = 1
)
@TypeConverters(RoomConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun activityDao(): ActivityDao
    abstract fun activityTypeDao(): ActivityTypeDao
}