package com.explained.producttmdb3.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object TvShows : Screen("tv_shows")
    object Profile : Screen("profile")

    object Detail : Screen("detail/{mediaId}/{mediaType}") {
        fun createRoute(mediaId: Int, mediaType: String) = "detail/$mediaId/$mediaType"
    }

    object MovieDetail : Screen("movie_detail/{movieId}") {
        fun createRoute(movieId: Int) = "movie_detail/$movieId"
    }

    object TvShowDetail : Screen("tv/{series_id}") {
        fun createRoute(seriesId: Int) = "tv/$seriesId"
    }
}
