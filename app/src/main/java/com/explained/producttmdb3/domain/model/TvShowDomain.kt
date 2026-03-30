package com.explained.producttmdb3.domain.model

data class TvShowDomain(
    val id: Int,
    val name: String, // TV shows use name, not title
    val posterPath: String?,
    val overview: String
)