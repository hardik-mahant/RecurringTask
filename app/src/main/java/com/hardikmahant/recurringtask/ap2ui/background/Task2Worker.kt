package com.hardikmahant.recurringtask.ap2ui.background

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.hardikmahant.recurringtask.repository.LogRepository
import com.hardikmahant.recurringtask.repository.local.LogItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class Task2Worker(
    private val context: Context,
    workerParameters: WorkerParameters
): Worker(context, workerParameters) {

    @Inject
    lateinit var repository: LogRepository

    companion object {
        private const val TAG = "JS@ Task2Worker"
    }

    override fun doWork(): Result {
        Log.i(TAG, "doWork: working...")
        try{
            addRecord()
        }catch (e: Exception){
            Result.retry()
        }
        return Result.success()
    }

    private fun addRecord() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.addTimeLog(
                LogItem(
                    timeStamp = System.currentTimeMillis().toString(),
                    serviceTitle = "Title"
                )
            )
            return@launch
        }
    }
}