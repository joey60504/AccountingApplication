package com.tom.accountingapplication.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tom.accountingapplication.accountingModel.ReadDataTagList
import com.tom.accountingapplication.databinding.ItemAccountingDataTagBinding

class AccountingDataTagAdapter :
    RecyclerView.Adapter<AccountingDataTagAdapter.PackageViewHolder>() {
    var itemList: ArrayList<ReadDataTagList> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageViewHolder {
        val view = ItemAccountingDataTagBinding.inflate(
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

    inner class PackageViewHolder(private val binding: ItemAccountingDataTagBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ReadDataTagList) {
            binding.txtItemTag.text = item.title
            binding.txtTagPrice.text = "小計：${item.tagPrice}"
            val accountingDataTagItemAdapter = AccountingDataTagItemAdapter()
            accountingDataTagItemAdapter.itemList = item.dataList
            binding.recyclerDataTagItem.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(
                    itemView.context,
                    LinearLayoutManager.VERTICAL, false
                )
                this.adapter = accountingDataTagItemAdapter
            }
        }
    }
}
