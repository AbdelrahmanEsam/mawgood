package com.iraqsoft.mawgood.di

import com.iraqsoft.mawgood.ApiProvider
//import com.iraqsoft.mawgood.db.CountriesDao
//import com.iraqsoft.mawgood.repository.CountriesRepository
//import com.iraqsoft.mawgood.repository.CountriesRepositoryImpl
import android.content.Context
import com.iraqsoft.mawgood.db.UserDeo
import com.iraqsoft.mawgood.repo.fingerPrint.FingerPrintRepo
import com.iraqsoft.mawgood.repo.fingerPrint.FingerPrintRepoImp
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {

    fun provideFingerPrintRepo(api: ApiProvider, context: Context, dao : UserDeo): FingerPrintRepo {
        return FingerPrintRepoImp(api, context, dao)
    }

//    fun provideHomeRepository(api: ApiProvider, context: Context, dao : UserDeo): HomeRepository {
//        return HomeRepository(api, context, dao)
//    }
    single { provideFingerPrintRepo(get(), androidContext(), get()) }


}