package com.hardikmahant.recurringtask.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.hardikmahant.recurringtask.R
import com.hardikmahant.recurringtask.background.TaskForegroundService
import com.hardikmahant.recurringtask.repository.LogRepository
import com.hardikmahant.recurringtask.ui.adapter.TimeStampListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
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
            startService()
        }

        btnStopService.setOnClickListener {
            stopService()
        }

        logAdapter = TimeStampListAdapter()
        setupRecyclerView()
        observeData()
    }

    private fun startService() {
        val input = edtName.text.toString()
        val intent = Intent(this@MainActivity, TaskForegroundService::class.java)
        intent.putExtra("input", input)

        startService(intent)
    }

    private fun stopService(){
        val intent = Intent(this@MainActivity, TaskForegroundService::class.java)
        stopService(intent)
    }

    private fun setupRecyclerView(){
        listTimeStamps.apply {
            adapter = logAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun observeData(){
        repository.getAllTimeLogs().observe(
            this, { list ->
                logAdapter.timeStamps = list.map { it.timeStamp.convertToTimeStamp() }
            }
        )
    }

    private fun String.convertToTimeStamp(): String{
        val sdf = SimpleDateFormat("HH:mm:ss | MMM dd", Locale.getDefault())
        val resultDate = Date(this.toLong())
        return sdf.format(resultDate)
    }
}