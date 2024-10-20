package com.example.mvi_plus_compose_plus_clean.di

import com.example.mvi_plus_compose_plus_clean.data.MovieRepositoryImpl
import com.example.mvi_plus_compose_plus_clean.data.framework.RetrofitInstance
import com.example.mvi_plus_compose_plus_clean.data.service_api.MovieApi
import com.example.mvi_plus_compose_plus_clean.domain.MovieRepository
import com.example.mvi_plus_compose_plus_clean.presentation.MovieListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

//any DI is not part of clean architecture layers, it's tend to be in the outer layer of the app
val appModule = module {
    single {
        RetrofitInstance.getRetrofitInstance().create(MovieApi::class.java)
    }
    //or factory when you need different instances each time MovieRepository needed
    single<MovieRepository> { MovieRepositoryImpl(get()) }

    viewModel {
        MovieListViewModel(repository = get())
    }
}
