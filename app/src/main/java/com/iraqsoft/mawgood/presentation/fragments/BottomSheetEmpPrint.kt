package com.iraqsoft.mawgood.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.iraqsoft.mawgood.R
import com.iraqsoft.mawgood.databinding.PrintSuccededBottomSheetBinding
import com.iraqsoft.mawgood.data.model.GetResponseItem
import com.iraqsoft.mawgood.presentation.fragments.fingerPrintFragment.FingerPrintViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class BottomSheetEmpPrint : BottomSheetDialogFragment() {

    private val args:BottomSheetEmpPrintArgs by navArgs()
   private val fingerprintViewModel by viewModel<FingerPrintViewModel>()
    private lateinit var mViewDataBinding: PrintSuccededBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL,R.style.bottomSheetThemeRadius)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mViewDataBinding  = DataBindingUtil.inflate(inflater,
            R.layout.print_succeded_bottom_sheet, container, false)
        val mRootView = mViewDataBinding.root
        mViewDataBinding.lifecycleOwner = this
        return mRootView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.viewModel = fingerprintViewModel
        var emp : GetResponseItem? = null
        if(args.position != -1 ) {
            emp = args.selectedEmp!![args.position]
        }

        fingerprintViewModel.initFingerPrint(emp)
        fingerprintViewModel.getFingerPrint()

        mViewDataBinding.closeImageView.setOnClickListener {
            requireActivity().onBackPressed()
        }

        mViewDataBinding.fingerPrintImageView.setOnClickListener {
            mViewDataBinding.doneButton.visibility = View.VISIBLE
        }
    }

    override fun onStart() {
        super.onStart()
        //this forces the sheet to appear at max height even on landscape
        val behavior = BottomSheetBehavior.from(requireView().parent as View)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }



}