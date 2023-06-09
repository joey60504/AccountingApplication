package com.tom.accountingapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tom.accountingapplication.accountingModel.TagItem
import com.tom.accountingapplication.databinding.FragmentBottomsheetTagBinding

class AccountingBottomSheetTagFragment(private val onItemClick: (TagItem) -> Unit) : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomsheetTagBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AccountingViewModel by viewModels()
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
        _binding = FragmentBottomsheetTagBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val itemAdapter = AccountingBottomSheetTagAdapter(
            onItemClick = { tag ->
                onItemClick(tag)
                dismiss()
            }
        )
        viewModel.displayTag.observe(this){
            itemAdapter.tagList = it.tagList
            binding.recyclerTag.apply {
                setHasFixedSize(true)
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                this.adapter = itemAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}