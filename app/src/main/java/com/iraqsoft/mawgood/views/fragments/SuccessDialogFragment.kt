package com.iraqsoft.mawgood.views.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.dialogfragment.viewBinding
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.iraqsoft.mawgood.R
import com.iraqsoft.mawgood.databinding.FragmentSuccessDialogBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SuccessDialogFragment : DialogFragment() {
    private val args:SuccessDialogFragmentArgs by navArgs()
    private val binding: FragmentSuccessDialogBinding by viewBinding()

    override fun getTheme() = R.style.RoundedCornersDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getEmpAndSetData()
        dismissTimer()

    }

    private fun getEmpAndSetData()
    {
       binding.nameTextView.text = args.employeeName
    }

    private fun dismissTimer()
    {
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            dialog?.dismiss()
        }
    }




}