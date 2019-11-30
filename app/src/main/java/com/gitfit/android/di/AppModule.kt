package com.gitfit.android.di

import com.gitfit.android.api.RetrofitClient
import com.gitfit.android.repos.GitFitAPIRepository
import com.gitfit.android.ui.login.LoginViewModel
import com.gitfit.android.ui.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { GitFitAPIRepository(get()) }
    single { RetrofitClient() }
    single { get<RetrofitClient>().getGitFitService() }

    viewModel { HomeViewModel() }
    viewModel { LoginViewModel(get()) }
}