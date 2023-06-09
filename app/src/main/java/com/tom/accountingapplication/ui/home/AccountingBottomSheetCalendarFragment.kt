package com.tom.accountingapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tom.accountingapplication.databinding.FragmentBottomsheetCalendarBinding

class AccountingBottomSheetCalendarFragment(private val onItemClick: (Int, Int, Int) -> Unit) :
    BottomSheetDialogFragment() {

    private var _binding: FragmentBottomsheetCalendarBinding? = null
    private val binding get() = _binding!!

    companion object {
        operator fun invoke(): AccountingBottomSheetTagFragment {
            return AccountingBottomSheetTagFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomsheetCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.calendarView.setOnDateChangeListener { _, year, month, day ->
            onItemClick(year, month, day)
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}