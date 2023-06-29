package com.tom.accountingapplication.ui.history.historyfilter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tom.accountingapplication.accountingModel.FilterTypeItemList
import com.tom.accountingapplication.databinding.ItemFilterTypeItemBinding

class HistoryFilterTypeAdapter() :
    RecyclerView.Adapter<HistoryFilterTypeAdapter.PackageViewHolder>() {

    var itemList: ArrayList<FilterTypeItemList> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageViewHolder {
        val view = ItemFilterTypeItemBinding.inflate(
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
        holder.bind(itemList[position], itemList.size, position)
    }

    inner class PackageViewHolder(private val binding: ItemFilterTypeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FilterTypeItemList, size: Int, position: Int) {
            binding.txtFilterTypeItem.text = "${item.type}："

            val historyFilterTypeItemAdapter = HistoryFilterTypeItemAdapter()

            historyFilterTypeItemAdapter.itemList = item.filterTypeItemList
            binding.recyclerFilterTypeItem.apply {
                setHasFixedSize(true)
                val manager =
                    GridLayoutManager(itemView.context, 3, GridLayoutManager.VERTICAL, false)
                layoutManager = manager
                this.adapter = historyFilterTypeItemAdapter
            }
            if (size - 1 == position) {
                binding.viewFilter.visibility = View.GONE
            }
        }
    }
}