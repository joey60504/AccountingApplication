package com.tom.accountingapplication.ui.gallery

import android.R
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tom.accountingapplication.databinding.FragmentGalleryBinding
import org.eazegraph.lib.models.PieModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class GalleryFragment : Fragment(),histortyadapter.OnItemClick{
    private var _binding : FragmentGalleryBinding?= null
    private val binding get() = _binding!!

    lateinit var auth : FirebaseAuth

    var dayslist= arrayListOf<Int>()
    var StoreArray= arrayListOf<HashMap<*,*>>()
    var chosentime:String="All"
    var chosenkind:String="All"
    var deletearray= arrayListOf<HashMap<*,*>>()
    var Finalarray= arrayListOf<HashMap<*,*>>()

    override fun onCreateView (
        inflater:LayoutInflater,
        container:ViewGroup?,
        savedInstanceState:Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater,container,false)
        val root:View = binding.root
        dataselectAll()
        dataselect()
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

    fun dataselectAll(){
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
    override fun onItemClick(position: Int) {

    }
    fun dataselect(){
        val time = arrayListOf("All","One_Week", "One_Month", "Six_Month", "One_Year")
        val adapter1 = ArrayAdapter(requireContext(), R.layout.simple_spinner_dropdown_item,time)
        binding.spinner.adapter = adapter1
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent:AdapterView<*>, view:View, pos:Int, id:Long){
                chosentime = time[pos]
            }
            override fun onNothingSelected(parent:AdapterView<*>){

            }
        }
        val kind = arrayListOf("All","Breakfast","Lunch","Dinner","Transportation", "Drink","Dessert","Social","Shopping", "Hospital","Game","Income","Other","Without Income")
        val adapter2 = ArrayAdapter(requireContext(), R.layout.simple_spinner_dropdown_item,kind)
        binding.spinner2.adapter = adapter2
        binding.spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent:AdapterView<*>, view:View, pos:Int, id:Long){
                chosenkind = kind[pos]
            }
            override fun onNothingSelected(parent:AdapterView<*>){

            }
        }
    }
    fun dataselectTime(){
        Log.d("kkk",StoreArray.toString())
        var dateFormat = SimpleDateFormat("yyyy/MM/dd")
        val nowdate = dateFormat.format(Date())
        var startTime: Date = dateFormat.parse(nowdate)
        dayslist.clear()
        for (i in StoreArray.indices){
            val StoreArrayhashmap = StoreArray[i]
            val Timedate = StoreArrayhashmap["Date"]
            var endTime: Date = dateFormat.parse(Timedate.toString())
            val diff = endTime.time - startTime.time
            var days = (diff / (1000 * 60 * 60 * 24)).toInt()
            dayslist.add(days)
        }//get相差天數陣列
        when (chosentime){
            "All"->{
            }
            "One_Week"->{
                for(i in dayslist.indices){
                    if (dayslist[i] <= -7) {
                        deletearray.add(StoreArray[i])
                    }
                }
                Finalarray.removeAll(deletearray)
                deletearray.clear()
            }
            "One_Month"->{
                for(i in dayslist.indices){
                    if (dayslist[i] <= -31) {
                        deletearray.add(StoreArray[i])
                    }
                }
                Finalarray.removeAll(deletearray)
                deletearray.clear()
            }
            "Six_Month"->{
                for(i in dayslist.indices){
                    if (dayslist[i] <= -186) {
                        deletearray.add(StoreArray[i])
                    }
                }
                Finalarray.removeAll(deletearray)
                deletearray.clear()
            }
            "One_Year"->{
                for(i in dayslist.indices){
                    if (dayslist[i] <= -365) {
                        deletearray.add(StoreArray[i])
                    }
                }
                Finalarray.removeAll(deletearray)
                deletearray.clear()
            }
        }
    }
}