package com.tom.accountingapplication.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tom.accountingapplication.databinding.ItemAccountingItemBinding

class AccountingItemAdapter(
    private val onItemClick: (UpdateItem) -> Unit,
) :
    RecyclerView.Adapter<AccountingItemAdapter.PackageViewHolder>() {
    var itemList: List<UpdateItem> = arrayListOf()
    var seq: Int? = 0
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
        holder.bind(itemList[position], onItemClick)
    }

    inner class PackageViewHolder(private val binding: ItemAccountingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UpdateItem, onItemClick: (UpdateItem) -> Unit) {
            binding.txtItemTitle.text = item.title
            binding.txtItemTitle.setCompoundDrawablesWithIntrinsicBounds(0, item.image, 0, 0)
            binding.txtItemTitle.setOnClickListener {
                onItemClick(item)
            }
        }
    }
}
