package com.comics.kujiraapp.models

import kotlinx.serialization.Serializable

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
    val comments: List<String>
)