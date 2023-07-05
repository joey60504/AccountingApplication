package com.tom.accountingapplication.ui.history.typefilter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tom.accountingapplication.accountingModel.FilterType
import com.tom.accountingapplication.accountingModel.FilterTypeItem
import com.tom.accountingapplication.databinding.FragmentHistoryViewpagerFilterTypeBinding

class HistoryFilterTypeViewPagerAdapter(private val onItemClick: (FilterTypeItem) -> Unit) :
    RecyclerView.Adapter<HistoryFilterTypeViewPagerAdapter.ViewHolder>() {

    var itemList = arrayListOf<FilterType>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentHistoryViewpagerFilterTypeBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(onItemClick, itemList[position])
    }


    inner class ViewHolder(private val binding: FragmentHistoryViewpagerFilterTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(onItemClick: (FilterTypeItem) -> Unit, item: FilterType) {
            val historyFilterTypeAdapter = HistoryFilterTypeAdapter(
                onItemClick = { filterTypeItem ->
                    onItemClick(filterTypeItem)
                }
            )
            historyFilterTypeAdapter.setData(item.typeList)
            binding.recyclerFilterType.apply {
                setHasFixedSize(true)
                val manager =
                    LinearLayoutManager(
                        itemView.context,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                manager.stackFromEnd = false
                addItemDecoration(
                    DividerItemDecoration(
                        itemView.context,
                        RecyclerView.VERTICAL
                    )
                )
                layoutManager = manager
                this.adapter = historyFilterTypeAdapter
            }

        }
    }
}