package com.explained.producttmdb3.ui.list

import app.cash.turbine.test
import com.explained.producttmdb3.domain.model.MovieDomain
import com.explained.producttmdb3.domain.repository.MovieRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MovieListViewModelTest {

    private val movieRepository: MovieRepository = mock()
    private lateinit var viewModel: MovieListViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MovieListViewModel(movieRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when view model is created, it should emit loading state`() = runTest {
        viewModel.state.test {
            assertEquals(MovieListState.Loading, awaitItem())
        }
    }

    @Test
    fun `when loadMovies is called, it should emit success state`() = runTest {
        val movies = listOf(
            MovieDomain(1, "title", "overview", "posterPath")
        )
        whenever(movieRepository.getTopRatedMovies()).thenReturn(movies)

        viewModel.handleIntent(MovieListIntent.LoadMovies)

        viewModel.state.test {
            assertEquals(MovieListState.Loading, awaitItem())
            val successState = awaitItem()
            assert(successState is MovieListState.Success)
            assertEquals(1, (successState as MovieListState.Success).data.size)
        }
    }

    @Test
    fun `when loadMovies is called and repository throws exception, it should emit error state`() =
        runTest {
            val errorMessage = "An error occurred"
            whenever(movieRepository.getTopRatedMovies()).thenThrow(RuntimeException(errorMessage))

            viewModel.handleIntent(MovieListIntent.LoadMovies)

            viewModel.state.test {
                assertEquals(MovieListState.Loading, awaitItem())
                val errorState = awaitItem()
                assert(errorState is MovieListState.Error)
                assertEquals(errorMessage, (errorState as MovieListState.Error).errorMessage)
            }
        }
}