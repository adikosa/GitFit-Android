package com.gitfit.android.di

import com.gitfit.android.PreferenceProvider
import com.gitfit.android.api.RetrofitClient
import com.gitfit.android.repos.GitFitAPIRepository
import com.gitfit.android.ui.login.LoginViewModel
import com.gitfit.android.ui.register.RegisterViewModel
import com.gitfit.android.ui.home.home.HomeViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { GitFitAPIRepository(get()) }
    single { RetrofitClient() }
    single { get<RetrofitClient>().getGitFitService() }
    single { PreferenceProvider(androidApplication().applicationContext) }

    viewModel { HomeViewModel() }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { RegisterViewModel(get(), get()) }
}