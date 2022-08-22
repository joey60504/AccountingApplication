package com.tom.accountingapplication.ui.gallery

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tom.accountingapplication.databinding.HistoryItemBinding

class HistoricAdapter(private val itemListener:OnItemClick):RecyclerView.Adapter<HistoricAdapter.ViewHolder>() {
    lateinit var dataList:ArrayList<HashMap<*,*>>
    private lateinit var binding: HistoryItemBinding
    class ViewHolder(val view:HistoryItemBinding):RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding= HistoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.cardview1.setOnClickListener {
            itemListener.onItemClick(position)
        }
        val data=dataList[position]
        val incomeOrExpense=data["IncomeOrExpense"].toString()
        val dateText=data["Date"].toString()
        val spinnerChoice=data["TypeChoice"].toString()
        val price=data["FillPrice"].toString()
        holder.view.textView12.text=dateText
        holder.view.textView4.text=spinnerChoice
        if(incomeOrExpense=="Income"){
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