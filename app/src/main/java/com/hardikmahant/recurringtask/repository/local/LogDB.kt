package com.hardikmahant.recurringtask.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [LogItem::class],
    version = 1
)
abstract class LogDB: RoomDatabase() {
    abstract fun logDao(): LogDao
}