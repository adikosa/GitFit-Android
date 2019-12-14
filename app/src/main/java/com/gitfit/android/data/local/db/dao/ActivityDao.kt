package com.gitfit.android.data.local.db.dao

import androidx.room.*
import com.gitfit.android.data.local.db.entity.Activity

@Dao
interface ActivityDao {

    @Query("SELECT * FROM activities")
    suspend fun getAll(): List<Activity>

    @Query("SELECT * FROM activities WHERE user LIKE :name")
    suspend fun getAllByUser(name: String): List<Activity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(activity: Activity)

    @Delete
    suspend fun delete(activity: Activity)

    @Query("DELETE FROM activities")
    suspend fun deleteAll()

}