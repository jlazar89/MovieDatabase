package com.explained.producttmdb3.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.explained.producttmdb3.data.network.model.asDomain
import com.explained.producttmdb3.domain.model.TvShowDomain
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

class TvPagingSource(
    private val tvApiService: TvApiService
) : PagingSource<Int, TvShowDomain>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShowDomain> {
        return try {
            delay(2000) // Simulate network delay
            val page = params.key ?: 1
            val response = tvApiService.getAllTvShowList(page)
            if (response.isSuccessful) {
                val movies = response.body()?.results?.map { it.asDomain() } ?: emptyList()
                val totalPages = response.body()?.totalPages ?: 0
                LoadResult.Page(
                    data = movies,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (page == totalPages) null else page + 1
                )
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TvShowDomain>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
