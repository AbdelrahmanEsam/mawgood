package com.iraqsoft.mawgood.presentation.fragments.mainFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.iraqsoft.mawgood.R
import com.iraqsoft.mawgood.databinding.FragmentMainBinding
import com.iraqsoft.mawgood.presentation.activities.mainActivity.MainActivity


class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding: FragmentMainBinding by viewBinding()
    private lateinit var nav: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nav = Navigation.findNavController(view)
        cachedBranchesObserver()
        listeners()


    }

    private fun listeners(){
        settingsListener()
        fingerprintListener()
    }


    private fun settingsListener()
    {
        binding.settingsButton.setOnClickListener {
            navigateToEnterYourCode()
        }
    }

    private fun fingerprintListener()
    {
        binding.cardViewFingerPrint.setOnClickListener {
          navigateToFingerprintBottomSheet()

        }
    }





    private fun cachedBranchesObserver()
    {
        (requireActivity() as MainActivity).mainViewModel.cachedBranches.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                if (it[0].code.isNullOrEmpty()) {
                    navigateToEnterYourCode()
                }
            } else {
                navigateToBottomSheet()
            }
        }

    }



    private fun navigateToBottomSheet()
    {
        nav.navigate(R.id.action_mainFragment_to_bottomSheetCompanySelector)
    }

    private fun navigateToEnterYourCode(){
        nav.navigate(R.id.action_mainFragment_to_enterYourCodeFragment)
    }

    private fun navigateToFingerprintBottomSheet()
    {
        nav.navigate(MainFragmentDirections.actionMainFragmentToFingerPrintFromMain())
    }

}