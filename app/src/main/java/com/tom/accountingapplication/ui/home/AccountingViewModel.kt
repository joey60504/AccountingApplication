package com.tom.accountingapplication.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tom.accountingapplication.R

class AccountingViewModel : ViewModel() {
    private val _displayAccounting: MutableLiveData<AccountingItem> = MutableLiveData()
    val displayAccounting: LiveData<AccountingItem> = _displayAccounting

    private var seq = 1 // 1 = expense 2 = income
    private var updateItem = arrayListOf<UpdateItem>()
    private var expenseList = arrayListOf<UpdateItem>()
    private var incomeList = arrayListOf<UpdateItem>()

    init {
        expenseList = arrayListOf(
            UpdateItem("Breakfast", R.drawable.icon_breakfast,1),
            UpdateItem("Lunch", R.drawable.icon_lunch,1),
            UpdateItem("Dinner", R.drawable.icon_dinner,1),
            UpdateItem("Transport", R.drawable.icon_transport,1),
            UpdateItem("Drink", R.drawable.icon_drink,1),
            UpdateItem("Dessert", R.drawable.icon_dessert,1),
            UpdateItem("Entertainment", R.drawable.icon_entertainment,1),
            UpdateItem("DailyNecessary",R.drawable.icon_daily_necessary,1),
            UpdateItem("Shopping", R.drawable.icon_shopping,1),
            UpdateItem("Bill", R.drawable.icon_bill,1),
            UpdateItem("Stock", R.drawable.icon_stock,1),
            UpdateItem("VC", R.drawable.icon_vertical_currency,1),
            UpdateItem("Other", R.drawable.icon_other,1),
        )
        incomeList = arrayListOf(
            UpdateItem("Salary", R.drawable.icon_salery,2),
            UpdateItem("Reward", R.drawable.icon_reward,2),
            UpdateItem("Dividend", R.drawable.icon_dividend,2),
            UpdateItem("Interest", R.drawable.icon_interest,2),
            UpdateItem("InvestStock", R.drawable.icon_invest_stock,2),
            UpdateItem("InvestVC", R.drawable.icon_invest_vertical_currency,2),
            UpdateItem("Other", R.drawable.icon_other,2),
        )
        updateItem = if (seq == 1) {
            expenseList
        } else {
            incomeList
        }
        _displayAccounting.postValue(
            AccountingItem(
            itemList = updateItem,
            seq = seq
        )
        )
    }
    fun onItemClick(updateItem : UpdateItem){
        Log.d("kkk",updateItem.toString())
    }
    fun onExpenseClick(){
        seq = 1
        updateItem = expenseList
        _displayAccounting.value?.seq = seq
        _displayAccounting.value?.itemList = updateItem
        _displayAccounting.postValue(_displayAccounting.value)
    }
    fun onIncomeClick(){
        seq = 2
        updateItem = incomeList
        _displayAccounting.value?.seq = seq
        _displayAccounting.value?.itemList = updateItem
        _displayAccounting.postValue(_displayAccounting.value)
    }
}

data class AccountingItem(
    var itemList :ArrayList<UpdateItem>,
    var seq : Int
)
data class UpdateItem(
    val title: String,
    val image: Int,
    val type :Int
)