package com.tom.accountingapplication.ui.home.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tom.accountingapplication.R
import com.tom.accountingapplication.accountingModel.AccountingItem
import com.tom.accountingapplication.databinding.ItemAccountingItemBinding

class AccountingItemAdapter(private val onItemClick: (AccountingItem) -> Unit) :
    RecyclerView.Adapter<AccountingItemAdapter.PackageViewHolder>() {
    var itemList = arrayListOf<AccountingItem>()
    var seq: Int? = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageViewHolder {
        val view = ItemAccountingItemBinding.inflate(
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
        holder.bind(itemList[position], seq ?: 1, onItemClick)
    }

    inner class PackageViewHolder(private val binding: ItemAccountingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AccountingItem, seq: Int, onItemClick: (AccountingItem) -> Unit) {
            binding.txtTitle.text = item.title
            binding.txtTitle.setCompoundDrawablesWithIntrinsicBounds(0, item.image, 0, 0)
            if (item.isSelect) {
                if (seq == 1) {
                    binding.txtTitle.setBackgroundResource(R.drawable.corners_rim_blue)
                } else {
                    binding.txtTitle.setBackgroundResource(R.drawable.corners_rim_pink)
                }
            } else {
                binding.txtTitle.setBackgroundResource(0)
            }
            binding.txtTitle.setOnClickListener {
                onItemClick(item)
            }
        }
    }
}
