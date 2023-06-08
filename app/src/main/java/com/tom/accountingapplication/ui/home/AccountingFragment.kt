package com.tom.accountingapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tom.accountingapplication.databinding.FragmentHomeBinding


class AccountingFragment : Fragment() {
    private val viewModel: AccountingViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.btnExpense.setOnClickListener {
            viewModel.onExpenseClick()
        }
        binding.btnIncome.setOnClickListener {
            viewModel.onIncomeClick()
        }
        val itemAdapter = AccountingItemAdapter(
            onItemClick = { UpdateItem->
                viewModel.onItemClick(UpdateItem)
            }
        )
        binding.recyclerItem.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
            this.adapter = itemAdapter
        }
        viewModel.displayAccounting.observe(this){
            itemAdapter.seq = it.seq
            itemAdapter.itemList = it.itemList

        }
        return root
    }


//    private fun datePicker() {
//        val c = Calendar.getInstance()
//        val year = c.get(Calendar.YEAR)
//        val month = c.get(Calendar.MONTH)
//        val day = c.get(Calendar.DAY_OF_MONTH)
//        DatePickerDialog(requireContext(), { _, year, month, day ->
//            run {
//                val format = setDateFormat(year, month, day)
//                binding.date.text = format
//            }
//        }, year, month, day).show()
//    }

//    private fun setDateFormat(year: Int, month: Int, day: Int): String {
//        val fixMonth = if (month < 9) {
//            "0${month + 1}"
//        } else {
//            "${month + 1}"
//        }
//        val fixDay = if (day < 10) {
//            "0$day"
//        } else {
//            "$day"
//        }
//        return "$year/$fixMonth/$fixDay"
//    }

//    private fun upDateData() {
//        TypeRemark = binding.filltype.text.toString()
//        FillPrice = binding.fillmoney.text.toString()
//        val chosenDate = binding.date.text.toString().replace("/", "")
//        val chosenDateMonth = binding.date.text.substring(0, 7).replace("/", "")
//
//        auth = FirebaseAuth.getInstance()
//        val userEmail = auth.currentUser?.email.toString()
//        val findLittleMouseAt = userEmail.indexOf("@")
//        val userEmailValue = userEmail.substring(0, findLittleMouseAt)
//        val database = FirebaseDatabase.getInstance().reference
//        if (FillPrice.isNotEmpty()) {
//            val upload = Accounting(
//                IncomeOrExpense,
//                TypeChoice,
//                binding.date.text.toString(),
//                TypeRemark,
//                FillPrice
//            ).toDict()
//            database.child(userEmailValue).get().addOnSuccessListener {
//                val databaseEmailValue = it.value as java.util.HashMap<String, Any>
//                //profile
//                val userProfile = databaseEmailValue["Profile"] as HashMap<*, *>
//                val userAsset = userProfile["Asset"].toString()
//                //accounting
//                val userAccounting =
//                    databaseEmailValue["Accounting"] as java.util.HashMap<String, Any>
//                if (userAccounting[chosenDateMonth] != null) {    //有無月份
//                    val recordingDateMonth = userAccounting[chosenDateMonth] as HashMap<String, Any>
//                    if (recordingDateMonth[chosenDate] != null) {  //有無日期
//                        val recordingDate =
//                            recordingDateMonth[chosenDate] as ArrayList<Map<String, *>>
//                        recordingDate.add(upload)
//                        recordingDateMonth[chosenDate] = recordingDate
//                        userAccounting[chosenDateMonth] = recordingDateMonth
//                        database.child(userEmailValue).child("Accounting")
//                            .updateChildren(userAccounting)
//                        //profile Asset
//                        fillProfileAsset(userAsset, userEmailValue)
//                    } else {
//                        recordingDateMonth[chosenDate] = arrayListOf(upload)
//                        userAccounting[chosenDateMonth] = recordingDateMonth
//                        database.child(userEmailValue).child("Accounting")
//                            .updateChildren(userAccounting)
//                        //profile Asset
//                        fillProfileAsset(userAsset, userEmailValue)
//                    }
//                } else {
//                    val recordingDateMonth = hashMapOf<String, Any>()
//                    recordingDateMonth[chosenDate] = arrayListOf(upload)
//                    userAccounting[chosenDateMonth] = recordingDateMonth
//                    database.child(userEmailValue).child("Accounting")
//                        .updateChildren(userAccounting)
//                    //profile Asset
//                    fillProfileAsset(userAsset, userEmailValue)
//                }
//            }
//        } else {
//            Toast.makeText(requireContext(), "Price must not be empty", Toast.LENGTH_LONG).show()
//        }
//    }
//    private fun dataSelect() {
//        auth = FirebaseAuth.getInstance()
//        val userEmail = auth.currentUser?.email.toString()
//        val findLittleMouseAt = userEmail.indexOf("@")
//        val userEmailValue = userEmail.substring(0, findLittleMouseAt)
//        val database = FirebaseDatabase.getInstance().reference
//
//        val dataListener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val root = dataSnapshot.value as HashMap<*, *>
//                val useremail = root[userEmailValue] as HashMap<*, *>
//                try {
//                    val accounting =
//                        useremail["Accounting"] as HashMap<String, HashMap<String, ArrayList<HashMap<*, *>>>>
//                    val sortedMonthKeyList = accounting.filter {
//                        it.key != "test"
//                    }.toSortedMap().values.toList()
//                    StoreArray.clear()
//                    for (i in sortedMonthKeyList.indices) {
//                        val sortedDateKeyList = sortedMonthKeyList[i].toSortedMap().values.toList()
//                        for (j in sortedDateKeyList.indices) {
//                            sortedDateKeyList[j].map {
//                                StoreArray.add(it)
//                            }
//                        }
//                    }
//                } catch (e: Exception) {
//                    StoreArray = arrayListOf()
//
//                }
//                activity?.runOnUiThread {
//                    binding.recyclerview2.apply {
//                        val myAdapter = homeadapter(this@HomeFragment)
//                        adapter = myAdapter
//                        val manager = LinearLayoutManager(requireContext())
//                        manager.orientation = LinearLayoutManager.VERTICAL
//                        layoutManager = manager
//                        manager.stackFromEnd = true
//                        myAdapter.dataList = StoreArray
//                    }
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//            }
//        }
//        database.addValueEventListener(dataListener)
//    }

//    private fun firstLogin() {
//        auth = FirebaseAuth.getInstance()
//        var userEmail = auth.currentUser?.email.toString()
//        val findLittleMouseAt = userEmail.indexOf("@")
//        val userEmailValue = userEmail.substring(0, findLittleMouseAt)
//        var database = FirebaseDatabase.getInstance().reference
//        database.get().addOnSuccessListener {
//            if (it.value == null) {
//                startActivity(Intent(requireContext(), ProfileActivity::class.java))
//            } else {
//                val databaseHashMap = it.value as java.util.HashMap<*, *>
//                if (databaseHashMap[userEmailValue] == null) {
//                    startActivity(Intent(requireContext(), ProfileActivity::class.java))
//                } else {
//                    dataSelect()
//                }
//            }
//        }
//    }

//    override fun onItemClick(position: Int) {
//
//    }
}