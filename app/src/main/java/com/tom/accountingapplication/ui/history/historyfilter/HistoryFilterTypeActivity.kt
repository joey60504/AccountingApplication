package com.tom.accountingapplication.ui.history.historyfilter

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tom.accountingapplication.databinding.ActivityFilterTypeBinding
import com.tom.accountingapplication.ui.history.HistoryViewModel

class HistoryFilterTypeActivity : AppCompatActivity() {

    private val viewModel: HistoryViewModel by viewModels()

    private lateinit var binding: ActivityFilterTypeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgFilterDismiss.setOnClickListener {
            finish()
        }
        val historyFilterTypeAdapter = HistoryFilterTypeAdapter()


        viewModel.displayTypeFilter.observe(this){
            historyFilterTypeAdapter.itemList = it
            binding.recyclerFilterType.apply {
                setHasFixedSize(true)
                val manager =
                    LinearLayoutManager(this@HistoryFilterTypeActivity, LinearLayoutManager.VERTICAL, false)
                manager.stackFromEnd = false
                layoutManager = manager
                this.adapter = historyFilterTypeAdapter
            }
        }
    }
}