package com.tom.accountingapplication.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tom.accountingapplication.accountingModel.AccountingDataModel
import com.tom.accountingapplication.accountingModel.DataListener
import com.tom.accountingapplication.accountingModel.ReadDataDate
import com.tom.accountingapplication.accountingModel.ReadDataType
import com.tom.accountingapplication.accountingModel.TagItem
import com.tom.accountingapplication.accountingModel.AccountingItem
import com.tom.accountingapplication.accountingModel.AccountingItemList
import com.tom.accountingapplication.accountingModel.TagItemList
import com.tom.accountingapplication.accountingModel.UploadData
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AccountingViewModel : ViewModel() {
    private val _showPairMessage = MutableLiveData<Pair<String, String>>()
    val showPairMessage: LiveData<Pair<String, String>> = _showPairMessage

    private val _displayItemSelect: MutableLiveData<AccountingItemList> = MutableLiveData()
    val displayItemSelect: LiveData<AccountingItemList> = _displayItemSelect

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

    init {
        setItemData()
    }


    private fun setItemData() {
        //icon
        _displayItemSelect.postValue(accountingUploadModel.fillItem())
        //Date
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy/MM/dd (E)", Locale.getDefault())
        val today = dateFormat.format(calendar.time)
        _displayDate.postValue(today)
        //tag
        val tagList = arrayListOf(
            TagItem(title = "一般記帳", isSelect = true),
            TagItem(title = "台中一日遊", isSelect = false),
            TagItem(title = "新竹一日遊", isSelect = false)
        )

        _displayTag.postValue(
            TagItemList(
                tagList = tagList,
                selectedTag = "一般記帳"
            )
        )
        //Data
        getData()
    }


    fun onExpenseClick() {
        seq = 1
        _displayItemSelect.value?.seq = seq
        _displayItemSelect.postValue(_displayItemSelect.value)
        getData()
    }

    fun onIncomeClick() {
        seq = 2
        _displayItemSelect.value?.seq = seq
        _displayItemSelect.postValue(_displayItemSelect.value)
        getData()
    }

    fun onItemClick(item: AccountingItem) {
        //filter下再用map是因為該陣列只會有一項1
        if (seq == 1) {
            _displayItemSelect.value?.itemExpense?.itemTypeList?.map {
                it.itemList.filter {  accountingItem ->
                    accountingItem.isSelect
                }.map { accountingItem ->
                    accountingItem.isSelect = false
                }
            }

            _displayItemSelect.value?.itemExpense?.itemTypeList?.map {
                it.itemList.filter {  accountingItem ->
                    accountingItem == item
                }.map { accountingItem ->
                    accountingItem.isSelect = true
                    _displayItemSelect.value?.itemSelectedDrawable = accountingItem.image
                    _displayItemSelect.value?.itemExpense?.typeSeq =
                        _displayItemSelect.value?.itemExpense?.typeList?.indexOf(accountingItem.type)
                            ?: 0
                }
            }
            _displayItemSelect.postValue(_displayItemSelect.value)
        } else {
            _displayItemSelect.value?.itemIncome?.itemTypeList?.map {
                it.itemList.filter {  accountingItem ->
                    accountingItem.isSelect
                }.map { accountingItem ->
                    accountingItem.isSelect = false
                }
            }

            _displayItemSelect.value?.itemIncome?.itemTypeList?.map {
                it.itemList.filter {  accountingItem ->
                    accountingItem == item
                }.map { accountingItem ->
                    accountingItem.isSelect = true
                    _displayItemSelect.value?.itemSelectedDrawable = accountingItem.image
                    _displayItemSelect.value?.itemIncome?.typeSeq =
                        _displayItemSelect.value?.itemIncome?.typeList?.indexOf(accountingItem.type)
                            ?: 0
                }
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
        var title = ""
        var type = ""
        var image = 0
        if (seq == 1) {
            _displayItemSelect.value?.itemExpense?.itemTypeList?.map {
                it.itemList.filter {  accountingItem ->
                    accountingItem.isSelect
                }.map { accountingItem ->
                    title = accountingItem.title
                    image = accountingItem.image
                    type = accountingItem.type
                }
            }
        } else {
            _displayItemSelect.value?.itemIncome?.itemTypeList?.map {
                it.itemList.filter {  accountingItem ->
                    accountingItem.isSelect
                }.map { accountingItem ->
                    title = accountingItem.title
                    image = accountingItem.image
                    type = accountingItem.type
                }
            }
        }
        val upLoad = UploadData(
            title = title,
            date = _displayDate.value.toString(),
            tag = _displayTag.value?.selectedTag.toString(),
            remark = remark,
            price = price,
            seq = seq,
            type = type,
            image = image
        )
        accountingUploadModel.uploadData(upLoad, seq)

    }

    private fun getData() {
        val typeString = if (seq == 1) "Expense" else "Income"
        accountingUploadModel.getAccountingData(object : DataListener {
            override fun onDataLoaded(readDataList: ArrayList<ReadDataType>) {
                val dataList = arrayListOf<ReadDataDate>()
                if (readDataList.isEmpty()) {
                    _displayRetain.postValue(_displayRetain.value ?: "13000")
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
                            //每月剩餘
                            if (type.type == "Expense") {
                                val calendar = Calendar.getInstance()
                                val dateFormatMonth =
                                    SimpleDateFormat("yyyyMM", Locale.getDefault())
                                val todayMonth = dateFormatMonth.format(calendar.time)
                                val monthPrice =
                                    year.monthList.find { it.month == todayMonth }?.monthPrice
                                if (monthPrice.isNullOrEmpty().not()) {
                                    val todayMonthPrice =
                                        (13000 - (monthPrice?.toInt() ?: 0)).toString()
                                    _displayRetain.postValue(todayMonthPrice)
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
