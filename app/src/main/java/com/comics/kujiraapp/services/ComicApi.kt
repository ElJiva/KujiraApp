package com.comics.kujiraapp.services

import com.comics.kujiraapp.models.ComicListResponse
import com.comics.kujiraapp.models.Comics
import retrofit2.http.GET
import retrofit2.http.Path

interface ComicApi {
    @GET("/")
    suspend fun getComics(): ComicListResponse

    @GET("/{id}")
    suspend fun getComicsDetail(@Path("id") id: String): Comics
}
