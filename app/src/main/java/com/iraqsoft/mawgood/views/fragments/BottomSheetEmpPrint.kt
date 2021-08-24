package com.iraqsoft.mawgood.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.dialogfragment.viewBinding
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.iraqsoft.mawgood.R
import com.iraqsoft.mawgood.databinding.FragmentFingerprintTestBinding
import com.iraqsoft.mawgood.databinding.FragmentMainBinding
import com.iraqsoft.mawgood.databinding.PrintSuccededBottomSheetBinding
import com.iraqsoft.mawgood.db.model.GetResponseItem
import com.iraqsoft.mawgood.util.toast
import com.iraqsoft.mawgood.viewmodels.FingerPrintViewModel
import com.iraqsoft.mawgood.viewmodels.MainFragmentViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class BottomSheetEmpPrint : BottomSheetDialogFragment() {

    private val args:BottomSheetEmpPrintArgs by navArgs()
    val fingerprintViewModel by viewModel<FingerPrintViewModel>()
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
        var directions = 2 ;
        if(args?.position != -1 ) {
            emp = args?.selectedEmp!![args?.position]
            directions = 1
        }

        fingerprintViewModel.initFingerPrint(directions , emp)
        fingerprintViewModel.getFingerPrint()

        mViewDataBinding.closeImageView.setOnClickListener {
            requireActivity().onBackPressed()
        }

        mViewDataBinding.fingerPrintImageView.setOnClickListener {
            mViewDataBinding.doneButton.visibility = View.VISIBLE
        }
    }



}