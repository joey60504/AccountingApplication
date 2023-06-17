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
    fun fillItem(): AccountingItemList {
        return AccountingItemList(
            itemExpense = AccountingItemTypeList(
                typeList = arrayListOf("飲食", "購物", "交通", "投資", "娛樂", "生活"),
                itemTypeList = arrayListOf(
                    AccountingItemType(
                        type = "飲食",
                        arrayListOf(
                            AccountingItem("早餐", 0, 1, true, "飲食"),
                            AccountingItem("午餐", 0, 1, false, "飲食"),
                            AccountingItem("晚餐", 0, 1, false, "飲食"),
                            AccountingItem("飲品", 0, 1, false, "飲食"),
                            AccountingItem("點心", 0, 1, false, "飲食"),
                            AccountingItem("其他", 0, 1, false, "飲食"),
                        ).onEach { it.image = getIcon(it.title) }
                    ),
                    AccountingItemType(
                        type = "購物",
                        arrayListOf(
                            AccountingItem("日用品", 0, 1, false, "購物"),
                            AccountingItem("購物", 0, 1, false, "購物"),
                            AccountingItem("其他", 0, 1, false, "購物")
                        ).onEach { it.image = getIcon(it.title) }
                    ),
                    AccountingItemType(
                        type = "交通",
                        arrayListOf(
                            AccountingItem("加油", 0, 1, false, "交通"),
                            AccountingItem("火車", 0, 1, false, "交通"),
                            AccountingItem("其他", 0, 1, false, "交通"),
                        ).onEach { it.image = getIcon(it.title) }
                    ),
                    AccountingItemType(
                        type = "投資",
                        arrayListOf(
                            AccountingItem("股票", 0, 1, false, "投資"),
                            AccountingItem("虛擬貨幣", 0, 1, false, "投資"),
                            AccountingItem("其他", 0, 1, false, "投資"),
                        ).onEach { it.image = getIcon(it.title) }
                    ),
                    AccountingItemType(
                        type = "娛樂",
                        arrayListOf(
                            AccountingItem("串流平台", 0, 1, false, "娛樂"),
                            AccountingItem("遊戲", 0, 1, false, "娛樂"),
                            AccountingItem("唱歌", 0, 1, false, "娛樂"),
                            AccountingItem("票券", 0, 1, false, "娛樂"),
                            AccountingItem("其他", 0, 1, false, "娛樂"),
                        ).onEach { it.image = getIcon(it.title) }
                    ),
                    AccountingItemType(
                        type = "生活",
                        arrayListOf(
                            AccountingItem("帳單", 0, 1, false, "生活"),
                            AccountingItem("孝親費", 0, 1, false, "生活"),
                            AccountingItem("其他", 0, 1, false, "生活"),
                        ).onEach { it.image = getIcon(it.title) }
                    ),
                ),
                typeSeq = 0
            ),
            itemIncome = AccountingItemTypeList(
                typeList = arrayListOf("生活"),
                itemTypeList = arrayListOf(
                    AccountingItemType(
                        type = "生活",
                        arrayListOf(
                            AccountingItem("薪水", 0, 2, true, "生活"),
                            AccountingItem("獎金", 0, 2, false, "生活"),
                            AccountingItem("股息", 0, 2, false, "生活"),
                            AccountingItem("利息", 0, 2, false, "生活"),
                            AccountingItem("股票收入", 0, 2, false, "生活"),
                            AccountingItem("虛擬貨幣收入", 0, 2, false, "生活"),
                            AccountingItem("其他", 0, 2, false, "生活"),
                        ).onEach { it.image = getIcon(it.title) })
                ),
                typeSeq = 0
            ),
            seq = 1,
            itemSelectedDrawable = getIcon("早餐")
        )
    }

    fun getIcon(title: String): Int {
        return when (title) {
            "早餐" -> R.drawable.icon_breakfast
            "午餐" -> R.drawable.icon_lunch
            "晚餐" -> R.drawable.icon_dinner
            "加油" -> R.drawable.icon_oil
            "火車" -> R.drawable.icon_transport
            "飲品" -> R.drawable.icon_drink
            "點心" -> R.drawable.icon_dessert
            "串流平台" -> R.drawable.icon_entertainment
            "遊戲" -> R.drawable.icon_game
            "唱歌" -> R.drawable.icon_sing
            "票券" -> R.drawable.icon_ticket
            "日用品" -> R.drawable.icon_daily_necessary
            "購物" -> R.drawable.icon_shopping
            "帳單" -> R.drawable.icon_bill
            "股票" -> R.drawable.icon_stock
            "虛擬貨幣" -> R.drawable.icon_vertical_currency
            "孝親費" -> R.drawable.icon_family
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

    fun uploadData(upload: UploadData, seq: Int) {
        val typeString = if (seq == 1) "Expense" else "Income"
        val dateYear = upload.date.substring(0, 4)
        val dateMonth = upload.date.substring(0, 7).replace("/", "")
        val date = upload.date.replace("/", "")
        val random = Random()
        val randomNumber = (random.nextInt(90000000) + 10000000).toString()
        val uploadMap = mapOf(
            "seq" to upload.seq,
            "title" to upload.title,
            "date" to upload.date,
            "tag" to upload.tag,
            "remark" to upload.remark,
            "price" to upload.price,
            "type" to upload.type
        )
        database.child(userEmailModify).child("AccountingVision2").child(typeString).child(dateYear)
            .child(dateMonth).child(date).child(upload.tag).child(randomNumber)
            .updateChildren(uploadMap)
    }

    fun deleteData(delete: UploadData, seq: Int) {
        val typeString = if (seq == 1) "Expense" else "Income"
        val dateYear = delete.date.substring(0, 4)
        val dateMonth = delete.date.substring(0, 7).replace("/", "")
        val date = delete.date.replace("/", "")
        database.child(userEmailModify).child("AccountingVision2").child(typeString).get()
            .addOnSuccessListener {
                val root = it.value as Map<*, *>
                val accountingYear = root[dateYear] as Map<*, *>
                val accountingMonth = accountingYear[dateMonth] as Map<*, *>
                val accountingDate = accountingMonth[date] as Map<*, *>
                val accountingTag = accountingDate[delete.tag] as Map<*, *>
                accountingTag.map { number ->
                    val numberValue = number.value as Map<*, *>
                    val data = UploadData(
                        title = numberValue["title"].toString(),
                        date = numberValue["date"].toString(),
                        tag = numberValue["tag"].toString(),
                        price = numberValue["price"].toString().toInt(),
                        remark = numberValue["remark"].toString(),
                        seq = numberValue["seq"].toString().toInt(),
                        type = numberValue["type"].toString(),
                        image = getIcon(numberValue["title"].toString())
                    )
                    if (data == delete) {
                        database.child(userEmailModify).child("AccountingVision2").child(typeString)
                            .child(dateYear)
                            .child(dateMonth).child(date).child(delete.tag)
                            .child(number.key.toString()).removeValue()
                    }
                }
            }
    }

    fun updateData(oldData: UploadData, newData: UploadData) {
        deleteData(oldData, oldData.seq)
        uploadData(newData, newData.seq)
    }

    fun getAccountingData(listener: DataListener) {
        val dataListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val root = snapshot.value as Map<*, *>
                val userRoot = root[userEmailModify] as Map<*, *>
                val readDataTypeList = arrayListOf<ReadDataType>()
                if (userRoot["AccountingVision2"] != null) {
                    val userAccountingRoot = userRoot["AccountingVision2"] as Map<*, *>
                    userAccountingRoot.map { type ->
                        val userAccountingTypeValue = type.value as Map<*, *>
                        val userAccountingTypeKey = type.key
                        val readDataYearList = arrayListOf<ReadDataYear>()
                        var typePrice = 0
                        userAccountingTypeValue.map { year ->
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
                                                    title = userAccountingData["title"].toString(),
                                                    date = userAccountingData["date"].toString(),
                                                    tag = userAccountingData["tag"].toString(),
                                                    remark = userAccountingData["remark"].toString(),
                                                    price = userAccountingData["price"].toString()
                                                        .toInt(),
                                                    seq = userAccountingData["seq"].toString()
                                                        .toInt(),
                                                    type = userAccountingData["type"].toString(),
                                                    image = getIcon(userAccountingData["title"].toString())
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
                        readDataYearList.map {
                            typePrice += it.yearPrice.toInt()
                        }
                        readDataTypeList.add(
                            ReadDataType(
                                typePrice = typePrice.toString(),
                                type = userAccountingTypeKey.toString(),
                                yearList = readDataYearList
                            )
                        )
                    }
                }
                listener.onDataLoaded(readDataTypeList)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        database.addValueEventListener(dataListener)
    }
}

interface DataListener {
    fun onDataLoaded(readDataList: ArrayList<ReadDataType>)
}

//item
data class AccountingItemList(
    var itemExpense: AccountingItemTypeList,
    var itemIncome: AccountingItemTypeList,
    var itemSelectedDrawable: Int,
    var seq: Int
)

data class AccountingItemTypeList(
    var itemTypeList: ArrayList<AccountingItemType>,
    var typeList: ArrayList<String>,
    var typeSeq: Int
)

data class AccountingItemType(
    var type: String,
    var itemList: ArrayList<AccountingItem>,
)

data class AccountingItem(
    val title: String,
    var image: Int,
    val seq: Int,
    var isSelect: Boolean,
    var type: String
)

//tag
data class TagItemList(
    var tagList: ArrayList<TagItem>,
    var selectedTag: String
)

data class TagItem(
    val title: String,
    var isSelect: Boolean
)

//上傳or下載
data class ReadDataType(
    var typePrice: String,
    var type: String,
    var yearList: ArrayList<ReadDataYear>
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

data class UploadData(
    var title: String,
    var date: String,
    var tag: String,
    var remark: String,
    var price: Int,
    var seq: Int,
    var type: String,
    var image: Int
)