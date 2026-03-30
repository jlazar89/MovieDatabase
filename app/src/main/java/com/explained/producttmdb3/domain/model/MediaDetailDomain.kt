package com.explained.producttmdb3.domain.model

data class MediaDetailDomain(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val genres: List<GenreDomain>
)
