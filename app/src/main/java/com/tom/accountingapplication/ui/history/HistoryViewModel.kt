package com.tom.accountingapplication.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tom.accountingapplication.accountingModel.AccountingDataModel
import com.tom.accountingapplication.accountingModel.DataListener
import com.tom.accountingapplication.accountingModel.FilterTypeItem
import com.tom.accountingapplication.accountingModel.FilterTypeItemList
import com.tom.accountingapplication.accountingModel.ReadDataDate
import com.tom.accountingapplication.accountingModel.ReadDataType


class HistoryViewModel :ViewModel() {
    private val _displayData: MutableLiveData<ArrayList<ReadDataDate>> = MutableLiveData()
    val displayData: LiveData<ArrayList<ReadDataDate>> = _displayData

    private val _displayTypeFilter: MutableLiveData<ArrayList<FilterTypeItemList>> = MutableLiveData()
    val displayTypeFilter: LiveData<ArrayList<FilterTypeItemList>> = _displayTypeFilter

    private val accountingUploadModel = AccountingDataModel()
    private var seq = 1
    init {
        getData()
        getItem()
    }
    private fun getData() {
        val typeString = if (seq == 1) "Expense" else "Income"
        accountingUploadModel.getAccountingData(object : DataListener {
            override fun onDataLoaded(readDataList: ArrayList<ReadDataType>) {
                val dataList = arrayListOf<ReadDataDate>()
                if (readDataList.isEmpty()) {
                    _displayData.postValue(arrayListOf())
                } else {
                    readDataList.map { type ->
                        type.yearList.map { year ->
                            year.monthList.map { month ->
                                month.dateList.map { date ->
                                    if (typeString == type.type) {
                                        dataList.add(date)
                                    }
                                }
                            }
                        }
                        dataList.sortBy { it.date }
                        _displayData.postValue(dataList)
                    }
                }
            }
        })
    }
    private fun getItem(){
        val accountingItemList = accountingUploadModel.fillItem()
        val typeItemList = arrayListOf<FilterTypeItemList>()
        if(seq == 1){
            accountingItemList.itemExpense.itemTypeList.map {
                val titleList = arrayListOf<FilterTypeItem>()
                it.itemList.map { accountingItem->
                    titleList.add(FilterTypeItem(accountingItem.title,false))
                }
                titleList.add(0,FilterTypeItem("${it.type}全選",false))
                typeItemList.add(
                    FilterTypeItemList(
                        type = it.type,
                        filterTypeItemList = titleList
                    )
                )
            }
        } else{
            accountingItemList.itemIncome.itemTypeList.map {
                val titleList = arrayListOf<FilterTypeItem>()
                it.itemList.map { accountingItem->
                    titleList.add(FilterTypeItem(accountingItem.title,false))
                }
                titleList.add(0,FilterTypeItem("${it.type}全選",false))
                typeItemList.add(
                    FilterTypeItemList(
                        type = it.type,
                        filterTypeItemList = titleList
                    )
                )
            }
        }
        _displayTypeFilter.postValue(typeItemList)
    }
    fun onExpenseClick() {
        seq = 1
        getData()
    }

    fun onIncomeClick() {
        seq = 2
        getData()
    }
}