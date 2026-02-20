package com.explained.producttmdb3.ui.list

import app.cash.turbine.test
import androidx.paging.PagingData
import com.explained.producttmdb3.domain.model.MovieDomain
import com.explained.producttmdb3.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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
        val movies = listOf(
            MovieDomain(1, "title", "overview", "posterPath")
        )
        val pagingData = PagingData.from(movies)
        whenever(movieRepository.getTopRatedMovies()).thenReturn(flowOf(pagingData))
        viewModel = MovieListViewModel(movieRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `movies flow emits paged data`() = runTest {
        viewModel.movies.test {
            val item = awaitItem()
            assert(item != null)
            cancelAndIgnoreRemainingEvents()
        }
    }
}