package com.tom.accountingapplication.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.tom.accountingapplication.R
import com.tom.accountingapplication.databinding.ActivityHomeBinding
import com.tom.accountingapplication.datashow.AccountingDataAdapter
import com.tom.accountingapplication.datashow.detail.AccountingDataDetailDialog
import com.tom.accountingapplication.ui.login.MainActivity
import com.tom.accountingapplication.ui.history.HistoryActivity
import com.tom.accountingapplication.ui.home.item.AccountingViewPagerAdapter


class AccountingActivity : AppCompatActivity() {
    private val viewModel: AccountingViewModel by viewModels()

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
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
        binding.txtDrawerAccounting.setBackgroundColor(getColor(this, R.color.bar))
        binding.txtDrawerAccounting.setTextColor(getColor(this, R.color.white))
        binding.txtDrawerHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
        binding.txtDrawerInvest.setOnClickListener {

        }
        binding.txtDrawerInformation.setOnClickListener {

        }
        binding.txtDrawerLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, MainActivity::class.java))
        }
        //側拉選單結束

        binding.btnExpense.setOnClickListener {
            viewModel.onExpenseClick()
        }
        binding.btnIncome.setOnClickListener {
            viewModel.onIncomeClick()
        }
        binding.imgDateLeft.setOnClickListener {
            viewModel.onDateLeftClick()
        }
        binding.imgDateRight.setOnClickListener {
            viewModel.onDateRightClick()
        }
        binding.txtSubmit.setOnClickListener {
            if (binding.edittextPrice.text.isNotEmpty()) {
                viewModel.onSubmitClick(
                    binding.edittextRemark.text.toString(),
                    binding.edittextPrice.text.toString().toInt()
                )
                binding.edittextRemark.text.clear()
                binding.edittextPrice.text.clear()
            } else{
                val inflater = layoutInflater
                val layout: View = inflater.inflate(R.layout.item_toast, findViewById(R.id.custom_toast_container))
                val text: TextView = layout.findViewById(R.id.custom_toast_text)
                text.text = "請填寫正確的金額唷~"
                with (Toast(applicationContext)) {
                    duration = Toast.LENGTH_SHORT
                    view = layout
                    show()
                }
            }
        }
        binding.txtDate.setOnClickListener {
            val bottomSheetFragment = AccountingBottomSheetCalendarFragment(
                onItemClick = { year, month, day ->
                    viewModel.onDateSelect(year, month, day)
                }
            )
            bottomSheetFragment.show(
                supportFragmentManager,
                bottomSheetFragment.tag
            )
        }
        binding.txtTag.setOnClickListener {
            val bottomSheetFragment = AccountingBottomSheetTagFragment(
                onItemClick = { tag ->
                    viewModel.onTagClick(tag)
                }
            )
            bottomSheetFragment.show(
                supportFragmentManager,
                bottomSheetFragment.tag
            )
        }
        val accountingDataAdapter = AccountingDataAdapter(
            onItemClick = { uploadData ->
                val customDialog = AccountingDataDetailDialog(uploadData)
                customDialog.show(supportFragmentManager, "CustomDialog")
            }
        )
        val itemAdapter = AccountingViewPagerAdapter(
            onItemClick = { accountingItem->
                viewModel.onItemClick(accountingItem)
            }
        )
        viewModel.showPairMessage.observe(this) {
            AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle(it.first)
                .setMessage(it.second)
                .setPositiveButton("確定", null).show()
        }

        viewModel.displayItemSelect.observe(this) {
            itemAdapter.seq = it.seq
            binding.viewpagerItem.adapter = itemAdapter
            binding.viewpagerItem.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            if (it.seq == 1) {
                binding.btnExpense.setBackgroundResource(R.drawable.corners_blue)
                binding.btnExpense.setTextColor(getColor(this, R.color.white))
                binding.btnIncome.setBackgroundColor(0)
                binding.btnIncome.setTextColor(getColor(this, R.color.greyish_brown))
                binding.imgChoiceIcon.setBackgroundResource(it.itemSelectedDrawable)

                binding.viewpagerItem.setCurrentItem(it.itemExpense.typeSeq,false)
                itemAdapter.itemType = it.itemExpense.itemTypeList
                TabLayoutMediator(binding.tabLayoutItem, binding.viewpagerItem) { tab, position ->
                    tab.text = it.itemExpense.typeList[position]
                }.attach()
            } else {
                binding.btnExpense.setBackgroundColor(0)
                binding.btnExpense.setTextColor(getColor(this, R.color.greyish_brown))
                binding.btnIncome.setBackgroundResource(R.drawable.corners_pink)
                binding.btnIncome.setTextColor(getColor(this, R.color.white))
                binding.imgChoiceIcon.setBackgroundResource(it.itemSelectedDrawable)

                binding.viewpagerItem.setCurrentItem(it.itemIncome.typeSeq,false)
                itemAdapter.itemType = it.itemIncome.itemTypeList
                TabLayoutMediator(binding.tabLayoutItem, binding.viewpagerItem) { tab, position ->
                    tab.text = it.itemIncome.typeList[position]
                }.attach()
            }

        }
        viewModel.displayDate.observe(this) {
            binding.txtDate.text = it
        }
        viewModel.displayTag.observe(this) {
            binding.txtTag.text = it.selectedTag
        }
        viewModel.displayData.observe(this) {
            accountingDataAdapter.itemList = it
            binding.recyclerData.apply {
                setHasFixedSize(true)
                val manager =
                    LinearLayoutManager(this@AccountingActivity, LinearLayoutManager.VERTICAL, true)
                manager.stackFromEnd = true
                layoutManager = manager
                this.adapter = accountingDataAdapter
            }
        }
        viewModel.displayRetain.observe(this) {
            binding.txtMonthRemain.text = "本月剩餘可使用金額：${it}"
        }
    }
}
