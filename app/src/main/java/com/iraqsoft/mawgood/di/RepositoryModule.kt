package com.iraqsoft.mawgood.di

import com.iraqsoft.mawgood.data.remote.ApiProvider
//import com.iraqsoft.mawgood.db.CountriesDao
//import com.iraqsoft.mawgood.repository.CountriesRepository
//import com.iraqsoft.mawgood.repository.CountriesRepositoryImpl
import android.content.Context
import com.iraqsoft.mawgood.data.dataBase.EmpNeedsToBeSyncedDao
import com.iraqsoft.mawgood.data.dataBase.EmployeesDao
import com.iraqsoft.mawgood.data.dataBase.SelectedBranchesDao
import com.iraqsoft.mawgood.data.dataBase.UserDao
import com.iraqsoft.mawgood.domain.repository.*
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {


    fun provideLoginRepo(api: ApiProvider, dao : UserDao): LoginRepositoryInterface {
        return LoginRepositoryImp(api,dao)
    }


    fun provideMainFragmentRepo(api: ApiProvider, userDao : UserDao, selectedBranchesDao: SelectedBranchesDao, empNeedsToBeSyncedDao: EmpNeedsToBeSyncedDao): MainFragmentRepositoryInterface {
        return MainFragmentRepositoryImp(api,userDao,selectedBranchesDao,empNeedsToBeSyncedDao)
    }

    fun provideDefineEmployeeRepo(api: ApiProvider, userDao : UserDao, selectedBranchesDao: SelectedBranchesDao, employeesDao: EmployeesDao): DefineEmployeeRepositoryInterface {
        return DefineEmployeeRepositoryEmp(api,userDao,selectedBranchesDao,employeesDao)
    }

    fun provideFingerPrintRepo(api: ApiProvider, employeesDao: EmployeesDao, empNeedsToBeSyncedDao: EmpNeedsToBeSyncedDao, context: Context): FingerPrintRpoInterface {
        return FingerPrintRepository(api,employeesDao,empNeedsToBeSyncedDao,context = context)
    }

    single { provideLoginRepo(get(), get()) }
    single { provideMainFragmentRepo(get(),get(),get(),get()) }
    single { provideDefineEmployeeRepo(get(),get(),get(),get()) }
    single { provideFingerPrintRepo(get(),get(),get(),androidContext())}


}