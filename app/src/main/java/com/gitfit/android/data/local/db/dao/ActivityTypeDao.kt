package com.gitfit.android.data.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gitfit.android.data.local.db.entity.Activity
import com.gitfit.android.data.local.db.entity.ActivityType

@Dao
interface ActivityTypeDao {

    @Query("SELECT * FROM activity_types")
    suspend fun getAll(): List<ActivityType>

    @Query("SELECT * FROM activity_types")
    fun getLiveData(): LiveData<List<ActivityType>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(activityType: ActivityType)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(activityTypes: List<ActivityType>)

    @Delete
    suspend fun delete(activity: Activity)

    @Query("DELETE FROM activity_types")
    suspend fun deleteAll()

}