package com.comics.kujiraapp.screens

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.comics.kujiraapp.models.Comics
import com.comics.kujiraapp.services.ComicApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun HomeScreen(navController: NavController) {
    val BASE_URL = "http://134.199.232.81:3000/comics"
    var comics by remember { mutableStateOf(listOf<Comics>()) }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(true) {
        try {
            Log.i("HomeScreen", "Creando instancoa Retrofit")
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(ComicApi::class.java)
            val result = withContext(Dispatchers.IO) {
                service.getComics()
            }
            Log.i("HomeScreen", "El resultado es: ${result}")
            comics = result
            loading = false
        } catch (e: Exception) {
            Log.e("HomeScreen", "Esta mal: ${e.toString()}")
            loading = false
        }
    }

    if (loading){
    }
}