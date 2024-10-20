package com.example.mvi_plus_compose_plus_clean

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvi_plus_compose_plus_clean.data.entity.MovieInfo
import com.example.mvi_plus_compose_plus_clean.data.entity.MovieResult
import com.example.mvi_plus_compose_plus_clean.domain.MovieRepository
import com.example.mvi_plus_compose_plus_clean.presentation.MovieListEvent
import com.example.mvi_plus_compose_plus_clean.presentation.MovieListState
import com.example.mvi_plus_compose_plus_clean.presentation.MovieListViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class MovieListViewModelTest {

    @get:Rule
    //It ensures that any architecture components (like LiveData) update synchronously during testing.
    var rule: TestRule = InstantTaskExecutorRule()

    // Use TestCoroutineDispatcher to handle coroutines in tests
    private val testDispatcher = StandardTestDispatcher()

    // Mock the repository
    private lateinit var repository: MovieRepository

    // The ViewModel being tested
    private lateinit var viewModel: MovieListViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        // Initialize the mock repository
        repository = mockk()

        // Initialize the ViewModel with the mocked repository
        viewModel = MovieListViewModel(repository)
    }

    @After
    fun tearDown() {
        // Cleanup coroutines
        Dispatchers.resetMain()
        // testDispatcher.scheduler.advanceUntilIdle() // Ensure no tasks are left
    }

    @Test
    fun `test loadMovies success`() = runBlockingTest {
        // Arrange: Mock successful response from the repository
        val results = listOf(
            MovieInfo(
                adult = false, backdrop_path = "/backdrop.jpg",
                genre_ids = listOf(28, 12),
                id = 123,
                original_language = "en",
                original_title = "The Godfather",
                overview = "An organized crime dynasty's aging patriarch transfers control of his clandestine empire to his reluctant son.",
                popularity = 9.7,
                poster_path = "/poster.jpg",
                release_date = "1972-03-24",
                title = "The Godfather",
                video = false,
                vote_average = 9.2,
                vote_count = 15000
            )
        )
        val movieInfo = MovieResult(
            page = 1, results = results,
            total_results = 1, total_pages = 1
        )
        val mockResponse = Response.success(movieInfo)

        coEvery { repository.getMovies() } returns mockResponse

        // Act: Trigger the ViewModel to load movies
        viewModel.onAction(MovieListEvent.LoadMovies)

        // Assert: Verify the initial state is Loading
        assertEquals(MovieListState.Loading, viewModel.viewState.first())

        testDispatcher.scheduler.advanceUntilIdle() // Ensure all coroutines complete
        assertEquals(MovieListState.Loaded(movieInfo.results), viewModel.viewState.first())
    }

    @Test
    fun `test loadMovies error`() = runBlockingTest {
        val errorResponseBody = """
        {
            "status_code": 500,
            "status_message": "Internal Server Error"
        }
         """.trimIndent().toResponseBody("application/json".toMediaTypeOrNull())

        // Arrange: Mock an error response from the repository
        val errorResponse = Response.error<MovieResult>(401, errorResponseBody)

        coEvery { repository.getMovies() } returns errorResponse

        // Act: Trigger the ViewModel to load movies
        viewModel.onAction(MovieListEvent.LoadMovies)

        // Assert: Verify the initial state is Loading
        assertEquals(MovieListState.Loading, viewModel.viewState.first())

        // Assert: Verify the state is Error after the response
        testDispatcher.scheduler.advanceUntilIdle() // Ensure all coroutines complete
        assertEquals(MovieListState.Error("Error loading movies"), viewModel.viewState.value)
    }
}
