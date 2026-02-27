package com.example.lab5.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.lab5.data.Game
import com.example.lab5.ui.theme.PixelCardBg
import com.example.lab5.ui.theme.PixelCyan
import com.example.lab5.ui.theme.PixelDarkPurple
import com.example.lab5.ui.theme.PixelGold
import com.example.lab5.ui.theme.PixelNeonGreen
import com.example.lab5.ui.theme.PixelOrange
import com.example.lab5.ui.theme.PixelPink

/**
 * Reusable pixel-styled game card composable.
 */
@Composable
fun GameCard(
    game: Game,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(4.dp), // pixel-sharp corners
        colors = CardDefaults.cardColors(containerColor = PixelCardBg),
        border = BorderStroke(
            width = 2.dp,
            brush = Brush.linearGradient(
                colors = listOf(PixelNeonGreen, PixelCyan)
            )
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column {
            // Game thumbnail
            Box {
                AsyncImage(
                    model = game.thumbnail,
                    contentDescription = game.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                )

                // Genre badge — top-right
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(
                            color = genreColor(game.genre).copy(alpha = 0.9f),
                            shape = RoundedCornerShape(2.dp)
                        )
                        .padding(horizontal = 6.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = game.genre.uppercase(),
                        style = MaterialTheme.typography.labelMedium,
                        color = PixelDarkPurple
                    )
                }

                // Platform badge — bottom-left
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp)
                        .background(
                            color = PixelDarkPurple.copy(alpha = 0.85f),
                            shape = RoundedCornerShape(2.dp)
                        )
                        .padding(horizontal = 6.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = platformIcon(game.platform),
                        style = MaterialTheme.typography.labelMedium,
                        color = PixelCyan
                    )
                }
            }

            // Text content
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = game.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = game.shortDescription,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "by ${game.developer}",
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    // FREE badge
                    Box(
                        modifier = Modifier
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(PixelNeonGreen, PixelCyan)
                                ),
                                shape = RoundedCornerShape(2.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "FREE",
                            style = MaterialTheme.typography.labelMedium,
                            color = PixelDarkPurple
                        )
                    }
                }
            }
        }
    }
}

/**
 * Returns a color for each genre for badge variety.
 */
private fun genreColor(genre: String) = when (genre.lowercase()) {
    "shooter" -> PixelOrange
    "mmorpg" -> PixelPink
    "strategy" -> PixelGold
    "moba" -> PixelCyan
    "racing" -> PixelOrange
    "sports" -> PixelNeonGreen
    "social" -> PixelPink
    "fighting" -> PixelOrange
    else -> PixelNeonGreen
}

/**
 * Simple platform display text.
 */
private fun platformIcon(platform: String) = when {
    platform.contains("PC", ignoreCase = true) && platform.contains("Browser", ignoreCase = true) -> "🖥️ + 🌐"
    platform.contains("PC", ignoreCase = true) -> "🖥️ PC"
    platform.contains("Browser", ignoreCase = true) -> "🌐 WEB"
    else -> "🎮 ${platform.uppercase()}"
}
