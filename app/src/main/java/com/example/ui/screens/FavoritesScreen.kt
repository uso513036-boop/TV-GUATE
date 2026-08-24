package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Channel
import com.example.model.ChannelWithGuide
import com.example.model.ProgramShow
import com.example.ui.components.ChannelCard
import com.example.ui.theme.GuateBluePrimary
import com.example.ui.theme.GuateBlueSecondary
import com.example.ui.theme.GuateDarkBackground
import com.example.ui.theme.GuateDarkBorder
import com.example.ui.theme.GuateDarkSurface
import com.example.ui.theme.GuateDarkSurfaceVariant
import com.example.ui.theme.GuateGrayText
import com.example.ui.theme.GuateWhite

@Composable
fun FavoritesScreen(
    channelsWithGuide: List<ChannelWithGuide>,
    favoriteChannelIds: Set<String>,
    reminderProgramIds: Set<String>,
    onChannelClick: (Channel) -> Unit,
    onToggleFavorite: (Channel) -> Unit,
    onViewGuideClick: (ChannelWithGuide) -> Unit,
    onToggleReminder: (ProgramShow, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val favoriteChannels = channelsWithGuide.filter { favoriteChannelIds.contains(it.channel.id) }

    // Find all programs that have reminders set
    val reminderPrograms = channelsWithGuide.flatMap { guide ->
        guide.programs.filter { reminderProgramIds.contains(it.id) }.map { show ->
            Pair(show, guide.channel)
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(GuateDarkBackground),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Section: Favorite Channels
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFBBF24), modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Canales Favoritos",
                        color = GuateWhite,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Surface(
                    color = GuateDarkSurface,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "${favoriteChannels.size} guardados",
                        color = GuateBlueSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

        if (favoriteChannels.isEmpty()) {
            item {
                Surface(
                    color = GuateDarkSurface,
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, GuateDarkBorder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Default.StarBorder, contentDescription = null, tint = GuateGrayText, modifier = Modifier.size(36.dp))
                        Spacer(modifier = Modifier.height(10.dp))
                        Text("No tienes canales favoritos aún", color = GuateWhite, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "Toca la estrella (★) en cualquier canal para agregarlo a tus favoritos y acceder rápidamente.",
                            color = GuateGrayText,
                            fontSize = 12.sp,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }
        } else {
            items(favoriteChannels, key = { it.channel.id }) { item ->
                ChannelCard(
                    channelWithGuide = item,
                    isFavorite = true,
                    onChannelClick = onChannelClick,
                    onToggleFavorite = onToggleFavorite,
                    onViewGuideClick = onViewGuideClick,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }
        }

        // Section: Program Reminders
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.NotificationsActive, contentDescription = null, tint = GuateBlueSecondary, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Recordatorios de Programas",
                        color = GuateWhite,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Surface(
                    color = GuateDarkSurface,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "${reminderPrograms.size} alertas",
                        color = GuateBlueSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

        if (reminderPrograms.isEmpty()) {
            item {
                Surface(
                    color = GuateDarkSurface,
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, GuateDarkBorder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Default.Schedule, contentDescription = null, tint = GuateGrayText, modifier = Modifier.size(36.dp))
                        Spacer(modifier = Modifier.height(10.dp))
                        Text("No hay recordatorios programados", color = GuateWhite, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "En la pestaña 'Guía EPG', toca la campana de cualquier programa para programar tu alerta.",
                            color = GuateGrayText,
                            fontSize = 12.sp,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }
        } else {
            items(reminderPrograms, key = { it.first.id }) { (program, channel) ->
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = GuateDarkSurface),
                    border = BorderStroke(1.dp, GuateDarkBorder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 5.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = Color(channel.accentColorHex),
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(channel.logoText, color = GuateWhite, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(channel.name, color = GuateBlueSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("• ${program.startHourMin} - ${program.endHourMin}", color = GuateGrayText, fontSize = 12.sp)
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(program.title, color = GuateWhite, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(program.synopsis, color = GuateGrayText, fontSize = 11.sp, maxLines = 1)
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = { onChannelClick(channel) }) {
                                Icon(Icons.Default.PlayArrow, contentDescription = "Ver Canal", tint = GuateBlueSecondary)
                            }
                            IconButton(onClick = { onToggleReminder(program, channel.name) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = GuateGrayText)
                            }
                        }
                    }
                }
            }
        }
    }
}
