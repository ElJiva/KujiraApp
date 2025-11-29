package com.comics.kujiraapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.comics.kujiraapp.screens.auth.LoginScreen
import com.comics.kujiraapp.screens.auth.SignUpScreen

@Composable
fun AuthNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onSignUpClicked = { navController.navigate("signup") }
            )
        }
        composable("signup") {
            SignUpScreen(
                onLoginClicked = { navController.navigate("login") }
            )
        }
    }
}
