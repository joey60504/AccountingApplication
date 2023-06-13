package com.tom.accountingapplication.accountingModel


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tom.accountingapplication.R
import java.util.Random

class AccountingDataModel {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().reference
    private val userEmail = auth.currentUser?.email.toString()
    private val userEmailModify = userEmail.substring(0, userEmail.indexOf("@"))
    fun getIcon(title: String): Int {
        return when (title) {
            "早餐" -> R.drawable.icon_breakfast
            "午餐" -> R.drawable.icon_lunch
            "晚餐" -> R.drawable.icon_dinner
            "交通" -> R.drawable.icon_transport
            "飲品" -> R.drawable.icon_drink
            "點心" -> R.drawable.icon_dessert
            "娛樂" -> R.drawable.icon_entertainment
            "日用品" -> R.drawable.icon_daily_necessary
            "購物" -> R.drawable.icon_shopping
            "帳單" -> R.drawable.icon_bill
            "股票" -> R.drawable.icon_stock
            "虛擬貨幣" -> R.drawable.icon_vertical_currency
            "其他" -> R.drawable.icon_other
            "薪水" -> R.drawable.icon_salery
            "獎金" -> R.drawable.icon_reward
            "股息" -> R.drawable.icon_dividend
            "利息" -> R.drawable.icon_interest
            "股票收入" -> R.drawable.icon_invest_stock
            "虛擬貨幣收入" -> R.drawable.icon_invest_vertical_currency
            else -> R.drawable.icon_other // 如果標題不在上述列表中，返回預設的圖標
        }
    }

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

    fun getAccountingData(listener: DataListener) {
        val dataListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val root = snapshot.value as Map<*, *>
                val userRoot = root[userEmailModify] as Map<*, *>
                if (userRoot["AccountingVision2"] != null) {
                    val userAccountingYear = userRoot["AccountingVision2"] as Map<*, *>
                    val readDataYearList = arrayListOf<ReadDataYear>()
                    userAccountingYear.map { year ->
                        val userAccountingYearValue = year.value as Map<*, *>
                        val userAccountingYearKey = year.key
                        val readDataMonthList = arrayListOf<ReadDataMonth>()
                        var yearPrice = 0
                        userAccountingYearValue.map { month ->
                            val userAccountingMonthValue = month.value as Map<*, *>
                            val userAccountingMonthKey = month.key
                            val readDataDateList = arrayListOf<ReadDataDate>()
                            var monthPrice = 0
                            userAccountingMonthValue.map { day ->
                                val userAccountingDayValue = day.value as Map<*, *>
                                val userAccountingDayKey = day.key
                                val readDataTagList = arrayListOf<ReadDataTag>()
                                var datePrice = 0
                                userAccountingDayValue.map { tag ->
                                    val userAccountingTagValue = tag.value as Map<*, *>
                                    val userAccountingTagKey = tag.key
                                    val readDataDayList = arrayListOf<UploadData>()
                                    var tagPrice = 0
                                    userAccountingTagValue.map { number ->
                                        val userAccountingData = number.value as Map<*, *>
                                        readDataDayList.add(
                                            UploadData(
                                                item = userAccountingData["item"].toString(),
                                                date = userAccountingData["date"].toString(),
                                                tag = userAccountingData["tag"].toString(),
                                                remark = userAccountingData["remark"].toString(),
                                                price = userAccountingData["price"].toString()
                                                    .toInt(),
                                                type = userAccountingData["type"].toString()
                                                    .toInt(),
                                                image = getIcon(userAccountingData["item"].toString())
                                            )
                                        )
                                    }
                                    readDataDayList.map {
                                        tagPrice += it.price
                                    }
                                    readDataTagList.add(
                                        ReadDataTag(
                                            tagPrice = tagPrice.toString(),
                                            title = userAccountingTagKey.toString(),
                                            dataList = readDataDayList
                                        )
                                    )
                                }
                                readDataTagList.map {
                                    datePrice += it.tagPrice.toInt()
                                }
                                readDataDateList.add(
                                    ReadDataDate(
                                        datePrice = datePrice.toString(),
                                        date = userAccountingDayKey.toString(),
                                        tagList = readDataTagList
                                    )
                                )
                            }
                            readDataDateList.map {
                                monthPrice += it.datePrice.toInt()
                            }
                            readDataMonthList.add(
                                ReadDataMonth(
                                    monthPrice = monthPrice.toString(),
                                    month = userAccountingMonthKey.toString(),
                                    dateList = readDataDateList
                                )
                            )
                        }
                        readDataMonthList.map {
                            yearPrice += it.monthPrice.toInt()
                        }
                        readDataYearList.add(
                            ReadDataYear(
                                yearPrice = yearPrice.toString(),
                                year = userAccountingYearKey.toString(),
                                monthList = readDataMonthList
                            )
                        )
                    }
                    listener.onDataLoaded(readDataYearList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        database.addValueEventListener(dataListener)
    }
}

interface DataListener {
    fun onDataLoaded(readDataList: ArrayList<ReadDataYear>)
}

data class AccountingItem(
    var itemExpenseList: ArrayList<UpdateItem>,
    var itemIncomeList: ArrayList<UpdateItem>,
    var itemSelectedDrawable: Int,
    var seq: Int
)

data class UpdateItem(
    val title: String,
    var image: Int,
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
    var image: Int,
    var item: String,
    var date: String,
    var tag: String,
    var remark: String,
    var price: Int,
    var type: Int
)

data class ReadDataYear(
    var yearPrice: String,
    var year: String,
    var monthList: ArrayList<ReadDataMonth>
)

data class ReadDataMonth(
    var monthPrice: String,
    var month: String,
    var dateList: ArrayList<ReadDataDate>
)

data class ReadDataDate(
    var datePrice: String,
    var date: String,
    var tagList: ArrayList<ReadDataTag>
)

data class ReadDataTag(
    var tagPrice: String,
    var title: String,
    var dataList: ArrayList<UploadData>
)

