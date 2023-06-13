package com.tom.accountingapplication.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tom.accountingapplication.accountingModel.ReadDataDate
import com.tom.accountingapplication.accountingModel.UpdateItem
import com.tom.accountingapplication.accountingModel.UploadData
import com.tom.accountingapplication.databinding.ItemAccountingDataBinding

class AccountingDataAdapter(private val onItemClick: (UploadData) -> Unit) :
    RecyclerView.Adapter<AccountingDataAdapter.PackageViewHolder>() {
    var itemList: ArrayList<ReadDataDate> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageViewHolder {
        val view = ItemAccountingDataBinding.inflate(
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

    inner class PackageViewHolder(private val binding: ItemAccountingDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ReadDataDate, onItemClick: (UploadData) -> Unit) {
            binding.txtDate.text = item.date
            binding.txtDatePrice.text = "日結：${item.datePrice}"
            val accountingDataTagAdapter = AccountingDataTagAdapter(
                onItemClick = { uploadData ->
                    onItemClick(uploadData)
                }
            )
            accountingDataTagAdapter.itemList = item.tagList
            binding.recyclerDataTag.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(
                    itemView.context,
                    LinearLayoutManager.VERTICAL, false
                )
                this.adapter = accountingDataTagAdapter
            }
        }
    }
}
