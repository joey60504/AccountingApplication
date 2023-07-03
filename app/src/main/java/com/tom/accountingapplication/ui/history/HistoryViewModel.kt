package com.tom.accountingapplication.ui.history


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tom.accountingapplication.accountingModel.AccountingDataModel
import com.tom.accountingapplication.accountingModel.DataListener
import com.tom.accountingapplication.accountingModel.DateEnum
import com.tom.accountingapplication.accountingModel.FilterDate
import com.tom.accountingapplication.accountingModel.FilterItem
import com.tom.accountingapplication.accountingModel.FilterItemData
import com.tom.accountingapplication.accountingModel.FilterTypeItem
import com.tom.accountingapplication.accountingModel.ReadDataDate
import com.tom.accountingapplication.accountingModel.ReadDataType
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class HistoryViewModel : ViewModel() {
    private val _displayData: MutableLiveData<ArrayList<ReadDataDate>> = MutableLiveData()
    val displayData: LiveData<ArrayList<ReadDataDate>> = _displayData


    private val _displayTypeFilter: MutableLiveData<FilterItemData> =
        MutableLiveData()
    val displayTypeFilter: LiveData<FilterItemData> = _displayTypeFilter

    private val _displayDateType: MutableLiveData<FilterDate> =
        MutableLiveData()
    val displayDateType: LiveData<FilterDate> = _displayDateType


    private val accountingUploadModel = AccountingDataModel()
    private var dateString = ""

    init {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        dateString = dateFormat.format(calendar.time)

        getData()

        _displayDateType.postValue(FilterDate("全", DateEnum.ALL, false, "日期篩選"))
    }

    fun init(filter: FilterItem?) {
        val filterCount = arrayListOf<FilterTypeItem>()
        filter?.typeItemList?.map {
            it.filterTypeItemList.filter { filterTypeItem ->
                filterTypeItem.isChecked
            }.map { filterTypeItem ->
                filterCount.add(filterTypeItem)
            }
        }
        _displayTypeFilter.postValue(FilterItemData(filter, filterCount.size))
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

    fun onDateClick() {
        _displayDateType.value?.let {
            when (it.state) {
                DateEnum.ALL -> {
                    it.title = "日"
                    it.state = DateEnum.DATE
                    it.isEnable = true
                    it.calendar = dateString
                }

                DateEnum.DATE -> {
                    it.title = "月"
                    it.state = DateEnum.MONTH
                    it.isEnable = true
                    it.calendar = dateString.substring(0, 7)
                }

                DateEnum.MONTH -> {
                    it.title = "年"
                    it.state = DateEnum.YEAR
                    it.isEnable = true
                    it.calendar = dateString.substring(0, 4)
                }

                else -> {
                    it.title = "全"
                    it.state = DateEnum.ALL
                    it.isEnable = false
                    it.calendar = "日期篩選"
                }
            }
        }
        _displayDateType.postValue(_displayDateType.value)
    }
}