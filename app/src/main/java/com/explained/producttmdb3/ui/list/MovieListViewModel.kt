package com.explained.producttmdb3.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.explained.producttmdb3.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _state = MutableStateFlow<MovieListState>(MovieListState.Loading)
    val state: StateFlow<MovieListState> = _state.onStart {
        handleIntent(MovieListIntent.LoadMovies)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MovieListState.Loading
    )

    fun handleIntent(intent: MovieListIntent) {
        when (intent) {
            is MovieListIntent.LoadMovies -> loadMovies()
        }
    }

    private fun loadMovies() {
        viewModelScope.launch(exceptionHandler) {
            _state.value = MovieListState.Loading
            try {
                val movies = movieRepository.getTopRatedMovies()
                val movieUiData = movies.map {
                    MovieUiData(
                        id = it.id,
                        title = it.title,
                        overview = it.overview,
                        posterPath = it.posterPath
                    )
                }
                _state.value = MovieListState.Success(movieUiData)
            } catch (e: Exception) {
                _state.value = MovieListState.Error(e.message ?: "An error occurred")
            }
        }
    }

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _state.value = MovieListState.Error(errorMessage = throwable.message ?: "An error occurred")
    }
}