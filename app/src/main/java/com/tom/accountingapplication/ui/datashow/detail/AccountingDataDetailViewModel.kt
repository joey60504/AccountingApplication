package com.tom.accountingapplication.ui.datashow.detail

import androidx.lifecycle.ViewModel
import com.tom.accountingapplication.accountingModel.AccountingDataModel
import com.tom.accountingapplication.accountingModel.UploadData

class AccountingDataDetailViewModel : ViewModel() {


    private val accountingUploadModel = AccountingDataModel()

    fun onDeleteClick(item: UploadData) {
        accountingUploadModel.deleteData(item, item.seq)
    }

    fun onUpdateClick(
        title: String,
        date: String,
        tag: String,
        price: Int,
        remark: String,
        type:String,
        item: UploadData
    ) {
        val newData = UploadData(
            title = title,
            date = date,
            tag = tag,
            price = price,
            remark = remark,
            seq = item.seq,
            type = type,
            image = accountingUploadModel.getIcon(title),
        )

        if (item != newData) {
            accountingUploadModel.updateData(item, newData)
        }
    }
}