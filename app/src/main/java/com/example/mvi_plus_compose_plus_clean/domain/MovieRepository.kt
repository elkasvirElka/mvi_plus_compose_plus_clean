package com.example.mvi_plus_compose_plus_clean.domain

import com.example.mvi_plus_compose_plus_clean.data.entity.MovieResult
import retrofit2.Response

interface MovieRepository {

    suspend fun getMovies(): Response<MovieResult>
}
