package com.hardikmahant.recurringtask.background

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.hardikmahant.recurringtask.R
import com.hardikmahant.recurringtask.RecurringTaskApplication.Companion.CHANNEL_ID
import com.hardikmahant.recurringtask.ui.MainActivity
import com.hardikmahant.recurringtask.util.Util

class TaskForegroundService : Service() {

    companion object {
        private const val TAG = "SampleFService"
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate: ")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
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
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.i(TAG, "onBind: ")
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy: ")
    }
}