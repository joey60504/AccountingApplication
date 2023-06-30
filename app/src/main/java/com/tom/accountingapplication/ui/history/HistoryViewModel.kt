package com.tom.accountingapplication.ui.history


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tom.accountingapplication.accountingModel.AccountingDataModel
import com.tom.accountingapplication.accountingModel.DataListener
import com.tom.accountingapplication.accountingModel.FilterItem
import com.tom.accountingapplication.accountingModel.ReadDataDate
import com.tom.accountingapplication.accountingModel.ReadDataType


class HistoryViewModel : ViewModel() {
    private val _displayData: MutableLiveData<ArrayList<ReadDataDate>> = MutableLiveData()
    val displayData: LiveData<ArrayList<ReadDataDate>> = _displayData


    private val _displayTypeFilter: MutableLiveData<FilterItem?> =
        MutableLiveData()
    val displayTypeFilter: LiveData<FilterItem?> = _displayTypeFilter

    private val accountingUploadModel = AccountingDataModel()

    init {
        getData()
    }

    fun init(filter: FilterItem?) {
        _displayTypeFilter.postValue(filter)
    }

    private fun getData() {
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
                                    dataList.add(date)

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
}