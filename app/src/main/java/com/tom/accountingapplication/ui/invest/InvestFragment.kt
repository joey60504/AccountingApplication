package com.tom.accountingapplication.ui.invest

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tom.accountingapplication.R
import com.tom.accountingapplication.databinding.FragmentInvestBinding
import com.tom.accountingapplication.invest
import com.tom.accountingapplication.ui.home.homeadapter
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class InvestFragment : Fragment(), InvestAdapter.OnItemClick {

    private var _binding: FragmentInvestBinding? = null
    private val binding get() = _binding!!

    lateinit var auth: FirebaseAuth
    lateinit var nowdate: String
    private var recordingKind = "Virtual Currency"
    private var recordingBuyOrSell = "Buy"
    var storeVirtualCurrencyArray = arrayListOf<HashMap<*, *>>()
    var storeStockArray = arrayListOf<HashMap<*, *>>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInvestBinding.inflate(inflater, container, false)
        val root: View = binding.root
        settingInvestKind()
        dataSelect()

        val format = SimpleDateFormat("yyyy/MM/dd")
        nowdate = format.format(Date())
        binding.button4.text = nowdate
        binding.button4.setOnClickListener {
            datePicker()
        }
        binding.button3.setOnClickListener {
            upload()
        }
        return root
    }

    private fun datePicker() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(requireContext(), { _, year, month, day ->
            run {
                val format = setDateFormat(year, month, day)
                binding.button4.text = format
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

    private fun settingInvestKind() {
        binding.textView72.setOnClickListener {
            binding.textView72.setBackgroundResource(R.drawable.beige_rectangle)
            binding.textView73.setBackgroundResource(R.drawable.color_rectangle)
            recordingKind = "Virtual Currency"
        }
        binding.textView73.setOnClickListener {
            binding.textView73.setBackgroundResource(R.drawable.beige_rectangle)
            binding.textView72.setBackgroundResource(R.drawable.color_rectangle)
            recordingKind = "Stock"
        }
        binding.textView74.setOnClickListener {
            binding.textView74.setBackgroundResource(R.drawable.beige_rectangle)
            binding.textView75.setBackgroundResource(R.drawable.color_rectangle)
            recordingBuyOrSell = "Buy"
        }
        binding.textView75.setOnClickListener {
            binding.textView75.setBackgroundResource(R.drawable.beige_rectangle)
            binding.textView74.setBackgroundResource(R.drawable.color_rectangle)
            recordingBuyOrSell = "Sell"
        }
    }

    private fun upload() {
        val price = binding.editTextTextPersonName4.text.toString()
        val remark = binding.editTextTextPersonName5.text.toString()

        auth = FirebaseAuth.getInstance()
        val userEmail = auth.currentUser?.email.toString()
        val findLittleMouseAt = userEmail.indexOf("@")
        val userEmailValue = userEmail.substring(0, findLittleMouseAt)
        val database = FirebaseDatabase.getInstance().reference

        if (recordingKind.isNotEmpty() && recordingBuyOrSell.isNotEmpty() && price.isNotEmpty() && remark.isNotEmpty()) {
            val invest = invest(
                recordingKind, recordingBuyOrSell, binding.button4.text.toString(), remark, price
            ).to_dict()
            database.child(userEmailValue).get().addOnSuccessListener {
                val databaseEmailValue = it.value as java.util.HashMap<String, Any>
                //profile
                val userProfile = databaseEmailValue["Profile"] as HashMap<*, *>
                val userAsset = userProfile["Asset"].toString()
                //Invest
                if (databaseEmailValue["Invest"] != null) {
                    val investHashMap =
                        databaseEmailValue["Invest"] as java.util.HashMap<String, Any>
                    if (investHashMap[recordingKind] != null) {
                        val recordingKindValue =
                            investHashMap[recordingKind] as ArrayList<Map<String, *>>
                        recordingKindValue.add(invest)
                        investHashMap[recordingKind] = recordingKindValue
                        database.child(userEmailValue).child("Invest").updateChildren(investHashMap)
                        fillProfileAsset(userAsset, userEmailValue, recordingBuyOrSell, price)
                    } else {
                        val recordingKindValue = arrayListOf(invest)
                        investHashMap[recordingKind] = recordingKindValue
                        database.child(userEmailValue).child("Invest").updateChildren(investHashMap)
                        fillProfileAsset(userAsset, userEmailValue, recordingBuyOrSell, price)
                    }
                } else {
                    val investHashMap = hashMapOf<String, Any>()
                    investHashMap[recordingKind] = arrayListOf(invest)
                    databaseEmailValue["Invest"] = investHashMap
                    database.child(userEmailValue).updateChildren(databaseEmailValue)
                    fillProfileAsset(userAsset, userEmailValue, recordingBuyOrSell, price)
                }
            }
        } else {
            Toast.makeText(requireContext(), "Data must not be empty", Toast.LENGTH_LONG).show()
        }
    }

    private fun fillProfileAsset(
        asset: String,
        emailName: String,
        recordingBuyOrSell: String,
        price: String
    ) {
        val database = FirebaseDatabase.getInstance().reference
        binding.editTextTextPersonName4.setText("")
        binding.editTextTextPersonName5.setText("")
        if (recordingBuyOrSell == "Sell") {
            val addAsset = (price.toFloat() + asset.toFloat()).toString()
            database.child(emailName).child("Profile").child("Asset")
                .setValue(addAsset)
        } else if (recordingBuyOrSell == "Buy") {
            val subAsset = (asset.toFloat() - price.toFloat()).toString()
            database.child(emailName).child("Profile").child("Asset")
                .setValue(subAsset)
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
                val userEmail = root[userEmailValue] as HashMap<*, *>
                if (userEmail["Invest"] != null) {
                    val investHashMap =
                        userEmail["Invest"] as HashMap<String, ArrayList<HashMap<*, *>>>
                    storeVirtualCurrencyArray = if (investHashMap["Virtual Currency"] != null) {
                        investHashMap["Virtual Currency"]!!
                    } else {
                        arrayListOf()
                    }
                    storeStockArray = if (investHashMap["Stock"] != null) {
                        investHashMap["Stock"]!!
                    } else {
                        arrayListOf()
                    }
                    activity?.runOnUiThread {
                        binding.recyclerInvestVirtualCurrency.apply {
                            val myAdapter = InvestAdapter(this@InvestFragment)
                            adapter = myAdapter
                            val manager = LinearLayoutManager(requireContext())
                            manager.orientation = LinearLayoutManager.VERTICAL
                            layoutManager = manager
                            manager.stackFromEnd = true
                            myAdapter.dataList = storeVirtualCurrencyArray
                        }
                        binding.recyclerInvestStock.apply {
                            val myAdapter = InvestAdapter(this@InvestFragment)
                            adapter = myAdapter
                            val manager = LinearLayoutManager(requireContext())
                            manager.orientation = LinearLayoutManager.VERTICAL
                            layoutManager = manager
                            manager.stackFromEnd = true
                            myAdapter.dataList = storeStockArray
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        database.addValueEventListener(dataListener)
    }

    override fun onItemClick(position: Int) {

    }
}