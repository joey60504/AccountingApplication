package com.tom.accountingapplication.ui.history.typefilter

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.tom.accountingapplication.accountingModel.FilterTypeData
import com.tom.accountingapplication.databinding.ActivityFilterTypeBinding
import com.tom.accountingapplication.ui.history.HistoryActivity

class HistoryFilterTypeActivity : AppCompatActivity() {

    private val viewModel: HistoryFilterTypeViewModel by viewModels()

    private lateinit var binding: ActivityFilterTypeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val filter: FilterTypeData? = intent.extras?.getParcelable("Filter")

        viewModel.init(filter)

        binding.imgFilterDismiss.setOnClickListener {
            finish()
        }
        binding.txtFilterTypeClear.setOnClickListener {
            viewModel.onTypeClearClick()
        }
        binding.txtFilterTypeSubmit.setOnClickListener {
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
        val itemAdapter = HistoryFilterTypeViewPagerAdapter(
            onItemClick = { filterTypeItem ->
                viewModel.onItemClick(filterTypeItem)
            }
        )
        viewModel.displayTypeFilter.observe(this) {
            binding.viewpagerHistoryFilterType.adapter = itemAdapter
            binding.viewpagerHistoryFilterType.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            binding.viewpagerHistoryFilterType.setCurrentItem(it?.position ?: 0, false)
            itemAdapter.itemList = it?.filterTypeList ?: arrayListOf()
            TabLayoutMediator(
                binding.tabLayoutItemHistoryFilterType,
                binding.viewpagerHistoryFilterType
            ) { tab, position ->
                tab.text = it?.tabList?.get(position)
            }.attach()
        }
    }
}