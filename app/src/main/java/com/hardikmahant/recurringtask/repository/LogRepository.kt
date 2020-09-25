package com.hardikmahant.recurringtask.repository

import com.hardikmahant.recurringtask.repository.local.LogDao
import javax.inject.Inject

class LogRepository @Inject constructor(
    private val logDao: LogDao
) {
}