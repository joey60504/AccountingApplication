package com.tom.accountingapplication.ui.history

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
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
import com.tom.accountingapplication.databinding.ActivityHistoryBinding
import com.tom.accountingapplication.datashow.AccountingDataAdapter
import com.tom.accountingapplication.datashow.detail.AccountingDataDetailDialog
import com.tom.accountingapplication.ui.login.MainActivity
import com.tom.accountingapplication.ui.home.AccountingActivity
import com.tom.accountingapplication.ui.home.AccountingViewModel


class HistoryActivity : AppCompatActivity() {
    private val viewModel: AccountingViewModel by viewModels()

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
        binding.txtDrawerAccounting.setOnClickListener {
            startActivity(Intent(this, AccountingActivity::class.java))
        }
        binding.txtDrawerHistory.setBackgroundColor(ContextCompat.getColor(this, R.color.bar))
        binding.txtDrawerHistory.setTextColor(ContextCompat.getColor(this, R.color.white))
        binding.txtDrawerInvest.setOnClickListener {

        }
        binding.txtDrawerInformation.setOnClickListener {

        }
        binding.txtDrawerLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, MainActivity::class.java))
        }
        //側拉選單結束


        val accountingDataAdapter = AccountingDataAdapter(
            onItemClick = { uploadData ->
                val customDialog = AccountingDataDetailDialog(uploadData)
                customDialog.show(this.supportFragmentManager, "CustomDialog")
            }
        )

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

        viewModel.displayData.observe(this) {
            accountingDataAdapter.itemList = it
            binding.recyclerHistoryData.apply {
                setHasFixedSize(true)
                val manager = LinearLayoutManager(this@HistoryActivity,LinearLayoutManager.VERTICAL,true)
                manager.stackFromEnd = true
                layoutManager = manager
                this.adapter = accountingDataAdapter
            }
        }
    }
}