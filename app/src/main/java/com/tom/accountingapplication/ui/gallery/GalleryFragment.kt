package com.tom.accountingapplication.ui.gallery

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tom.accountingapplication.AccountDetailDialog
import com.tom.accountingapplication.databinding.FragmentGalleryBinding
import org.eazegraph.lib.models.PieModel
import java.lang.Exception
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class GalleryFragment : Fragment(), HistoricAdapter.OnItemClick {
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!
    lateinit var auth: FirebaseAuth

    var chosenTime: String = "All"
    var monthAddPrice: Float = 0f
    var chosenKind: String = "All"
    var asset: String = ""
    var storeArray = arrayListOf<HashMap<*, *>>()
    var daysDiffList = arrayListOf<Int>()
    var monthList = arrayListOf<String>()
    var deleteArray = arrayListOf<HashMap<*, *>>()
    var typeArray = arrayListOf<String>()
    var typeDeleteArray = arrayListOf<HashMap<*, *>>()
    var calculateTypeArray = arrayListOf<Float>()

    var finalBreakfast: Float = 0f
    var finalLunch: Float = 0f
    var finalDinner: Float = 0f
    var finalTransportation: Float = 0f
    var finalDrink: Float = 0f
    var finalDessert: Float = 0f
    var finalSocial: Float = 0f
    var finalShopping: Float = 0f
    var finalBill: Float = 0f
    var finalGame: Float = 0f
    var finalOther: Float = 0f
    var finalIncome: Float = 0f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        dataSelectAll()
        spinnerDataSelect()

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    fun setData(
        Breakfast: Float, Lunch: Float, Dinner: Float, Transportation: Float,
        Drink: Float, Dessert: Float, Social: Float, Shopping: Float,
        Bill: Float, Game: Float, Other: Float
    ) {
        val total = calculateTypeArray.sum()
        binding.piechart.clearChart()
        binding.piechart.addPieSlice(
            PieModel(
                "Breakfast",
                (Breakfast / total),
                Color.parseColor("#c5d0e2")
            )
        )
        binding.piechart.addPieSlice(
            PieModel(
                "Lunch",
                (Lunch / total),
                Color.parseColor("#f7f1bd")
            )
        )
        binding.piechart.addPieSlice(
            PieModel(
                "Dinner",
                (Dinner / total),
                Color.parseColor("#e2cece")
            )
        )
        binding.piechart.addPieSlice(
            PieModel(
                "Transportation",
                (Transportation / total),
                Color.parseColor("#bce9b2")
            )
        )
        binding.piechart.addPieSlice(
            PieModel(
                "Drink",
                (Drink / total),
                Color.parseColor("#d9d19b")
            )
        )
        binding.piechart.addPieSlice(
            PieModel(
                "Dessert",
                (Dessert / total),
                Color.parseColor("#a77979")
            )
        )
        binding.piechart.addPieSlice(
            PieModel(
                "Social",
                (Social / total),
                Color.parseColor("#99a4f5")
            )
        )
        binding.piechart.addPieSlice(
            PieModel(
                "Shopping",
                (Shopping / total),
                Color.parseColor("#d4a46f")
            )
        )
        binding.piechart.addPieSlice(
            PieModel(
                "Bill",
                (Bill / total),
                Color.parseColor("#f2b175")
            )
        )
        binding.piechart.addPieSlice(
            PieModel("Game",
                (Game / total),
                Color.parseColor("#ffcece")
            )
        )
        binding.piechart.addPieSlice(
            PieModel(
                "Other",
                (Other / total),
                Color.parseColor("#876f99")
            )
        )
        binding.piechart.startAnimation();
        val fixDataTwoDot = DecimalFormat("000.00")
        val fixDataThreeDot = DecimalFormat("00,000")
        binding.textView32.text =
            "${fixDataTwoDot.format((Breakfast / total) * 100.0)}%  $${fixDataThreeDot.format(Breakfast)}"
        binding.textView33.text =
            "${fixDataTwoDot.format((Lunch / total) * 100.0)}%  $${fixDataThreeDot.format(Lunch)}"
        binding.textView34.text =
            "${fixDataTwoDot.format((Dinner / total) * 100.0)}%  $${fixDataThreeDot.format(Dinner)}"
        binding.textView35.text =
            "${fixDataTwoDot.format((Transportation / total) * 100.0)}%  $${fixDataThreeDot.format(Transportation)}"
        binding.textView36.text =
            "${fixDataTwoDot.format((Drink / total) * 100.0)}%  $${fixDataThreeDot.format(Drink)}"
        binding.textView37.text =
            "${fixDataTwoDot.format((Dessert / total) * 100.0)}%  $${fixDataThreeDot.format(Dessert)}"
        binding.textView38.text =
            "${fixDataTwoDot.format((Social / total) * 100.0)}%  $${fixDataThreeDot.format(Social)}"
        binding.textView39.text =
            "${fixDataTwoDot.format((Shopping / total) * 100.0)}%  $${fixDataThreeDot.format(Shopping)}"
        binding.textView40.text =
            "${fixDataTwoDot.format((Bill / total) * 100.0)}%  $${fixDataThreeDot.format(Bill)}"
        binding.textView41.text =
            "${fixDataTwoDot.format((Game / total) * 100.0)}%  $${fixDataThreeDot.format(Game)}"
        binding.textView42.text =
            "${fixDataTwoDot.format((Other / total) * 100.0)}%  $${fixDataThreeDot.format(Other)}"
        binding.textView62.text =
            "${fixDataTwoDot.format((total / total) * 100.0)}%  $${fixDataThreeDot.format(total)}"
        binding.textView54.text = "$${asset.toFloat()}"
        binding.textView55.text = "$${finalIncome}"
        binding.textView61.text = "$${10000 - monthAddPrice}"
    }

    private fun spinnerDataSelect() {
        binding.spinner.adapter = MyAdapter(
            requireContext(),
            listOf("All", "One_Week", "One_Month", "Last_Month", "Six_Month", "One_Year")
        )
        val time =
            arrayListOf("All", "One_Week", "One_Month", "Last_Month", "Six_Month", "One_Year")
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                dataSelectAll()
                chosenTime = time[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        binding.spinner2.adapter = MyAdapter(
            requireContext(),
            listOf(
                "All",
                "Breakfast",
                "Lunch",
                "Dinner",
                "Transportation",
                "Drink",
                "Dessert",
                "Social",
                "Shopping",
                "Bill",
                "Game",
                "Income",
                "Other",
                "Without Income"
            )
        )
        val kind = arrayListOf(
            "All",
            "Breakfast",
            "Lunch",
            "Dinner",
            "Transportation",
            "Drink",
            "Dessert",
            "Social",
            "Shopping",
            "Bill",
            "Game",
            "Income",
            "Other",
            "Without Income"
        )
        binding.spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                dataSelectAll()
                chosenKind = kind[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    fun dataSelectAll() {
        auth = FirebaseAuth.getInstance()
        val userEmail = auth.currentUser?.email.toString()
        val findLittleMouseAt = userEmail.indexOf("@")
        val userEmailValue = userEmail.substring(0, findLittleMouseAt)
        val database = FirebaseDatabase.getInstance().reference

        val dataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val root = dataSnapshot.value as HashMap<*, *>
                val useremail = root[userEmailValue] as HashMap<*, *>
                try {
                    val profile = useremail["Profile"] as HashMap<*, *>
                    asset = profile["Asset"].toString()
                    val accounting =
                        useremail["Accounting"] as HashMap<String, HashMap<String, ArrayList<HashMap<*, *>>>>
                    val sortedMonthKeyList = accounting.filter {
                        it.key != "test"
                    }.toSortedMap().values.toList()
                    storeArray.clear()
                    for (i in sortedMonthKeyList.indices) {
                        val sortedDateKeyList = sortedMonthKeyList[i].toSortedMap().values.toList()
                        for (j in sortedDateKeyList.indices) {
                            sortedDateKeyList[j].map {
                                storeArray.add(it)
                            }
                        }
                    }
                    chosenTimeValue()
                    dataSelectTime()
                    dataSelectKind()
                    dataKindSelectCalculatePrice()
                    setData(
                        finalBreakfast,
                        finalLunch,
                        finalDinner,
                        finalTransportation,
                        finalDrink,
                        finalDessert,
                        finalSocial,
                        finalShopping,
                        finalBill,
                        finalGame,
                        finalOther
                    )
                } catch (e: Exception) {
                    storeArray = arrayListOf()

                }
                activity?.runOnUiThread {
                    binding.recyclerview1.apply {
                        val myAdapter = HistoricAdapter(this@GalleryFragment)
                        adapter = myAdapter
                        val manager = LinearLayoutManager(requireContext())
                        manager.orientation = LinearLayoutManager.VERTICAL
                        layoutManager = manager
                        manager.stackFromEnd = true
                        myAdapter.dataList = storeArray
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        database.addValueEventListener(dataListener)
    }

    class MyAdapter(context: Context, item: List<String>) :
        ArrayAdapter<String>(context, R.layout.simple_spinner_dropdown_item, item) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view: TextView = super.getDropDownView(position, convertView, parent) as TextView
            view.setTextColor(Color.BLACK)
            return view
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view: TextView = super.getDropDownView(position, convertView, parent) as TextView
//            //set the color of first item in the drop down list to gray
//            if (position == 0) {
//                view.setTextColor(Color.GRAY)
//            } else {
//                //here it is possible to define color for other items by
//                //view.setTextColor(Color.RED)
//            }
            return view
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun dataSelectTime() {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd")
        val nowDate = dateFormat.format(Date())
        val startTime: Date = dateFormat.parse(nowDate)

        val dateFormatMonth = SimpleDateFormat("MM")
        val nowDateMonth = dateFormatMonth.format(Date())
        daysDiffList.clear()
        monthList.clear()
        for (i in storeArray.indices) {
            val storeArrayHashMap = storeArray[i]
            val timeDate = storeArrayHashMap["Date"]
            val endTime: Date = dateFormat.parse(timeDate.toString())
            val diff = endTime.time - startTime.time
            val days = (diff / (1000 * 60 * 60 * 24)).toInt()
            daysDiffList.add(days)

            val DataMonth = timeDate.toString().substring(5, 7)
            monthList.add(DataMonth)
        }//get相差天數陣列
        when (chosenTime) {
            "All" -> {

            }
            "One_Week" -> {
                for (i in daysDiffList.indices) {
                    if (daysDiffList[i] <= -7) {
                        deleteArray.add(storeArray[i])
                    }
                }
                storeArray.removeAll(deleteArray)
                deleteArray.clear()
            }
            "One_Month" -> {
                for (i in monthList.indices) {
                    if (monthList[i] != nowDateMonth) {
                        deleteArray.add(storeArray[i])
                    }
                }
                storeArray.removeAll(deleteArray)
                deleteArray.clear()
            }
            "Last_Month" -> {
                if (nowDateMonth == "01") {
                    for (i in monthList.indices) {
                        if (monthList[i] != "12") {
                            deleteArray.add(storeArray[i])
                        }
                    }
                    storeArray.removeAll(deleteArray)
                    deleteArray.clear()
                } else {
                    for (i in monthList.indices) {
                        if (monthList[i] != "0${nowDateMonth.toInt() - 1}") {
                            deleteArray.add(storeArray[i])
                        }
                    }
                    storeArray.removeAll(deleteArray)
                    deleteArray.clear()
                }
            }
            "Six_Month" -> {
                var montharray = arrayListOf<String>()
                when (nowDateMonth) {
                    "1" -> {
                        montharray = arrayListOf("07", "06", "05", "04", "03", "02")
                    }
                    "2" -> {
                        montharray = arrayListOf("08", "07", "06", "05", "04", "03")
                    }
                    "3" -> {
                        montharray = arrayListOf("09", "08", "07", "06", "05", "04")
                    }
                    "4" -> {
                        montharray = arrayListOf("10", "09", "08", "07", "06", "05")
                    }
                    "5" -> {
                        montharray = arrayListOf("11", "10", "09", "08", "07", "06")
                    }
                    "6" -> {
                        montharray = arrayListOf("12", "11", "10", "09", "08", "07")
                    }
                    "7" -> {
                        montharray = arrayListOf("01", "12", "11", "10", "09", "08")
                    }
                    "8" -> {
                        montharray = arrayListOf("02", "01", "12", "11", "10", "09")
                    }
                    "9" -> {
                        montharray = arrayListOf("03", "02", "01", "12", "11", "10")
                    }
                    "10" -> {
                        montharray = arrayListOf("04", "03", "02", "01", "12", "11")
                    }
                    "11" -> {
                        montharray = arrayListOf("05", "04", "03", "02", "01", "12")
                    }
                    "12" -> {
                        montharray = arrayListOf("06", "05", "04", "03", "02", "01")
                    }
                }
                for (i in monthList.indices) {
                    if (montharray.contains(monthList[i])) {
                        deleteArray.add(storeArray[i])
                    }
                }
                storeArray.removeAll(deleteArray)
                deleteArray.clear()
            }
            "One_Year" -> {

            }
        }
    }

    fun dataSelectKind() {
        typeArray.clear()
        for (i in storeArray.indices) {
            val TP = storeArray[i]
            val Type = TP["TypeChoice"].toString()
            typeArray.add(Type)
        }
        when (chosenKind) {
            "All" -> {

            }
            "Breakfast" -> {
                for (i in typeArray.indices) {
                    if (typeArray[i] != "Breakfast") {
                        typeDeleteArray.add(storeArray[i])
                    }
                }
                storeArray.removeAll(typeDeleteArray)
                typeDeleteArray.clear()
            }
            "Lunch" -> {
                for (i in typeArray.indices) {
                    if (typeArray[i] != "Lunch") {
                        typeDeleteArray.add(storeArray[i])
                    }
                }
                storeArray.removeAll(typeDeleteArray)
                typeDeleteArray.clear()
            }
            "Dinner" -> {
                for (i in typeArray.indices) {
                    if (typeArray[i] != "Dinner") {
                        typeDeleteArray.add(storeArray[i])
                    }
                }
                storeArray.removeAll(typeDeleteArray)
                typeDeleteArray.clear()
            }
            "Transportation" -> {
                for (i in typeArray.indices) {
                    if (typeArray[i] != "Transportation") {
                        typeDeleteArray.add(storeArray[i])
                    }
                }
                storeArray.removeAll(typeDeleteArray)
                typeDeleteArray.clear()
            }
            "Drink" -> {
                for (i in typeArray.indices) {
                    if (typeArray[i] != "Drink") {
                        typeDeleteArray.add(storeArray[i])
                    }
                }
                storeArray.removeAll(typeDeleteArray)
                typeDeleteArray.clear()
            }
            "Dessert" -> {
                for (i in typeArray.indices) {
                    if (typeArray[i] != "Dessert") {
                        typeDeleteArray.add(storeArray[i])
                    }
                }
                storeArray.removeAll(typeDeleteArray)
                typeDeleteArray.clear()
            }
            "Social" -> {
                for (i in typeArray.indices) {
                    if (typeArray[i] != "Social") {
                        typeDeleteArray.add(storeArray[i])
                    }
                }
                storeArray.removeAll(typeDeleteArray)
                typeDeleteArray.clear()
            }
            "Shopping" -> {
                for (i in typeArray.indices) {
                    if (typeArray[i] != "Shopping") {
                        typeDeleteArray.add(storeArray[i])
                    }
                }
                storeArray.removeAll(typeDeleteArray)
                typeDeleteArray.clear()
            }
            "Bill" -> {
                for (i in typeArray.indices) {
                    if (typeArray[i] != "Bill") {
                        typeDeleteArray.add(storeArray[i])
                    }
                }
                storeArray.removeAll(typeDeleteArray)
                typeDeleteArray.clear()
            }
            "Game" -> {
                for (i in typeArray.indices) {
                    if (typeArray[i] != "Game") {
                        typeDeleteArray.add(storeArray[i])
                    }
                }
                storeArray.removeAll(typeDeleteArray)
                typeDeleteArray.clear()
            }
            "Income" -> {
                for (i in typeArray.indices) {
                    if (typeArray[i] != "Income") {
                        typeDeleteArray.add(storeArray[i])
                    }
                }
                storeArray.removeAll(typeDeleteArray)
                typeDeleteArray.clear()
            }
            "Other" -> {
                for (i in typeArray.indices) {
                    if (typeArray[i] != "Other") {
                        typeDeleteArray.add(storeArray[i])
                    }
                }
                storeArray.removeAll(typeDeleteArray)
                typeDeleteArray.clear()
            }
            "Without Income" -> {
                for (i in typeArray.indices) {
                    if (typeArray[i] == "Income") {
                        typeDeleteArray.add(storeArray[i])
                    }
                }
                storeArray.removeAll(typeDeleteArray)
                typeDeleteArray.clear()
            }
        }
    }

    fun dataKindSelectCalculatePrice() {
        finalBreakfast = 0f
        finalLunch = 0f
        finalDinner = 0f
        finalTransportation = 0f
        finalDrink = 0f
        finalDessert = 0f
        finalSocial = 0f
        finalShopping = 0f
        finalBill = 0f
        finalGame = 0f
        finalIncome = 0f
        finalOther = 0f
        calculateTypeArray.clear()
        for (i in storeArray.indices) {
            val dataHashMap = storeArray[i]
            val dataType = dataHashMap["TypeChoice"].toString()
            val dataPrice = dataHashMap["FillPrice"].toString().toFloat()
            when (dataType) {
                "Breakfast" -> {
                    finalBreakfast += dataPrice
                }
                "Lunch" -> {
                    finalLunch += dataPrice
                }
                "Dinner" -> {
                    finalDinner += dataPrice
                }
                "Transportation" -> {
                    finalTransportation += dataPrice
                }
                "Drink" -> {
                    finalDrink += dataPrice
                }
                "Dessert" -> {
                    finalDessert += dataPrice
                }
                "Social" -> {
                    finalSocial += dataPrice
                }
                "Shopping" -> {
                    finalShopping += dataPrice
                }
                "Bill" -> {
                    finalBill += dataPrice
                }
                "Game" -> {
                    finalGame += dataPrice
                }
                "Income" -> {
                    finalIncome += dataPrice
                }
                "Other" -> {
                    finalOther += dataPrice
                }
            }
        }
        calculateTypeArray.add(finalBreakfast)
        calculateTypeArray.add(finalLunch)
        calculateTypeArray.add(finalDinner)
        calculateTypeArray.add(finalTransportation)
        calculateTypeArray.add(finalDrink)
        calculateTypeArray.add(finalDessert)
        calculateTypeArray.add(finalSocial)
        calculateTypeArray.add(finalShopping)
        calculateTypeArray.add(finalBill)
        calculateTypeArray.add(finalGame)
        calculateTypeArray.add(finalOther)
    }

    override fun onItemClick(position: Int) {
        activity?.supportFragmentManager?.let {
            AccountDetailDialog(storeArray[position]).show(
                it,
                "AccountDetailDialog"
            )
        }
    }


    @SuppressLint("SimpleDateFormat")
    fun chosenTimeValue() {
        monthAddPrice = 0f
        var dateFormat = SimpleDateFormat("MM")
        val nowDate = dateFormat.format(Date())
        for (i in storeArray.indices) {
            val dataHashMap = storeArray[i]
            val date = dataHashMap["Date"].toString()
            val dateMonth = date.substring(5, 7)
            if (dateMonth == nowDate && dataHashMap["IncomeOrExpense"] == "Expense") {
                monthAddPrice += dataHashMap["FillPrice"].toString().toInt()
            }
        }
    }
}














