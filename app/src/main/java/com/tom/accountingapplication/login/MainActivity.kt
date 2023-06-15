package com.tom.accountingapplication.login

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.tom.accountingapplication.databinding.ActivityMainBinding
import com.tom.accountingapplication.ui.home.AccountingActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var progressDialog : ProgressDialog
    private lateinit var firebaseAuth : FirebaseAuth
    private var email=""
    private var password=""
    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Logging In...")
        progressDialog.setCanceledOnTouchOutside(false)
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()
        binding.signupbtn.setOnClickListener{
            startActivity(Intent(this@MainActivity, SignupActivity::class.java))
        }
        binding.Login.setOnClickListener {
            validataData()
        }
    }


    private fun validataData() {
        email = binding.Email.text.toString().trim()
        password = binding.Password.text.toString().trim()
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.Email.error = "Invalid email format"
        }
        else if(TextUtils.isEmpty(password)){
            binding.Password.error = "Please enter password"
        }
        else{
            firebaseLogin()
        }
    }
    private fun firebaseLogin() {
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this@MainActivity, "LoggedIn as $email", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, AccountingActivity::class.java))
                finish()
            }
            .addOnFailureListener {e->
                progressDialog.dismiss()
                Toast.makeText(this@MainActivity, "Login failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUser(){
        val firebaseUser=firebaseAuth.currentUser
        if(firebaseUser!=null){
            startActivity(Intent(this@MainActivity, AccountingActivity::class.java))
            finish()
        }
    }
}