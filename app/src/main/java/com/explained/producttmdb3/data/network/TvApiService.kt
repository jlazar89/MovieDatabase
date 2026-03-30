package com.explained.producttmdb3.data.network

import com.explained.producttmdb3.data.network.model.MovieDetailResponse
import com.explained.producttmdb3.data.network.model.TvShowDetailResponse
import com.explained.producttmdb3.data.network.model.TvShowListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvApiService {
    @GET("tv/top_rated?language=en-US")
    suspend fun getAllTvShowList(@Query("page") page: Int): Response<TvShowListResponse>

    @GET("tv/{series_id}")
    suspend fun getTvShowDetail(@Path("series_id") movieId: Int): TvShowDetailResponse
}