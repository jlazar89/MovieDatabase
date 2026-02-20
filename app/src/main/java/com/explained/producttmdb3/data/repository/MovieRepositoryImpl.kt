package com.explained.producttmdb3.data.repository

import com.explained.producttmdb3.domain.model.MovieDomain
import com.explained.producttmdb3.data.network.MovieApiService
import com.explained.producttmdb3.data.network.model.MovieResponse
import com.explained.producttmdb3.di.IoDispatcher
import com.explained.producttmdb3.domain.repository.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApiService: MovieApiService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : MovieRepository {

    override suspend fun getTopRatedMovies(): List<MovieDomain> {
        return withContext(dispatcher) {
            val response = movieApiService.getAllMoviesList()
            val moviesList = response.body()
            if (response.isSuccessful && moviesList != null) {
                moviesList.results.map { it.toDomain() }
            } else {
                emptyList()
            }
        }
    }
}

private fun MovieResponse.toDomain() = MovieDomain(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath
)