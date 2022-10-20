package com.iraqsoft.mawgood.di

import com.iraqsoft.mawgood.data.remote.ApiProvider
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {

    fun provideApiProvider(retrofit: Retrofit): ApiProvider {
        return retrofit.create(ApiProvider::class.java)
    }
    single { provideApiProvider(get()) }

}