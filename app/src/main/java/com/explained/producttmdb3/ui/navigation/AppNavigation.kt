package com.explained.producttmdb3.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.explained.producttmdb3.ui.list.MovieListScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        // Top-level screen for the Home tab
        composable(Screen.Home.route) {
            // HomeDashboardScreen will need to accept the NavController to navigate to details
            /*   HomeDashboardScreen(
                   onSeeAllMovies = { *//* navController.navigate(...) *//* },
                onMovieClicked = { movieId ->
                    navController.navigate(Screen.MovieDetail.createRoute(movieId))
                }
            )*/
            MovieListScreen()
        }

        // Top-level screen for the TV Shows tab
        composable(Screen.TvShows.route) {
            // Your TvShowsScreen Composable
        }

        // Top-level screen for the Profile tab
        composable(Screen.Profile.route) {
            // Your ProfileScreen Composable
        }

        /*        // Detail screen (not on the bottom bar)
                composable(
                    route = Screen.MovieDetail.route,
                    arguments = listOf(navArgument("movieId") { type = NavType.IntType }),
                    deepLinks = listOf(navDeepLink { uriPattern = "https://your-app.com/movies/{movieId}" })
                ) { backStackEntry ->
                    val movieId = backStackEntry.arguments?.getInt("movieId")
                    // Your MovieDetailScreen Composable, which would fetch details using this ID
                    MovieDetailScreen(movieId = movieId)
                }*/
    }
}