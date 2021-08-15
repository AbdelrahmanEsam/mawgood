package com.iraqsoft.mawgood.di

//import com.iraqsoft.mawgood.viewmodel.CountriesViewModel

import com.iraqsoft.mawgood.viewmodels.FingerPrintViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    // Specific viewModel pattern to tell Koin how to build CountriesViewModel
    viewModel {
        FingerPrintViewModel(repo = get())
    }



}