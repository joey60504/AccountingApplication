package com.tom.accountingapplication.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tom.accountingapplication.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AccountingViewModel : ViewModel() {
    private val _displayItemSelect: MutableLiveData<AccountingItem> = MutableLiveData()
    val displayItemSelect: LiveData<AccountingItem> = _displayItemSelect
    private val _displayDate: MutableLiveData<String> = MutableLiveData()
    val displayDate: LiveData<String> = _displayDate

    private var seq = 1 // 1 = expense 2 = income
    private var expenseList = arrayListOf<UpdateItem>()
    private var incomeList = arrayListOf<UpdateItem>()

    init {
        getTodayDate()
        setItemData()
    }

    private fun getTodayDate() {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy/MM/dd (E)", Locale.getDefault())
        val today = dateFormat.format(calendar.time)
        _displayDate.postValue(today)
    }

    private fun setItemData() {
        expenseList = arrayListOf(
            UpdateItem("早餐", R.drawable.icon_breakfast, 1, true),
            UpdateItem("午餐", R.drawable.icon_lunch, 1, false),
            UpdateItem("晚餐", R.drawable.icon_dinner, 1, false),
            UpdateItem("交通", R.drawable.icon_transport, 1, false),
            UpdateItem("飲品", R.drawable.icon_drink, 1, false),
            UpdateItem("點心", R.drawable.icon_dessert, 1, false),
            UpdateItem("娛樂", R.drawable.icon_entertainment, 1, false),
            UpdateItem("日用品", R.drawable.icon_daily_necessary, 1, false),
            UpdateItem("購物", R.drawable.icon_shopping, 1, false),
            UpdateItem("帳單", R.drawable.icon_bill, 1, false),
            UpdateItem("股票", R.drawable.icon_stock, 1, false),
            UpdateItem("虛擬貨幣", R.drawable.icon_vertical_currency, 1, false),
            UpdateItem("其他", R.drawable.icon_other, 1, false),
        )
        incomeList = arrayListOf(
            UpdateItem("薪水", R.drawable.icon_salery, 2, true),
            UpdateItem("獎金", R.drawable.icon_reward, 2, false),
            UpdateItem("股息", R.drawable.icon_dividend, 2, false),
            UpdateItem("利息", R.drawable.icon_interest, 2, false),
            UpdateItem("股票", R.drawable.icon_invest_stock, 2, false),
            UpdateItem("虛擬貨幣", R.drawable.icon_invest_vertical_currency, 2, false),
            UpdateItem("其他", R.drawable.icon_other, 2, false),
        )
        _displayItemSelect.postValue(
            AccountingItem(
                itemExpenseList = expenseList,
                itemIncomeList = incomeList,
                itemSelectedDrawable = R.drawable.icon_breakfast,
                seq = seq
            )
        )
    }

    fun onExpenseClick() {
        seq = 1
        _displayItemSelect.value?.seq = seq
        _displayItemSelect.value?.itemSelectedDrawable =
            _displayItemSelect.value?.itemExpenseList?.find { it.isSelect }?.image ?: 0
        _displayItemSelect.postValue(_displayItemSelect.value)
    }

    fun onIncomeClick() {
        seq = 2
        _displayItemSelect.value?.seq = seq
        _displayItemSelect.value?.itemSelectedDrawable =
            _displayItemSelect.value?.itemIncomeList?.find { it.isSelect }?.image ?: 0
        _displayItemSelect.postValue(_displayItemSelect.value)
    }

    fun onItemClick(updateItem: UpdateItem) {
        if (seq == 1) {
            _displayItemSelect.value?.itemExpenseList?.find {
                it.isSelect
            }.let {
                it?.isSelect = false
            }
            _displayItemSelect.value?.itemExpenseList?.find {
                it == updateItem
            }.let {
                it?.isSelect = true
                _displayItemSelect.value?.itemSelectedDrawable = it?.image ?: 0
            }
            _displayItemSelect.postValue(_displayItemSelect.value)
        } else {
            _displayItemSelect.value?.itemIncomeList?.find {
                it.isSelect
            }.let {
                it?.isSelect = false
            }
            _displayItemSelect.value?.itemIncomeList?.find {
                it == updateItem
            }.let {
                it?.isSelect = true
                _displayItemSelect.value?.itemSelectedDrawable = it?.image ?: 0
            }
            _displayItemSelect.postValue(_displayItemSelect.value)
        }
    }

    fun onDateSelect(year: Int, month: Int, dayOfMonth: Int) {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd (E)", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val selectedDate = calendar.time
        _displayDate.postValue(dateFormat.format(selectedDate))
    }

    fun onDateLeftClick() {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd (E)", Locale.getDefault())
        val date = dateFormat.parse(_displayDate.value.toString())

        val calendar = Calendar.getInstance()
        calendar.time = date as Date
        calendar.add(Calendar.DAY_OF_MONTH, -1)

        val newDate = calendar.time
        _displayDate.postValue(dateFormat.format(newDate))
    }

    fun onDateRightClick() {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd (E)", Locale.getDefault())
        val date = dateFormat.parse(_displayDate.value.toString())

        val calendar = Calendar.getInstance()
        calendar.time = date as Date
        calendar.add(Calendar.DAY_OF_MONTH, 1)

        val newDate = calendar.time
        _displayDate.postValue(dateFormat.format(newDate))
    }
}

data class AccountingItem(
    var itemExpenseList: ArrayList<UpdateItem>,
    var itemIncomeList: ArrayList<UpdateItem>,
    var itemSelectedDrawable: Int,
    var seq: Int
)

data class UpdateItem(
    val title: String,
    val image: Int,
    val type: Int,
    var isSelect: Boolean
)