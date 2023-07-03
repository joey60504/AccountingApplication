package com.tom.accountingapplication.ui.history.typefilter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tom.accountingapplication.accountingModel.FilterTypeItem
import com.tom.accountingapplication.accountingModel.FilterTypeItemList
import com.tom.accountingapplication.databinding.ItemFilterTypeItemBinding

class HistoryFilterTypeAdapter(private val onItemClick: (FilterTypeItem) -> Unit) :
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
        holder.bind(itemList[position], onItemClick)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: ArrayList<FilterTypeItemList>) {
        itemList.clear()
        itemList.addAll(newData)
        notifyDataSetChanged()
    }

    class PackageViewHolder(private val binding: ItemFilterTypeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FilterTypeItemList, onItemClick: (FilterTypeItem) -> Unit) {
            binding.txtFilterTypeItem.text = "${item.type}："
            val historyFilterTypeItemAdapter = HistoryFilterTypeItemAdapter(
                onItemClick = { filterTypeItem ->
                    onItemClick(filterTypeItem)
                }
            )

            historyFilterTypeItemAdapter.itemList = item.filterTypeItemList
            binding.recyclerFilterTypeItem.apply {
                setHasFixedSize(true)
                val manager =
                    GridLayoutManager(itemView.context, 3, GridLayoutManager.VERTICAL, false)
                layoutManager = manager
                this.adapter = historyFilterTypeItemAdapter
            }
        }
    }
}