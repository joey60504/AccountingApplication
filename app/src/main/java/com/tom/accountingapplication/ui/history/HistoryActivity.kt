package com.tom.accountingapplication.ui.history

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.auth.FirebaseAuth
import com.tom.accountingapplication.R
import com.tom.accountingapplication.accountingModel.DateEnum
import com.tom.accountingapplication.accountingModel.FilterTypeData
import com.tom.accountingapplication.databinding.ActivityHistoryBinding
import com.tom.accountingapplication.ui.datashow.AccountingDataAdapter
import com.tom.accountingapplication.ui.datashow.detail.AccountingDataDetailDialog
import com.tom.accountingapplication.ui.history.calendarfilter.HistoryFilterCalendarFragment
import com.tom.accountingapplication.ui.history.typefilter.HistoryFilterTypeActivity
import com.tom.accountingapplication.ui.login.MainActivity
import com.tom.accountingapplication.ui.home.AccountingActivity


class HistoryActivity : AppCompatActivity() {
    private val viewModel: HistoryViewModel by viewModels()

    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //側拉選單
        binding.imgFilter.setOnClickListener {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }

        }
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            null,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        //生活記帳
        binding.txtDrawerAccounting.setOnClickListener {
            startActivity(Intent(this, AccountingActivity::class.java))
        }
        //歷史分析
        binding.txtDrawerHistory.setBackgroundColor(ContextCompat.getColor(this, R.color.bar))
        binding.txtDrawerHistory.setTextColor(ContextCompat.getColor(this, R.color.white))
        //投資理財
        binding.txtDrawerInvest.setOnClickListener {

        }
        //個人資訊
        binding.txtDrawerInformation.setOnClickListener {

        }
        //登出
        binding.txtDrawerLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, MainActivity::class.java))
        }
        //側拉選單結束

        val filter: FilterTypeData? = intent.extras?.getParcelable("Filter")

        viewModel.onTypeFiltered(filter)

        binding.txtFilterType.setOnClickListener {
            startActivity(
                Intent(
                    this@HistoryActivity,
                    HistoryFilterTypeActivity::class.java
                ).apply {
                    putExtras(Bundle().apply {
                        putParcelable("Filter", filter)
                    })
                })
        }
        binding.layoutDateFilter.setOnClickListener {
            viewModel.onDateClick()
        }
        binding.txtFilterCalendar.setOnClickListener {
            if (viewModel.displayDate.value?.state == DateEnum.ALL) {
                val inflater = layoutInflater
                val layout: View = inflater.inflate(
                    R.layout.custom_toast,
                    findViewById(R.id.custom_toast_container)
                )
                val text: TextView = layout.findViewById(R.id.custom_toast_text)
                text.text = "請先選擇篩選模式~"
                with(Toast(applicationContext)) {
                    duration = Toast.LENGTH_SHORT
                    view = layout
                    show()
                }
            } else {
                val bottomSheetFragment =
                    HistoryFilterCalendarFragment(
                        date = viewModel.displayDate.value,
                        onItemClick = { date ->
                            viewModel.onDateFiltered(date)
                        })
                bottomSheetFragment.show(
                    supportFragmentManager,
                    bottomSheetFragment.tag
                )
            }
        }
        val accountingDataAdapter = AccountingDataAdapter(
            onItemClick = { uploadData ->
                val customDialog = AccountingDataDetailDialog(uploadData)
                customDialog.show(this.supportFragmentManager, "CustomDialog")
            }
        )
        viewModel.displayData.observe(this) {
            accountingDataAdapter.itemList = it
            binding.recyclerHistoryData.apply {
                setHasFixedSize(true)
                val manager =
                    LinearLayoutManager(this@HistoryActivity, LinearLayoutManager.VERTICAL, true)
                manager.stackFromEnd = true
                layoutManager = manager
                this.adapter = accountingDataAdapter
            }
            // 設置圓餅圖的一些屬性
            binding.pieChart.setUsePercentValues(true)
            binding.pieChart.description.isEnabled = false
            binding.pieChart.setExtraOffsets(5f, 10f, 5f, 5f)

            // 創建圓餅圖的資料集
            val entries = arrayListOf(
                PieEntry(20f, "項目1"),
                PieEntry(30f, "項目2"),
                PieEntry(50f, "項目3")
            )

            val dataSet = PieDataSet(entries, "圓餅圖標題")
            dataSet.colors = ColorTemplate.JOYFUL_COLORS.toList()
            dataSet.valueTextSize = 12f

            // 創建圓餅圖的資料
            val data = PieData(dataSet)
            data.setValueFormatter(PercentFormatter(binding.pieChart))
            data.setValueTextSize(11f)
            data.setValueTextColor(Color.WHITE)

            // 設置中心文字
            binding.pieChart.centerText = "中間文字"
            binding.pieChart.setCenterTextSize(16f)

            // 將資料設置到圓餅圖上
            binding.pieChart.data = data
            binding.pieChart.invalidate()
        }

        viewModel.displayFilterCount.observe(this) {
            binding.txtFilterType.text = "種類篩選（${it}）"
        }
        viewModel.displayDate.observe(this) {
            binding.txtDateFilter.text = it.title
            binding.txtFilterCalendar.text = it.calendar
        }
    }
}