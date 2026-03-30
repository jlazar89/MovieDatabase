package com.explained.producttmdb3.domain.repository

import androidx.paging.PagingData
import com.explained.producttmdb3.domain.model.MediaDetailDomain
import com.explained.producttmdb3.domain.model.MovieDomain
import kotlinx.coroutines.flow.Flow


interface MovieRepository {
    fun getTopRatedMovies(): Flow<PagingData<MovieDomain>>
    suspend fun getMovieDetail(movieId: Int): MediaDetailDomain
}
