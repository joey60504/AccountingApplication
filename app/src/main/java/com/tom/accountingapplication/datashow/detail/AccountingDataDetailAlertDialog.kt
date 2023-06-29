package com.tom.accountingapplication.datashow.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.tom.accountingapplication.databinding.DialogAccountingDataAlertBinding
import com.tom.accountingapplication.ui.home.AccountingBottomSheetTagFragment

class AccountingDataDetailAlertDialog(
    private val onItemClick: (Boolean) -> Unit,
    private val title: String
) : DialogFragment() {
    private var _binding: DialogAccountingDataAlertBinding? = null
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
        _binding = DialogAccountingDataAlertBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtDialogAlertTitle.text = title

        binding.btnDialogCancel.setOnClickListener {
            onItemClick(false)
            dismiss()
        }
        binding.btnDialogConfirm.setOnClickListener {
            onItemClick(true)
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}