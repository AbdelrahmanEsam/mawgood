package com.iraqsoft.mawgood.views.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.iraqsoft.mawgood.R
import com.iraqsoft.mawgood.databinding.FragmentMainBinding
import com.iraqsoft.mawgood.viewmodels.LoginViewModel
import com.iraqsoft.mawgood.viewmodels.MainFragmentViewModel
import com.iraqsoft.mawgood.views.activities.MainActivity2
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel


class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding: FragmentMainBinding by viewBinding()
    private lateinit var nav: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nav = Navigation.findNavController(view)
        cachedBranchesObserver()
        settingsListener()





    }


    private fun settingsListener()
    {
        binding.settingsButton.setOnClickListener {
            navigateToEnterYourCode()
        }
    }

    private fun cachedBranchesObserver()
    {
        (requireActivity() as MainActivity2).mainViewModel.cachedBranches.observe(viewLifecycleOwner,{
                if(it.isNotEmpty())
                {
                    if(it[0].code.isNullOrEmpty()){
                        navigateToEnterYourCode()
                    }
                }else{
                    navigateToBottomSheet()
                }
            })

    }



    private fun navigateToBottomSheet()
    {
        nav.navigate(R.id.action_mainFragment_to_bottomSheetCompanySelector)
    }

    private fun navigateToEnterYourCode(){
        nav.navigate(R.id.action_mainFragment_to_enterYourCodeFragment)
    }


}