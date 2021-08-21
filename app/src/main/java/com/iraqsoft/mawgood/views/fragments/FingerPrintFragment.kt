package com.iraqsoft.mawgood.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.iraqsoft.mawgood.R
import com.iraqsoft.mawgood.databinding.FragmentFingerprintTestBinding
import com.iraqsoft.mawgood.viewmodels.FingerPrintViewModel
import com.iraqsoft.mawgood.views.activities.MainActivity
import kotlinx.android.synthetic.main.fragment_fingerprint_test.*
import org.koin.android.viewmodel.ext.android.viewModel

class FingerPrintFragment : Fragment() {
    private val fingerPrintViewModel by viewModel<FingerPrintViewModel>() ;
    private lateinit var mViewDataBinding: FragmentFingerprintTestBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mViewDataBinding  = DataBindingUtil.inflate(inflater,
            R.layout.fragment_fingerprint_test, container, false)
        val mRootView = mViewDataBinding.root
        mViewDataBinding.lifecycleOwner = this
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.viewModel = fingerPrintViewModel
//        fingerPrintViewModel.initFingerPrint()
        btn_enrol.setOnClickListener{
            fingerPrintViewModel.getFingerPrint();
        }
    }



}