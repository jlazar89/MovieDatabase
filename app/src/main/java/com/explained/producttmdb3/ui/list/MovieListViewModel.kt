package com.explained.producttmdb3.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.explained.producttmdb3.domain.model.MovieDomain
import com.explained.producttmdb3.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val movies: Flow<PagingData<MovieDomain>> = movieRepository
        .getTopRatedMovies()
        .cachedIn(viewModelScope)
}
