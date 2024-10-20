package com.example.mvi_plus_compose_plus_clean.data.service_api

import com.example.mvi_plus_compose_plus_clean.data.entity.MovieResult
import retrofit2.Response
import retrofit2.http.GET

interface MovieApi {

    @GET("discover/movie")
    suspend fun getMovies(): Response<MovieResult>
}
