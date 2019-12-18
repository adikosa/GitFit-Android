package com.gitfit.android.repos

import com.gitfit.android.data.local.db.dao.ActivityDao
import com.gitfit.android.data.local.db.entity.Activity

class ActivityRepository(private val activityDao: ActivityDao) {

    suspend fun getById(id: Long) = activityDao.getById(id)

    suspend fun deleteAll() = activityDao.deleteAll()

    suspend fun delete(activity: Activity) = activityDao.delete(activity)

    suspend fun insert(activity: Activity) = activityDao.insert(activity)

    suspend fun insertList(activities: List<Activity>) = activityDao.insertList(activities)

    fun getLiveData() = activityDao.getLiveData()

    suspend fun getAll() = activityDao.getAll()

    suspend fun getAllByActivityType(activityType: String) = activityDao.getAllByActivityType(activityType)
}