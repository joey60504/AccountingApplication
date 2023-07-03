@file:Suppress("DEPRECATED_ANNOTATION")

package com.tom.accountingapplication.accountingModel


import android.os.Parcelable
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tom.accountingapplication.R
import kotlinx.android.parcel.Parcelize
import java.util.Random
import java.util.logging.Filter

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
                            AccountingItem("宵夜", 0, 1, false, "飲食"),
                            AccountingItem("飲品", 0, 1, false, "飲食"),
                            AccountingItem("蛋糕", 0, 1, false, "飲食"),
                            AccountingItem("單品小吃", 0, 1, false, "飲食"),
                            AccountingItem("自煮食材", 0, 1, false, "飲食"),
                            AccountingItem("其他", 0, 1, false, "飲食"),
                        ).onEach { it.image = getIcon(it.title) }
                    ),
                    AccountingItemType(
                        type = "購物",
                        arrayListOf(
                            AccountingItem("日用品", 0, 1, false, "購物"),
                            AccountingItem("包包", 0, 1, false, "購物"),
                            AccountingItem("衣著", 0, 1, false, "購物"),
                            AccountingItem("鞋子", 0, 1, false, "購物"),
                            AccountingItem("配件", 0, 1, false, "購物"),
                            AccountingItem("化妝品", 0, 1, false, "購物"),
                            AccountingItem("保養品", 0, 1, false, "購物"),
                            AccountingItem("傢俱", 0, 1, false, "購物"),
                            AccountingItem("電腦周邊", 0, 1, false, "購物"),
                            AccountingItem("手機周邊", 0, 1, false, "購物"),
                            AccountingItem("其他周邊", 0, 1, false, "購物"),
                            AccountingItem("其他", 0, 1, false, "購物")
                        ).onEach { it.image = getIcon(it.title) }
                    ),
                    AccountingItemType(
                        type = "交通",
                        arrayListOf(
                            AccountingItem("加油", 0, 1, false, "交通"),
                            AccountingItem("公車", 0, 1, false, "交通"),
                            AccountingItem("火車", 0, 1, false, "交通"),
                            AccountingItem("捷運", 0, 1, false, "交通"),
                            AccountingItem("高鐵", 0, 1, false, "交通"),
                            AccountingItem("修理費", 0, 1, false, "交通"),
                            AccountingItem("租借機車", 0, 1, false, "交通"),
                            AccountingItem("租借汽車", 0, 1, false, "交通"),
                            AccountingItem("其他", 0, 1, false, "交通")
                        ).onEach { it.image = getIcon(it.title) }
                    ),
                    AccountingItemType(
                        type = "投資",
                        arrayListOf(
                            AccountingItem("股票", 0, 1, false, "投資"),
                            AccountingItem("VC", 0, 1, false, "投資"),
                            AccountingItem("基金", 0, 1, false, "投資"),
                            AccountingItem("定期存款", 0, 1, false, "投資"),
                            AccountingItem("儲蓄險", 0, 1, false, "投資"),
                            AccountingItem("債券", 0, 1, false, "投資"),
                            AccountingItem("其他", 0, 1, false, "投資"),
                        ).onEach { it.image = getIcon(it.title) }
                    ),
                    AccountingItemType(
                        type = "娛樂",
                        arrayListOf(
                            AccountingItem("串流平台", 0, 1, false, "娛樂"),
                            AccountingItem("訂閱費用", 0, 1, false, "娛樂"),
                            AccountingItem("遊戲", 0, 1, false, "娛樂"),
                            AccountingItem("唱歌", 0, 1, false, "娛樂"),
                            AccountingItem("住宿費", 0, 1, false, "娛樂"),
                            AccountingItem("票券", 0, 1, false, "娛樂"),
                            AccountingItem("其他", 0, 1, false, "娛樂"),
                        ).onEach { it.image = getIcon(it.title) }
                    ),
                    AccountingItemType(
                        type = "生活",
                        arrayListOf(
                            AccountingItem("房租", 0, 1, false, "生活"),
                            AccountingItem("水費", 0, 1, false, "生活"),
                            AccountingItem("電費", 0, 1, false, "生活"),
                            AccountingItem("瓦斯費", 0, 1, false, "生活"),
                            AccountingItem("電信費", 0, 1, false, "生活"),
                            AccountingItem("網路費", 0, 1, false, "生活"),
                            AccountingItem("保費", 0, 1, false, "生活"),
                            AccountingItem("學費", 0, 1, false, "生活"),
                            AccountingItem("孝親費", 0, 1, false, "生活"),
                            AccountingItem("其他", 0, 1, false, "生活"),
                        ).onEach { it.image = getIcon(it.title) }
                    ),
                ),
                typeSeq = 0
            ),
            itemIncome = AccountingItemTypeList(
                typeList = arrayListOf("收入"),
                itemTypeList = arrayListOf(
                    AccountingItemType(
                        type = "收入",
                        arrayListOf(
                            AccountingItem("薪水", 0, 2, true, "收入"),
                            AccountingItem("獎金", 0, 2, false, "收入"),
                            AccountingItem("接案", 0, 2, false, "收入"),
                            AccountingItem("股息", 0, 2, false, "收入"),
                            AccountingItem("利息", 0, 2, false, "收入"),
                            AccountingItem("股票收入", 0, 2, false, "收入"),
                            AccountingItem("VC收入", 0, 2, false, "收入"),
                            AccountingItem("其他", 0, 2, false, "收入"),
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
            //支出
            //飲食
            "早餐" -> R.drawable.icon_breakfast
            "午餐" -> R.drawable.icon_lunch
            "晚餐" -> R.drawable.icon_dinner
            "宵夜" -> R.drawable.icon_nightsnack
            "飲品" -> R.drawable.icon_drink
            "蛋糕" -> R.drawable.icon_dessert
            "單品小吃" -> R.drawable.icon_food
            "自煮食材" -> R.drawable.icon_lettuce
            //購物
            "日用品" -> R.drawable.icon_daily_necessary
            "包包" -> R.drawable.icon_ladybag
            "衣著" -> R.drawable.icon_cloth
            "鞋子" -> R.drawable.icon_shoe
            "配件" -> R.drawable.icon_ring
            "化妝品" -> R.drawable.icon_lipstick
            "保養品" -> R.drawable.icon_lotion
            "傢俱" -> R.drawable.icon_refrigerator
            "電腦周邊" -> R.drawable.icon_computer
            "手機周邊" -> R.drawable.icon_phone
            "其他周邊" -> R.drawable.icon_bag
            //交通
            "加油" -> R.drawable.icon_oil
            "公車" -> R.drawable.icon_bus
            "火車" -> R.drawable.icon_train
            "捷運" -> R.drawable.icon_mrt
            "高鐵" -> R.drawable.icon_hsr
            "修理費" -> R.drawable.icon_vehicle_fix
            "租借機車" -> R.drawable.icon_motocycle_rent
            "租借汽車" -> R.drawable.icon_car_rent
            //投資
            "股票" -> R.drawable.icon_stock
            "VC" -> R.drawable.icon_vertical_currency
            "基金" -> R.drawable.icon_fund
            "定期存款" -> R.drawable.icon_money
            "儲蓄險" -> R.drawable.icon_savinginsurance
            "債券" -> R.drawable.icon_bond
            //娛樂
            "串流平台" -> R.drawable.icon_entertainment
            "訂閱費用" -> R.drawable.icon_subscribe
            "遊戲" -> R.drawable.icon_game
            "唱歌" -> R.drawable.icon_sing
            "住宿費" -> R.drawable.icon_house
            "票券" -> R.drawable.icon_ticket
            //生活
            "房租" -> R.drawable.icon_building
            "水費" -> R.drawable.icon_water
            "電費" -> R.drawable.icon_electricity
            "瓦斯費" -> R.drawable.icon_fire
            "電信費" -> R.drawable.icon_phonefee
            "網路費" -> R.drawable.icon_network
            "保費" -> R.drawable.icon_bill
            "學費" -> R.drawable.icon_school
            "孝親費" -> R.drawable.icon_family
            //收入
            //生活
            "薪水" -> R.drawable.icon_salery
            "獎金" -> R.drawable.icon_reward
            "接案" -> R.drawable.icon_mission
            "股息" -> R.drawable.icon_dividend
            "利息" -> R.drawable.icon_interest
            "股票收入" -> R.drawable.icon_invest_stock
            "VC收入" -> R.drawable.icon_invest_vertical_currency
            //其他
            "其他" -> R.drawable.icon_other

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
    var itemSelectedDrawable: Int, //當前選取Item的drawable
    var seq: Int  //收入or支出
)

data class AccountingItemTypeList(
    var itemTypeList: ArrayList<AccountingItemType>,
    var typeList: ArrayList<String>, // 支出 : "飲食", "購物", "交通", "投資", "娛樂", "生活" //收入 : "生活"
    var typeSeq: Int //在viewpager的第幾項
)

data class AccountingItemType(
    var type: String, //typeList下 是哪一個
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

//篩選 種類
data class FilterItemData(
    var itemList: FilterItem?,
    var count: Int
)

@Parcelize
data class FilterItem(
    var typeItemList: ArrayList<FilterTypeItemList>
) : Parcelable

@Parcelize
data class FilterTypeItemList(
    var type: String,
    var filterTypeItemList: ArrayList<FilterTypeItem>
) : Parcelable

@Parcelize
data class FilterTypeItem(
    var type: String,
    var title: String,
    var isChecked: Boolean
) : Parcelable

// 篩選 日期
data class FilterDate(
    var title: String,
    var state: DateEnum,
    var isEnable: Boolean,
    var calendar: String
)

enum class DateEnum {
    ALL,
    DATE,
    MONTH,
    YEAR
}