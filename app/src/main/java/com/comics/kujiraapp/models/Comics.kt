package com.comics.kujiraapp.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ComicListResponse(
    val comics: List<Comics>?
)

@Serializable
data class Comics(
    @SerializedName("id")
    val id: String,

    @SerializedName("Title")
    val title: String,

    @SerializedName("Imagen")
    val imagen: String,

    @SerializedName("Category")
    val category: String,

    @SerializedName("Editorial")
    val editorial: String,

    @SerializedName("Rating")
    val rating: Double,

    @SerializedName("Author")
    val author: String,

    @SerializedName("VideoLink")
    val videoLink: String,

    @SerializedName("BuyLink")
    val buyLink: String,

    @SerializedName("comments")
    val comments: List<Comment> = emptyList()
)

@Serializable
data class Comment(
    @SerializedName("Username")
    val username: String,

    @SerializedName("Text")
    val text: String,

    @SerializedName("CreatedAt")
    val createdAt: String
)
