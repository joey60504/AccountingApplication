package com.tom.accountingapplication.ui.slideshow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.tom.accountingapplication.databinding.FragmentSlideshowBinding

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null
    private val binding get() = _binding!!

    lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.imageButton3.setOnClickListener {
            startActivity(Intent(requireContext(), ModifyPersonalInformation::class.java))
        }
        getData()
        return root
    }

    private fun getData() {
        auth = FirebaseAuth.getInstance()
        var userEmail = auth.currentUser?.email.toString()
        val findLittleMouseAt = userEmail.indexOf("@")
        val userEmailValue = userEmail.substring(0, findLittleMouseAt)
        var database = FirebaseDatabase.getInstance().reference

        database.child(userEmailValue).child("Profile").get().addOnSuccessListener {
            val profile = it.value as HashMap<*, *>
            var name = profile["name"].toString()
            var birthday = profile["birthday"].toString()
            var phone = profile["phone"].toString()
            binding.textView9.text = name
            binding.textView19.text = birthday
            binding.textView21.text = phone
            binding.textView23.text = userEmail
        }
    }
}