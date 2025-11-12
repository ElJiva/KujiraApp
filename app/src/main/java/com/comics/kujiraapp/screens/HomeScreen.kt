package com.comics.kujiraapp.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.comics.kujiraapp.models.Comics

@Composable
fun HomeScreen(navController: NavController) {
    val BASE_URL = "http://134.199.232.81:3000/comics"
    var comics by remember { mutableStateOf(listOf<Comics>()) }
    var loading by remember { mutableStateOf(true) }

