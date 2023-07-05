package com.tom.accountingapplication.ui.history.calendarfilter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tom.accountingapplication.databinding.FragmentBottomsheetCalendarMonthBinding
import com.tom.accountingapplication.ui.home.AccountingBottomSheetTagFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class HistoryFilterCalendarMonthFragment(private val onItemClick: (Int, Int) -> Unit) :
    BottomSheetDialogFragment() {

    private var _binding: FragmentBottomsheetCalendarMonthBinding? = null
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
        _binding = FragmentBottomsheetCalendarMonthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy/M", Locale.getDefault())
        val today = dateFormat.format(calendar.time)
        val splitDay = today.split("/")
        var selectedYear = splitDay.firstOrNull()
        var selectedMonth = splitDay[1] + "月"

        val years = Array(51) { index -> (2000 + index).toString() }
        val months = arrayOf("1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月")

        binding.yearPicker.minValue = 0
        binding.yearPicker.maxValue = years.size - 1
        binding.yearPicker.displayedValues = years
        binding.yearPicker.value =  years.indexOf(selectedYear)

        binding.monthPicker.minValue = 0
        binding.monthPicker.maxValue = months.size - 1
        binding.monthPicker.displayedValues = months
        binding.monthPicker.value = months.indexOf(selectedMonth)

        binding.yearPicker.setOnValueChangedListener { _, _, newVal ->
            selectedYear = years[newVal]
            // 執行相應的操作，例如更新顯示的數據或觸發其他邏輯
        }

        binding.monthPicker.setOnValueChangedListener { _, _, newVal ->
            selectedMonth = months[newVal]
        }

        binding.btnFilterMonthConfirm.setOnClickListener {
            onItemClick(selectedYear?.toInt() ?: 2023 ,selectedMonth.substringBefore("月").toInt())
            dismiss()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}