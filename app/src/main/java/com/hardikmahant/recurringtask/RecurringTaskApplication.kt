package com.hardikmahant.recurringtask

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class RecurringTaskApplication: Application() {

    companion object {
        const val CHANNEL_ID = "my_app_service_channel"
    }

    @Inject
    lateinit var workerFactory: WorkerFactory

    private fun configureWorkManager() {
        val config = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

        WorkManager.initialize(this, config)
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        configureWorkManager()
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                CHANNEL_ID,
                "my_app_notification_channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}