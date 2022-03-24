package com.tom.accountingapplication.ui.home

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.tom.accountingapplication.ProfileActivity
import com.tom.accountingapplication.accounting
import com.tom.accountingapplication.databinding.FragmentHomeBinding
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class HomeFragment : Fragment(),homeadapter.OnItemClick {
    private var _binding : FragmentHomeBinding?= null
    private val binding get() = _binding!!

    lateinit var auth : FirebaseAuth
    private lateinit var firebaseAuth : FirebaseAuth

    var IncomeOrExpense = "Expense"
    lateinit var FillPrice : String
    lateinit var TypeChoice : String
    lateinit var nowdate:String

    lateinit var StoreArray:ArrayList<HashMap<*,*>>

    override fun onCreateView(inflater:LayoutInflater, container:ViewGroup?, savedInstanceState:Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        val root : View = binding.root

        val format = SimpleDateFormat("yyyy/MM/dd")
        nowdate = format.format(Date())
        binding.date.text = nowdate

        firstlogin()
//        dataselect()

        binding.date.setOnClickListener {
            datePicker()
        }
        binding.income.setOnClickListener{
            IncomeOrExpense = "Income"
            Toast.makeText(requireContext(), "Money Income", Toast.LENGTH_SHORT).show()
        }
        binding.expense.setOnClickListener{
            IncomeOrExpense = "Expense"
            Toast.makeText(requireContext(), "Money Expense", Toast.LENGTH_SHORT).show()
        }
        binding.upload.setOnClickListener{
            updatedata()
        }
        binding.Accounting.setOnClickListener {
            startActivity(Intent(requireContext(), ProfileActivity::class.java))
        }
//        val lunch = arrayListOf("飲食", "娛樂", "治裝", "交通", "其他", "薪水")
//        val adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,lunch)
//        binding.kind.adapter = adapter
//        binding.kind.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent:AdapterView<*>, view:View, pos:Int, id:Long){
//
//            }
//            override fun onNothingSelected(parent:AdapterView<*>){
//
//            }
//        }

        return root
    }

    fun datePicker(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(requireContext(), {_,year,month,day ->
            run{
                val format = setDateFormat(year, month, day)
                binding.date.text = format
            }
        }, year, month, day).show()
    }

    private fun setDateFormat(year:Int, month:Int, day:Int) : String{
        return "$year-${month + 1}-$day"
    }

    fun updatedata(){
        TypeChoice = binding.filltype.text.toString()
        FillPrice = binding.fillmoney.text.toString()
        val FixNowDate=nowdate.replace("/","")

        auth = FirebaseAuth.getInstance()
        var email = auth.currentUser?.email.toString()
        val LittleMouseAt=email.indexOf("@")
        val emailname=email.substring(0,LittleMouseAt)
        var database = FirebaseDatabase.getInstance().reference

        if (TypeChoice.isNotEmpty() && IncomeOrExpense.isNotEmpty() && FillPrice.isNotEmpty()) {
            Log.d("kkk","456")
            val upload = accounting(IncomeOrExpense,nowdate,TypeChoice,FillPrice).to_dict()
            database.child(emailname).get().addOnSuccessListener {
                Log.d("kkk",emailname)
                val emailvalue = it.value as java.util.HashMap<String, Any>
                Log.d("kkk",emailvalue.toString())
                val accounting = emailvalue["Accounting"] as java.util.HashMap<String, Any>
                if (accounting[FixNowDate] != null) {
                    val NowRecording = accounting[FixNowDate] as ArrayList<Map<String, *>>
                    NowRecording.add(upload)
                    accounting.put(FixNowDate, NowRecording)
                    database.child(emailname).child("Accounting").updateChildren(accounting)
                }
                else{
                    accounting.put(FixNowDate, arrayListOf<Map<String,*>>(upload))
                    database.child(emailname).child("Accounting").updateChildren(accounting)
                }
            }
        }
        else{
            Toast.makeText(requireContext(), "Form must not be empty", Toast.LENGTH_LONG).show()
        }
    }
    fun dataselect(){

        auth = FirebaseAuth.getInstance()
        var email = auth.currentUser?.email.toString()
        val LittleMouseAt=email.indexOf("@")
        val emailname=email.substring(0,LittleMouseAt)
        var database = FirebaseDatabase.getInstance().reference

        val dataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val root=dataSnapshot.value as HashMap<*,*>
                val useremail=root[emailname] as HashMap<*,*>
                val accounting = useremail["Accounting"] as HashMap<*,*>
                val AccountingKeysList = accounting.keys.toList()
                for (i in AccountingKeysList.indices){
                    val date = accounting[i] as ArrayList<HashMap<*,*>>
                    StoreArray.addAll(date)
                }
                activity?.runOnUiThread {
                    binding.recyclerview2.apply {
                        val myAdapter = homeadapter(this@HomeFragment)
                        adapter = myAdapter
                        val manager = LinearLayoutManager(requireContext())
                        manager.orientation = LinearLayoutManager.VERTICAL
                        layoutManager = manager
                        myAdapter.dataList = StoreArray
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        database.addValueEventListener(dataListener)
    }

    fun firstlogin(){
        auth = FirebaseAuth.getInstance()
        var email = auth.currentUser?.email.toString()
        val LittleMouseAt=email.indexOf("@")
        val emailname=email.substring(0,LittleMouseAt)
        var database = FirebaseDatabase.getInstance().reference
        database.get().addOnSuccessListener {
            if(it.value == null) {
                startActivity(Intent(requireContext(), ProfileActivity::class.java))
            }
            else{
                val data0 = it.value as java.util.HashMap<*, *>
                if (data0[emailname] == null) {
                    startActivity(Intent(requireContext(), ProfileActivity::class.java))
                } else {
                    Toast.makeText(requireContext(), "Welcome back", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    override fun onItemClick(position: Int) {

    }
}