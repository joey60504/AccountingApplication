package com.tom.accountingapplication.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tom.accountingapplication.databinding.HistoryItemBinding

class histortyadapter(private val itemListener:OnItemClick):RecyclerView.Adapter<histortyadapter.ViewHolder>() {
    lateinit var dataList:HashMap<*,*>
    private lateinit var binding: HistoryItemBinding
    class ViewHolder(val view:HistoryItemBinding):RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding= HistoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date=dataList.keys.toString()
        val datehashmap=dataList[date] as HashMap<*,*>
        val incomeorexpense=datehashmap["incomeexpense"].toString()
        val datetext=datehashmap["date"].toString()
        val spinnerchoice=datehashmap["spinnerchoice"].toString()
        val price=datehashmap["price"].toString()
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
    override fun getItemCount(): Int {
        return dataList.size
    }
    interface OnItemClick{
        fun onItemClick(position: Int)
    }
}