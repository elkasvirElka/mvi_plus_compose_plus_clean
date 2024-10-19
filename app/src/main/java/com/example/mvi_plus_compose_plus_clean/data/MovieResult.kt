package com.example.mvi_plus_compose_plus_clean.data

data class MovieResult(
    val page: Int,
    val results: List<MovieInfo>,
    val total_pages: Int,
    val total_results: Int
)
