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
import com.iraqsoft.mawgood.databinding.LoginFragmentBinding
import com.iraqsoft.mawgood.util.toast
import com.iraqsoft.mawgood.viewmodels.LoginViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class LoginFragment : Fragment() {

    private val loginViewModel by viewModel<LoginViewModel>()
    private lateinit var  binding :LoginFragmentBinding
    private lateinit var nav: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false)
        binding.viewModel = loginViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nav = Navigation.findNavController(view)
        binding.loginButton.setOnClickListener {
            loginViewModel.login()
            loginObserver()
        }
        errorObserver()







    }









    private fun loginObserver()
    {
        loginViewModel.loginSuccess.observe(viewLifecycleOwner,{
            if(it == "success"){
                nav.navigate(R.id.action_loginFragment_to_mainFragment)
            }
        })
    }


    private  fun errorObserver()
    {
        loginViewModel.errorMessage.observe(viewLifecycleOwner,{
            toast(requireContext(), it)
        })
    }

}