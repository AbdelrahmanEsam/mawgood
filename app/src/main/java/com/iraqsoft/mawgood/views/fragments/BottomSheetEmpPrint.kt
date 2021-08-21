package com.iraqsoft.mawgood.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.dialogfragment.viewBinding
import androidx.annotation.Nullable
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.iraqsoft.mawgood.R
import com.iraqsoft.mawgood.databinding.FragmentMainBinding
import com.iraqsoft.mawgood.databinding.PrintSuccededBottomSheetBinding
import com.iraqsoft.mawgood.util.toast
import com.iraqsoft.mawgood.viewmodels.FingerPrintViewModel
import com.iraqsoft.mawgood.viewmodels.MainFragmentViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class BottomSheetEmpPrint : BottomSheetDialogFragment() {

    private val binding: PrintSuccededBottomSheetBinding by viewBinding()
    private val args:BottomSheetEmpPrintArgs by navArgs()
    val fingerprintViewModel by viewModel<FingerPrintViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL,R.style.bottomSheetThemeRadius)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.closeImageView.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.fingerPrintImageView.setOnClickListener {
            binding.doneButton.visibility = View.VISIBLE
        }
    }



}