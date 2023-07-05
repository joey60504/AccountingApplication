package com.tom.accountingapplication.ui.history.typefilter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tom.accountingapplication.accountingModel.AccountingDataModel
import com.tom.accountingapplication.accountingModel.FilterItem
import com.tom.accountingapplication.accountingModel.FilterType
import com.tom.accountingapplication.accountingModel.FilterTypeData
import com.tom.accountingapplication.accountingModel.FilterTypeItem

class HistoryFilterTypeViewModel : ViewModel() {

    private val _displayTypeFilter: MutableLiveData<FilterTypeData?> =
        MutableLiveData()
    val displayTypeFilter: LiveData<FilterTypeData?> = _displayTypeFilter

    private val accountingUploadModel = AccountingDataModel()

    fun init(filter: FilterTypeData?) {
        if (filter != null) {
            _displayTypeFilter.postValue(filter)
        } else {
            getItem()
        }
    }

    private fun getItem() {
        val accountingItemList = accountingUploadModel.fillItem()

        val typeList = arrayListOf<FilterType>()
        val expenseTypeList = arrayListOf<FilterItem>()
        val incomeTypeList = arrayListOf<FilterItem>()
        accountingItemList.itemExpense.itemTypeList.map {
            val titleList = arrayListOf<FilterTypeItem>()
            it.itemList.map { accountingItem ->
                titleList.add(FilterTypeItem(1, it.type, accountingItem.title, false))
            }
            titleList.add(0, FilterTypeItem(1, it.type, "${it.type}全選", false))
            expenseTypeList.add(
                FilterItem(
                    seq = 1,
                    type = it.type,
                    filterTypeItemList = titleList
                )
            )
        }
        typeList.add(
            FilterType(
                seq = 1,
                typeList = expenseTypeList
            )
        )
        accountingItemList.itemIncome.itemTypeList.map {
            val titleList = arrayListOf<FilterTypeItem>()
            it.itemList.map { accountingItem ->
                titleList.add(FilterTypeItem(2, it.type, accountingItem.title, false))
            }
            titleList.add(0, FilterTypeItem(2, it.type, "${it.type}全選", false))
            incomeTypeList.add(
                FilterItem(
                    seq = 2,
                    type = it.type,
                    filterTypeItemList = titleList
                )
            )
        }
        typeList.add(
            FilterType(
                seq = 2,
                typeList = incomeTypeList
            )
        )
        _displayTypeFilter.postValue(
            FilterTypeData(
                filterTypeList = typeList,
                tabList = arrayListOf("支出", "收入"),
                position = 0
            )
        )
    }

    fun onTypeClearClick() {
        _displayTypeFilter.value?.filterTypeList?.map {
            it.typeList.map { filterItem ->
                filterItem.filterTypeItemList.map { filterTypeItem ->
                    filterTypeItem.isChecked = false
                }
            }
        }
        _displayTypeFilter.postValue(_displayTypeFilter.value)
    }

    fun onItemClick(item: FilterTypeItem) {
        val checkedBoolean = item.isChecked

        _displayTypeFilter.value?.filterTypeList?.map {
            it.typeList.map { filterItem ->
                if ("全選" in item.title) {
                    if (filterItem.type in item.title) {
                        filterItem.filterTypeItemList.onEach { filterTypeItem ->
                            filterTypeItem.isChecked = checkedBoolean.not()
                        }
                    }
                } else {
                    filterItem.filterTypeItemList.find { filterTypeItem ->
                        filterTypeItem.title == item.title && filterTypeItem.type == item.type
                    }.let { filterTypeItem ->
                        filterTypeItem?.isChecked = checkedBoolean.not()
                    }
                    //只要有一個isChecked==false 全選也會==false
                    val filterListFalse = filterItem.filterTypeItemList.filter { filterTypeItem ->
                        filterTypeItem.isChecked.not()
                    }
                    if (filterListFalse.isNotEmpty()) {
                        filterItem.filterTypeItemList.find { filterTypeItem ->
                            "全選" in filterTypeItem.title
                        }?.isChecked = false
                    }
                    //若全都isChecked==true 全選也會被選取
                    val filterListTrue = filterItem.filterTypeItemList.filter { filterTypeItem ->
                        filterTypeItem.isChecked
                    }
                    val filterSize = filterItem.filterTypeItemList.size - 1
                    if (filterSize == filterListTrue.size) {
                        filterItem.filterTypeItemList.find { filterTypeItem ->
                            "全選" in filterTypeItem.title
                        }?.isChecked = true
                    }
                }
            }
        }
        _displayTypeFilter.value?.position = if (item.seq == 1) 0 else 1
        _displayTypeFilter.postValue(_displayTypeFilter.value)
    }
}