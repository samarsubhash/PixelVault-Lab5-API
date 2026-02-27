package com.example.lab5.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository that wraps FreeToGame API calls on the IO dispatcher.
 */
class GameRepository {

    private val api = RetrofitInstance.api

    /**
     * Fetch list of games with optional filters.
     */
    suspend fun getGames(
        platform: String? = null,
        category: String? = null,
        sortBy: String? = null
    ): List<Game> = withContext(Dispatchers.IO) {
        api.getGames(platform = platform, category = category, sortBy = sortBy)
    }

    /**
     * Fetch detailed game information by ID.
     */
    suspend fun getGameDetails(id: Int): GameDetail = withContext(Dispatchers.IO) {
        api.getGameDetails(id)
    }
}
