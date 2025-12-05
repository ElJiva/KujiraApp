package com.comics.kujiraapp.network

import com.comics.kujiraapp.services.AuthApi
import com.comics.kujiraapp.services.ComicApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://134.199.232.81:3000/"

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authApi: AuthApi by lazy {
        instance.create(AuthApi::class.java)
    }
    val comicApi: ComicApi by lazy {
        instance.create(ComicApi::class.java)
    }
}
