package com.example.lab5.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab5.data.Game
import com.example.lab5.data.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the game list / home screen.
 */
class GameListViewModel : ViewModel() {

    private val repository = GameRepository()

    private val _uiState = MutableStateFlow<GameListUiState>(GameListUiState.Loading)
    val uiState: StateFlow<GameListUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    // Cache the full list for local search filtering
    private var allGames: List<Game> = emptyList()

    init {
        fetchGames()
    }

    /**
     * Fetch games from API with optional category filter.
     */
    fun fetchGames(category: String? = null) {
        viewModelScope.launch {
            _uiState.value = GameListUiState.Loading
            try {
                val games = repository.getGames(
                    category = category,
                    sortBy = "popularity"
                )
                allGames = games
                _uiState.value = GameListUiState.Success(games)
            } catch (e: Exception) {
                _uiState.value = GameListUiState.Error(
                    e.message ?: "Failed to load games"
                )
            }
        }
    }

    /**
     * Filter by category chip.
     */
    fun selectCategory(category: String?) {
        _selectedCategory.value = category
        _searchQuery.value = ""
        fetchGames(category = category)
    }

    /**
     * Search games locally by title (filters the cached list).
     */
    fun searchGames(query: String) {
        _searchQuery.value = query
        if (query.isBlank()) {
            _uiState.value = GameListUiState.Success(allGames)
        } else {
            val filtered = allGames.filter {
                it.title.contains(query, ignoreCase = true)
            }
            _uiState.value = GameListUiState.Success(filtered)
        }
    }

    /**
     * Refresh list from API.
     */
    fun refresh() {
        fetchGames(category = _selectedCategory.value)
    }
}

/**
 * UI state sealed class for the game list.
 */
sealed class GameListUiState {
    data object Loading : GameListUiState()
    data class Success(val games: List<Game>) : GameListUiState()
    data class Error(val message: String) : GameListUiState()
}
