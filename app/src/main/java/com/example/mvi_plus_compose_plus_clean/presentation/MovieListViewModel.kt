package com.example.mvi_plus_compose_plus_clean.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvi_plus_compose_plus_clean.data.entity.MovieInfo
import com.example.mvi_plus_compose_plus_clean.data.entity.MovieResult
import com.example.mvi_plus_compose_plus_clean.domain.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class MovieListViewModel(private val repository: MovieRepository) : ViewModel() {

    private var _viewState = MutableStateFlow<MovieListState>(MovieListState.Loading)

    // Public immutable state flow for UI observation
    val viewState: StateFlow<MovieListState> = _viewState.asStateFlow()

    fun onAction(event: MovieListEvent) {
        when (event) {
            is MovieListEvent.LoadMovies -> loadMovies()
            is MovieListEvent.MovieDetails -> loadMovieDetails(event.movieId)
        }
    }

    private fun loadMovies() {
        _viewState.value = MovieListState.Loading
        viewModelScope.launch {
            repository.getMovies()?.let { response ->
                when(response.code()) {
                    200 -> {
                        _viewState.value = MovieListState.Loaded(response.body()?.results)
                    }

                    else -> {
                        _viewState.value = MovieListState.Error("Error loading movies")
                    }
                }
            }
        }
    }

   private fun loadMovieDetails(movieId: Int) {
        // Load movie details from repository
    }

    // The reducer function which processes the state based on the response and the event
    //In my opinion this is not very nice code, I prefer to handle states in functions like loadMovies
    /*private fun reducer(
        currentState: MovieListState,
        event: MovieListEvent,
        response: Response<MovieResult>? = null
    ): MovieListState {
        return when (event) {
            is MovieListEvent.LoadMovies -> {
                if (response != null && response.isSuccessful) {
                    response.body()?.results?.let {
                        MovieListState.Loaded(it)
                    } ?: MovieListState.Error("No data available")
                } else {
                    MovieListState.Error("Error loading movies")
                }
            }
            else -> currentState // Return the current state for other events
        }
    }*/
}

sealed class MovieListEvent {
    object LoadMovies : MovieListEvent()
    data class MovieDetails(val movieId: Int) : MovieListEvent()
}

sealed class MovieListState {
    object Loading : MovieListState()
    data class Loaded(val movies: List<MovieInfo>?) : MovieListState()
    data class Error(val message: String) : MovieListState()
}
