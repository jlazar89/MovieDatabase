package com.explained.producttmdb3.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object TvShows : Screen("tv_shows")
    object Profile : Screen("profile")

    object MovieDetail : Screen("movie_detail/{movieId}") {
        fun createRoute(movieId: Int) = "movie_detail/$movieId"
    }
}
