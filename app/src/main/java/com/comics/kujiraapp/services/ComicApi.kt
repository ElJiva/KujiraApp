package com.comics.kujiraapp.services

import com.comics.kujiraapp.models.ComicListResponse
import com.comics.kujiraapp.models.Comics
import retrofit2.http.GET
import retrofit2.http.Path

interface ComicApi {
    @GET("comics")
    suspend fun getComics(): List<Comics>

    @GET("comics/{id}")
    suspend fun getComicsDetail(@Path("id") id: String): Comics
}
