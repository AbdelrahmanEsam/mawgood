package com.iraqsoft.mawgood.di

import com.iraqsoft.mawgood.ApiProvider
//import com.iraqsoft.mawgood.db.CountriesDao
//import com.iraqsoft.mawgood.repository.CountriesRepository
//import com.iraqsoft.mawgood.repository.CountriesRepositoryImpl
import android.content.Context
import com.iraqsoft.mawgood.db.EmployeesDao
import com.iraqsoft.mawgood.db.SelectedBranchesDao
import com.iraqsoft.mawgood.db.UserDao
import com.iraqsoft.mawgood.repo.fingerPrint.FingerPrintRepo
import com.iraqsoft.mawgood.repo.fingerPrint.FingerPrintRepoImp
import com.iraqsoft.mawgood.repository.*
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {

    fun provideFingerPrintRepo(api: ApiProvider): FingerPrintRepo {
        return FingerPrintRepoImp(api)
    }

//    fun provideHomeRepository(api: ApiProvider, context: Context, dao : UserDeo): HomeRepository {
//        return HomeRepository(api, context, dao)
//    }


    fun provideLoginRepo(api: ApiProvider, dao : UserDao): LoginRepositoryInterface {
        return LoginRepositoryImp(api,dao)
    }


    fun provideMainFragmentRepo(userDao : UserDao,selectedBranchesDao: SelectedBranchesDao): MainFragmentRepositoryInterface {
        return MainFragmentRepositoryImp(userDao,selectedBranchesDao)
    }

    fun provideDefineEmployeeRepo(api: ApiProvider,userDao : UserDao,selectedBranchesDao: SelectedBranchesDao,employeesDao: EmployeesDao): DefineEmployeeRepositoryInterface {
        return DefineEmployeeRepositoryEmp(api,userDao,selectedBranchesDao,employeesDao)
    }

    single { provideFingerPrintRepo(get()) }
    single { provideLoginRepo(get(), get()) }
    single { provideMainFragmentRepo(get(),get()) }
    single { provideDefineEmployeeRepo(get(),get(),get(),get()) }


}