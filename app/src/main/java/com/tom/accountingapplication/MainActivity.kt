package com.tom.accountingapplication

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.tom.accountingapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var actionBar : ActionBar
    private lateinit var progressDialog : ProgressDialog
    private lateinit var firebaseAuth : FirebaseAuth
    private var email=""
    private var password=""
    lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar = supportActionBar!!
        actionBar.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFBB86FC")))
        actionBar.setTitle(Html.fromHtml("<font color=\"black\">"+"Login" ))
        //getActionBar()/* or getSupportActionBar() */.setTitle(Html.fromHtml("<font color=\"red\">" + getString(R.string.app_name) + "</font>"));
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
                startActivity(Intent(this, homepage::class.java))
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
            startActivity(Intent(this@MainActivity, homepage::class.java))
            finish()
        }
    }
}