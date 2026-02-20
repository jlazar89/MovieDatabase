package com.explained.producttmdb3.domain.model

data class MovieDomain(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String
)