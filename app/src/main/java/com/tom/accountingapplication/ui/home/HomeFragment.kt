package com.tom.accountingapplication.ui.home

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
import com.tom.accountingapplication.accounting
import com.tom.accountingapplication.databinding.FragmentHomeBinding
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import com.tom.accountingapplication.R;
import java.util.EnumSet.range


class HomeFragment : Fragment(),homeadapter.OnItemClick {
    private var _binding : FragmentHomeBinding ?= null
    private val binding get() = _binding!!

    lateinit var auth : FirebaseAuth

    var IncomeOrExpense = "Expense"
    var TypeChoice = "Other"
    lateinit var FillPrice : String
    lateinit var TypeRemark:String
    lateinit var nowdate:String

    var StoreArray= arrayListOf<HashMap<*,*>>()
    lateinit var imagebtnlist:ArrayList<ImageButton>

    override fun onCreateView(inflater:LayoutInflater, container:ViewGroup?, savedInstanceState:Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        val root : View = binding.root

        val format = SimpleDateFormat("yyyy/MM/dd")
        nowdate = format.format(Date())
        binding.date.text = nowdate

        binding.income.setBackgroundColor(Color.parseColor("#F5F5DC"))
        binding.expense.setBackgroundColor(Color.parseColor("#907dac"));

        setimagebtnlist()
        firstlogin()
        setonclick()
        return root
    }
    fun setimagebtnlist(){
        imagebtnlist= arrayListOf(
            binding.imageEgg,binding.imageLunch,binding.imageDinner,binding.imageBus,
            binding.imageDrink,binding.imageCake,binding.imagePeople,binding.imageBag,
            binding.imageHospital,binding.imageGame,binding.imageIncome,binding.imageOther)
    }
    fun setonclick(){
        binding.imageEgg.setOnClickListener {
            TypeChoice="Breakfast"
            binding.imageEgg.setBackgroundResource(R.drawable.beige_rectangle)
            imagebtnlist.remove(binding.imageEgg)
            for (i in imagebtnlist.indices){
                imagebtnlist[i].setBackgroundResource(R.drawable.color_rectangle)
            }
            setimagebtnlist()
        }
        binding.imageLunch.setOnClickListener {
            TypeChoice="Lunch"
            binding.imageLunch.setBackgroundResource(R.drawable.beige_rectangle)
            imagebtnlist.remove(binding.imageLunch)
            for (i in imagebtnlist.indices){
                imagebtnlist[i].setBackgroundResource(R.drawable.color_rectangle)
            }
            setimagebtnlist()
        }
        binding.imageDinner.setOnClickListener {
            TypeChoice="Dinner"
            binding.imageDinner.setBackgroundResource(R.drawable.beige_rectangle)
            imagebtnlist.remove(binding.imageDinner)
            for (i in imagebtnlist.indices){
                imagebtnlist[i].setBackgroundResource(R.drawable.color_rectangle)
            }
            setimagebtnlist()
        }
        binding.imageBus.setOnClickListener {
            TypeChoice="Transportation"
            binding.imageBus.setBackgroundResource(R.drawable.beige_rectangle)
            imagebtnlist.remove(binding.imageBus)
            for (i in imagebtnlist.indices){
                imagebtnlist[i].setBackgroundResource(R.drawable.color_rectangle)
            }
            setimagebtnlist()
        }
        binding.imageDrink.setOnClickListener {
            TypeChoice="Drink"
            binding.imageDrink.setBackgroundResource(R.drawable.beige_rectangle)
            imagebtnlist.remove(binding.imageDrink)
            for (i in imagebtnlist.indices){
                imagebtnlist[i].setBackgroundResource(R.drawable.color_rectangle)
            }
            setimagebtnlist()
        }
        binding.imageCake.setOnClickListener {
            TypeChoice="Dessert"
            binding.imageCake.setBackgroundResource(R.drawable.beige_rectangle)
            imagebtnlist.remove(binding.imageCake)
            for (i in imagebtnlist.indices){
                imagebtnlist[i].setBackgroundResource(R.drawable.color_rectangle)
            }
            setimagebtnlist()
        }
        binding.imagePeople.setOnClickListener {
            TypeChoice="Social"
            binding.imagePeople.setBackgroundResource(R.drawable.beige_rectangle)
            imagebtnlist.remove(binding.imagePeople)
            for (i in imagebtnlist.indices){
                imagebtnlist[i].setBackgroundResource(R.drawable.color_rectangle)
            }
            setimagebtnlist()
        }
        binding.imageBag.setOnClickListener {
            TypeChoice="Shopping"
            binding.imageBag.setBackgroundResource(R.drawable.beige_rectangle)
            imagebtnlist.remove(binding.imageBag)
            for (i in imagebtnlist.indices){
                imagebtnlist[i].setBackgroundResource(R.drawable.color_rectangle)
            }
            setimagebtnlist()
        }
        binding.imageHospital.setOnClickListener {
            TypeChoice="Hospital"
            binding.imageHospital.setBackgroundResource(R.drawable.beige_rectangle)
            imagebtnlist.remove(binding.imageHospital)
            for (i in imagebtnlist.indices){
                imagebtnlist[i].setBackgroundResource(R.drawable.color_rectangle)
            }
            setimagebtnlist()
        }
        binding.imageGame.setOnClickListener {
            TypeChoice="Game"
            binding.imageGame.setBackgroundResource(R.drawable.beige_rectangle)
            imagebtnlist.remove(binding.imageGame)
            for (i in imagebtnlist.indices){
                imagebtnlist[i].setBackgroundResource(R.drawable.color_rectangle)
            }
            setimagebtnlist()
        }
        binding.imageIncome.setOnClickListener {
            TypeChoice="Income"
            binding.imageIncome.setBackgroundResource(R.drawable.beige_rectangle)
            imagebtnlist.remove(binding.imageIncome)
            for (i in imagebtnlist.indices){
                imagebtnlist[i].setBackgroundResource(R.drawable.color_rectangle)
            }
            setimagebtnlist()
        }
        binding.imageOther.setOnClickListener {
            TypeChoice="Other"
            binding.imageOther.setBackgroundResource(R.drawable.beige_rectangle)
            imagebtnlist.remove(binding.imageOther)
            for (i in imagebtnlist.indices){
                imagebtnlist[i].setBackgroundResource(R.drawable.color_rectangle)
            }
            setimagebtnlist()
        }
        binding.income.setOnClickListener{
            IncomeOrExpense = "Income"
            binding.income.setBackgroundColor(Color.parseColor("#907dac"));
            binding.expense.setBackgroundColor(Color.parseColor("#F5F5DC"));
            TypeChoice="Income"
            binding.imageIncome.setBackgroundResource(R.drawable.beige_rectangle)
            imagebtnlist.remove(binding.imageIncome)
            for (i in imagebtnlist.indices){
                imagebtnlist[i].setBackgroundResource(R.drawable.color_rectangle)
            }
            setimagebtnlist()
        }
        binding.expense.setOnClickListener{
            IncomeOrExpense = "Expense"
            binding.income.setBackgroundColor(Color.parseColor("#F5F5DC"));
            binding.expense.setBackgroundColor(Color.parseColor("#907dac"));
        }
        binding.date.setOnClickListener {
            datePicker()
        }
        binding.upload.setOnClickListener{
            updatedata()
        }
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
        TypeRemark= binding.filltype.text.toString()
        FillPrice = binding.fillmoney.text.toString()
        val FixNowDate=nowdate.replace("/","")

        auth = FirebaseAuth.getInstance()
        var email = auth.currentUser?.email.toString()
        val LittleMouseAt=email.indexOf("@")
        val emailname=email.substring(0,LittleMouseAt)
        var database = FirebaseDatabase.getInstance().reference

        if ( FillPrice.isNotEmpty()) {
            val upload = accounting(IncomeOrExpense,TypeChoice,nowdate,TypeRemark,FillPrice).to_dict()
            database.child(emailname).get().addOnSuccessListener {
                val emailvalue = it.value as java.util.HashMap<String, Any>
                val accounting = emailvalue["Accounting"] as java.util.HashMap<String, Any>
                if (accounting[FixNowDate] != null) {
                    val NowRecording = accounting[FixNowDate] as ArrayList<Map<String, *>>
                    NowRecording.add(upload)
                    accounting.put(FixNowDate, NowRecording)
                    database.child(emailname).child("Accounting").updateChildren(accounting)
                    binding.filltype.setText("")
                    binding.fillmoney.setText("")
                } else {
                    accounting.put(FixNowDate, arrayListOf<Map<String, *>>(upload))
                    database.child(emailname).child("Accounting").updateChildren(accounting)
                    binding.filltype.setText("")
                    binding.fillmoney.setText("")
                }
            }
        }
        else{
            Toast.makeText(requireContext(), "Price must not be empty", Toast.LENGTH_LONG).show()
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
                try {
                    val accounting = useremail["Accounting"] as HashMap<*,*>
                    val keysarray = accounting.keys.filter {
                        it != "test"
                    }.toList()
                    val AccountingKeysList = mutableListOf("")
                    for (i in keysarray.indices){
                        AccountingKeysList.add(keysarray[i] as String)
                    }
                    AccountingKeysList.removeAt(0)
                    AccountingKeysList.sort()
                    StoreArray.clear()
                    for (i in AccountingKeysList.indices) {
                        val date = accounting[AccountingKeysList[i]] as ArrayList<HashMap<*, *>>
                        StoreArray.addAll(date)
                    }
                }
                catch (e:Exception){
                    StoreArray= arrayListOf()
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
                }
                else{
                    dataselect()
                }
            }
        }
    }
    override fun onItemClick(position: Int) {

    }
}