package com.hardikmahant.recurringtask.background

import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.hardikmahant.recurringtask.R
import com.hardikmahant.recurringtask.RecurringTaskApplication.Companion.CHANNEL_ID
import com.hardikmahant.recurringtask.repository.LogRepository
import com.hardikmahant.recurringtask.ui.MainActivity
import com.hardikmahant.recurringtask.util.Util
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TaskForegroundService : LifecycleService() {

    @Inject
    lateinit var repository: LogRepository

    companion object {
        private const val TAG = "SampleFService"
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate: ")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        Log.i(TAG, "onStartCommand: ")
        val input = intent?.getStringExtra("input")

        val notifIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0,
            notifIntent, 0
        )
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("FS Service")
            .setContentText(input)
            .setSmallIcon(R.drawable.ic_app)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(1, notification)
        Util.scheduleJob(this)
        repository.getAllTimeLogs().observe(
            this, {
                Log.i(TAG, "onStartCommand: $it")
            }
        )
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy: ")
    }
}