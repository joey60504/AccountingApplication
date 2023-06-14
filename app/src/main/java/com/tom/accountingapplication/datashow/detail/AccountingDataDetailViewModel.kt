package com.tom.accountingapplication.datashow.detail

import androidx.lifecycle.ViewModel
import com.tom.accountingapplication.accountingModel.AccountingDataModel
import com.tom.accountingapplication.accountingModel.UploadData

class AccountingDataDetailViewModel : ViewModel() {


    private val accountingUploadModel = AccountingDataModel()

    fun onDeleteClick(item: UploadData) {
        accountingUploadModel.deleteData(item)
    }

    fun onUpdateClick(
        title: String,
        date: String,
        tag: String,
        price: Int,
        remark: String,
        item: UploadData
    ) {
        val newData = UploadData(
            item = title,
            date = date,
            tag = tag,
            price = price,
            remark = remark,
            type = item.type,
            image = accountingUploadModel.getIcon(title)
        )

        if (item != newData) {
            accountingUploadModel.updateData(item, newData)
        }
    }
}