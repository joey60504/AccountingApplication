package com.tom.accountingapplication

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class yesnodialog(context: Context): Dialog(context){

    private var message: String?= null

    private var cancelListener: IOnCancelListener? = null
    private var confirmListener: IOnConfirmListener? = null

    fun setMessage(message: String?): yesnodialog {
        this.message = message
        return this
    }

    fun setConfirm(Listener: IOnConfirmListener): yesnodialog {
        this.confirmListener = Listener
        return this
    }

    fun setCancel(Listener: IOnCancelListener): yesnodialog {
        this.cancelListener = Listener
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yesnodialog)

        val btNegative: Button = findViewById(R.id.dialog_negative)
        val btPositive: Button= findViewById(R.id.dialog_positive)
        val tvContent: TextView = findViewById(R.id.dialog_yes_no_message)

        message?.let {
            tvContent.text= it
        }

        btPositive.setOnClickListener(this::clickListener)
        btNegative.setOnClickListener(this::clickListener)
    }

    private fun clickListener(v: View){
        when(v.id){
            R.id.dialog_positive -> {
                confirmListener?.let {
                    it.onConfirm(this)
                }
            }
            R.id.dialog_negative -> {
                cancelListener?.let {
                    it.onCancel(this)
                }
            }
        }
    }

    interface IOnCancelListener {
        fun onCancel(dialog: yesnodialog?)
    }

    interface IOnConfirmListener {
        fun onConfirm(dialog: yesnodialog?)
    }
}