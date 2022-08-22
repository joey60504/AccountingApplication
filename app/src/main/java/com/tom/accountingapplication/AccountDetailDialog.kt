package com.tom.accountingapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.tom.accountingapplication.databinding.ActivityDialogAccuountingDetailBinding


class AccountDetailDialog(private val dataHashMap: HashMap<*, *>) : DialogFragment() {
    private lateinit var binding: ActivityDialogAccuountingDetailBinding
    lateinit var auth: FirebaseAuth

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityDialogAccuountingDetailBinding.inflate(layoutInflater)
        val incomeOrExpense = dataHashMap["IncomeOrExpense"].toString()
        val dateText = dataHashMap["Date"].toString()
        val spinnerChoice = dataHashMap["TypeChoice"].toString()
        val price = dataHashMap["FillPrice"].toString()
        val remark = dataHashMap["TypeRemark"].toString()
        binding.textView54.text = dateText
        binding.textView59.text = spinnerChoice
        binding.textView60.text = incomeOrExpense
        binding.textView61.text = "$${price}"
        binding.textView66.text = remark
        binding.textView64.setOnClickListener {
            showDialog("Sure To Delete?", dateText, incomeOrExpense, price)
        }

        return binding.root
    }

    private fun showDialog(
        message: String,
        targetDate: String,
        targetIncomeOrExpense: String,
        targetPrice: String
    ) {
        val yesNoDialog = YesOrNoDialog(requireContext())
        yesNoDialog
            .setMessage(message)
            .setCancel(object : YesOrNoDialog.IOnCancelListener {
                override fun onCancel(dialog: YesOrNoDialog?) {
                    yesNoDialog.dismiss()
                }
            })
            .setConfirm(object : YesOrNoDialog.IOnConfirmListener {
                override fun onConfirm(dialog: YesOrNoDialog?) {
                    deleteData(targetDate, targetIncomeOrExpense, targetPrice)
                    yesNoDialog.dismiss()
                    dismiss()
                    return
                }
            }).show()
    }

    private fun deleteData(targetDate: String, targetIncomeOrExpense: String, targetPrice: String) {
        auth = FirebaseAuth.getInstance()
        val userEmail = auth.currentUser?.email.toString()
        val findLittleMouseAt = userEmail.indexOf("@")
        val userEmailValue = userEmail.substring(0, findLittleMouseAt)
        val database = FirebaseDatabase.getInstance().reference

        val targetDate = targetDate.replace("/", "")
        val targetDateMonth = targetDate.substring(0, 6).replace("/", "")

        database.child(userEmailValue).get().addOnSuccessListener {
            val databaseEmailValue = it.value as java.util.HashMap<String, Any>
            //accounting
            val userAccounting =
                databaseEmailValue["Accounting"] as java.util.HashMap<String, Any>
            val userTargetDateMonth =
                userAccounting[targetDateMonth] as java.util.HashMap<String, Any>
            val userTargetDate = userTargetDateMonth[targetDate] as ArrayList<HashMap<String, Any>>
            userTargetDate.remove(dataHashMap)
            userTargetDateMonth[targetDate] = userTargetDate
            userAccounting[targetDateMonth] = userTargetDateMonth
            database.child(userEmailValue).child("Accounting")
                .updateChildren(userAccounting)
            //profile
            val userProfile =
                databaseEmailValue["Profile"] as HashMap<*, *>
            val userAsset = userProfile["Asset"].toString()

            if (targetIncomeOrExpense == "Income") {
                val subAsset = (userAsset.toDouble() - targetPrice.toDouble()).toString()
                database.child(userEmailValue).child("Profile").child("Asset")
                    .setValue(subAsset)
            } else if (targetIncomeOrExpense == "Expense") {
                val addAsset = (userAsset.toDouble() + targetPrice.toDouble()).toString()
                database.child(userEmailValue).child("Profile").child("Asset")
                    .setValue(addAsset)
            }
        }
    }
}