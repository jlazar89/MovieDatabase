package com.explained.producttmdb3.domain.repository

import com.explained.producttmdb3.domain.model.MovieDomain


interface MovieRepository {
    suspend fun getTopRatedMovies() : List<MovieDomain>
}