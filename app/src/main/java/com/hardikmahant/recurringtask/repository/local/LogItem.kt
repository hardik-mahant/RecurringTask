package com.hardikmahant.recurringtask.repository.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "log_items")
data class LogItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val timeStamp: String,
    val serviceTitle: String
)