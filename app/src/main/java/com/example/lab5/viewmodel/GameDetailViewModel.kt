package com.example.lab5.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.lab5.data.GameDetail
import com.example.lab5.data.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the game detail screen.
 */
class GameDetailViewModel(private val gameId: Int) : ViewModel() {

    private val repository = GameRepository()

    private val _uiState = MutableStateFlow<GameDetailUiState>(GameDetailUiState.Loading)
    val uiState: StateFlow<GameDetailUiState> = _uiState.asStateFlow()

    init {
        fetchGameDetails()
    }

    private fun fetchGameDetails() {
        viewModelScope.launch {
            _uiState.value = GameDetailUiState.Loading
            try {
                val detail = repository.getGameDetails(gameId)
                _uiState.value = GameDetailUiState.Success(detail)
            } catch (e: Exception) {
                _uiState.value = GameDetailUiState.Error(
                    e.message ?: "Failed to load game details"
                )
            }
        }
    }

    fun retry() {
        fetchGameDetails()
    }
}

sealed class GameDetailUiState {
    data object Loading : GameDetailUiState()
    data class Success(val game: GameDetail) : GameDetailUiState()
    data class Error(val message: String) : GameDetailUiState()
}

/**
 * Factory to create GameDetailViewModel with a game ID parameter.
 */
class GameDetailViewModelFactory(private val gameId: Int) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameDetailViewModel::class.java)) {
            return GameDetailViewModel(gameId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
