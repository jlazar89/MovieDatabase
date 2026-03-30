package com.explained.producttmdb3.domain.repository

import androidx.paging.PagingData
import com.explained.producttmdb3.domain.model.MediaDetailDomain
import com.explained.producttmdb3.domain.model.TvShowDomain
import kotlinx.coroutines.flow.Flow

interface TvRepository {
    fun getTopRatedTvShows(): Flow<PagingData<TvShowDomain>>
    suspend fun getTvShowDetail(movieId: Int): MediaDetailDomain
}
