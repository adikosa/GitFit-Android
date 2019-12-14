package com.gitfit.android.repos

import com.gitfit.android.data.local.db.dao.ActivityDao

class ActivityRepository(private val activityDao: ActivityDao) {

    suspend fun deleteAll() {
        activityDao.deleteAll()
    }

}