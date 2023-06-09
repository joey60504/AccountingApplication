package com.tom.accountingapplication.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tom.accountingapplication.accountingModel.TagItem
import com.tom.accountingapplication.databinding.ItemAccountingTagBinding

class AccountingBottomSheetTagAdapter(
    private val onItemClick: (TagItem) -> Unit,
) :
    RecyclerView.Adapter<AccountingBottomSheetTagAdapter.PackageViewHolder>() {
    var tagList: ArrayList<TagItem> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageViewHolder {
        val view = ItemAccountingTagBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PackageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tagList.size
    }

    override fun onBindViewHolder(holder: PackageViewHolder, position: Int) {
        holder.bind(tagList[position], onItemClick)
    }

    inner class PackageViewHolder(private val binding: ItemAccountingTagBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TagItem, onItemClick: (TagItem) -> Unit) {
            binding.txtAccountingTag.text = item.title
            binding.txtAccountingTag.setOnClickListener {
                onItemClick(item)
            }
        }
    }
}