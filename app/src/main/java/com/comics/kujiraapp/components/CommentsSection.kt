package com.comics.kujiraapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Send
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.comics.kujiraapp.models.Comment
import com.comics.kujiraapp.ui.theme.BackgroundCard
import com.comics.kujiraapp.ui.theme.KujiraAppTheme
import com.comics.kujiraapp.ui.theme.PrimaryAccent
import com.comics.kujiraapp.ui.theme.PrimaryBackground
import com.comics.kujiraapp.ui.theme.SecondaryText

@Composable
fun CommentsSection(
    comments: List<Comment>,
    onSendComment: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var newComment by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(BackgroundCard)
            .padding(16.dp)
    ) {
        Text(
            text = "Comments",
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        // input + botón enviar
        Row(verticalAlignment = Alignment.CenterVertically) {
            // avatar
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(SecondaryText)
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Account",
                    tint = PrimaryBackground,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            TextField(
                value = newComment,
                onValueChange = { newComment = it },
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp),
                placeholder = {
                    Text(
                        text = "Add a comment...",
                        color = SecondaryText
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = PrimaryBackground,
                    unfocusedContainerColor = PrimaryBackground,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.White
                ),
                shape = RoundedCornerShape(24.dp),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send comment",
                        tint = PrimaryAccent,
                        modifier = Modifier.clickable {
                            if (newComment.isNotBlank()) {
                                onSendComment(newComment)
                                newComment = ""
                            }
                        }
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        comments.forEach { comment ->
            CommentItem(
                userName = comment.username,
                date = comment.createdAt,
                comment = comment.text
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun CommentItem(
    userName: String,
    date: String,
    comment: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF181818))
            .padding(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(SecondaryText)
        ){
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Account.",
                tint = PrimaryBackground,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = userName,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = date,
                    color = SecondaryText,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = comment,
                color = Color.LightGray,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
@Preview
fun CommmentsSectionPreview() {
    KujiraAppTheme {
        CommentsSection(
            comments = listOf(
                Comment("Alice", "Great comic!", "2025-11-08T12:34:56Z"),
                Comment("Bob", "I loved the artwork.", "2025-11-07T10:20:30Z"),
                Comment("Charlie", "Can\'t wait for the next issue.", "2025-11-06T08:15:45Z")
            )
        )
    }
}