package com.tom.accountingapplication.ui.history.historyfilter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tom.accountingapplication.accountingModel.FilterTypeItem
import com.tom.accountingapplication.databinding.ItemFilterItemBinding

class HistoryFilterTypeItemAdapter() :
    RecyclerView.Adapter<HistoryFilterTypeItemAdapter.PackageViewHolder>() {

    var itemList: ArrayList<FilterTypeItem>  = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageViewHolder {
        val view = ItemFilterItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PackageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: PackageViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    inner class PackageViewHolder(private val binding: ItemFilterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FilterTypeItem) {
            binding.txtFilterItem.text = item.title
        }
    }
}