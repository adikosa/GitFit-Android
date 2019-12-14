package com.gitfit.android.repos

import com.gitfit.android.data.local.db.dao.ActivityTypeDao
import com.gitfit.android.data.local.db.entity.ActivityType

class ActivityTypeRepository(private val activityTypeDao: ActivityTypeDao) {

    suspend fun deleteAll() {
        activityTypeDao.deleteAll()
    }

    suspend fun insertList(activityTypes: List<ActivityType>) {
        activityTypeDao.insertList(activityTypes)
    }

    fun getLiveData() = activityTypeDao.getLiveData()


}