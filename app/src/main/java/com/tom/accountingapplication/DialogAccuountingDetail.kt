package com.tom.accountingapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.tom.accountingapplication.databinding.ActivityDialogAccuountingDetailBinding

lateinit var auth: FirebaseAuth

class DialogAccuountingDetail(private val datahashmap:HashMap<*,*>): DialogFragment() {
    private lateinit var binding: ActivityDialogAccuountingDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityDialogAccuountingDetailBinding.inflate(layoutInflater)
        val incomeorexpense=datahashmap["IncomeOrExpense"].toString()
        val datetext=datahashmap["Date"].toString()
        val spinnerchoice=datahashmap["TypeChoice"].toString()
        val price=datahashmap["FillPrice"].toString()
        val remark = datahashmap["TypeRemark"].toString()
        binding.textView54.text = datetext
        binding.textView59.text = spinnerchoice
        binding.textView60.text = incomeorexpense
        binding.textView61.text = "$${price}"
        binding.textView66.text = remark

        binding.button2.setOnClickListener {
            showDialog("Sure To Delete?")
        }
        return binding.root
    }
    private fun showDialog(message: String){
        val yesNoDialog= yesnodialog(requireContext())
        yesNoDialog
            .setMessage(message)
            .setCancel(object : yesnodialog.IOnCancelListener {
                override fun onCancel(dialog: yesnodialog?) {
                    yesNoDialog.dismiss()
                }
            })
            .setConfirm(object : yesnodialog.IOnConfirmListener {
                override fun onConfirm(dialog: yesnodialog?) {
                    deletedata()
                }
            }).show()
    }
    fun deletedata(){

    }
}