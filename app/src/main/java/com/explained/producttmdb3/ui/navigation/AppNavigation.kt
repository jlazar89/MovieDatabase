package com.explained.producttmdb3.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.explained.producttmdb3.ui.movies.detail.DetailScreen
import com.explained.producttmdb3.ui.movies.list.MovieListScreen
import com.explained.producttmdb3.ui.tvshows.list.TvListScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        // Top-level screen for the Home tab
        composable(Screen.Home.route) {
            MovieListScreen(
                onMovieClicked = { movieId ->
                    navController.navigate(Screen.Detail.createRoute(movieId, "movie"))
                }
            )
        }

        // Top-level screen for the TV Shows tab
        composable(Screen.TvShows.route) {
            TvListScreen(
                onTvShowClicked = { tvId ->
                    navController.navigate(Screen.Detail.createRoute(tvId, "tv"))
                }
            )
        }

        // Top-level screen for the Profile tab
        composable(Screen.Profile.route) {
            // Your ProfileScreen Composable
        }

        // Unified Detail screen
        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("mediaId") { type = NavType.IntType },
                navArgument("mediaType") { type = NavType.StringType }
            )
        ) {
            DetailScreen()
        }
    }
}
