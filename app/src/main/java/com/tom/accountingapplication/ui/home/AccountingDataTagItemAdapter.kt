package com.tom.accountingapplication.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tom.accountingapplication.accountingModel.UploadData
import com.tom.accountingapplication.databinding.ItemAccountingDataTagItemBinding

class AccountingDataTagItemAdapter :
    RecyclerView.Adapter<AccountingDataTagItemAdapter.PackageViewHolder>() {
    var itemList: ArrayList<UploadData> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageViewHolder {
        val view = ItemAccountingDataTagItemBinding.inflate(
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

    inner class PackageViewHolder(private val binding: ItemAccountingDataTagItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UploadData) {
            binding.txtItemTitle.text = item.item
            binding.imgItemIcon
            binding.txtItemType.text = if (item.type == 1) "支出：" else "收入："
            binding.txtItemPrice.text = item.price.toString()
            binding.txtItemRemark.text = item.remark
        }
    }
}