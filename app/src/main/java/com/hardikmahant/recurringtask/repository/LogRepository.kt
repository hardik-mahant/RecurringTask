package com.hardikmahant.recurringtask.repository

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hardikmahant.recurringtask.repository.local.LogDao
import com.hardikmahant.recurringtask.repository.local.LogItem
import javax.inject.Inject

class LogRepository @Inject constructor(
    private val logDao: LogDao
) {

    suspend fun addTimeLog(logItem: LogItem) = logDao.addTimeLog(logItem)
    fun getAllTimeLogs(): LiveData<List<LogItem>> = logDao.getAllTimeLogs()

}