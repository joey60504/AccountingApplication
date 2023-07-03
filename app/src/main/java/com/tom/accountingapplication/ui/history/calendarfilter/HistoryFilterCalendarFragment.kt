package com.tom.accountingapplication.ui.history.calendarfilter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tom.accountingapplication.accountingModel.DateEnum
import com.tom.accountingapplication.databinding.FragmentBottomsheetHistoryCalendarBinding
import com.tom.accountingapplication.ui.home.AccountingBottomSheetTagFragment


class HistoryFilterCalendarFragment(
    private val dateEnum: DateEnum,
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtFilterCalendarFirst.text = "起點"
        binding.txtFilterCalendarSecond.text = "終點"
        binding.txtFilterCalendarFirst.setOnClickListener {
            binding.cbCalendarFilter.isChecked = false
            when(dateEnum){
                DateEnum.DATE ->{
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
                DateEnum.MONTH->{

                }
                DateEnum.ALL->{}
            }
        }
        binding.txtFilterCalendarSecond.setOnClickListener {
            binding.cbCalendarFilter.isChecked = false
            when(dateEnum){
                DateEnum.DATE ->{
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
                DateEnum.MONTH->{

                }
                DateEnum.ALL->{}
            }
        }
        binding.cbCalendarFilter.setOnClickListener {
            if(binding.cbCalendarFilter.isChecked){
                binding.txtFilterCalendarSecond.text = binding.txtFilterCalendarFirst.text
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}