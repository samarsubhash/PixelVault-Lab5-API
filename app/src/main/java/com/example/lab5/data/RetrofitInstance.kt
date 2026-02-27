package com.example.lab5.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Singleton Retrofit instance for the FreeToGame API.
 */
object RetrofitInstance {

    private const val BASE_URL = "https://www.freetogame.com/api/"

    val api: FreeToGameApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FreeToGameApiService::class.java)
    }
}
