package com.berni.allinone.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.berni.allinone.ui.accounts.EditProfileScreen
import com.berni.allinone.ui.auth.LoginScreen
import com.berni.allinone.ui.auth.SignUpScreen
import com.berni.allinone.ui.viewmodel.UserViewModel

@Composable
fun Navigation(navController: NavHostController, userViewModel: UserViewModel) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController, userViewModel)
        }
        composable("signup") {
            SignUpScreen(navController)
        }
        composable("main") {
            BottomNavGraph(navController, userViewModel) // <-- bottom nav container here
        }
        composable("editProfile") {
            EditProfileScreen(navController, userViewModel)
        }
    }
}
