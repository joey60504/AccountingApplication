package com.tom.accountingapplication.accountingModel


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.Random

class AccountingDataModel {
    private lateinit var auth: FirebaseAuth

    fun uploadData(upload: UploadData) {
        auth = FirebaseAuth.getInstance()
        val database = FirebaseDatabase.getInstance().reference
        val dateYear = upload.date.substring(0, 4)
        val dateMonth = upload.date.substring(0, 7).replace("/", "")
        val date = upload.date.replace("/", "")
        val random = Random()
        val randomNumber = (random.nextInt(90000000) + 10000000).toString()
        val uploadMap = mapOf(
            "item" to upload.item,
            "date" to upload.date,
            "tag" to upload.tag,
            "remark" to upload.remark,
            "price" to upload.price
        )
        database.child(upload.email).child("AccountingVision2").child(dateYear)
            .child(dateMonth).child(date).child(upload.tag).child(randomNumber)
            .updateChildren(uploadMap)
    }
}

data class AccountingItem(
    var itemExpenseList: ArrayList<UpdateItem>,
    var itemIncomeList: ArrayList<UpdateItem>,
    var itemSelectedDrawable: Int,
    var seq: Int
)

data class UpdateItem(
    val title: String,
    val image: Int,
    val type: Int,
    var isSelect: Boolean
)

data class TagItemList(
    var tagList: ArrayList<TagItem>,
    var selectedTag: String
)

data class TagItem(
    val title: String,
    var isSelect: Boolean
)

data class UploadData(
    var email: String,
    var item: String,
    var date: String,
    var tag: String,
    var remark: String,
    var price: Int
)