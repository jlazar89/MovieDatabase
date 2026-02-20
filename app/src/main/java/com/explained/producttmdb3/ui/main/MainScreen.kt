package com.explained.producttmdb3.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.explained.producttmdb3.ui.navigation.AppNavigation
import com.explained.producttmdb3.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    // The NavController is now created and owned by this MainScreen composable.
    val navController = rememberNavController()

    // List of top-level destinations to show in the bottom bar
    val bottomNavItems = listOf(Screen.Home, Screen.TvShows, Screen.Profile)

    // Get the current back stack entry to determine the current screen
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Determine if the current screen is a top-level destination
    val isTopLevelDestination = bottomNavItems.any { it.route == currentDestination?.route }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    val title = when (currentDestination?.route) {
                        Screen.Home.route -> "Home Dashboard"
                        Screen.TvShows.route -> "TV Shows"
                        Screen.Profile.route -> "Profile"
                        Screen.MovieDetail.route -> "Movie Details"
                        else -> "ProductTMDB"
                    }
                    Text(title)
                },
                navigationIcon = {
                    if (!isTopLevelDestination) {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                bottomNavItems.forEach { screen ->
                    val icon = when (screen) {
                        Screen.Home -> Icons.Default.Home
                        Screen.Profile -> Icons.Default.Person
                        Screen.TvShows -> Icons.Default.Tv
                        else -> Icons.Default.Home // Should not happen
                    }
                    NavigationBarItem(
                        icon = { Icon(icon, contentDescription = null) },
                        label = {
                            Text(
                                text = screen.route.replaceFirstChar { it.uppercase() },
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            // The AppNavigation composable is called here, inside the Scaffold's content area
            AppNavigation(navController = navController)
        }
    }
}