package com.tom.accountingapplication.ui.history.historyfilter

import android.content.Intent
import android.os.Bundle

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tom.accountingapplication.accountingModel.FilterItem
import com.tom.accountingapplication.databinding.ActivityFilterTypeBinding
import com.tom.accountingapplication.ui.history.HistoryActivity

class HistoryFilterTypeActivity : AppCompatActivity() {

    private val viewModel: HistoryFilterTypeViewModel by viewModels()

    private lateinit var binding: ActivityFilterTypeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val filter: FilterItem? = intent.extras?.getParcelable("Filter")

        viewModel.init(filter)

        binding.imgFilterDismiss.setOnClickListener {
            finish()
        }
        binding.txtFilterTypeClear.setOnClickListener {
            viewModel.onTypeClearClick()
        }
        binding.txtFilterTypeSubmit.setOnClickListener {
            viewModel.onTypeSubmitClick()
            startActivity(
                Intent(
                    this@HistoryFilterTypeActivity,
                    HistoryActivity::class.java
                ).apply {
                    putExtras(Bundle().apply {
                        putParcelable("Filter", viewModel.displayTypeFilter.value)
                    })
                })
            finish()
        }
        val historyFilterTypeAdapter = HistoryFilterTypeAdapter(
            onItemClick = { filterTypeItem ->
                viewModel.onItemClick(filterTypeItem)
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
            historyFilterTypeAdapter.setData(it?.typeItemList ?: arrayListOf())
        }
    }
}