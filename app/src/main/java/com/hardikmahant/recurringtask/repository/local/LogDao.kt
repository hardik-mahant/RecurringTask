package com.hardikmahant.recurringtask.repository.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTimeLog(logItem: LogItem)

    @Query("SELECT * FROM log_items")
    fun getAllTimeLogs(): LiveData<List<LogItem>>

}