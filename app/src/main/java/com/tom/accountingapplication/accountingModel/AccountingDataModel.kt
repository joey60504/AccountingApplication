package com.tom.accountingapplication.accountingModel


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Random

class AccountingDataModel {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().reference
    private val userEmail = auth.currentUser?.email.toString()
    private val userEmailModify = userEmail.substring(0, userEmail.indexOf("@"))
    fun uploadData(upload: UploadData) {
        val dateYear = upload.date.substring(0, 4)
        val dateMonth = upload.date.substring(0, 7).replace("/", "")
        val date = upload.date.replace("/", "")
        val random = Random()
        val randomNumber = (random.nextInt(90000000) + 10000000).toString()
        val uploadMap = mapOf(
            "type" to upload.type,
            "item" to upload.item,
            "date" to upload.date,
            "tag" to upload.tag,
            "remark" to upload.remark,
            "price" to upload.price
        )
        database.child(userEmailModify).child("AccountingVision2").child(dateYear)
            .child(dateMonth).child(date).child(upload.tag).child(randomNumber)
            .updateChildren(uploadMap)
    }

    fun getData(listener: DataListener) {
        val readDataList = arrayListOf<ReadData>()
        val dataListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val root = snapshot.value as Map<*, *>
                val userRoot = root[userEmailModify] as Map<*, *>
                if (userRoot["AccountingVision2"] != null) {
                    val userAccountingYear = userRoot["AccountingVision2"] as Map<*, *>
                    userAccountingYear.map { year ->
                        val userAccountingYearValue = year.value as Map<*, *>
                        userAccountingYearValue.map { month ->
                            val userAccountingMonthValue = month.value as Map<*, *>
                            userAccountingMonthValue.map { day ->
                                val userAccountingDayValue = day.value as Map<*, *>
                                val userAccountingDayKey = day.key
                                val tagList = arrayListOf<ReadDataTagList>()
                                var datePrice = 0
                                userAccountingDayValue.map { tag ->
                                    val userAccountingTagValue = tag.value as Map<*, *>
                                    val userAccountingTagKey = tag.key
                                    val dataList = arrayListOf<UploadData>()
                                    var tagPrice = 0
                                    userAccountingTagValue.map { number ->
                                        val userAccountingData = number.value as Map<*, *>
                                        dataList.add(
                                            UploadData(
                                                item = userAccountingData["item"].toString(),
                                                date = userAccountingData["date"].toString(),
                                                tag = userAccountingData["tag"].toString(),
                                                remark = userAccountingData["remark"].toString(),
                                                price = userAccountingData["price"].toString()
                                                    .toInt(),
                                                type = userAccountingData["type"].toString().toInt()
                                            )
                                        )
                                    }
                                    dataList.map {
                                        tagPrice += it.price
                                    }
                                    tagList.add(
                                        ReadDataTagList(
                                            tagPrice = tagPrice.toString(),
                                            title = userAccountingTagKey.toString(),
                                            dataList = dataList
                                        )
                                    )
                                }
                                tagList.map {
                                    datePrice += it.tagPrice.toInt()
                                }
                                readDataList.add(
                                    ReadData(
                                        datePrice = datePrice.toString(),
                                        date = userAccountingDayKey.toString(),
                                        tagList = tagList
                                    )
                                )
                            }
                        }
                    }
                    listener.onDataLoaded(readDataList)
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        }
        database.addValueEventListener(dataListener)
    }
}
interface DataListener {
    fun onDataLoaded(readDataList: ArrayList<ReadData>)
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
    var item: String,
    var date: String,
    var tag: String,
    var remark: String,
    var price: Int,
    var type: Int
)

data class ReadData(
    var datePrice: String,
    var date: String,
    var tagList: ArrayList<ReadDataTagList>
)

data class ReadDataTagList(
    var tagPrice: String,
    var title: String,
    var dataList: ArrayList<UploadData>
)

