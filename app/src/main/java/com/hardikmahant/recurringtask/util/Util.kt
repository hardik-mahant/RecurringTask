package com.hardikmahant.recurringtask.util

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import com.hardikmahant.recurringtask.background.TaskJobService

object Util {
    fun scheduleJob(context: Context) {
        val serviceComponent = ComponentName(context, TaskJobService::class.java)
        val builder = JobInfo.Builder(0, serviceComponent)
        builder.setMinimumLatency(1000.toLong()) // wait at least
        builder.setOverrideDeadline(3000.toLong()) // maximum delay
        val jobScheduler: JobScheduler = context.getSystemService(JobScheduler::class.java)
        jobScheduler.schedule(builder.build())
    }

}