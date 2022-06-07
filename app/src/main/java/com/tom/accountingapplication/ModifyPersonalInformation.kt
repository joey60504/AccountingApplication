package com.tom.accountingapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.tom.accountingapplication.databinding.ActivityModifyPersonalInformationBinding



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
        val yesNoDialog= yesnodialog(this@ModifyPersonalInformation)
        yesNoDialog
            .setMessage(message)
            .setCancel(object : yesnodialog.IOnCancelListener {
                override fun onCancel(dialog: yesnodialog?) {
                    yesNoDialog.dismiss()
                }
            })
            .setConfirm(object : yesnodialog.IOnConfirmListener {
                override fun onConfirm(dialog: yesnodialog?) {
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