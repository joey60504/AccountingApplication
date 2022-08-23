package com.tom.accountingapplication.ui.invest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tom.accountingapplication.databinding.InvestItemBinding

class InvestAdapter(
    private val itemListenerVC: OnMagnifierClickStock,
    private val itemListenerS: OnMagnifierClickVirtualCurrency
) :
    RecyclerView.Adapter<InvestAdapter.ViewHolder>() {
    lateinit var dataList: ArrayList<HashMap<*, *>>
    private lateinit var binding: InvestItemBinding

    class ViewHolder(val view: InvestItemBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = InvestItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    interface OnMagnifierClickStock {
        fun onMagnifierClickStock(position: Int)
    }

    interface OnMagnifierClickVirtualCurrency {
        fun onMagnifierClickVirtualCurrency(position: Int)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        val buyOrSell = data["BuyOrSell"].toString()
        val date = data["Date"].toString()
        val virtualCurrencyOrStock = data["VirtualCurrencyOrStock"].toString()
        val fillPrice = data["FillPrice"].toString()
        holder.view.investDate.text = date
        holder.view.investKind.text = virtualCurrencyOrStock

        if (virtualCurrencyOrStock == "VirtualCurrency") {
            holder.view.imageView.setOnClickListener {
                itemListenerVC.onMagnifierClickStock(position)
            }
        } else {
            holder.view.imageView.setOnClickListener {
                itemListenerS.onMagnifierClickVirtualCurrency(position)
            }
        }

        if (buyOrSell == "Buy") {
            holder.view.investBuy.text = "-$fillPrice"
            holder.view.investSell.text = "+0"
        } else {
            holder.view.investBuy.text = "+-0"
            holder.view.investSell.text = "+$fillPrice"
        }
    }
}