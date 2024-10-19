package com.example.mvi_plus_compose_plus_clean.data

import com.example.mvi_plus_compose_plus_clean.ui.theme.Result

data class MovieInfo(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)
