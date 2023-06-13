package com.tom.accountingapplication.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tom.accountingapplication.accountingModel.ReadDataTag
import com.tom.accountingapplication.accountingModel.UploadData
import com.tom.accountingapplication.databinding.ItemAccountingDataTagBinding

class AccountingDataTagAdapter(private val onItemClick: (UploadData) -> Unit) :
    RecyclerView.Adapter<AccountingDataTagAdapter.PackageViewHolder>() {
    var itemList: ArrayList<ReadDataTag> = arrayListOf()
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
        holder.bind(itemList[position], onItemClick)
    }

    inner class PackageViewHolder(private val binding: ItemAccountingDataTagBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ReadDataTag, onItemClick: (UploadData) -> Unit) {
            binding.txtItemTag.text = item.title
            binding.txtTagPrice.text = "小計：${item.tagPrice}"
            val accountingDataTagItemAdapter = AccountingDataTagItemAdapter(
                onItemClick = { uploadData ->
                    onItemClick(uploadData)
                }
            )
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
