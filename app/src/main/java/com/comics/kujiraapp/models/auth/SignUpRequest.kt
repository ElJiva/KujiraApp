package com.comics.kujiraapp.models.auth

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
  val username: String,
  val email: String,
  val password: String,
  val confirmPassword: String
)
