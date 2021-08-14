package com.iraqsoft.mawgood.di

//import com.iraqsoft.mawgood.db.CountriesDao
//import com.iraqsoft.mawgood.db.CountriesDatabase
import android.app.Application
import androidx.room.Room
import com.iraqsoft.mawgood.db.UserDatabase
import com.iraqsoft.mawgood.db.UserDeo
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    fun provideDatabase(application: Application): UserDatabase {
       return Room.databaseBuilder(application, UserDatabase::class.java, "users")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideCountriesDao(database: UserDatabase): UserDeo {
        return  database.userDeo
    }
//
    single { provideDatabase(androidApplication()) }
    single { provideCountriesDao(get()) }


}
