package com.example.mvi_plus_compose_plus_clean.data

import com.example.mvi_plus_compose_plus_clean.data.entity.MovieResult
import com.example.mvi_plus_compose_plus_clean.data.service_api.MovieApi
import com.example.mvi_plus_compose_plus_clean.domain.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class MovieRepositoryImpl(val service: MovieApi): MovieRepository {

    override suspend fun getMovies(): Response<MovieResult> {
        return withContext(Dispatchers.IO) {
            service.getMovies()
        }
    }
}
