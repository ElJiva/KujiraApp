package com.comics.kujiraapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.comics.kujiraapp.screens.HomeScreen
import com.comics.kujiraapp.screens.auth.LoginScreen
import com.comics.kujiraapp.screens.auth.SignUpScreen
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.comics.kujiraapp.screens.ComicDetailScreen

@Composable
fun AuthNavigation() {
  val navController = rememberNavController()

  NavHost(navController = navController, startDestination = "login") {

    composable("login") {
      LoginScreen(
        onSignUpClicked = { navController.navigate("signup") },
        onLoginSuccess = {
          navController.navigate("home") {
            popUpTo("login") { inclusive = true }
          }
        }
      )
    }

    composable("signup") {
      SignUpScreen(
        onLoginClicked = { navController.navigate("login") }
      )
    }

    composable("home") {
      HomeScreen(
        onComicClick = { comicId ->
          navController.navigate("comicDetail/$comicId")
        }
      )
    }
      composable(
          route = "comicDetail/{comicId}",
          arguments = listOf(
              navArgument("comicId") { type = NavType.StringType }
          )
      ) { backStackEntry ->
          val comicId = backStackEntry.arguments?.getString("comicId") ?: ""
          ComicDetailScreen(comicId = comicId)
      }
  }
}

