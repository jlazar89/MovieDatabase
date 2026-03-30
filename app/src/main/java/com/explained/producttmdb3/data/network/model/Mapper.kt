package com.explained.producttmdb3.data.network.model

import com.explained.producttmdb3.domain.model.GenreDomain
import com.explained.producttmdb3.domain.model.MediaDetailDomain
import com.explained.producttmdb3.domain.model.MovieDomain
import com.explained.producttmdb3.domain.model.TvShowDomain

fun MovieResponse.asDomain(): MovieDomain {
    return MovieDomain(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath
    )
}

fun TvShowResponse.asDomain(): TvShowDomain {
    return TvShowDomain(
        id = id,
        name = name,
        overview = overview,
        posterPath = posterPath
    )
}


fun MovieDetailResponse.asDomain(): MediaDetailDomain {
    return MediaDetailDomain(
        id = id,
        title = title, overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount,
        genres = genres.map { it.asDomain() }
    )
}

fun TvShowDetailResponse.asDomain(): MediaDetailDomain {
    return MediaDetailDomain(
        id = id,
        title = name,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = firstAirDate,
        voteAverage = voteAverage,
        voteCount = voteCount,
        genres = genres.map { it.asDomain() }
    )
}

fun Genre.asDomain(): GenreDomain {
    return GenreDomain(
        id = id,
        name = name
    )
}
