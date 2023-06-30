package com.tom.accountingapplication.ui.history.historyfilter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tom.accountingapplication.R
import com.tom.accountingapplication.accountingModel.FilterTypeItem
import com.tom.accountingapplication.databinding.ItemFilterItemBinding

class HistoryFilterTypeItemAdapter(private val onItemClick: (FilterTypeItem) -> Unit) :
    RecyclerView.Adapter<HistoryFilterTypeItemAdapter.PackageViewHolder>() {

    var itemList: ArrayList<FilterTypeItem> = arrayListOf()

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
        holder.bind(itemList[position], onItemClick)
    }

    inner class PackageViewHolder(private val binding: ItemFilterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FilterTypeItem, onItemClick: (FilterTypeItem) -> Unit) {
            binding.txtFilterItem.text = item.title
            binding.txtFilterItem.setOnClickListener {
                onItemClick(item)
            }
            if(item.isChecked){
                binding.txtFilterItem.setBackgroundResource(R.drawable.corners_rim_grey_1)
            } else {
                binding.txtFilterItem.setBackgroundResource(0)
            }
        }
    }
}