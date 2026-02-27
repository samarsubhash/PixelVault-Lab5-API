package com.example.lab5.data

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit interface for the FreeToGame API.
 * Base URL: https://www.freetogame.com/api/
 */
interface FreeToGameApiService {

    /**
     * Get list of all free-to-play games.
     * Optional filters: platform, category (genre), sort-by.
     */
    @GET("games")
    suspend fun getGames(
        @Query("platform") platform: String? = null,
        @Query("category") category: String? = null,
        @Query("sort-by") sortBy: String? = null
    ): List<Game>

    /**
     * Get detailed info about a specific game.
     */
    @GET("game")
    suspend fun getGameDetails(
        @Query("id") id: Int
    ): GameDetail
}
