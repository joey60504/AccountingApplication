package com.tom.accountingapplication.ui.invest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tom.accountingapplication.databinding.ActivityInvestWebBinding
import com.tom.accountingapplication.Invest

private lateinit var binding : ActivityInvestWebBinding
private var invest = arrayListOf<Invest>()
class InvestWeb : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInvestWebBinding.inflate(layoutInflater)
        setContentView(binding.root)
        invest = intent.getParcelableArrayListExtra("Invest")!!
    }
}