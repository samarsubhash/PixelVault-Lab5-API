package com.example.lab5.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lab5.ui.theme.PixelCardBg
import com.example.lab5.ui.theme.PixelCyan
import com.example.lab5.ui.theme.PixelDarkPurple
import com.example.lab5.ui.theme.PixelGold
import com.example.lab5.ui.theme.PixelGray
import com.example.lab5.ui.theme.PixelNeonGreen
import com.example.lab5.ui.theme.PixelOrange
import com.example.lab5.ui.theme.PixelWhite
import com.example.lab5.ui.theme.PressStart2P
import com.example.lab5.viewmodel.GameListUiState
import com.example.lab5.viewmodel.GameListViewModel

// Available genre categories from FreeToGame
private val categories = listOf(
    "All" to null,
    "Shooter" to "shooter",
    "MMORPG" to "mmorpg",
    "Strategy" to "strategy",
    "MOBA" to "moba",
    "Racing" to "racing",
    "Sports" to "sports",
    "Social" to "social",
    "Card Game" to "card",
    "Fighting" to "fighting",
    "Fantasy" to "fantasy",
    "Sci-Fi" to "sci-fi",
    "Action RPG" to "action-rpg"
)

@Composable
fun HomeScreen(
    onGameClick: (Int) -> Unit,
    viewModel: GameListViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    var showSearch by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // ─── Top Header ───
        PixelHeader(
            showSearch = showSearch,
            searchQuery = searchQuery,
            onSearchToggle = { showSearch = !showSearch },
            onSearchQueryChange = { viewModel.searchGames(it) },
            onRefresh = { viewModel.refresh() }
        )

        // ─── Category Chips ───
        CategoryChips(
            selectedCategory = selectedCategory,
            onCategorySelected = { viewModel.selectCategory(it) }
        )

        // ─── Game List ───
        when (val state = uiState) {
            is GameListUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(
                            color = PixelNeonGreen,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "LOADING...",
                            style = MaterialTheme.typography.labelMedium,
                            color = PixelNeonGreen
                        )
                    }
                }
            }

            is GameListUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "⚠️",
                            fontSize = 48.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "ERROR!",
                            style = MaterialTheme.typography.headlineMedium,
                            color = PixelOrange
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = state.message,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 32.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        PixelButton(text = "RETRY", onClick = { viewModel.refresh() })
                    }
                }
            }

            is GameListUiState.Success -> {
                if (state.games.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "NO GAMES FOUND",
                            style = MaterialTheme.typography.headlineMedium,
                            color = PixelGray
                        )
                    }
                } else {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(state.games) { game ->
                            GameCard(
                                game = game,
                                onClick = { onGameClick(game.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PixelHeader(
    showSearch: Boolean,
    searchQuery: String,
    onSearchToggle: () -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onRefresh: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        PixelDarkPurple,
                        MaterialTheme.colorScheme.background
                    )
                )
            )
            .padding(top = 48.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "PIXEL",
                    style = MaterialTheme.typography.displayLarge,
                    color = PixelNeonGreen
                )
                Text(
                    text = "VAULT",
                    style = MaterialTheme.typography.displayLarge,
                    color = PixelCyan
                )
            }

            Row {
                IconButton(onClick = onSearchToggle) {
                    Icon(
                        imageVector = if (showSearch) Icons.Default.Close else Icons.Default.Search,
                        contentDescription = "Search",
                        tint = PixelNeonGreen,
                        modifier = Modifier.size(28.dp)
                    )
                }
                IconButton(onClick = onRefresh) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh",
                        tint = PixelCyan,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }

        // ─── Search Bar ───
        AnimatedVisibility(
            visible = showSearch,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
                    .border(
                        width = 2.dp,
                        color = PixelNeonGreen,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .background(
                        color = PixelCardBg,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 10.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = PixelGray,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    BasicTextField(
                        value = searchQuery,
                        onValueChange = onSearchQueryChange,
                        singleLine = true,
                        textStyle = TextStyle(
                            fontFamily = PressStart2P,
                            fontSize = 10.sp,
                            color = PixelWhite
                        ),
                        cursorBrush = SolidColor(PixelNeonGreen),
                        modifier = Modifier.fillMaxWidth(),
                        decorationBox = { innerTextField ->
                            if (searchQuery.isEmpty()) {
                                Text(
                                    text = "SEARCH GAMES...",
                                    style = TextStyle(
                                        fontFamily = PressStart2P,
                                        fontSize = 10.sp,
                                        color = PixelGray
                                    )
                                )
                            }
                            innerTextField()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun CategoryChips(
    selectedCategory: String?,
    onCategorySelected: (String?) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categories.forEach { (label, value) ->
            val isSelected = selectedCategory == value
            Box(
                modifier = Modifier
                    .border(
                        width = if (isSelected) 2.dp else 1.dp,
                        color = if (isSelected) PixelNeonGreen else PixelGray,
                        shape = RoundedCornerShape(2.dp)
                    )
                    .background(
                        color = if (isSelected) PixelNeonGreen.copy(alpha = 0.15f) else PixelCardBg,
                        shape = RoundedCornerShape(2.dp)
                    )
                    .clickable { onCategorySelected(value) }
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Text(
                    text = label.uppercase(),
                    style = MaterialTheme.typography.labelMedium,
                    color = if (isSelected) PixelNeonGreen else PixelGray
                )
            }
        }
    }
}

@Composable
fun PixelButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(PixelNeonGreen, PixelCyan)
                ),
                shape = RoundedCornerShape(2.dp)
            )
            .border(
                width = 2.dp,
                color = PixelGold,
                shape = RoundedCornerShape(2.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = PixelDarkPurple
        )
    }
}
