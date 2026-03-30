package com.explained.producttmdb3.data.network

import com.explained.producttmdb3.data.network.model.MovieDetailResponse
import com.explained.producttmdb3.data.network.model.MovieListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET("movie/top_rated?language=en-US")
    suspend fun getAllMoviesList(@Query("page") page: Int): Response<MovieListResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id") movieId: Int): MovieDetailResponse
}