package com.tom.accountingapplication.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tom.accountingapplication.R
import com.tom.accountingapplication.accountingModel.AccountingDataModel
import com.tom.accountingapplication.accountingModel.AccountingItem
import com.tom.accountingapplication.accountingModel.DataListener
import com.tom.accountingapplication.accountingModel.ReadDataDate
import com.tom.accountingapplication.accountingModel.ReadDataYear
import com.tom.accountingapplication.accountingModel.TagItem
import com.tom.accountingapplication.accountingModel.TagItemList
import com.tom.accountingapplication.accountingModel.UpdateItem
import com.tom.accountingapplication.accountingModel.UploadData
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.exp

class AccountingViewModel : ViewModel() {
    private val _showPairMessage = MutableLiveData<Pair<String, String>>()
    val showPairMessage: LiveData<Pair<String, String>> = _showPairMessage

    private val _displayItemSelect: MutableLiveData<AccountingItem> = MutableLiveData()
    val displayItemSelect: LiveData<AccountingItem> = _displayItemSelect
    private val _displayDate: MutableLiveData<String> = MutableLiveData()
    val displayDate: LiveData<String> = _displayDate
    private val _displayTag: MutableLiveData<TagItemList> = MutableLiveData()
    val displayTag: LiveData<TagItemList> = _displayTag
    private val _displayData: MutableLiveData<ArrayList<ReadDataDate>> = MutableLiveData()
    val displayData: LiveData<ArrayList<ReadDataDate>> = _displayData
    private val _displayRetain: MutableLiveData<String> = MutableLiveData()
    val displayRetain: LiveData<String> = _displayRetain


    private val accountingUploadModel = AccountingDataModel()

    private var seq = 1 // 1 = expense 2 = income
    private var expenseList = arrayListOf<UpdateItem>()
    private var incomeList = arrayListOf<UpdateItem>()

    init {
        setItemData()
    }


    private fun setItemData() {
        //icon
        expenseList = arrayListOf(
            UpdateItem("早餐",0, 1, true),
            UpdateItem("午餐", 0, 1, false),
            UpdateItem("晚餐",0, 1, false),
            UpdateItem("交通", 0, 1, false),
            UpdateItem("飲品", 0, 1, false),
            UpdateItem("點心",0, 1, false),
            UpdateItem("娛樂", 0, 1, false),
            UpdateItem("日用品",0, 1, false),
            UpdateItem("購物", 0, 1, false),
            UpdateItem("帳單", 0, 1, false),
            UpdateItem("股票", 0, 1, false),
            UpdateItem("虛擬貨幣", 0, 1, false),
            UpdateItem("其他", 0, 1, false),
        )
        incomeList = arrayListOf(
            UpdateItem("薪水", 0, 2, true),
            UpdateItem("獎金", 0, 2, false),
            UpdateItem("股息", 0, 2, false),
            UpdateItem("利息", 0, 2, false),
            UpdateItem("股票", 0, 2, false),
            UpdateItem("虛擬貨幣", 0, 2, false),
            UpdateItem("其他",0, 2, false),
        )
        expenseList.onEach {
            it.image = accountingUploadModel.getIcon(it.title)
        }
        incomeList.onEach {
            it.image = accountingUploadModel.getIcon(it.title)
        }
        _displayItemSelect.postValue(
            AccountingItem(
                itemExpenseList = expenseList,
                itemIncomeList = incomeList,
                itemSelectedDrawable = R.drawable.icon_breakfast,
                seq = seq
            )
        )
        //Date
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy/MM/dd (E)", Locale.getDefault())
        val today = dateFormat.format(calendar.time)
        _displayDate.postValue(today)
        //tag
        val tagList = arrayListOf(
            TagItem(title = "日常", isSelect = true),
            TagItem(title = "台中一日遊", isSelect = false)
        )
        val tagItemList = TagItemList(
            tagList = tagList,
            selectedTag = tagList.find { it.isSelect }?.title.toString()
        )
        _displayTag.postValue(tagItemList)
        //Data
        accountingUploadModel.getAccountingData(object : DataListener {
            override fun onDataLoaded(readDataList: ArrayList<ReadDataYear>) {
                val dataList = arrayListOf<ReadDataDate>()
                readDataList.map { year ->
                    year.monthList.map { month ->
                        month.dateList.map { date ->
                            dataList.add(date)
                        }
                    }
                    //每月剩餘
                    val dateFormatMonth = SimpleDateFormat("yyyyMM", Locale.getDefault())
                    val todayMonth = dateFormatMonth.format(calendar.time)
                    val monthPrice = year.monthList.find { it.month == todayMonth }?.monthPrice
                    if (monthPrice.isNullOrEmpty().not()) {
                        val todayMonthPrice = (13000 - (monthPrice?.toInt() ?: 0)).toString()
                        _displayRetain.postValue(todayMonthPrice)
                    }
                }
                _displayData.postValue(dataList)
            }
        })

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
                updateItem.isSelect = false
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
        val newDate = calendar.time
        _displayDate.postValue(dateFormat.format(newDate))
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

    fun onTagClick(tag: TagItem) {
        _displayTag.value?.tagList?.find {
            it.isSelect
        }.let {
            it?.isSelect = false
            tag.isSelect = false
        }
        _displayTag.value?.tagList?.find {
            it == tag
        }.let {
            it?.isSelect = true
            _displayTag.value?.selectedTag = it?.title.toString()
        }
        _displayTag.postValue(_displayTag.value)
    }

    fun onSubmitClick(remark: String, price: Int) {
        val upLoad = UploadData(
            image = _displayItemSelect.value?.itemSelectedDrawable ?: 0,
            item = if (seq == 1) {
                _displayItemSelect.value?.itemExpenseList?.find { it.isSelect }?.title
            } else {
                _displayItemSelect.value?.itemIncomeList?.find { it.isSelect }?.title
            }.toString(),
            date = _displayDate.value.toString(),
            tag = _displayTag.value?.selectedTag.toString(),
            remark = remark,
            price = price,
            type = seq
        )
        accountingUploadModel.uploadData(upLoad)
    }
}
