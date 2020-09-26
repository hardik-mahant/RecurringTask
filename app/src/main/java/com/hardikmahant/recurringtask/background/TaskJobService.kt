package com.hardikmahant.recurringtask.background

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import com.hardikmahant.recurringtask.repository.LogRepository
import com.hardikmahant.recurringtask.repository.local.LogItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TaskJobService : JobService() {

    @Inject
    lateinit var repository: LogRepository

    companion object {
        const val JOB_ID = 123
        private const val TAG = "JS@: SampleJobService"
    }

    var isJobCancelled = false

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.i(TAG, "onStartJob: ")
        doBackgroundWork(params)
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.i(TAG, "onStopJob: ")
        isJobCancelled = true
        return true
    }

    private fun doBackgroundWork(params: JobParameters?) {
        Thread {
            if (isJobCancelled) return@Thread
            Log.i(TAG, "Working...")
            addRecord()
            jobFinished(params, true)
        }.start()
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