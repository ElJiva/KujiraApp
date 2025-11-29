package com.comics.kujiraapp.screens

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.comics.kujiraapp.components.ComicsHeader
import com.comics.kujiraapp.ui.theme.KujiraAppTheme
import com.comics.kujiraapp.ui.theme.PrimaryAccent
import com.comics.kujiraapp.ui.theme.PrimaryBackground
import com.comics.kujiraapp.ui.theme.SecondaryText
import com.comics.kujiraapp.viewmodels.ComicDetailViewModel

@Composable
fun ComicDetailScreen(comicId: String) {
    val viewModel: ComicDetailViewModel = viewModel(factory = ComicDetailViewModel.Factory(comicId))
    val state by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryBackground),
        contentAlignment = Alignment.Center
    ) {
        if (state.loading) {
            CircularProgressIndicator(
                color = PrimaryAccent,
                trackColor = SecondaryText
            )
        } else if (state.error != null) {
            Text(text = state.error!!, color = Color.Red)
        } else if (state.comic != null) {
            val comic = state.comic!!
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 45.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    ComicsHeader(comics = comic)
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
                            comic.category,
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
                                comic.author,
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
                    ComicComments(
                        trackNumber = trackName,
                        title = comic.title,
                        autor = comic.author,
                        imageUrl = comic.imagen
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun ComicComments(
    trackNumber: String,
    title: String,
    autor: String,
    imageUrl: String
) {
    // Aquí puedes implementar el diseño para cada comentario del cómic
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "Comment for $title by $autor",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.DarkGray
        )
    }
}


@Preview
@Composable
fun ComicDetailScreenPreview() {
    KujiraAppTheme {
        // This preview will not work as it needs a real comicId and a ViewModel.
        // For a meaningful preview, you might want to create a fake ViewModel.
    }
}
