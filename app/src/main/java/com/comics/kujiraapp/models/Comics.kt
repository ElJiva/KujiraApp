package com.comics.kujiraapp.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ComicListResponse(
    val comics: List<Comics>?
)

@Serializable
data class Comics(
    val id: String,
    val title: String,
    val imagen: String,
    val category: String,
    val editorial: String,
    val rating: String,
    val author: String,
    val videoLink: String,
    val buyLink: String,
    val comments: List<Comment> = emptyList()
)

@Serializable
data class Comment(
    @SerialName("Username") val username: String,
    @SerialName("Text") val text: String,
    @SerialName("CreatedAt") val createdAt: String
)