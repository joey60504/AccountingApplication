package com.tom.accountingapplication.ui.slideshow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.tom.accountingapplication.YesOrNoDialog
import com.tom.accountingapplication.databinding.ActivityModifyPersonalInformationBinding
import com.tom.accountingapplication.homepage


private lateinit var binding : ActivityModifyPersonalInformationBinding
class ModifyPersonalInformation : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModifyPersonalInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button2.setOnClickListener {
            showDialog("Sure To Modify?")
        }
    }
    private fun showDialog(message: String){
        val yesNoDialog= YesOrNoDialog(this@ModifyPersonalInformation)
        yesNoDialog
            .setMessage(message)
            .setCancel(object : YesOrNoDialog.IOnCancelListener {
                override fun onCancel(dialog: YesOrNoDialog?) {
                    yesNoDialog.dismiss()
                }
            })
            .setConfirm(object : YesOrNoDialog.IOnConfirmListener {
                override fun onConfirm(dialog: YesOrNoDialog?) {
                    profileupload()
                }
            }).show()
    }


    fun profileupload(){
        val name = binding.editTextTextPersonName.text.toString()
        val phone = binding.editTextTextPersonName2.text.toString()
        val birthday = binding.editTextTextPersonName3.text.toString()
        auth = FirebaseAuth.getInstance()
        var email = auth.currentUser?.email.toString()
        val LittleMouseAt=email.indexOf("@")
        val emailname=email.substring(0,LittleMouseAt)
        database = FirebaseDatabase.getInstance().reference
        if (name.isNotEmpty() && phone.isNotEmpty() && birthday.isNotEmpty()){
            database.child(emailname).child("Profile").child("name").setValue(name)
            database.child(emailname).child("Profile").child("phone").setValue(phone)
            database.child(emailname).child("Profile").child("birthday").setValue(birthday)
            startActivity(Intent(this@ModifyPersonalInformation,homepage::class.java))
        }
        else{
            Toast.makeText(this@ModifyPersonalInformation, "form must not be empty", Toast.LENGTH_LONG).show()
        }

    }

}