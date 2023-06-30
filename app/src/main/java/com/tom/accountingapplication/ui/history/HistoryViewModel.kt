package com.tom.accountingapplication.ui.history


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tom.accountingapplication.accountingModel.AccountingDataModel
import com.tom.accountingapplication.accountingModel.DataListener
import com.tom.accountingapplication.accountingModel.FilterItem
import com.tom.accountingapplication.accountingModel.FilterTypeItem
import com.tom.accountingapplication.accountingModel.FilterTypeItemList
import com.tom.accountingapplication.accountingModel.ReadDataDate
import com.tom.accountingapplication.accountingModel.ReadDataType


class HistoryViewModel : ViewModel() {
    private val _displayData: MutableLiveData<ArrayList<ReadDataDate>> = MutableLiveData()
    val displayData: LiveData<ArrayList<ReadDataDate>> = _displayData

    private val _displayTypeFilter: MutableLiveData<FilterItem> =
        MutableLiveData()
    val displayTypeFilter: LiveData<FilterItem> = _displayTypeFilter

    private val _displaySeq: MutableLiveData<Int> =
        MutableLiveData()
    val displaySeq: LiveData<Int> = _displaySeq

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

    private fun getItem() {
        val accountingItemList = accountingUploadModel.fillItem()
        val expenseTypeItemList = arrayListOf<FilterTypeItemList>()
        val incomeTypeItemList = arrayListOf<FilterTypeItemList>()
        accountingItemList.itemExpense.itemTypeList.map {
            val titleList = arrayListOf<FilterTypeItem>()
            it.itemList.map { accountingItem ->
                titleList.add(FilterTypeItem(accountingItem.title, false))
            }
            titleList.add(0, FilterTypeItem("${it.type}全選", false))
            expenseTypeItemList.add(
                FilterTypeItemList(
                    type = it.type,
                    filterTypeItemList = titleList
                )
            )
        }
        accountingItemList.itemIncome.itemTypeList.map {
            val titleList = arrayListOf<FilterTypeItem>()
            it.itemList.map { accountingItem ->
                titleList.add(FilterTypeItem(accountingItem.title, false))
            }
            titleList.add(0, FilterTypeItem("${it.type}全選", false))
            incomeTypeItemList.add(
                FilterTypeItemList(
                    type = it.type,
                    filterTypeItemList = titleList
                )
            )
        }
        _displayTypeFilter.postValue(
            FilterItem(
                expenseTypeItemList = expenseTypeItemList,
                incomeTypeItemList = incomeTypeItemList,
                seq = 1
            )
        )
    }

    fun onExpenseClick() {
        seq = 1
        getData()
        _displaySeq.postValue(seq)
    }

    fun onIncomeClick() {
        seq = 2
        getData()
        _displaySeq.postValue(seq)
    }

    fun onItemClick(item: FilterTypeItem, seq: Int) {
        val checkedBoolean = item.isChecked

        val typeFilterList = if (seq == 1) {
            _displayTypeFilter.value?.expenseTypeItemList
        } else {
            _displayTypeFilter.value?.incomeTypeItemList
        }
        typeFilterList?.map {
            if ("全選" in item.title) {
                if (it.type in item.title) {
                    it.filterTypeItemList.onEach { filterTypeItem ->
                        filterTypeItem.isChecked = checkedBoolean.not()
                    }
                }
            } else {
                it.filterTypeItemList.find { filterTypeItem ->
                    filterTypeItem.title == item.title
                }.let { filterTypeItem ->
                    filterTypeItem?.isChecked = checkedBoolean.not()
                }
                //只要有一個isChecked==false 全選也會==false
                val filterListFalse = it.filterTypeItemList.filter { filterTypeItem ->
                    filterTypeItem.isChecked.not()
                }
                if (filterListFalse.isNotEmpty()) {
                    it.filterTypeItemList.find { filterTypeItem ->
                        "全選" in filterTypeItem.title
                    }?.isChecked = false
                }
                //若全都isChecked==true 全選也會被選取
                val filterListTrue = it.filterTypeItemList.filter { filterTypeItem ->
                    filterTypeItem.isChecked
                }
                val filterSize = it.filterTypeItemList.size - 1
                if (filterSize == filterListTrue.size) {
                    it.filterTypeItemList.find { filterTypeItem ->
                        "全選" in filterTypeItem.title
                    }?.isChecked = true
                }
            }
        }
        _displayTypeFilter.postValue(_displayTypeFilter.value)
    }
}