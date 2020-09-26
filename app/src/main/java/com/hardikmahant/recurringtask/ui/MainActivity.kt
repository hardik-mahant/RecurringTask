package com.hardikmahant.recurringtask.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.hardikmahant.recurringtask.R
import com.hardikmahant.recurringtask.ap2ui.background.Task2Worker
import com.hardikmahant.recurringtask.background.TaskForegroundService
import com.hardikmahant.recurringtask.repository.LogRepository
import com.hardikmahant.recurringtask.repository.local.LogItem
import com.hardikmahant.recurringtask.ui.adapter.TimeStampListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var repository: LogRepository
    lateinit var logAdapter: TimeStampListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStartService.setOnClickListener {
            captureInputs()
        }

        btnStopService.setOnClickListener {
//            stopService()
        }

        logAdapter = TimeStampListAdapter()
        setupRecyclerView()
        observeData()
    }

    private fun captureInputs() {
        val input = edtName.text.toString()
        addRecord(input)

        //startService(input)
        startRecurringTask(input)
    }

    //region: Approach 1:Foreground Service
    private fun startService(input: String) {
        val intent = Intent(this@MainActivity, TaskForegroundService::class.java)
        intent.putExtra("input", input)
        startService(intent)
    }

    private fun stopService() {
        val intent = Intent(this@MainActivity, TaskForegroundService::class.java)
        stopService(intent)
    }
    //endregion: Approach 1:Foreground Service

    //region: Approach 2: Worker & WorkManager
    private fun startRecurringTask(input: String) {
        val recurringWork = PeriodicWorkRequest.Builder(
            Task2Worker::class.java,
            15,
            TimeUnit.MINUTES
        ).build()

        val workManager = WorkManager.getInstance(this)
        workManager.enqueue(recurringWork)
    }
    //endregion: Approach 2: Worker & WorkManager

    private fun addRecord(title: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.addTimeLog(
                LogItem(
                    timeStamp = System.currentTimeMillis().toString(),
                    serviceTitle = title,
                    startedAt = System.currentTimeMillis().toString()
                )
            )
            return@launch
        }
    }

    private fun setupRecyclerView() {
        listTimeStamps.apply {
            adapter = logAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun observeData() {
        repository.getAllTimeLogs().observe(
            this, { list ->
                logAdapter.timeStamps = list.map {
                    it.timeStamp.convertToDateTimeStamp() +
                            if (it.startedAt != "") {
                                it.startedAt.convertToTimeStamp()
                            } else {
                                ""
                            }
                }
            }
        )
    }

    private fun String.convertToDateTimeStamp(): String {
        val sdf = SimpleDateFormat("HH:mm:ss | MMM dd", Locale.getDefault())
        val resultDate = Date(this.toLong())
        return sdf.format(resultDate)
    }

    private fun String.convertToTimeStamp(): String {
        val sdf = SimpleDateFormat(" | HH:mm:ss", Locale.getDefault())
        val resultDate = Date(this.toLong())
        return sdf.format(resultDate)
    }
}