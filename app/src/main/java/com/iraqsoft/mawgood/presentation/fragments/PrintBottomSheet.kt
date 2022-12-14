package com.iraqsoft.mawgood.presentation.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.dialogfragment.viewBinding
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.iraqsoft.mawgood.R
import com.iraqsoft.mawgood.databinding.PrintBottomSheetBinding
import com.iraqsoft.mawgood.data.model.GetResponse

class PrintBottomSheet : BottomSheetDialogFragment() {
    private val binding: PrintBottomSheetBinding by viewBinding()
    private lateinit var nav: NavController
    private val args:PrintBottomSheetArgs by navArgs()

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

        nav = Navigation.findNavController(requireParentFragment().requireView())
        binding.cardViewFingerPrint.setOnClickListener {
         nav.navigate(PrintBottomSheetDirections.actionPrintBottomSheetToBottomSheetEmpPrint(args.selectedEmp,args.position))
        }
        setData()

    }

    override fun onStart() {
        super.onStart()
        //this forces the sheet to appear at max height even on landscape
        val behavior = BottomSheetBehavior.from(requireView().parent as View)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun setData(){
//      binding.nameTextView.text = args.selectedEmp[args.position].displayName
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        nav.previousBackStackEntry?.savedStateHandle?.set("key", "yep")
        nav.popBackStack()
    }


}