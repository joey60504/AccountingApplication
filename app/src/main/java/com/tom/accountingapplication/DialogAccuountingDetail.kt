package com.tom.accountingapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.tom.accountingapplication.databinding.ActivityDialogAccuountingDetailBinding
import java.util.ArrayList

lateinit var auth: FirebaseAuth
class DialogAccuountingDetail(val datalist:HashMap<*,*>): DialogFragment() {
    private lateinit var binding: ActivityDialogAccuountingDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityDialogAccuountingDetailBinding.inflate(layoutInflater)
        val incomeorexpense=datalist["IncomeOrExpense"].toString()
        val datetext=datalist["Date"].toString()
        val spinnerchoice=datalist["TypeChoice"].toString()
        val price=datalist["FillPrice"].toString()
        val remark = datalist["TypeRemark"].toString()
        return binding.root
    }
}