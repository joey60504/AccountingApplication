package com.tom.accountingapplication.ui.history.calendarfilter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tom.accountingapplication.R
import com.tom.accountingapplication.accountingModel.DateEnum
import com.tom.accountingapplication.accountingModel.FilterDate
import com.tom.accountingapplication.databinding.FragmentBottomsheetHistoryCalendarBinding
import com.tom.accountingapplication.ui.home.AccountingBottomSheetTagFragment
import java.text.SimpleDateFormat

class HistoryFilterCalendarFragment(
    private val date: FilterDate?,
    private val onItemClick: (String) -> Unit
) :
    BottomSheetDialogFragment() {

    private var _binding: FragmentBottomsheetHistoryCalendarBinding? = null
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
        _binding = FragmentBottomsheetHistoryCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (date?.isFiltered == true) {
            if ("~" in date.calendar) {
                binding.txtFilterCalendarFirst.text = date.calendar.split("~").firstOrNull()
                binding.txtFilterCalendarSecond.text = date.calendar.split("~")[1]
            } else {
                binding.txtFilterCalendarFirst.text = date.calendar
                binding.txtFilterCalendarSecond.text = date.calendar
            }
        } else {
            binding.txtFilterCalendarFirst.text = "起點"
            binding.txtFilterCalendarSecond.text = "終點"
        }
        binding.txtFilterCalendarFirst.setOnClickListener {
            binding.cbCalendarFilter.isChecked = false
            when (date?.state) {
                DateEnum.DATE -> {
                    val bottomSheetFragment = HistoryFilterCalendarDateFragment(
                        onItemClick = { year, month, day ->
                            binding.txtFilterCalendarFirst.text = "${year}/${month}/${day}"
                        }
                    )
                    bottomSheetFragment.show(
                        requireActivity().supportFragmentManager,
                        bottomSheetFragment.tag
                    )
                }

                DateEnum.MONTH -> {
                    val bottomSheetFragment = HistoryFilterCalendarMonthFragment(
                        onItemClick = { year, month ->
                            binding.txtFilterCalendarFirst.text = "${year}/${month}"
                        }
                    )
                    bottomSheetFragment.show(
                        requireActivity().supportFragmentManager,
                        bottomSheetFragment.tag
                    )
                }

                DateEnum.ALL -> {}
                else -> {}
            }
        }
        binding.txtFilterCalendarSecond.setOnClickListener {
            binding.cbCalendarFilter.isChecked = false
            when (date?.state) {
                DateEnum.DATE -> {
                    val bottomSheetFragment = HistoryFilterCalendarDateFragment(
                        onItemClick = { year, month, day ->
                            binding.txtFilterCalendarSecond.text = "${year}/${month}/${day}"
                        }
                    )
                    bottomSheetFragment.show(
                        requireActivity().supportFragmentManager,
                        bottomSheetFragment.tag
                    )
                }

                DateEnum.MONTH -> {
                    val bottomSheetFragment = HistoryFilterCalendarMonthFragment(
                        onItemClick = { year, month ->
                            binding.txtFilterCalendarSecond.text = "${year}/${month}"
                        }
                    )
                    bottomSheetFragment.show(
                        requireActivity().supportFragmentManager,
                        bottomSheetFragment.tag
                    )
                }

                DateEnum.ALL -> {}
                else -> {}
            }
        }
        binding.cbCalendarFilter.setOnClickListener {
            if (binding.cbCalendarFilter.isChecked) {
                binding.txtFilterCalendarSecond.text = binding.txtFilterCalendarFirst.text
            }
        }
        binding.btnCalendarSubmit.setOnClickListener {

            val format = if (date?.state == DateEnum.DATE) {
                SimpleDateFormat("yyyy/MM/dd")
            } else {
                SimpleDateFormat("yyyy/M")
            }

            val firstDate = format.parse(binding.txtFilterCalendarFirst.text.toString())
            val secondDate = format.parse(binding.txtFilterCalendarSecond.text.toString())

            if (firstDate?.after(secondDate) == true) {
                val layout: View = LayoutInflater.from(requireContext()).inflate(
                    R.layout.custom_toast,
                    null
                )
                val text: TextView = layout.findViewById(R.id.custom_toast_text)
                text.text = "起點不可小於終點唷~"
                val toast = Toast(requireContext())
                toast.duration = Toast.LENGTH_SHORT
                toast.view = layout
                toast.show()
            } else if (firstDate?.before(secondDate) == true) {
                onItemClick("${binding.txtFilterCalendarFirst.text}~${binding.txtFilterCalendarSecond.text}")
                dismiss()
            } else {
                onItemClick(binding.txtFilterCalendarFirst.text.toString())
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}