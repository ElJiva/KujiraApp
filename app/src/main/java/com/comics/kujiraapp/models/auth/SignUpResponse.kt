package com.comics.kujiraapp.models.auth

import kotlinx.serialization.Serializable

@Serializable
data class SignUpResponse(
    val token: String
)
