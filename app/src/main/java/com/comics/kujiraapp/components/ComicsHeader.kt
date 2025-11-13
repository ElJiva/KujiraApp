package com.comics.kujiraapp.components

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontVariation.weight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.comics.kujiraapp.models.Comics
import com.comics.kujiraapp.ui.theme.KujiraAppTheme
import com.comics.kujiraapp.ui.theme.SecondaryText

@Composable
fun ComicsHeader(comics: Comics) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth() // Asegura que el Row ocupe todo el ancho disponible
                .padding(horizontal = 8.dp), // Agrega un padding a los lados si lo necesitas
            horizontalArrangement = Arrangement.SpaceBetween, // Distribuye el espacio entre los elementos
            verticalAlignment = Alignment.CenterVertically // Opcional: Centra verticalmente los iconos
        ) {
            // 1. Icono de la Izquierda (ArrowBack)
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Go Back",
                tint = Color.White,
                modifier = Modifier
                    .size(45.dp)
                    .padding(8.dp)
                    .alpha(0.7f)
            )

            Text(
                text = comics.title,
                color = Color.White,
                fontSize = 20.sp,
                // **✨ Implementación para puntos suspensivos:**
                maxLines = 1, // Limita el texto a una sola línea
                overflow = TextOverflow.Ellipsis, // Muestra "..." si el texto excede el espacio
                // **✨ Fin de la implementación**
                modifier = Modifier
                    // Es crucial añadir un peso (weight) para que el texto ocupe
                    // el espacio restante entre los iconos.
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )

            Icon(
                imageVector = Icons.Default.BookmarkBorder,
                contentDescription = "Bookmark",
                tint = Color.White,
                modifier = Modifier
                    .size(45.dp)
                    .padding(8.dp)
                    .alpha(0.7f),

                )
        }


        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(16.dp))
        ) {


            AsyncImage(
                model = comics.imagen,
                contentDescription = comics.title,
                error = ColorPainter(SecondaryText),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.4f), SecondaryText.copy(alpha = 0.4f)
                            )
                        )
                    )
            )
        }
    }
}

@Preview
@Composable
fun ComicsHeaderPreview() {
    KujiraAppTheme {
        ComicsHeader(
            comics = Comics(
                id = "1",
                title = "The Amazing Spider-man",
                author = "Eiichiro Oda",
                imagen = "https://example.com/onepiece.jpg",
                category = "Adventure",
                editorial = "Shueisha",
                rating = "4.8",
                videoLink = "https://example.com/onepiecevideo.mp4",
                buyLink = "https://example.com/buyonepiece",
                comments = listOf(
                    "Great manga!", "Loved it!"


                )
            )
        )
    }
}