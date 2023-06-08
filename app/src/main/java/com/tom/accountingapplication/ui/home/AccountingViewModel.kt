package com.tom.accountingapplication.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tom.accountingapplication.R

class AccountingViewModel : ViewModel() {
    private val _displayAccounting: MutableLiveData<AccountingItem> = MutableLiveData()
    val displayAccounting: LiveData<AccountingItem> = _displayAccounting

    private var seq = 1 // 1 = expense 2 = income
    private var expenseList = arrayListOf<UpdateItem>()
    private var incomeList = arrayListOf<UpdateItem>()

    init {
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
        _displayAccounting.postValue(
            AccountingItem(
                itemExpenseList = expenseList,
                itemIncomeList = incomeList,
                seq = seq
            )
        )
    }

    fun onItemClick(updateItem: UpdateItem) {
        if (seq == 1) {
            _displayAccounting.value?.itemExpenseList?.find {
                it.isSelect
            }.let {
                it?.isSelect = false
            }
            _displayAccounting.value?.itemExpenseList?.find {
                it == updateItem
            }.let {
                it?.isSelect = true
            }
            _displayAccounting.postValue(_displayAccounting.value)
        } else{
            _displayAccounting.value?.itemIncomeList?.find {
                it.isSelect
            }.let {
                it?.isSelect = false
            }
            _displayAccounting.value?.itemIncomeList?.find {
                it == updateItem
            }.let {
                it?.isSelect = true
            }
            _displayAccounting.postValue(_displayAccounting.value)
        }
    }

    fun onExpenseClick() {
        seq = 1
        _displayAccounting.value?.seq = seq
        _displayAccounting.postValue(_displayAccounting.value)
    }

    fun onIncomeClick() {
        seq = 2
        _displayAccounting.value?.seq = seq
        _displayAccounting.postValue(_displayAccounting.value)
    }
}

data class AccountingItem(
    var itemExpenseList: ArrayList<UpdateItem>,
    var itemIncomeList:ArrayList<UpdateItem>,
    var seq: Int
)

data class UpdateItem(
    val title: String,
    val image: Int,
    val type: Int,
    var isSelect: Boolean
)