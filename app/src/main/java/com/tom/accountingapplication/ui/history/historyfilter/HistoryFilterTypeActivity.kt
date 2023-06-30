package com.tom.accountingapplication.ui.history.historyfilter

import android.os.Bundle

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tom.accountingapplication.databinding.ActivityFilterTypeBinding
import com.tom.accountingapplication.ui.history.HistoryViewModel

class HistoryFilterTypeActivity : AppCompatActivity() {

    private val viewModel: HistoryViewModel by viewModels()

    private lateinit var binding: ActivityFilterTypeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val seq: Int = requireNotNull(intent.extras?.getInt("Seq"))

        binding.imgFilterDismiss.setOnClickListener {
            finish()
        }
        val historyFilterTypeAdapter = HistoryFilterTypeAdapter(
            onItemClick = { filterTypeItem ->
                viewModel.onItemClick(filterTypeItem,seq)
            }
        )
        binding.recyclerFilterType.apply {
            setHasFixedSize(true)
            val manager =
                LinearLayoutManager(
                    this@HistoryFilterTypeActivity,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            manager.stackFromEnd = false
            addItemDecoration(
                DividerItemDecoration(
                    this@HistoryFilterTypeActivity,
                    RecyclerView.VERTICAL
                )
            )
            layoutManager = manager
            this.adapter = historyFilterTypeAdapter
        }

        viewModel.displayTypeFilter.observe(this) {
            if (seq == 1) {
                historyFilterTypeAdapter.setData(it.expenseTypeItemList)
            } else {
                historyFilterTypeAdapter.setData(it.incomeTypeItemList)
            }
        }
    }
}