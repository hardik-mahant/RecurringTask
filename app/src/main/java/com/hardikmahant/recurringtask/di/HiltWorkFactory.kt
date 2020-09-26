package com.hardikmahant.recurringtask.di

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.hardikmahant.recurringtask.ap2ui.background.Task2Worker
import com.hardikmahant.recurringtask.repository.LogRepository

class HiltWorkFactory (
    private val repository: LogRepository
) : WorkerFactory() {

    override fun createWorker(appContext: Context, workerClassName: String, workerParameters: WorkerParameters): ListenableWorker? {

        val workerKlass = Class.forName(workerClassName).asSubclass(Worker::class.java)
        val constructor = workerKlass.getDeclaredConstructor(Context::class.java, WorkerParameters::class.java)
        val instance = constructor.newInstance(appContext, workerParameters)

        when (instance) {
            is Task2Worker -> {
                instance.repository = repository
            }
            // optionally, handle other workers
        }

        return instance
    }
}