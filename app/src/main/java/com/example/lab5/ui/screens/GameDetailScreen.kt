package com.example.lab5.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.lab5.ui.theme.PixelCardBg
import com.example.lab5.ui.theme.PixelCyan
import com.example.lab5.ui.theme.PixelDarkPurple
import com.example.lab5.ui.theme.PixelGold
import com.example.lab5.ui.theme.PixelGray
import com.example.lab5.ui.theme.PixelNeonGreen
import com.example.lab5.ui.theme.PixelOrange
import com.example.lab5.ui.theme.PixelWhite
import com.example.lab5.viewmodel.GameDetailUiState
import com.example.lab5.viewmodel.GameDetailViewModel
import com.example.lab5.viewmodel.GameDetailViewModelFactory

@Composable
fun GameDetailScreen(
    gameId: Int,
    onBackClick: () -> Unit,
    viewModel: GameDetailViewModel = viewModel(
        factory = GameDetailViewModelFactory(gameId)
    )
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when (val state = uiState) {
            is GameDetailUiState.Loading -> {
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

            is GameDetailUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("⚠️", fontSize = 48.sp)
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
                        Row {
                            PixelButton(text = "← BACK", onClick = onBackClick)
                            Spacer(modifier = Modifier.width(12.dp))
                            PixelButton(text = "RETRY", onClick = { viewModel.retry() })
                        }
                    }
                }
            }

            is GameDetailUiState.Success -> {
                val game = state.game

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    // ─── Hero Image ───
                    Box {
                        AsyncImage(
                            model = game.thumbnail,
                            contentDescription = game.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp)
                        )

                        // Gradient overlay
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp)
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            PixelDarkPurple.copy(alpha = 0.3f),
                                            PixelDarkPurple
                                        )
                                    )
                                )
                        )

                        // Back Button
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier
                                .padding(top = 40.dp, start = 8.dp)
                                .align(Alignment.TopStart)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(
                                        color = PixelCardBg.copy(alpha = 0.8f),
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = PixelNeonGreen,
                                        shape = RoundedCornerShape(4.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back",
                                    tint = PixelNeonGreen,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        // Title on image
                        Text(
                            text = game.title,
                            style = MaterialTheme.typography.headlineMedium,
                            color = PixelWhite,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                        )
                    }

                    // ─── Info Badges Row ───
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        InfoBadge(label = "GENRE", value = game.genre, color = PixelNeonGreen)
                        InfoBadge(label = "PLATFORM", value = game.platform, color = PixelCyan)
                        InfoBadge(label = "STATUS", value = game.status ?: "Live", color = PixelGold)
                    }

                    // ─── Details Section ───
                    Column(modifier = Modifier.padding(16.dp)) {

                        // Publisher & Developer
                        PixelInfoRow(label = "PUBLISHER", value = game.publisher)
                        Spacer(modifier = Modifier.height(4.dp))
                        PixelInfoRow(label = "DEVELOPER", value = game.developer)
                        Spacer(modifier = Modifier.height(4.dp))
                        PixelInfoRow(label = "RELEASED", value = game.releaseDate)

                        Spacer(modifier = Modifier.height(16.dp))

                        // Description
                        SectionHeader(text = "DESCRIPTION")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = game.description,
                            style = MaterialTheme.typography.bodyLarge,
                            color = PixelWhite
                        )

                        // System Requirements
                        game.minimumSystemRequirements?.let { sysReq ->
                            Spacer(modifier = Modifier.height(20.dp))
                            SectionHeader(text = "SYSTEM REQUIREMENTS")
                            Spacer(modifier = Modifier.height(8.dp))

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                        width = 1.dp,
                                        color = PixelCyan,
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .background(
                                        color = PixelCardBg,
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                sysReq.os?.let { PixelInfoRow(label = "OS", value = it) }
                                sysReq.processor?.let { PixelInfoRow(label = "CPU", value = it) }
                                sysReq.memory?.let { PixelInfoRow(label = "RAM", value = it) }
                                sysReq.graphics?.let { PixelInfoRow(label = "GPU", value = it) }
                                sysReq.storage?.let { PixelInfoRow(label = "DISK", value = it) }
                            }
                        }

                        // Screenshots
                        if (!game.screenshots.isNullOrEmpty()) {
                            Spacer(modifier = Modifier.height(20.dp))
                            SectionHeader(text = "SCREENSHOTS")
                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .horizontalScroll(rememberScrollState()),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                game.screenshots.forEach { screenshot ->
                                    AsyncImage(
                                        model = screenshot.image,
                                        contentDescription = "Screenshot",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .width(280.dp)
                                            .height(160.dp)
                                            .clip(RoundedCornerShape(4.dp))
                                            .border(
                                                width = 1.dp,
                                                color = PixelNeonGreen,
                                                shape = RoundedCornerShape(4.dp)
                                            )
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Play button
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            PixelButton(
                                text = "▶ PLAY FREE",
                                onClick = { /* Could open game URL */ }
                            )
                        }

                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoBadge(label: String, value: String, color: androidx.compose.ui.graphics.Color) {
    Column(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = color,
                shape = RoundedCornerShape(4.dp)
            )
            .background(
                color = color.copy(alpha = 0.1f),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 10.dp, vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = PixelGray,
            fontSize = 7.sp
        )
        Text(
            text = value.uppercase(),
            style = MaterialTheme.typography.labelMedium,
            color = color
        )
    }
}

@Composable
private fun PixelInfoRow(label: String, value: String) {
    Row {
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.labelMedium,
            color = PixelCyan
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = PixelWhite
        )
    }
}

@Composable
private fun SectionHeader(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(16.dp)
                .background(PixelNeonGreen)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.headlineMedium,
            color = PixelNeonGreen
        )
    }
}
