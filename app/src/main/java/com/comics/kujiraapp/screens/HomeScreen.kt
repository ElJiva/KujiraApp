package com.comics.kujiraapp.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.comics.kujiraapp.models.Comics
import com.comics.kujiraapp.ui.theme.BackgroundCard
import com.comics.kujiraapp.ui.theme.SecondaryText
import com.comics.kujiraapp.viewmodels.HomeViewModel
import kotlin.collections.forEach
import androidx.compose.foundation.Image
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.comics.kujiraapp.ui.theme.PrimaryAccent
import com.comics.kujiraapp.utils.getDrawableResourceId

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    onComicClick: (String) -> Unit,
    homeViewModel: HomeViewModel = viewModel()
) {
    val state by homeViewModel.state.collectAsState()
    var showFilters by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundCard)
            .padding(top = 40.dp)
    ) {
        item {
            HomeHeader(
                searchText = state.searchQuery,
                onSearchTextChange = { query -> homeViewModel.onSearchQueryChange(query) },
                onFilterClick = { showFilters = !showFilters }
            )
        }

        item {
            AnimatedVisibility(
                visible = showFilters,
                enter = expandVertically(expandFrom = Alignment.Top) + fadeIn(),
                exit = shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut()
            ) {
                FilterSection(
                    selectedGenre = state.selectedGenre,
                    onGenreChange = {homeViewModel.onGenreChange(it)},
                    marvelChecked = state.marvelChecked,
                    onMarvelCheckChange = { homeViewModel.onMarvelCheckChange(it) },
                    dcChecked = state.dcChecked,
                    onDcCheckChange = { homeViewModel.onDcCheckChange(it) },
                    onClearFilters = { homeViewModel.clearFilters() },
                    onApplyFilters = { showFilters = false}
                )
            }
        }

        when {
            state.loading -> {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = SecondaryText)
                    }
                }
            }

            state.error != null -> {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Error: ${state.error}", color = Color.White)
                    }
                }
            }

            else -> {
                val comics = state.comics
                items(
                    count = (comics.size + 1) / 2,
                    key = { index -> "${comics.getOrNull(index * 2)?.id}_${comics.getOrNull(index * 2 + 1)?.id}" }
                ) { rowIndex ->
                    val firstIndex = rowIndex * 2
                    val secondIndex = firstIndex + 1

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            ComicItem(comic = comics[firstIndex], onComicClick = onComicClick)
                        }

                        if (secondIndex < comics.size) {
                            Box(modifier = Modifier.weight(1f)) {
                                ComicItem(comic = comics[secondIndex], onComicClick = onComicClick)
                            }
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

//Header
@Composable
private fun HomeHeader(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onFilterClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Browse Comics",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            IconButton(onClick = onFilterClick) {
                Icon(
                    imageVector = Icons.Default.Tune,
                    contentDescription = "Filter",
                    tint = Color(0xFFE25B5B)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

// SearchBar
        TextField(
            value = searchText,
            onValueChange = onSearchTextChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = {
                Text("Search comics or characters...", color = Color.LightGray)
            },
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = Color.White
                )
            },
            shape = RoundedCornerShape(15.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = PrimaryAccent,
                unfocusedIndicatorColor = PrimaryAccent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = PrimaryAccent,
                focusedLabelColor = SecondaryText,
                unfocusedLabelColor = SecondaryText,
            )
        )
    }
}

//Filtro para Comics
@Composable
private fun FilterSection(
    selectedGenre: String,
    onGenreChange: (String) -> Unit,
    marvelChecked: Boolean,
    onMarvelCheckChange: (Boolean) -> Unit,
    dcChecked: Boolean,
    onDcCheckChange: (Boolean) -> Unit,
    onClearFilters: () -> Unit,
    onApplyFilters: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val genres = listOf("All Genres",  "Superheroes")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
    ) {
        Text(
            text = "FILTER BY",
            color = Color.LightGray,
            style = MaterialTheme.typography.labelSmall
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Genre
        Text("Genre",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))

        Box {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true }
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = selectedGenre, color = Color.White)
                Icon(Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown",
                    tint = Color.White
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(Color(0xFF2C2C2C))
            ) {
                genres.forEach { genre ->
                    DropdownMenuItem(
                        text = { Text(genre, color = Color.White) },
                        onClick = {
                            onGenreChange(genre)
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Publisher
        Text("Publisher",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier
            .height(8.dp)
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = marvelChecked,
                onCheckedChange = onMarvelCheckChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFFE25B5B),
                    uncheckedColor = Color.Gray,
                    checkmarkColor = Color.White
                )
            )
            Text("Marvel", color = Color.White)
            Spacer(modifier = Modifier.width(16.dp))
            Checkbox(
                checked = dcChecked,
                onCheckedChange = onDcCheckChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFFE25B5B),
                    uncheckedColor = Color.Gray,
                    checkmarkColor = Color.White
                )
            )
            Text("DC", color = Color.White)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onClearFilters) {
                Text("Clear",
                    color = Color.White,
                    fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = onApplyFilters,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE25B5B))
            ) {
                Text("Apply Filters", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun ComicItem(
    comic: Comics,
    onComicClick: (String) -> Unit
) {
    val context = LocalContext.current
    val imagesResId = getDrawableResourceId(context, comic.imagen)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFF1E1E1E),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onComicClick(comic.id) }
            .padding(12.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 4f)
                .clip(RoundedCornerShape(12.dp))
        ) {
            if (imagesResId != 0) {
                Image(
                    painter = painterResource(id = imagesResId),
                    contentDescription = comic.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No Image",
                        color = Color.White,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.7f)
                            )
                        )
                    )
            )

            if (comic.category.isNotBlank()) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(
                            color = PrimaryAccent,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = comic.category,
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Título
        Text(
            text = comic.title,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            lineHeight = 18.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Autor
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .height(12.dp)
                    .background(PrimaryAccent)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = comic.author,
                color = Color(0xFFB0B0B0),
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Editorial
        Text(
            text = comic.editorial,
            color = Color(0xFF808080),
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(onComicClick = {})
}

