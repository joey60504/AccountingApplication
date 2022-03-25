package com.tom.accountingapplication.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tom.accountingapplication.databinding.HistoryItemBinding

class homeadapter (private val itemListener:OnItemClick): RecyclerView.Adapter<homeadapter.ViewHolder>() {
    lateinit var dataList:ArrayList<HashMap<*,*>>
    private lateinit var binding: HistoryItemBinding
    class ViewHolder(val view: HistoryItemBinding): RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding= HistoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)

    }
    override fun getItemCount(): Int {
        return dataList.size
    }
    interface OnItemClick{
        fun onItemClick(position: Int)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data=dataList[position]
        val incomeorexpense=data["IncomeOrExpense"].toString()
        val datetext=data["Date"].toString()
        val spinnerchoice=data["TypeChoice"].toString()
        val price=data["FillPrice"].toString()
        holder.view.textView12.text=datetext
        holder.view.textView4.text=spinnerchoice
        if(incomeorexpense=="Income"){
            holder.view.textView16.text="+$price"
            holder.view.textView18.text="-0"
        }
        else{
            holder.view.textView16.text="+0"
            holder.view.textView18.text="-$price"
        }
    }
}