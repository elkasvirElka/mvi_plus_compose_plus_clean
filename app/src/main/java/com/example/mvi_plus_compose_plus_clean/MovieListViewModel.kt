package com.example.mvi_plus_compose_plus_clean

import androidx.lifecycle.ViewModel
import com.example.mvi_plus_compose_plus_clean.data.MovieInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MovieListViewModel : ViewModel() {

    private var _viewState = MutableStateFlow<MovieListState>(MovieListState.Loading)

    // Public immutable state flow for UI observation
    val viewState: StateFlow<MovieListState> = _viewState.asStateFlow()

    fun onAction(event: MovieListEvent) {
        when (event) {
            is MovieListEvent.LoadMovies -> loadMovies()
            is MovieListEvent.MovieDetails -> loadMovieDetails(event.movieId)
        }
    }

    fun loadMovies() {
        _viewState.value = MovieListState.Loading
        //call retrofit

        // Load movies from repository
    }

    fun loadMovieDetails(movieId: Int) {
        // Load movie details from repository
    }
}

sealed class MovieListEvent {
    object LoadMovies : MovieListEvent()
    data class MovieDetails(val movieId: Int) : MovieListEvent()
}

sealed class MovieListState {
    object Loading : MovieListState()
    data class Loaded(val movies: List<MovieInfo>) : MovieListState()
    data class Error(val message: String) : MovieListState()
}
