package com.comics.kujiraapp.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontVariation.weight
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.colorSpace
import com.comics.kujiraapp.models.Comics

import com.comics.kujiraapp.ui.theme.BackgroundCard
import com.comics.kujiraapp.ui.theme.KujiraAppTheme
import com.comics.kujiraapp.ui.theme.PrimaryAccent
import com.comics.kujiraapp.ui.theme.PrimaryBackground
import com.comics.kujiraapp.ui.theme.SecondaryText
import org.w3c.dom.Comment
import androidx.compose.foundation.Image
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.comics.kujiraapp.utils.getDrawableResourceId



@Composable
fun ComicsHeader(comics: Comics) {
    val context = LocalContext.current
    val imageResId = getDrawableResourceId(context, comics.imagen )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BackgroundCard)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Go Back",
                tint = Color.White,
                modifier = Modifier
                    .size(45.dp)
                    .padding(8.dp)
//                    .alpha(0.7f)
            )

            Text(
                text = comics.title,
                color = Color.White,
                fontSize = 18.sp,

                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
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
//                    .alpha(0.7f),

            )
        }


        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier

                .fillMaxWidth()
                .height(500.dp)
//                .aspectRatio(1f)
                .clip(RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {


            if (imageResId != 0) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = comics.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(450.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                // fallback si no encuentra el recurso
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(450.dp)
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No Image Found", color = Color.White)
                }
            }

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

@Composable
fun InfoContainer(
    title: String,
    content: String,
    rating: Float,
    reviews: Int,
    onBuyClicked: () -> Unit,
    onWatchTrailerClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(BackgroundCard)
            .padding(12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = title,
            color = SecondaryText,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 30.sp
        )
        Text(
            text = content,
            color = Color.DarkGray,
            style = MaterialTheme.typography.bodyMedium
        )

        RatingRow(
            rating = rating,
            reviews = reviews,
            modifier = Modifier.padding(top = 8.dp)
        )

        Column(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onBuyClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryAccent,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Buy Link",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onWatchTrailerClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, PrimaryAccent),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BackgroundCard,
                    contentColor = PrimaryAccent
                )
            ) {
                Text(
                    text = "View Trailer",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }


    }
}


@Composable
fun RatingRow(
    rating: Float,
    reviews: Int,
    modifier: Modifier = Modifier
) {
    val maxStars = 5
    val filledStars = rating.toInt()
    val hasHalfStar = rating - filledStars >= 0.3f
    val emptyStars = maxStars - filledStars - if (hasHalfStar) 1 else 0

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(filledStars) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier.size(24.dp)
            )
        }

        if (hasHalfStar) {
            Icon(
                imageVector = Icons.Filled.StarHalf,
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier.size(24.dp)
            )
        }

        repeat(emptyStars) {
            Icon(
                imageVector = Icons.Outlined.Star,
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = String.format("%.1f", rating),
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "(${reviews.toString()} reviews)",
            color = Color.DarkGray,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}



@Preview
@Composable
fun ComicsHeaderPreview() {
    KujiraAppTheme {
//        ComicsHeader(
//            comics = Comics(
//                id = "1",
//                title = "The Amazing Spider-man",
//                author = "Eiichiro Oda",
//                imagen = "https://example.com/onepiece.jpg",
//                category = "Adventure",
//                editorial = "Shueisha",
//                rating = "4.8",
//                videoLink = "https://example.com/onepiecevideo.mp4",
//                buyLink = "https://example.com/buyonepiece",
//                comments = listOf(
//                    "Great manga!", "Loved it!"
//                )
//            )
//       )
        InfoContainer(
            title = "Author",
            content = "Eiichiro Oda",
            onBuyClicked = {},
            onWatchTrailerClicked = {},
            rating = 4.5f,
            reviews = 100
        )
//        RatingRow(
//            rating = 4.5f
//
//        )

    }
}