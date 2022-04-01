package com.tom.accountingapplication

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.tom.accountingapplication.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProfileBinding
    private lateinit var actionBar : ActionBar
    lateinit var auth: FirebaseAuth
    lateinit var database:DatabaseReference
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBar = supportActionBar!!
        actionBar.setTitle(Html.fromHtml("<font color=\"black\">"))
        actionBar.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFBB86FC")))

        binding.button.setOnClickListener{
            profileupload()

        }
    }
    fun profileupload(){
        val name = binding.editTextTextPersonName11.text.toString()
        val phone = binding.editTextTextPersonName12.text.toString()
        val birthday = binding.editTextTextPersonName13.text.toString()

        auth = FirebaseAuth.getInstance()
        var email = auth.currentUser?.email.toString()
        val LittleMouseAt=email.indexOf("@")
        val emailname=email.substring(0,LittleMouseAt)
        database = FirebaseDatabase.getInstance().reference
        if (name.isNotEmpty() && phone.isNotEmpty() && birthday.isNotEmpty()){
            val upload = profile(name, phone, birthday)
            val test = test("QQ")
            database.child(emailname).child("Accounting").setValue(test)
            database.child(emailname).child("Profile").setValue(upload)
                .addOnCompleteListener{
                    startActivity(Intent(this, homepage::class.java))
                    Toast.makeText(this@ProfileActivity, "Upload Success", Toast.LENGTH_LONG).show()
                }
        }
        else{
            Toast.makeText(this@ProfileActivity, "Form must not be empty", Toast.LENGTH_LONG).show()
        }
    }
}