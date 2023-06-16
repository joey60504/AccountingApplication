package com.tom.accountingapplication.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.tom.accountingapplication.R
import com.tom.accountingapplication.databinding.FragmentHomeBinding
import com.tom.accountingapplication.datashow.AccountingDataAdapter
import com.tom.accountingapplication.datashow.detail.AccountingDataDetailDialog
import com.tom.accountingapplication.ui.login.MainActivity
import com.tom.accountingapplication.ui.history.HistoryActivity


class AccountingActivity : AppCompatActivity() {
    private val viewModel: AccountingViewModel by viewModels()

    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentHomeBinding.inflate(layoutInflater)
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
        binding.txtDrawerAccounting.setBackgroundColor(getColor(this,R.color.bar))
        binding.txtDrawerAccounting.setTextColor(getColor(this,R.color.white))
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

        val itemAdapter = AccountingItemAdapter(
            onItemClick = { updateItem ->
                viewModel.onItemClick(updateItem)
            }
        )
        val accountingDataAdapter = AccountingDataAdapter(
            onItemClick = { uploadData ->
                val customDialog = AccountingDataDetailDialog(uploadData)
                customDialog.show(supportFragmentManager, "CustomDialog")
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
            if (it.seq == 1) {
                binding.btnExpense.setBackgroundResource(R.drawable.corners_blue)
                binding.btnExpense.setTextColor(getColor(this, R.color.white))
                binding.btnIncome.setBackgroundColor(0)
                binding.btnIncome.setTextColor(getColor(this, R.color.greyish_brown))
                binding.imgChoiceIcon.setBackgroundResource(it.itemSelectedDrawable)
                itemAdapter.itemList = it.itemExpenseList
            } else {
                binding.btnExpense.setBackgroundColor(0)
                binding.btnExpense.setTextColor(getColor(this, R.color.greyish_brown))
                binding.btnIncome.setBackgroundResource(R.drawable.corners_pink)
                binding.btnIncome.setTextColor(getColor(this, R.color.white))
                binding.imgChoiceIcon.setBackgroundResource(it.itemSelectedDrawable)
                itemAdapter.itemList = it.itemIncomeList
            }
            binding.recyclerItem.apply {
                setHasFixedSize(true)
                layoutManager =
                    GridLayoutManager(this@AccountingActivity, 6, GridLayoutManager.VERTICAL, false)
                this.adapter = itemAdapter
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
//    private fun firstLogin() {
//        auth = FirebaseAuth.getInstance()
//        var userEmail = auth.currentUser?.email.toString()
//        val findLittleMouseAt = userEmail.indexOf("@")
//        val userEmailValue = userEmail.substring(0, findLittleMouseAt)
//        var database = FirebaseDatabase.getInstance().reference
//        database.get().addOnSuccessListener {
//            if (it.value == null) {
//                startActivity(Intent(requireContext(), ProfileActivity::class.java))
//            } else {
//                val databaseHashMap = it.value as java.util.HashMap<*, *>
//                if (databaseHashMap[userEmailValue] == null) {
//                    startActivity(Intent(requireContext(), ProfileActivity::class.java))
//                } else {
//                    dataSelect()
//                }
//            }
//        }
//    }

//    override fun onItemClick(position: Int) {
//
//    }
}