package com.iraqsoft.mawgood.di


import com.iraqsoft.mawgood.viewmodels.*
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    // Specific viewModel pattern to tell Koin how to build CountriesViewModel
    viewModel {
        FingerPrintViewModel(repo = get())
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