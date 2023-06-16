package com.tom.accountingapplication.ui.login

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.tom.accountingapplication.databinding.ActivitySignupBinding
import com.tom.accountingapplication.ui.home.AccountingActivity

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var progressDialog : ProgressDialog
    private lateinit var firebaseAuth : FirebaseAuth
    private var email=""
    private var password=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog= ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Create Account In....")
        progressDialog.setCanceledOnTouchOutside(false)
        firebaseAuth= FirebaseAuth.getInstance()

        binding.signupcomplete.setOnClickListener {
            vaildataData()
        }
    }

    private fun vaildataData() {
        email=binding.email2.text.toString().trim()
        password=binding.password2.text.toString().trim()
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.email2.error="Invalid email format"
        }
        else if(TextUtils.isEmpty(password)){
            binding.password2.error="Please enter password"
        }
        else if(password.length<6){
            binding.password2.error="Password must atleast 6 chracters long"
        }
        else{
            firebaseSignup()
        }
    }

    private fun firebaseSignup() {
        progressDialog.show()
        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this,"Account created with email${email}",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, AccountingActivity::class.java))
                finish()
            }
            .addOnFailureListener {e->
                progressDialog.dismiss()
                Toast.makeText(this,"SignUp Failed due to ${e.message}",Toast.LENGTH_SHORT).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}