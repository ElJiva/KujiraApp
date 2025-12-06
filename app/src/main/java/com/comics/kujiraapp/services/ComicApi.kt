package com.comics.kujiraapp.services

import com.comics.kujiraapp.models.ComicListResponse
import com.comics.kujiraapp.models.Comics
import com.comics.kujiraapp.models.Comment
import com.comics.kujiraapp.models.auth.CommentRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ComicApi {
    @GET("comics")
    suspend fun getComics(): List<Comics>

    @GET("comics/{id}")
    suspend fun getComicsDetail(@Path("id") id: String): Comics

    @POST("/{id}/comments")
    suspend fun postComment(
    @Path("id") id: String,
    @Body request: CommentRequest
    ): Comment
}