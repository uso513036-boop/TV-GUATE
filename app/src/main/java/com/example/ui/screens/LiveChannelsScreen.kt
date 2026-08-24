package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.TvOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Channel
import com.example.model.ChannelCategory
import com.example.model.ChannelWithGuide
import com.example.ui.components.CategoryChips
import com.example.ui.components.ChannelCard
import com.example.ui.theme.GuateBlueContainer
import com.example.ui.theme.GuateBluePrimary
import com.example.ui.theme.GuateBlueSecondary
import com.example.ui.theme.GuateDarkBackground
import com.example.ui.theme.GuateDarkSurface
import com.example.ui.theme.GuateGrayText
import com.example.ui.theme.GuateLiveRed
import com.example.ui.theme.GuateShieldGreen
import com.example.ui.theme.GuateWhite

@Composable
fun LiveChannelsScreen(
    channelsWithGuide: List<ChannelWithGuide>,
    selectedCategory: ChannelCategory,
    searchQuery: String,
    favoriteChannelIds: Set<String>,
    onSelectCategory: (ChannelCategory) -> Unit,
    onChannelClick: (Channel) -> Unit,
    onToggleFavorite: (Channel) -> Unit,
    onViewGuideClick: (ChannelWithGuide) -> Unit,
    modifier: Modifier = Modifier
) {
    // Filter channels based on Category and Search query
    val filteredChannels = channelsWithGuide.filter { item ->
        val matchesCategory = (selectedCategory == ChannelCategory.TODOS) || (item.channel.category == selectedCategory)
        val matchesSearch = searchQuery.isBlank() ||
                item.channel.name.contains(searchQuery, ignoreCase = true) ||
                item.channel.alias.contains(searchQuery, ignoreCase = true) ||
                item.channel.description.contains(searchQuery, ignoreCase = true) ||
                item.programs.any { it.title.contains(searchQuery, ignoreCase = true) }
        matchesCategory && matchesSearch
    }

    val featuredChannel = channelsWithGuide.firstOrNull { it.channel.id == "canal7" } ?: channelsWithGuide.firstOrNull()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(GuateDarkBackground),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Hero Featured Channel Banner (Shown when no search query is active and "Todos" category)
        if (searchQuery.isBlank() && selectedCategory == ChannelCategory.TODOS && featuredChannel != null) {
            item {
                FeaturedChannelHero(
                    channelWithGuide = featuredChannel,
                    onPlayClick = { onChannelClick(featuredChannel.channel) },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }

        // Category Filter Pills
        item {
            CategoryChips(
                selectedCategory = selectedCategory,
                onCategorySelected = onSelectCategory
            )
            Spacer(modifier = Modifier.height(4.dp))
        }

        // Section Title & Channel Count
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (selectedCategory == ChannelCategory.TODOS) "Todos los Canales de Guatemala" else selectedCategory.displayName,
                    color = GuateWhite,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
                Surface(
                    color = GuateDarkSurface,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "${filteredChannels.size} en vivo",
                        color = GuateBlueSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

        // Channels List
        if (filteredChannels.isEmpty()) {
            item {
                EmptyChannelsState(searchQuery = searchQuery)
            }
        } else {
            items(
                items = filteredChannels,
                key = { it.channel.id }
            ) { item ->
                ChannelCard(
                    channelWithGuide = item,
                    isFavorite = favoriteChannelIds.contains(item.channel.id),
                    onChannelClick = onChannelClick,
                    onToggleFavorite = onToggleFavorite,
                    onViewGuideClick = onViewGuideClick,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }
        }
    }
}

@Composable
fun FeaturedChannelHero(
    channelWithGuide: ChannelWithGuide,
    onPlayClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val channel = channelWithGuide.channel
    val currentShow = channelWithGuide.currentShow

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = GuateDarkSurface),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onPlayClick() }
            .testTag("featured_channel_hero")
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            GuateBlueContainer.copy(alpha = 0.55f),
                            GuateDarkSurface
                        )
                    )
                )
                .padding(18.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Highlight Badge
                    Surface(
                        color = GuateBluePrimary,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "DESTACADO DE GUATEMALA",
                            color = GuateWhite,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 0.5.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    // Live Pill
                    Surface(
                        color = GuateLiveRed,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .background(GuateWhite, CircleShape)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "EN VIVO",
                                color = GuateWhite,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = channel.name + " - " + channel.alias,
                    color = GuateWhite,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Ahora: " + (currentShow?.title ?: "Transmisión Central"),
                    color = GuateBlueSecondary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = currentShow?.synopsis ?: channel.description,
                    color = GuateGrayText,
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            color = GuateShieldGreen.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Icon(Icons.Default.Shield, contentDescription = null, tint = GuateShieldGreen, modifier = Modifier.size(13.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Anti Geo-Bloqueo", color = GuateShieldGreen, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                        Text("• Sin Publicidad Externa", color = GuateGrayText, fontSize = 11.sp)
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = GuateBluePrimary,
                        modifier = Modifier.clickable { onPlayClick() }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null, tint = GuateWhite, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Sintonizar", color = GuateWhite, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyChannelsState(searchQuery: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 60.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = GuateDarkSurface,
                modifier = Modifier.size(72.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.TvOff,
                        contentDescription = null,
                        tint = GuateGrayText,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "No se encontraron canales",
                color = GuateWhite,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = if (searchQuery.isNotEmpty()) "No hay resultados para \"$searchQuery\". Prueba con otro canal o categoría." else "No hay canales disponibles en esta categoría.",
                color = GuateGrayText,
                fontSize = 13.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}
