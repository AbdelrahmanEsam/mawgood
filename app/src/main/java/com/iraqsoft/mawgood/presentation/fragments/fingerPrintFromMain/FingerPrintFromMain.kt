package com.iraqsoft.mawgood.presentation.fragments.fingerPrintFromMain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.iraqsoft.mawgood.R
import com.iraqsoft.mawgood.databinding.FragmentFingerPrintFromMainBinding
import org.koin.android.viewmodel.ext.android.viewModel


class FingerPrintFromMain : BottomSheetDialogFragment() {
    private val fingerPrintViewModel by viewModel<MatchFingerprintViewModel>()
    private lateinit var mViewDataBinding: FragmentFingerPrintFromMainBinding
    private lateinit var nav: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL,R.style.bottomSheetThemeRadius)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding  = DataBindingUtil.inflate(inflater, R.layout.fragment_finger_print_from_main, container, false)
        mViewDataBinding.viewModel = fingerPrintViewModel
        val mRootView = mViewDataBinding.root
        mViewDataBinding.lifecycleOwner = this
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nav = Navigation.findNavController(requireParentFragment().requireView())
        fingerPrintViewModel.initFingerPrint()
        fingerPrintViewModel.getFingerPrint()
        mViewDataBinding.closeImageView.setOnClickListener {
            requireActivity().onBackPressed()
        }
        fingerPrintViewModel.test()
        closeObserver()
    }

    private fun closeObserver(){
        fingerPrintViewModel.emp.observe(viewLifecycleOwner) {
            if (it != null && fingerPrintViewModel.fingerPrintMatch.value != 0) {
                nav.navigate(
                    FingerPrintFromMainDirections.actionFingerPrintFromMainToSuccessDialogFragment(
                        it.displayName
                    )
                )
            }

        }
    }











}