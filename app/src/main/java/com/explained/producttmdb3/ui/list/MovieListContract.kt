package com.explained.producttmdb3.ui.list

sealed interface MovieListState {
    data object Loading : MovieListState
    data class Success(
        val data: List<MovieUiData>,
        val isLoading: Boolean = false,
        val currentPage: Int = 1,
        val totalPages: Int = 0
    ) : MovieListState

    data class Error(val errorMessage: String) : MovieListState
}

sealed interface MovieListEvent {
    data object LoadMovies : MovieListEvent
    data object OnRefresh : MovieListEvent
}