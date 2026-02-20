package com.explained.producttmdb3.data.network.model

import com.explained.producttmdb3.domain.model.MovieDomain

fun MovieResponse.asDomain(): MovieDomain {
    return MovieDomain(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath
    )
}
