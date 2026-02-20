package com.explained.producttmdb3.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.explained.producttmdb3.data.network.model.asDomain
import com.explained.producttmdb3.domain.model.MovieDomain
import retrofit2.HttpException
import java.io.IOException

class MoviePagingSource(
    private val movieApiService: MovieApiService
) : PagingSource<Int, MovieDomain>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDomain> {
        return try {
            val page = params.key ?: 1
            val response = movieApiService.getAllMoviesList(page)
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

    override fun getRefreshKey(state: PagingState<Int, MovieDomain>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
