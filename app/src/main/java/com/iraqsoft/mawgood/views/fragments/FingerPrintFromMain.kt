package com.iraqsoft.mawgood.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.iraqsoft.mawgood.R
import com.iraqsoft.mawgood.databinding.FragmentFingerPrintFromMainBinding
import com.iraqsoft.mawgood.databinding.FragmentFingerprintTestBinding
import com.iraqsoft.mawgood.util.toast
import com.iraqsoft.mawgood.viewmodels.FingerPrintViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel


class FingerPrintFromMain : Fragment() {
    private val fingerPrintViewModel by viewModel<FingerPrintViewModel>()
    private lateinit var mViewDataBinding: FragmentFingerPrintFromMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        mViewDataBinding.fingerPrintImageView.setOnClickListener {
            mViewDataBinding.detailsConstraint.visibility = View.VISIBLE
        }

    }


}