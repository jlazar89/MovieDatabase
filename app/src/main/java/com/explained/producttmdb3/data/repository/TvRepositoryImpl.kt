package com.explained.producttmdb3.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.explained.producttmdb3.data.network.TvApiService
import com.explained.producttmdb3.data.network.TvPagingSource
import com.explained.producttmdb3.data.network.model.asDomain
import com.explained.producttmdb3.di.IoDispatcher
import com.explained.producttmdb3.domain.model.MediaDetailDomain
import com.explained.producttmdb3.domain.model.TvShowDomain
import com.explained.producttmdb3.domain.repository.TvRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TvRepositoryImpl @Inject constructor(
    private val tvApiService: TvApiService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : TvRepository {
    override fun getTopRatedTvShows(): Flow<PagingData<TvShowDomain>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { TvPagingSource(tvApiService) }

        ).flow
    }

    override suspend fun getTvShowDetail(seriesId: Int): MediaDetailDomain {
        return withContext(dispatcher) {
            tvApiService.getTvShowDetail(seriesId).asDomain()
        }
    }
}