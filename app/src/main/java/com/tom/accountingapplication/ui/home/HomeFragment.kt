package com.tom.accountingapplication.ui.home

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.tom.accountingapplication.ProfileActivity
import com.tom.accountingapplication.Accounting
import com.tom.accountingapplication.databinding.FragmentHomeBinding
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import com.tom.accountingapplication.R;


class HomeFragment : Fragment(), homeadapter.OnItemClick {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    lateinit var auth: FirebaseAuth

    var IncomeOrExpense = "Expense"
    var TypeChoice = "Other"
    lateinit var FillPrice: String
    lateinit var TypeRemark: String
    lateinit var nowdate: String

    var StoreArray = arrayListOf<HashMap<*, *>>()
    lateinit var imagebtnlist: ArrayList<ImageButton>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val format = SimpleDateFormat("yyyy/MM/dd")
        nowdate = format.format(Date())
        binding.date.text = nowdate

        binding.income.setBackgroundColor(Color.parseColor("#F5F5DC"))
        binding.expense.setBackgroundColor(Color.parseColor("#907dac"));

        setimagebtnlist()
        firstLogin()
        setOnClick()

        return root
    }

    fun setimagebtnlist() {
        imagebtnlist = arrayListOf(
            binding.imageEgg, binding.imageLunch, binding.imageDinner, binding.imageBus,
            binding.imageDrink, binding.imageCake, binding.imagePeople, binding.imageBag,
            binding.imageBill, binding.imageGame, binding.imageIncome, binding.imageOther
        )
    }

    fun setOnClick() {
        binding.imageEgg.setOnClickListener {
            TypeChoice = "Breakfast"
            binding.imageEgg.setBackgroundResource(R.drawable.beige_rectangle)
            imagebtnlist.remove(binding.imageEgg)
            for (i in imagebtnlist.indices) {
                imagebtnlist[i].setBackgroundResource(R.drawable.color_rectangle)
            }
            setimagebtnlist()
        }
        binding.imageLunch.setOnClickListener {
            TypeChoice = "Lunch"
            binding.imageLunch.setBackgroundResource(R.drawable.beige_rectangle)
            imagebtnlist.remove(binding.imageLunch)
            for (i in imagebtnlist.indices) {
                imagebtnlist[i].setBackgroundResource(R.drawable.color_rectangle)
            }
            setimagebtnlist()
        }
        binding.imageDinner.setOnClickListener {
            TypeChoice = "Dinner"
            binding.imageDinner.setBackgroundResource(R.drawable.beige_rectangle)
            imagebtnlist.remove(binding.imageDinner)
            for (i in imagebtnlist.indices) {
                imagebtnlist[i].setBackgroundResource(R.drawable.color_rectangle)
            }
            setimagebtnlist()
        }
        binding.imageBus.setOnClickListener {
            TypeChoice = "Transportation"
            binding.imageBus.setBackgroundResource(R.drawable.beige_rectangle)
            imagebtnlist.remove(binding.imageBus)
            for (i in imagebtnlist.indices) {
                imagebtnlist[i].setBackgroundResource(R.drawable.color_rectangle)
            }
            setimagebtnlist()
        }
        binding.imageDrink.setOnClickListener {
            TypeChoice = "Drink"
            binding.imageDrink.setBackgroundResource(R.drawable.beige_rectangle)
            imagebtnlist.remove(binding.imageDrink)
            for (i in imagebtnlist.indices) {
                imagebtnlist[i].setBackgroundResource(R.drawable.color_rectangle)
            }
            setimagebtnlist()
        }
        binding.imageCake.setOnClickListener {
            TypeChoice = "Dessert"
            binding.imageCake.setBackgroundResource(R.drawable.beige_rectangle)
            imagebtnlist.remove(binding.imageCake)
            for (i in imagebtnlist.indices) {
                imagebtnlist[i].setBackgroundResource(R.drawable.color_rectangle)
            }
            setimagebtnlist()
        }
        binding.imagePeople.setOnClickListener {
            TypeChoice = "Social"
            binding.imagePeople.setBackgroundResource(R.drawable.beige_rectangle)
            imagebtnlist.remove(binding.imagePeople)
            for (i in imagebtnlist.indices) {
                imagebtnlist[i].setBackgroundResource(R.drawable.color_rectangle)
            }
            setimagebtnlist()
        }
        binding.imageBag.setOnClickListener {
            TypeChoice = "Shopping"
            binding.imageBag.setBackgroundResource(R.drawable.beige_rectangle)
            imagebtnlist.remove(binding.imageBag)
            for (i in imagebtnlist.indices) {
                imagebtnlist[i].setBackgroundResource(R.drawable.color_rectangle)
            }
            setimagebtnlist()
        }
        binding.imageBill.setOnClickListener {
            TypeChoice = "Bill"
            binding.imageBill.setBackgroundResource(R.drawable.beige_rectangle)
            imagebtnlist.remove(binding.imageBill)
            for (i in imagebtnlist.indices) {
                imagebtnlist[i].setBackgroundResource(R.drawable.color_rectangle)
            }
            setimagebtnlist()
        }
        binding.imageGame.setOnClickListener {
            TypeChoice = "Game"
            binding.imageGame.setBackgroundResource(R.drawable.beige_rectangle)
            imagebtnlist.remove(binding.imageGame)
            for (i in imagebtnlist.indices) {
                imagebtnlist[i].setBackgroundResource(R.drawable.color_rectangle)
            }
            setimagebtnlist()
        }
        binding.imageIncome.setOnClickListener {
            TypeChoice = "Income"
            binding.imageIncome.setBackgroundResource(R.drawable.beige_rectangle)
            imagebtnlist.remove(binding.imageIncome)
            for (i in imagebtnlist.indices) {
                imagebtnlist[i].setBackgroundResource(R.drawable.color_rectangle)
            }
            setimagebtnlist()
        }
        binding.imageOther.setOnClickListener {
            TypeChoice = "Other"
            binding.imageOther.setBackgroundResource(R.drawable.beige_rectangle)
            imagebtnlist.remove(binding.imageOther)
            for (i in imagebtnlist.indices) {
                imagebtnlist[i].setBackgroundResource(R.drawable.color_rectangle)
            }
            setimagebtnlist()
        }
        binding.income.setOnClickListener {
            IncomeOrExpense = "Income"
            binding.income.setBackgroundColor(Color.parseColor("#907dac"));
            binding.expense.setBackgroundColor(Color.parseColor("#F5F5DC"));
            TypeChoice = "Income"
            binding.imageIncome.setBackgroundResource(R.drawable.beige_rectangle)
            imagebtnlist.remove(binding.imageIncome)
            for (i in imagebtnlist.indices) {
                imagebtnlist[i].setBackgroundResource(R.drawable.color_rectangle)
            }
            setimagebtnlist()
        }
        binding.expense.setOnClickListener {
            IncomeOrExpense = "Expense"
            binding.income.setBackgroundColor(Color.parseColor("#F5F5DC"));
            binding.expense.setBackgroundColor(Color.parseColor("#907dac"));
        }
        binding.date.setOnClickListener {
            datePicker()
        }
        binding.upload.setOnClickListener {
            upDateData()
        }
    }

    private fun datePicker() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(requireContext(), { _, year, month, day ->
            run {
                val format = setDateFormat(year, month, day)
                binding.date.text = format
            }
        }, year, month, day).show()
    }

    private fun setDateFormat(year: Int, month: Int, day: Int): String {
        val fixMonth = if (month < 9) {
            "0${month + 1}"
        } else {
            "${month + 1}"
        }
        val fixDay = if (day < 10) {
            "0$day"
        } else {
            "$day"
        }
        return "$year/$fixMonth/$fixDay"
    }

    private fun upDateData() {
        TypeRemark = binding.filltype.text.toString()
        FillPrice = binding.fillmoney.text.toString()
        val chosenDate = binding.date.text.toString().replace("/", "")
        val chosenDateMonth = binding.date.text.substring(0, 7).replace("/", "")

        auth = FirebaseAuth.getInstance()
        val userEmail = auth.currentUser?.email.toString()
        val findLittleMouseAt = userEmail.indexOf("@")
        val userEmailValue = userEmail.substring(0, findLittleMouseAt)
        val database = FirebaseDatabase.getInstance().reference
        if (FillPrice.isNotEmpty()) {
            val upload = Accounting(
                IncomeOrExpense,
                TypeChoice,
                binding.date.text.toString(),
                TypeRemark,
                FillPrice
            ).toDict()
            database.child(userEmailValue).get().addOnSuccessListener {
                val databaseEmailValue = it.value as java.util.HashMap<String, Any>
                //profile
                val userProfile = databaseEmailValue["Profile"] as HashMap<*, *>
                val userAsset = userProfile["Asset"].toString()
                //accounting
                val userAccounting =
                    databaseEmailValue["Accounting"] as java.util.HashMap<String, Any>
                if (userAccounting[chosenDateMonth] != null) {    //有無月份
                    val recordingDateMonth = userAccounting[chosenDateMonth] as HashMap<String, Any>
                    if (recordingDateMonth[chosenDate] != null) {  //有無日期
                        val recordingDate =
                            recordingDateMonth[chosenDate] as ArrayList<Map<String, *>>
                        recordingDate.add(upload)
                        recordingDateMonth[chosenDate] = recordingDate
                        userAccounting[chosenDateMonth] = recordingDateMonth
                        database.child(userEmailValue).child("Accounting")
                            .updateChildren(userAccounting)
                        //profile Asset
                        fillProfileAsset(userAsset, userEmailValue)
                    } else {
                        recordingDateMonth[chosenDate] = arrayListOf(upload)
                        userAccounting[chosenDateMonth] = recordingDateMonth
                        database.child(userEmailValue).child("Accounting")
                            .updateChildren(userAccounting)
                        //profile Asset
                        fillProfileAsset(userAsset, userEmailValue)
                    }
                } else {
                    val recordingDateMonth = hashMapOf<String, Any>()
                    recordingDateMonth[chosenDate] = arrayListOf(upload)
                    userAccounting[chosenDateMonth] = recordingDateMonth
                    database.child(userEmailValue).child("Accounting")
                        .updateChildren(userAccounting)
                    //profile Asset
                    fillProfileAsset(userAsset, userEmailValue)
                }
            }
        } else {
            Toast.makeText(requireContext(), "Price must not be empty", Toast.LENGTH_LONG).show()
        }
    }

    private fun fillProfileAsset(asset: String, emailName: String) {
        val database = FirebaseDatabase.getInstance().reference
        binding.filltype.setText("")
        binding.fillmoney.setText("")
        if (IncomeOrExpense == "Income") {
            val addasset = (FillPrice.toFloat() + asset.toFloat()).toString()
            database.child(emailName).child("Profile").child("Asset")
                .setValue(addasset)
        } else if (IncomeOrExpense == "Expense") {
            val subasset = (asset.toFloat() - FillPrice.toFloat()).toString()
            database.child(emailName).child("Profile").child("Asset")
                .setValue(subasset)
        }
    }

    private fun dataSelect() {
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
                    val accounting =
                        useremail["Accounting"] as HashMap<String, HashMap<String, ArrayList<HashMap<*, *>>>>
                    val sortedMonthKeyList = accounting.filter {
                        it.key != "test"
                    }.toSortedMap().values.toList()
                    StoreArray.clear()
                    for (i in sortedMonthKeyList.indices) {
                        val sortedDateKeyList = sortedMonthKeyList[i].toSortedMap().values.toList()
                        for (j in sortedDateKeyList.indices) {
                            sortedDateKeyList[j].map {
                                StoreArray.add(it)
                            }
                        }
                    }
                } catch (e: Exception) {
                    StoreArray = arrayListOf()

                }
                activity?.runOnUiThread {
                    binding.recyclerview2.apply {
                        val myAdapter = homeadapter(this@HomeFragment)
                        adapter = myAdapter
                        val manager = LinearLayoutManager(requireContext())
                        manager.orientation = LinearLayoutManager.VERTICAL
                        layoutManager = manager
                        manager.stackFromEnd = true
                        myAdapter.dataList = StoreArray
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        database.addValueEventListener(dataListener)
    }

    private fun firstLogin() {
        auth = FirebaseAuth.getInstance()
        var userEmail = auth.currentUser?.email.toString()
        val findLittleMouseAt = userEmail.indexOf("@")
        val userEmailValue = userEmail.substring(0, findLittleMouseAt)
        var database = FirebaseDatabase.getInstance().reference
        database.get().addOnSuccessListener {
            if (it.value == null) {
                startActivity(Intent(requireContext(), ProfileActivity::class.java))
            } else {
                val databaseHashMap = it.value as java.util.HashMap<*, *>
                if (databaseHashMap[userEmailValue] == null) {
                    startActivity(Intent(requireContext(), ProfileActivity::class.java))
                } else {
                    dataSelect()
                }
            }
        }
    }

    override fun onItemClick(position: Int) {

    }
}