package com.comics.kujiraapp.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.comics.kujiraapp.models.Comics
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.comics.kujiraapp.services.ComicApi
import com.comics.kujiraapp.ui.theme.KujiraAppTheme
import com.comics.kujiraapp.ui.theme.PrimaryAccent
import com.comics.kujiraapp.ui.theme.SecondaryText

@Composable
fun ComicDetailScreen(comicId: String) {
    val BASE_URL = "http://134.199.232.81:3000/comics"
    var comics by remember { mutableStateOf<Comics?>(null) }


    LaunchedEffect(true) {
        try {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(ComicApi::class.java)
            val result = withContext(Dispatchers.IO) { service.getComicsDetail(comicId) }
            comics = result
            Log.i("ProductDetail", comics.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    val Comics = comics

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryAccent),
        contentAlignment = Alignment.Center
    ) {
        if (Comics != null) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 45.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    // Aquí puedes agregar un componente para mostrar los detalles del cómic
                    // Por ejemplo, una imagen de portada, título, descripción, etc.
                }

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .shadow(4.dp, RoundedCornerShape(16.dp))
                            .background(Color.White)
                            .clip(RoundedCornerShape(16.dp))
                            .padding(12.dp)
                    ) {
                        Text(
                            "About this album",
                            color = SecondaryText,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            Comics.category,
                            color = Color.DarkGray,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }


                item {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .align(Alignment.CenterStart)
                                .width(240.dp)
                                .shadow(4.dp, RoundedCornerShape(16.dp))
                                .background(Color.White)
                                .clip(RoundedCornerShape(16.dp))
                                .padding(12.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                "Author",
                                color = SecondaryText,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                Comics. author,
                                color = Color.DarkGray,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }


                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                val tracks = (1..24 step 1).map { trackNumber ->
                    "Track.$trackNumber"
                }

                itemsIndexed(tracks) { index, trackName ->
//                    SongItem(
//                        trackNumber = trackName,
//                        title = Album.title,
//                        artist = Album.artist,
//                        imageUrl = Album.image
//                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        } else {
            // Mostrar un indicador de carga o un mensaje de error si es necesario
        }
    }
}


@Preview
@Composable
fun ComicDetailScreenPreview() {
    KujiraAppTheme {
        ComicDetailScreen(
            comicId = "1"
        )
    }
}
