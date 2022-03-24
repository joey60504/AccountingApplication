package com.tom.accountingapplication.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.tom.accountingapplication.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment(),histortyadapter.OnItemClick{
    private var _binding : FragmentGalleryBinding?= null
    private val binding get() = _binding!!
    private lateinit var database: DatabaseReference
    override fun onCreateView (
        inflater:LayoutInflater,
        container:ViewGroup?,
        savedInstanceState:Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater,container,false)
        val root:View = binding.root
        dataselect()
        return root
    }
    fun dataselect(){
        database = FirebaseDatabase.getInstance().reference
        val dataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val root=dataSnapshot.value as HashMap<*,*>
                val accounting=root["accounting"] as HashMap<*,*>
                activity?.runOnUiThread {
                    binding.recyclerview1.apply {
                        val myAdapter = histortyadapter(this@GalleryFragment)
                        adapter = myAdapter
                        val manager = LinearLayoutManager(requireContext())
                        manager.orientation = LinearLayoutManager.VERTICAL
                        layoutManager = manager
                        myAdapter.dataList = accounting
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        database.addValueEventListener(dataListener)
    }
    override fun onItemClick(position: Int) {
    }
}