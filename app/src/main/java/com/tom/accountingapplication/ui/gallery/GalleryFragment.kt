package com.tom.accountingapplication.ui.gallery

import android.R
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.common.internal.AccountType
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tom.accountingapplication.databinding.FragmentGalleryBinding
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel
import java.io.PipedWriter
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class GalleryFragment : Fragment(),histortyadapter.OnItemClick{
    private var _binding : FragmentGalleryBinding?= null
    private val binding get() = _binding!!

    lateinit var auth : FirebaseAuth

    var chosentime:String="All"
    var chosenkind:String="All"
    var StoreArray= arrayListOf<HashMap<*,*>>()
    var dayslist= arrayListOf<Int>()
    var deletearray= arrayListOf<HashMap<*,*>>()
    var Typearray= arrayListOf<String>()
    var Typedeletearray = arrayListOf<HashMap<*,*>>()
    var CalculatetypeArray = arrayListOf<Float>()


    var FinalBreakfast:Float= 0f
    var FinalLunch:Float= 0f
    var FinalDinner:Float= 0f
    var FinalTransportation:Float= 0f
    var FinalDrink:Float= 0f
    var FinalDessert:Float= 0f
    var FinalSocial:Float= 0f
    var FinalShopping:Float= 0f
    var FinalHospital:Float= 0f
    var FinalGame:Float= 0f
    var FinalOther:Float= 0f

    override fun onCreateView (
        inflater:LayoutInflater,
        container:ViewGroup?,
        savedInstanceState:Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater,container,false)
        DataSelectAll()
        SpinnerDataSelect()
        return binding.root
    }
    fun setdata(Breakfast:Float,Lunch:Float,Dinner:Float,Transportation:Float,
                Drink:Float,Dessert:Float,Social:Float,Shopping:Float,
                Hospital:Float,Game:Float,Other:Float){
        val total = CalculatetypeArray.sum()
        binding.piechart.clearChart()
        binding.piechart.addPieSlice(PieModel("Breakfast", (Breakfast/total),Color.parseColor("#c5d0e2")))
        binding.piechart.addPieSlice(PieModel("Lunch",(Lunch/total),Color.parseColor("#f7f1bd")))
        binding.piechart.addPieSlice(PieModel("Dinner", (Dinner/total),Color.parseColor("#e2cece")))
        binding.piechart.addPieSlice(PieModel("Transportation",(Transportation/total),Color.parseColor("#bce9b2")))
        binding.piechart.addPieSlice(PieModel("Drink", (Drink/total),Color.parseColor("#d9d19b")))
        binding.piechart.addPieSlice(PieModel("Dessert",(Dessert/total),Color.parseColor("#a77979")))
        binding.piechart.addPieSlice(PieModel("Social", (Social/total),Color.parseColor("#99a4f5")))
        binding.piechart.addPieSlice(PieModel("Shopping",(Shopping/total),Color.parseColor("#d4a46f")))
        binding.piechart.addPieSlice(PieModel("Hospital", (Hospital/total),Color.parseColor("#e69575")))
        binding.piechart.addPieSlice(PieModel("Game",(Game/total),Color.parseColor("#ffcece")))
        binding.piechart.addPieSlice(PieModel("Other",(Other/total),Color.parseColor("#e6e1d7")))
        binding.piechart.startAnimation();
        val fixdata = DecimalFormat("00.00")
        val fixdata2 = DecimalFormat("00,000")
        binding.textView32.text = "${fixdata.format((Breakfast/total)*100.0)}%  $${fixdata2.format(Breakfast)}"
        binding.textView33.text = "${fixdata.format((Lunch/total)*100.0)}%  $${fixdata2.format(Lunch)}"
        binding.textView34.text = "${fixdata.format((Dinner/total)*100.0)}%  $${fixdata2.format(Dinner)}"
        binding.textView35.text = "${fixdata.format((Transportation/total)*100.0)}%  $${fixdata2.format(Transportation)}"
        binding.textView36.text = "${fixdata.format((Drink/total)*100.0)}%  $${fixdata2.format(Drink)}"
        binding.textView37.text = "${fixdata.format((Dessert/total)*100.0)}%  $${fixdata2.format(Dessert)}"
        binding.textView38.text = "${fixdata.format((Social/total)*100.0)}%  $${fixdata2.format(Social)}"
        binding.textView39.text = "${fixdata.format((Shopping/total)*100.0)}%  $${fixdata2.format(Shopping)}"
        binding.textView40.text = "${fixdata.format((Hospital/total)*100.0)}%  $${fixdata2.format(Hospital)}"
        binding.textView41.text = "${fixdata.format((Game/total)*100.0)}%  $${fixdata2.format(Game)}"
        binding.textView42.text = "${fixdata.format((Other/total)*100.0)}%  $${fixdata2.format(Other)}"
    }
    fun SpinnerDataSelect(){
        binding.spinner.adapter = MyAdapter(requireContext(),listOf("All","One_Week", "One_Month", "Six_Month", "One_Year"))
        val time = arrayListOf("All","One_Week", "One_Month", "Six_Month", "One_Year")
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                DataSelectAll()
                chosentime = time[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        binding.spinner2.adapter = MyAdapter(requireContext(),listOf("All","Breakfast","Lunch","Dinner","Transportation", "Drink","Dessert","Social","Shopping", "Hospital","Game","Income","Other","Without Income"))
        val kind = arrayListOf("All","Breakfast","Lunch","Dinner","Transportation", "Drink","Dessert","Social","Shopping", "Hospital","Game","Income","Other","Without Income")
        binding.spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                DataSelectAll()
                chosenkind = kind[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }
    fun DataSelectAll(){
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
                    DataSelectTime()
                    DataSelectKind()
                    DataFloatSelect()
                    setdata(FinalBreakfast,FinalLunch,FinalDinner,FinalTransportation,FinalDrink,FinalDessert,FinalSocial,FinalShopping,FinalHospital,FinalGame,FinalOther)
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
    fun DataSelectTime(){
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
                StoreArray.removeAll(deletearray)
                deletearray.clear()
            }
            "One_Month"->{
                for(i in dayslist.indices){
                    if (dayslist[i] <= -31) {
                        deletearray.add(StoreArray[i])
                    }
                }
                StoreArray.removeAll(deletearray)
                deletearray.clear()
            }
            "Six_Month"->{
                for(i in dayslist.indices){
                    if (dayslist[i] <= -186) {
                        deletearray.add(StoreArray[i])
                    }
                }
                StoreArray.removeAll(deletearray)
                deletearray.clear()
            }
            "One_Year"->{
                for(i in dayslist.indices){
                    if (dayslist[i] <= -365) {
                        deletearray.add(StoreArray[i])
                    }
                }
                StoreArray.removeAll(deletearray)
                deletearray.clear()
            }
        }
    }
    fun DataSelectKind() {
        Typearray.clear()
        for (i in StoreArray.indices) {
            val TP = StoreArray[i]
            val Type = TP["TypeChoice"].toString()
            Typearray.add(Type)
        }
        when (chosenkind) {
            "All" -> {

            }
            "Breakfast" -> {
                for (i in Typearray.indices) {
                    if (Typearray[i] != "Breakfast") {
                        Typedeletearray.add(StoreArray[i])
                    }
                }
                StoreArray.removeAll(Typedeletearray)
                Typedeletearray.clear()
            }
            "Lunch" -> {
                for (i in Typearray.indices) {
                    if (Typearray[i] != "Lunch") {
                        Typedeletearray.add(StoreArray[i])
                    }
                }
                StoreArray.removeAll(Typedeletearray)
                Typedeletearray.clear()
            }
            "Dinner" -> {
                for (i in Typearray.indices) {
                    if (Typearray[i] != "Dinner") {
                        Typedeletearray.add(StoreArray[i])
                    }
                }
                StoreArray.removeAll(Typedeletearray)
                Typedeletearray.clear()
            }
            "Transportation" -> {
                for (i in Typearray.indices) {
                    if (Typearray[i] != "Transportation") {
                        Typedeletearray.add(StoreArray[i])
                    }
                }
                StoreArray.removeAll(Typedeletearray)
                Typedeletearray.clear()
            }
            "Drink" -> {
                for (i in Typearray.indices) {
                    if (Typearray[i] != "Drink") {
                        Typedeletearray.add(StoreArray[i])
                    }
                }
                StoreArray.removeAll(Typedeletearray)
                Typedeletearray.clear()
            }
            "Dessert" -> {
                for (i in Typearray.indices) {
                    if (Typearray[i] != "Dessert") {
                        Typedeletearray.add(StoreArray[i])
                    }
                }
                StoreArray.removeAll(Typedeletearray)
                Typedeletearray.clear()
            }
            "Social" -> {
                for (i in Typearray.indices) {
                    if (Typearray[i] != "Social") {
                        Typedeletearray.add(StoreArray[i])
                    }
                }
                StoreArray.removeAll(Typedeletearray)
                Typedeletearray.clear()
            }
            "Shopping" -> {
                for (i in Typearray.indices) {
                    if (Typearray[i] != "Shopping") {
                        Typedeletearray.add(StoreArray[i])
                    }
                }
                StoreArray.removeAll(Typedeletearray)
                Typedeletearray.clear()
            }
            "Hospital" -> {
                for (i in Typearray.indices) {
                    if (Typearray[i] != "Hospital") {
                        Typedeletearray.add(StoreArray[i])
                    }
                }
                StoreArray.removeAll(Typedeletearray)
                Typedeletearray.clear()
            }
            "Game" -> {
                for (i in Typearray.indices) {
                    if (Typearray[i] != "Game") {
                        Typedeletearray.add(StoreArray[i])
                    }
                }
                StoreArray.removeAll(Typedeletearray)
                Typedeletearray.clear()
            }
            "Income" -> {
                for (i in Typearray.indices) {
                    if (Typearray[i] != "Income") {
                        Typedeletearray.add(StoreArray[i])
                    }
                }
                StoreArray.removeAll(Typedeletearray)
                Typedeletearray.clear()
            }
            "Other" -> {
                for (i in Typearray.indices) {
                    if (Typearray[i] != "Other") {
                        Typedeletearray.add(StoreArray[i])
                    }
                }
                StoreArray.removeAll(Typedeletearray)
                Typedeletearray.clear()
            }
            "Without Income" -> {
                for (i in Typearray.indices) {
                    if (Typearray[i] == "Income") {
                        Typedeletearray.add(StoreArray[i])
                    }
                }
                StoreArray.removeAll(Typedeletearray)
                Typedeletearray.clear()
            }
        }
    }
    fun DataFloatSelect(){
        FinalBreakfast = 0f
        FinalLunch= 0f
        FinalDinner= 0f
        FinalTransportation= 0f
        FinalDrink= 0f
        FinalDessert= 0f
        FinalSocial= 0f
        FinalShopping= 0f
        FinalHospital= 0f
        FinalGame= 0f
        FinalOther= 0f
        CalculatetypeArray.clear()
        for (i in StoreArray.indices) {
            val typehashmap = StoreArray[i]
            val Type = typehashmap["TypeChoice"].toString()
            val price = typehashmap["FillPrice"].toString().toFloat()
            when (Type) {
                "Breakfast" -> {
                    FinalBreakfast+=price
                }
                "Lunch" -> {
                    FinalLunch+=price
                }
                "Dinner" -> {
                    FinalDinner+=price
                }
                "Transportation" -> {
                    FinalTransportation+=price
                }
                "Drink" -> {
                    FinalDrink+=price
                }
                "Dessert" -> {
                    FinalDessert+=price
                }
                "Social" -> {
                    FinalSocial+=price
                }
                "Shopping" -> {
                    FinalShopping+=price
                }
                "Hospital" -> {
                    FinalHospital+=price
                }
                "Game" -> {
                    FinalGame+=price
                }
                "Other" -> {
                    FinalOther+=price
                }
            }
        }
        CalculatetypeArray.add(FinalBreakfast)
        CalculatetypeArray.add(FinalLunch)
        CalculatetypeArray.add(FinalDinner)
        CalculatetypeArray.add(FinalTransportation)
        CalculatetypeArray.add(FinalDrink)
        CalculatetypeArray.add(FinalDessert)
        CalculatetypeArray.add(FinalSocial)
        CalculatetypeArray.add(FinalShopping)
        CalculatetypeArray.add(FinalHospital)
        CalculatetypeArray.add(FinalGame)
        CalculatetypeArray.add(FinalOther)
        Log.d("kkk",CalculatetypeArray.toString())
    }
    override fun onItemClick(position: Int) {

    }

}
