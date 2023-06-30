package com.tom.accountingapplication.ui.datashow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tom.accountingapplication.accountingModel.UploadData
import com.tom.accountingapplication.databinding.ItemAccountingDataTagItemBinding

class AccountingDataTagItemAdapter(private val onItemClick: (UploadData) -> Unit) :
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
        holder.bind(itemList[position], onItemClick)
    }

    inner class PackageViewHolder(private val binding: ItemAccountingDataTagItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UploadData, onItemClick: (UploadData) -> Unit) {
            binding.txtItemTitle.text = item.title
            binding.imgItemIcon.setBackgroundResource(item.image)
            binding.txtItemType.text = if (item.seq == 1) "支出：" else "收入："
            binding.txtItemPrice.text = "＄${item.price}"
            binding.txtItemRemark.text = item.remark
            binding.txtItemDetail.setOnClickListener {
                onItemClick(item)
            }
        }
    }
}