package com.example.mvi_plus_compose_plus_clean.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieListFragment : Fragment(){

    //in case we need repository here (not the best idea, but this is how to do it)
    //private val repository by inject<MovieRepository>()
    //if you use by activityViewModel you get shared ViewModel and only one instance for fragments in the same activity.
    private val viewModel by viewModel<MovieListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate a ComposeView and set the content to your composable
        return ComposeView(requireContext()).apply {
            setContent {
                MovieListScreen() // Call your composable here
            }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.viewState.collect { state ->
                when (state) {
                    is MovieListState.Loading -> {
                        // Show loading
                    }
                    is MovieListState.Loaded -> {
                        val a = state.movies
                        // Show movies
                    }
                    is MovieListState.Error -> {
                        // Show error
                    }
                }
            }
        }
        viewModel.onAction(MovieListEvent.LoadMovies)
    }
    @Composable
    fun MovieListScreen() {
        // Placeholder for the actual screen content
        Text(text = "This is the Movie List Screen")
    }
}
