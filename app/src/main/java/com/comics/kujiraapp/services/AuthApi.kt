package com.comics.kujiraapp.services

import com.comics.kujiraapp.models.auth.LoginRequest
import com.comics.kujiraapp.models.auth.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}
