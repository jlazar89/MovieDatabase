package com.explained.producttmdb3.data.network

import com.explained.producttmdb3.data.network.model.MovieListResponse
import retrofit2.Response
import retrofit2.http.GET

interface MovieApiService {
    @GET("movie/top_rated?language=en-US&page=1")
    suspend fun getAllMoviesList(): Response<MovieListResponse>
}