package com.iraqsoft.mawgood.core

import com.iraqsoft.mawgood.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import androidx.multidex.MultiDexApplication

class MawgoodApplication : MultiDexApplication(){

    override fun onCreate() {
        super.onCreate()

//        if (Build.VERSION.SDK_INT < 19) {
//            try {
//                ProviderInstaller.installIfNeeded(this)
//            } catch (e: GooglePlayServicesRepairableException) {
//                GoogleApiAvailability.getInstance().showErrorNotification(this, e.connectionStatusCode)
//            }
//        }
      startKoin {
            androidLogger()
            androidContext(this@MawgoodApplication)
            modules(
                apiModule,
                viewModelModule,
                repositoryModule,
                networkModule,
                databaseModule
            )
        }
    }
}