package com.tom.accountingapplication.datashow.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.tom.accountingapplication.accountingModel.UploadData
import com.tom.accountingapplication.databinding.DialogAccountingDataBinding
import com.tom.accountingapplication.ui.home.AccountingBottomSheetTagFragment

class AccountingDataDetailDialog(private var item: UploadData) : DialogFragment() {
    private var _binding: DialogAccountingDataBinding? = null
    private val binding get() = _binding!!
    private val viewModelDataDetail: AccountingDataDetailViewModel by viewModels()

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
        _binding = DialogAccountingDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtDialogTitle.text = item.title
        binding.txtDialogDate.text = item.date
        binding.txtDialogTag.text = item.tag
        binding.etDialogPrice.setText(item.price.toString())
        binding.etDialogRemark.setText(item.remark)

        binding.imgCancel.setOnClickListener {
            dismiss()
        }
        binding.btnDialogDelete.setOnClickListener {
            val customDialog = AccountingDataDetailAlertDialog(
                onItemClick = { b: Boolean ->
                    if (b) {
                        viewModelDataDetail.onDeleteClick(item)
                        dismiss()
                    } else {
                        dismiss()
                    }
                },
                "確定刪除?",
            )
            customDialog.show(requireActivity().supportFragmentManager, "CustomDialog")
        }
        binding.btnDialogUpdate.setOnClickListener {
            val customDialog = AccountingDataDetailAlertDialog(
                onItemClick = { b: Boolean ->
                    if (b) {
                        if (binding.etDialogPrice.text.isNotEmpty()) {
                            viewModelDataDetail.onUpdateClick(
                                binding.txtDialogTitle.text.toString(),
                                binding.txtDialogDate.text.toString(),
                                binding.txtDialogTag.text.toString(),
                                binding.etDialogPrice.text.toString().toInt(),
                                binding.etDialogRemark.text.toString(),
                                item.type,
                                item
                            )
                        }
                        dismiss()
                    } else {
                        dismiss()
                    }
                },
                "確定更新?",
            )
            customDialog.show(requireActivity().supportFragmentManager, "CustomDialog")
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}