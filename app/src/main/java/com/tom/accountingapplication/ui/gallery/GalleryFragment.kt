package com.tom.accountingapplication.ui.gallery

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.tom.accountingapplication.accounting
import com.tom.accountingapplication.databinding.FragmentGalleryBinding
import org.eazegraph.lib.models.PieModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class GalleryFragment : Fragment(),histortyadapter.OnItemClick{
    private var _binding : FragmentGalleryBinding?= null
    private val binding get() = _binding!!

    lateinit var auth : FirebaseAuth

    lateinit var nowdate:String
    var StoreArray= arrayListOf<HashMap<*,*>>()

    override fun onCreateView (
        inflater:LayoutInflater,
        container:ViewGroup?,
        savedInstanceState:Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater,container,false)
        val root:View = binding.root
        dataselect()
        dataCalculation()
        setdata(9f,9f,9f,9f,9f,9f,9f,9f,9f,9f,5f,5f)

        return root
    }
    fun setdata(Breakfast:Float,Lunch:Float,Dinner:Float,Transportation:Float,
                Drink:Float,Dessert:Float,Social:Float,Shopping:Float,
                Hospital:Float,Game:Float,Gift:Float,Other:Float){
        binding.piechart.addPieSlice(PieModel("Breakfast", Breakfast,Color.parseColor("#FFA726")))
        binding.piechart.addPieSlice(PieModel("Lunch",Lunch,Color.parseColor("#FFFFFF")))
        binding.piechart.addPieSlice(PieModel("Dinner", Dinner,Color.parseColor("#EF5350")))
        binding.piechart.addPieSlice(PieModel("Transportation",Transportation,Color.parseColor("#29B6F6")))
        binding.piechart.addPieSlice(PieModel("Drink", Drink,Color.parseColor("#FFA726")))
        binding.piechart.addPieSlice(PieModel("Dessert",Dessert,Color.parseColor("#FFFFFF")))
        binding.piechart.addPieSlice(PieModel("Social", Social,Color.parseColor("#EF5350")))
        binding.piechart.addPieSlice(PieModel("Shopping",Shopping,Color.parseColor("#29B6F6")))
        binding.piechart.addPieSlice(PieModel("Hospital", Hospital,Color.parseColor("#FFA726")))
        binding.piechart.addPieSlice(PieModel("Game",Game,Color.parseColor("#FFFFFF")))
        binding.piechart.addPieSlice(PieModel("Gift", Gift,Color.parseColor("#EF5350")))
        binding.piechart.addPieSlice(PieModel("Other",Other,Color.parseColor("#29B6F6")))
        binding.piechart.startAnimation();
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
                    val AccountingKeysList = accounting.keys.filter {
                        it != "test"
                    }.toList()
                    StoreArray.clear()
                    for (i in AccountingKeysList.indices) {
                        val date = accounting[AccountingKeysList[i]] as ArrayList<HashMap<*, *>>
                        StoreArray.addAll(date)
                    }
                }
                catch (e: Exception){
                    StoreArray= arrayListOf()
                }
                activity?.runOnUiThread {
                    binding.recyclerview1.apply {
                        val myAdapter = histortyadapter(this@GalleryFragment)
                        adapter = myAdapter
                        val manager = LinearLayoutManager(requireContext())
                        manager.orientation = LinearLayoutManager.VERTICAL
                        layoutManager = manager
                        manager.stackFromEnd = false
                        myAdapter.dataList = StoreArray
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
    fun dataCalculation(){
        auth = FirebaseAuth.getInstance()
        var email = auth.currentUser?.email.toString()
        val LittleMouseAt=email.indexOf("@")
        val emailname=email.substring(0,LittleMouseAt)
        var database = FirebaseDatabase.getInstance().reference

        val format = SimpleDateFormat("yyyy/MM/dd")
        nowdate = format.format(Date())
        val FixNowDate=nowdate.replace("/","")

        val dataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val root=dataSnapshot.value as HashMap<*,*>
                val useremail=root[emailname] as HashMap<*,*>
                try {
                    val accounting = useremail["Accounting"] as HashMap<*,*>
                    val AccountingKeysList = accounting.keys.filter {
                        it != "test"
                    }.toList()
                    StoreArray.clear()
                    for (i in AccountingKeysList.indices) {
                        val date = accounting[AccountingKeysList[i]] as ArrayList<HashMap<*, *>>
                        StoreArray.addAll(date)
                    }
                }
                catch (e: Exception){
                    StoreArray= arrayListOf()
                }
                for(i in StoreArray.indices){
                    val data = StoreArray[i]
                    Log.d("kkk",data.toString())
//                    2022-03-25 17:48:24.739 5312-5312/com.tom.accountingapplication D/kkk: {TypeRemark=早餐吐司＋咖啡, IncomeOrExpense=Expense, TypeChoice=Other, FillPrice=90, Date=2022/03/26}
//                    2022-03-25 17:48:24.740 5312-5312/com.tom.accountingapplication D/kkk: {TypeRemark=, IncomeOrExpense=Expense, TypeChoice=Other, FillPrice=100, Date=2022/03/26}
//                    2022-03-25 17:48:24.740 5312-5312/com.tom.accountingapplication D/kkk: {TypeRemark=送禮, IncomeOrExpense=Expense, TypeChoice=Gift, FillPrice=100, Date=2022/03/26}
//                    2022-03-25 17:48:24.740 5312-5312/com.tom.accountingapplication D/kkk: {TypeRemark=飲料, IncomeOrExpense=Expense, TypeChoice=Drink, FillPrice=100, Date=2022/03/26}
//                    2022-03-25 17:48:24.740 5312-5312/com.tom.accountingapplication D/kkk: {TypeRemark=早餐吐司＋咖啡, IncomeOrExpense=Expense, TypeChoice=Other, FillPrice=90, Date=2022/03/25}
//                    2022-03-25 17:48:24.740 5312-5312/com.tom.accountingapplication D/kkk: {TypeRemark=, IncomeOrExpense=Expense, TypeChoice=Other, FillPrice=100, Date=2022/03/25}
//                    2022-03-25 17:48:24.740 5312-5312/com.tom.accountingapplication D/kkk: {TypeRemark=送禮, IncomeOrExpense=Expense, TypeChoice=Gift, FillPrice=100, Date=2022/03/25}
//                    2022-03-25 17:48:24.740 5312-5312/com.tom.accountingapplication D/kkk: {TypeRemark=飲料, IncomeOrExpense=Expense, TypeChoice=Drink, FillPrice=100, Date=2022/03/25}
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        database.addValueEventListener(dataListener)
    }
}