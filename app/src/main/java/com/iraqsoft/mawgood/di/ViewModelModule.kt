package com.iraqsoft.mawgood.di


import com.iraqsoft.mawgood.presentation.activities.mainActivity.MainFragmentViewModel
import com.iraqsoft.mawgood.presentation.fragments.defineEmplyeeFragment.DefineEmployeeViewModel
import com.iraqsoft.mawgood.presentation.fragments.fingerPrintFragment.FingerPrintViewModel
import com.iraqsoft.mawgood.presentation.fragments.fingerPrintFromMain.MatchFingerprintViewModel
import com.iraqsoft.mawgood.presentation.fragments.loginFragment.LoginViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    // Specific viewModel pattern to tell Koin how to build CountriesViewModel
    viewModel {
        FingerPrintViewModel(fingerprintRepo = get())
    }

    viewModel {
        MatchFingerprintViewModel(fingerprintRepo = get())
    }


    viewModel {
       LoginViewModel(authRepo = get())
    }



    viewModel {
        MainFragmentViewModel(mainRepo = get())
    }

    viewModel {
        DefineEmployeeViewModel(get())
    }





}