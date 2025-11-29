package com.comics.kujiraapp.models.auth

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    val email: String,
    val password: String
)
