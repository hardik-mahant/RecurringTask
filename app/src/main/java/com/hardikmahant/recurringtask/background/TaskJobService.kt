package com.hardikmahant.recurringtask.background

import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.hardikmahant.recurringtask.R
import com.hardikmahant.recurringtask.RecurringTaskApplication.Companion.CHANNEL_ID

class TaskJobService : JobService(){

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
            jobFinished(params, false)
        }.start()
    }
}