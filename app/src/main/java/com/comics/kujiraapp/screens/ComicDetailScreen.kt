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
import com.comics.kujiraapp.components.CommentsSection
import com.comics.kujiraapp.components.InfoContainer
import com.comics.kujiraapp.ui.theme.KujiraAppTheme
import com.comics.kujiraapp.ui.theme.PrimaryAccent
import com.comics.kujiraapp.ui.theme.PrimaryBackground
import com.comics.kujiraapp.ui.theme.SecondaryText
import com.comics.kujiraapp.viewmodels.ComicDetailViewModel
import androidx.compose.ui.platform.LocalUriHandler

@Composable
fun ComicDetailScreen(comicId: String) {
    val viewModel: ComicDetailViewModel =
        viewModel(factory = ComicDetailViewModel.Factory(comicId))
    val state by viewModel.state.collectAsState()

    val uriHandler = LocalUriHandler.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryBackground),
        contentAlignment = Alignment.Center
    ) {
        when {
            state.loading -> {
                CircularProgressIndicator(
                    color = PrimaryAccent,
                    trackColor = SecondaryText
                )
            }

            state.error != null -> {
                Text(text = state.error!!, color = Color.Red)
            }

            state.comic != null -> {
                val comic = state.comic!!

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 45.dp),
                    contentPadding = PaddingValues(bottom = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    item {
                        ComicsHeader(comics = comic)
                    }

                    item {
                        InfoContainer(
                            title = comic.title,
                            content = comic.editorial,
                            rating = comic.rating.toFloatOrNull() ?: 0f,
                            reviews = comic.comments.size,
                            onBuyClicked = {
                                uriHandler.openUri(comic.buyLink)
                            },
                            onWatchTrailerClicked = {
                                uriHandler.openUri(comic.videoLink)
                            },
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }

                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .background(Color.Transparent)
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
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    item {
                        CommentsSection(
                            comments = comic.comments,
                            onSendComment = { newComment ->
                                viewModel.addComment(newComment)
                            },
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ComicDetailScreenPreview() {
    KujiraAppTheme {

    }
}