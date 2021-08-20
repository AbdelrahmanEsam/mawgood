package com.iraqsoft.mawgood.di

//import com.iraqsoft.mawgood.db.CountriesDao
//import com.iraqsoft.mawgood.db.CountriesDatabase
import android.app.Application
import androidx.room.Room
import com.iraqsoft.mawgood.db.EmployeesDao
import com.iraqsoft.mawgood.db.UserDatabase
import com.iraqsoft.mawgood.db.UserDao
import com.iraqsoft.mawgood.db.SelectedBranchesDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    fun provideUsersDatabase(application: Application): UserDatabase {
       return Room.databaseBuilder(application, UserDatabase::class.java, "users")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideUsersDao(database: UserDatabase): UserDao {
        return  database.userDao
    }

    fun provideBranchesDao(database: UserDatabase): SelectedBranchesDao {
        return  database.branchesDao
    }

    fun provideEmployeesDao(database: UserDatabase): EmployeesDao {
        return  database.employeesDao
    }


//
    single { provideUsersDatabase(androidApplication()) }
    single { provideUsersDao(get()) }
    single { provideBranchesDao(get()) }
    single { provideEmployeesDao(get()) }


}
