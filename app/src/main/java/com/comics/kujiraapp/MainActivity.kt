package com.comics.kujiraapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.comics.kujiraapp.screens.ComicDetailScreen
import com.comics.kujiraapp.screens.ComicDetailScreenRoute
import com.comics.kujiraapp.screens.HomeScreen
import com.comics.kujiraapp.screens.HomeScreenRoute
import com.comics.kujiraapp.ui.theme.KujiraAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KujiraAppTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                       navController = navController,
                        startDestination = HomeScreenRoute,
                        modifier = Modifier.padding(innerPadding)
                        ) {
                        composable<HomeScreenRoute> {
                            HomeScreen(
                                onComicClick = { id ->
                                    navController.navigate(ComicDetailScreenRoute(id))
                                }
                            )
                        }
                        composable<ComicDetailScreenRoute> { backEntry ->
                            val args = backEntry.toRoute<ComicDetailScreenRoute>()
                            ComicDetailScreen(args.id)
                        }
                    }
                }
                }
            }
        }
    }



