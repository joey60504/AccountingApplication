package com.tom.accountingapplication.ui.history.historyfilter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tom.accountingapplication.databinding.ActivityFilterCalendarBinding

class HistoryFilterCalendarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFilterCalendarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}