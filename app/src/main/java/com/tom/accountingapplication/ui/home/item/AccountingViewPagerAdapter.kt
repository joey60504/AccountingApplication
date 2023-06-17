package com.tom.accountingapplication.ui.home.item


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tom.accountingapplication.accountingModel.AccountingItem
import com.tom.accountingapplication.accountingModel.AccountingItemType
import com.tom.accountingapplication.databinding.FragmentAccountingViewpagerItemBinding

class AccountingViewPagerAdapter(private val onItemClick: (AccountingItem) -> Unit) :
    RecyclerView.Adapter<AccountingViewPagerAdapter.ViewHolder>() {

    var itemType = arrayListOf<AccountingItemType>()
    var seq: Int? = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentAccountingViewpagerItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemType.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemType[position], seq ?: 1, onItemClick)
    }


    inner class ViewHolder(private val binding: FragmentAccountingViewpagerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: AccountingItemType,
            seq: Int,
            onItemClick: (AccountingItem) -> Unit
        ) {
            val accountingItemAdapter = AccountingItemAdapter(
                onItemClick = { accountingItem ->
                    onItemClick(accountingItem)
                }
            )
            accountingItemAdapter.itemList = item.itemList
            accountingItemAdapter.seq = seq
            binding.recyclerItem.apply {
                setHasFixedSize(true)
                val manager =
                    GridLayoutManager(itemView.context,6, LinearLayoutManager.VERTICAL, false)
                manager.stackFromEnd = false
                layoutManager = manager
                this.adapter = accountingItemAdapter
            }
        }
    }
}