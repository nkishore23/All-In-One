package com.berni.allinone.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.berni.allinone.ui.accounts.ProfileScreen
import com.berni.allinone.ui.pages.WeatherScreen
import com.berni.allinone.ui.pages.HomeScreen
import com.berni.allinone.ui.pages.NavigationBarExample
import com.berni.allinone.ui.viewmodel.UserViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Web

sealed class BottomNavItem(val route: String, val label: String, val icon: @Composable () -> Unit) {
    object Home : BottomNavItem("home", "Home", { Icon(Icons.Filled.Home, contentDescription = "Home") })
    object Profile : BottomNavItem("profile", "Profile", { Icon(Icons.Filled.Person, contentDescription = "Profile") })
    object Weather : BottomNavItem("weather", "Weather", { Icon(Icons.Filled.Cloud, contentDescription = "Weather") })
    object Webview : BottomNavItem("webview", "Webview", { Icon(Icons.Filled.Web, contentDescription = "Web View") })
}

@Composable
fun BottomNavGraph(navController: NavHostController, userViewModel: UserViewModel) {
    val bottomNavController = rememberNavController()

    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Profile,
        BottomNavItem.Weather,
        BottomNavItem.Webview
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = screen.icon,
                        label = { Text(screen.label) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            bottomNavController.navigate(screen.route) {
                                popUpTo(bottomNavController.graph.findStartDestination().id) {
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
        NavHost(
            navController = bottomNavController,
            startDestination = BottomNavItem.Home.route,
            Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Home.route) {
                HomeScreen(navController, userViewModel)
            }
            composable(BottomNavItem.Profile.route) {
                ProfileScreen(navController, userViewModel)
            }
            composable(BottomNavItem.Weather.route) {
                WeatherScreen()
            }
            composable(BottomNavItem.Webview.route) {
                NavigationBarExample()
            }
        }
    }
}
