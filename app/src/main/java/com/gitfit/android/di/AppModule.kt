package com.gitfit.android.di

import com.gitfit.android.api.GitFitServiceAPI
import com.gitfit.android.repos.GitFitAPIRepository
import com.gitfit.android.ui.login.LoginViewModel
import com.gitfit.android.ui.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { GitFitAPIRepository(get()) }
    single { GitFitServiceAPI() }
    single { get<GitFitServiceAPI>().getGitFitService() }

    viewModel { HomeViewModel() }
    viewModel { LoginViewModel() }
}