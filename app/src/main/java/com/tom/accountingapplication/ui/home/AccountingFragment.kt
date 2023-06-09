package com.tom.accountingapplication.ui.home

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tom.accountingapplication.R
import com.tom.accountingapplication.databinding.FragmentHomeBinding


class AccountingFragment : Fragment() {
    private val viewModel: AccountingViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.M)
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
        binding.imgDateLeft.setOnClickListener {
            viewModel.onDateLeftClick()
        }
        binding.imgDateRight.setOnClickListener {
            viewModel.onDateRightClick()
        }
        binding.txtSubmit.setOnClickListener {
            viewModel.onSubmitClick(
                binding.edittextRemark.text.toString(),
                binding.edittextPrice.text.toString().toInt()
            )
        }
        binding.txtDate.setOnClickListener {
            val bottomSheetFragment = AccountingBottomSheetCalendarFragment(
                onItemClick = { year, month, day ->
                    viewModel.onDateSelect(year, month, day)
                }
            )
            bottomSheetFragment.show(
                requireActivity().supportFragmentManager,
                bottomSheetFragment.tag
            )
        }
        binding.txtTag.setOnClickListener {
            val bottomSheetFragment = AccountingBottomSheetTagFragment(
                onItemClick = { tag ->
                    viewModel.onTagClick(tag)
                }
            )
            bottomSheetFragment.show(
                requireActivity().supportFragmentManager,
                bottomSheetFragment.tag
            )
        }

        val itemAdapter = AccountingItemAdapter(
            onItemClick = { updateItem ->
                viewModel.onItemClick(updateItem)
            }
        )

        viewModel.showPairMessage.observe(this) {
            AlertDialog.Builder(requireContext())
                .setCancelable(false)
                .setTitle(it.first)
                .setMessage(it.second)
                .setPositiveButton("確定", null).show()
        }

        viewModel.displayItemSelect.observe(this) {
            itemAdapter.seq = it.seq
            if (it.seq == 1) {
                binding.btnExpense.setBackgroundResource(R.drawable.corners_blue)
                binding.btnExpense.setTextColor(getColor(requireContext(), R.color.white))
                binding.btnIncome.setBackgroundColor(0)
                binding.btnIncome.setTextColor(getColor(requireContext(), R.color.greyish_brown))
                binding.imgChoiceIcon.setBackgroundResource(it.itemSelectedDrawable)
                itemAdapter.itemList = it.itemExpenseList
            } else {
                binding.btnExpense.setBackgroundColor(0)
                binding.btnExpense.setTextColor(getColor(requireContext(), R.color.greyish_brown))
                binding.btnIncome.setBackgroundResource(R.drawable.corners_pink)
                binding.btnIncome.setTextColor(getColor(requireContext(), R.color.white))
                binding.imgChoiceIcon.setBackgroundResource(it.itemSelectedDrawable)
                itemAdapter.itemList = it.itemIncomeList
            }
            binding.recyclerItem.apply {
                setHasFixedSize(true)
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                this.adapter = itemAdapter
            }
        }
        viewModel.displayDate.observe(this) {
            binding.txtDate.text = it
        }
        viewModel.displayTag.observe(this){
            binding.txtTag.text = it.selectedTag
        }
        return root
    }


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