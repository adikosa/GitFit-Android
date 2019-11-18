package com.gitfit.android.ui

import android.app.Application
import com.gitfit.android.repos.AuthRepository
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApplication : Application() {
    private val myModule = module {
        single { AuthRepository() }
        viewModel { HomeViewModel(get()) }
    }


    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(myModule)
        }
    }
}