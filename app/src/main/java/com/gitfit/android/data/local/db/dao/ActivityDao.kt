package com.gitfit.android.data.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gitfit.android.data.local.db.entity.Activity

@Dao
interface ActivityDao {

    @Query("SELECT * FROM activities")
    suspend fun getAll(): List<Activity>

    @Query("SELECT * FROM activities")
    fun getLiveData(): LiveData<List<Activity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(activity: Activity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(activities: List<Activity>)

    @Delete
    suspend fun delete(activity: Activity)

    @Query("DELETE FROM activities")
    suspend fun deleteAll()

}