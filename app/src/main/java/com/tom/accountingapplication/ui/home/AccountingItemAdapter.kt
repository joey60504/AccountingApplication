package com.tom.accountingapplication.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tom.accountingapplication.R
import com.tom.accountingapplication.accountingModel.UpdateItem
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
            binding.txtTitle.text = item.title
            binding.txtTitle.setCompoundDrawablesWithIntrinsicBounds(0, item.image, 0, 0)
            if (item.isSelect) {
                if (seq == 1) {
                    binding.txtTitle.setBackgroundResource(R.drawable.corners_rim_blue)
                } else {
                    binding.txtTitle.setBackgroundResource(R.drawable.corners_rim_pink)
                }
            } else{
                binding.txtTitle.setBackgroundResource(0)
            }
            binding.txtTitle.setOnClickListener {
                onItemClick(item)
            }
        }
    }
}
