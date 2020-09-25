package com.hardikmahant.recurringtask.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hardikmahant.recurringtask.R
import com.hardikmahant.recurringtask.background.TaskForegroundService
import com.hardikmahant.recurringtask.background.TaskJobService
import com.hardikmahant.recurringtask.util.Util
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStartService.setOnClickListener {
            startService()
        }

        btnStopService.setOnClickListener {
            stopService()
        }
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
}