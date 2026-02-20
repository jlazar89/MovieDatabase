package com.explained.producttmdb3.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.explained.producttmdb3.domain.model.MovieDomain
import com.explained.producttmdb3.data.network.MovieApiService
import com.explained.producttmdb3.data.network.MoviePagingSource
import com.explained.producttmdb3.di.IoDispatcher
import com.explained.producttmdb3.domain.repository.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApiService: MovieApiService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : MovieRepository {

    override fun getTopRatedMovies(): Flow<PagingData<MovieDomain>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { MoviePagingSource(movieApiService) }
        ).flow
    }
}
