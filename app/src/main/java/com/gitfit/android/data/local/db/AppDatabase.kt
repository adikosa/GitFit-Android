package com.gitfit.android.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gitfit.android.data.local.db.converter.Converters
import com.gitfit.android.data.local.db.dao.ActivityDao
import com.gitfit.android.data.local.db.entity.Activity


@Database(
    entities = [Activity::class],
    exportSchema = false,
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun activityDao(): ActivityDao
}