package com.tom.accountingapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tom.accountingapplication.databinding.ActivityModifyPersonalInformationBinding


private lateinit var binding : ActivityModifyPersonalInformationBinding
class ModifyPersonalInformation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModifyPersonalInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}