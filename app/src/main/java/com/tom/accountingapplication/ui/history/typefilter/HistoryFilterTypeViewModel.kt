package com.tom.accountingapplication.ui.history.typefilter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tom.accountingapplication.accountingModel.AccountingDataModel
import com.tom.accountingapplication.accountingModel.FilterItem
import com.tom.accountingapplication.accountingModel.FilterTypeItem
import com.tom.accountingapplication.accountingModel.FilterTypeItemList

class HistoryFilterTypeViewModel : ViewModel() {

    private val _displayTypeFilter: MutableLiveData<FilterItem?> =
        MutableLiveData()
    val displayTypeFilter: LiveData<FilterItem?> = _displayTypeFilter

    private val accountingUploadModel = AccountingDataModel()
    private var selectedFilter = arrayListOf<FilterTypeItem>()

    fun init(filter: FilterItem?) {
        if(filter!= null){
            _displayTypeFilter.postValue(filter)
        } else {
            getItem()
        }
    }

    private fun getItem() {
        val accountingItemList = accountingUploadModel.fillItem()
        val typeItemList = arrayListOf<FilterTypeItemList>()
        accountingItemList.itemExpense.itemTypeList.map {
            val titleList = arrayListOf<FilterTypeItem>()
            it.itemList.map { accountingItem ->
                titleList.add(FilterTypeItem(it.type,accountingItem.title, false))
            }
            titleList.add(0, FilterTypeItem(it.type,"${it.type}全選", false))
            typeItemList.add(
                FilterTypeItemList(
                    type = it.type,
                    filterTypeItemList = titleList
                )
            )
        }
        accountingItemList.itemIncome.itemTypeList.map {
            val titleList = arrayListOf<FilterTypeItem>()
            it.itemList.map { accountingItem ->
                titleList.add(FilterTypeItem(it.type,accountingItem.title, false))
            }
            titleList.add(0, FilterTypeItem(it.type,"${it.type}全選", false))
            typeItemList.add(
                FilterTypeItemList(
                    type = it.type,
                    filterTypeItemList = titleList
                )
            )
        }
        _displayTypeFilter.postValue(
            FilterItem(
                typeItemList = typeItemList
            )
        )
    }
    fun onTypeClearClick(){
        _displayTypeFilter.value?.typeItemList?.map {
            it.filterTypeItemList.map { filterTypeItem ->
                filterTypeItem.isChecked = false
            }
        }
        _displayTypeFilter.postValue(_displayTypeFilter.value)
    }
    fun onTypeSubmitClick(){
        _displayTypeFilter.value?.typeItemList?.map {
            it.filterTypeItemList.filter { filterTypeItem ->
                filterTypeItem.isChecked
            }.map {filterTypeItem ->
                selectedFilter.add(filterTypeItem)
            }
        }
    }
    fun onItemClick(item: FilterTypeItem) {
        val checkedBoolean = item.isChecked

        _displayTypeFilter.value?.typeItemList?.map {
            if ("全選" in item.title) {
                if (it.type in item.title) {
                    it.filterTypeItemList.onEach { filterTypeItem ->
                        filterTypeItem.isChecked = checkedBoolean.not()
                    }
                }
            } else {
                it.filterTypeItemList.find { filterTypeItem ->
                    filterTypeItem.title == item.title && filterTypeItem.type == item.type
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