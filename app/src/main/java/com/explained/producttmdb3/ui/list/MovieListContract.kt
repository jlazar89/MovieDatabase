package com.explained.producttmdb3.ui.list

sealed class MovieListState {
    data object Loading : MovieListState()
    data class Error(val errorMessage: String) : MovieListState()
    data class Success(val data: List<MovieUiData>) : MovieListState()
}

sealed class MovieListIntent {
    data object LoadMovies : MovieListIntent()
}