package com.iraqsoft.mawgood.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.iraqsoft.mawgood.R
import com.iraqsoft.mawgood.databinding.FragmentEnterYourCodeBinding
import com.iraqsoft.mawgood.util.toast
import com.iraqsoft.mawgood.views.activities.MainActivity2

class EnterYourCodeFragment : Fragment() {


    private lateinit var  binding : FragmentEnterYourCodeBinding
    private lateinit var nav: NavController


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_enter_your_code, container, false)
        binding.viewModel = (requireActivity() as MainActivity2).mainViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nav = Navigation.findNavController(view)
       cacheButtonListener()
        back()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as MainActivity2).mainViewModel.enteredCode.value = ""
    }

    private fun cacheButtonListener()
    {
        binding.cacheButton.setOnClickListener {
            if(  (requireActivity() as MainActivity2).mainViewModel.enteredCode.value.isNullOrEmpty()){
                toast(requireContext(),"")
            }else{
                if (  (requireActivity() as MainActivity2).mainViewModel.cachedBranches.value?.get(0)?.code.isNullOrEmpty()){
                     (requireActivity() as MainActivity2).mainViewModel.setCacheBranches()
                    (requireActivity() as MainActivity2).mainViewModel.error.observe(viewLifecycleOwner,{
                        if (it == "no errors") {
                            requireActivity().onBackPressed()
                        }
                    })
                    // to do
                    toast(requireContext(),"code added")
                }else{
                    if (  (requireActivity() as MainActivity2).mainViewModel.cachedBranches.value?.get(0)?.code
                        ==(requireActivity() as MainActivity2).mainViewModel.enteredCode.value){

                          nav.navigate(R.id.action_enterYourCodeFragment_to_defineEmployeeFragment)
                    }else{

                        // to do
                        toast(requireContext(),"wrong password")
                    }

                }
            }


        }
    }


    private fun back()
    {
        binding.backImageView.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }




}