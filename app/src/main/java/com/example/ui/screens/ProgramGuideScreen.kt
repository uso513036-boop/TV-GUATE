package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Channel
import com.example.model.ChannelWithGuide
import com.example.model.ProgramShow
import com.example.ui.theme.GuateBluePrimary
import com.example.ui.theme.GuateBlueSecondary
import com.example.ui.theme.GuateDarkBackground
import com.example.ui.theme.GuateDarkBorder
import com.example.ui.theme.GuateDarkSurface
import com.example.ui.theme.GuateDarkSurfaceVariant
import com.example.ui.theme.GuateGrayText
import com.example.ui.theme.GuateLiveRed
import com.example.ui.theme.GuateShieldGreen
import com.example.ui.theme.GuateWhite
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

enum class EpgTimeFilter(val label: String) {
    ALL("Todo el Día"),
    LIVE_NOW("En Vivo Ahora"),
    MORNING("Mañana (5:00 - 12:00)"),
    AFTERNOON("Tarde (12:00 - 18:00)"),
    NIGHT("Noche (18:00 - 24:00)")
}

@Composable
fun ProgramGuideScreen(
    channelsWithGuide: List<ChannelWithGuide>,
    reminderProgramIds: Set<String>,
    onPlayChannel: (Channel) -> Unit,
    onShowDetail: (ProgramShow, ChannelWithGuide) -> Unit,
    onToggleReminder: (ProgramShow, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTimeFilter by remember { mutableStateOf(EpgTimeFilter.ALL) }
    var currentGtTimeStr by remember { mutableStateOf("") }

    // Real-time clock update for Guatemala timezone (GMT-6)
    LaunchedEffect(Unit) {
        val sdf = SimpleDateFormat("EEEE, d 'de' MMMM • HH:mm:ss 'CST'", Locale("es", "GT"))
        sdf.timeZone = TimeZone.getTimeZone("America/Guatemala")
        while (true) {
            currentGtTimeStr = sdf.format(Date()).replaceFirstChar { it.uppercase() }
            delay(1000)
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(GuateDarkBackground),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Real-Time Guatemala Header
        item {
            Surface(
                color = GuateDarkSurface,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, GuateDarkBorder),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AccessTime,
                                contentDescription = null,
                                tint = GuateBlueSecondary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "HORA OFICIAL GUATEMALA (GMT-6)",
                                color = GuateBlueSecondary,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (currentGtTimeStr.isNotEmpty()) currentGtTimeStr else "Sincronizando reloj satelital...",
                            color = GuateWhite,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    // Live EPG sync badge
                    Surface(
                        color = GuateShieldGreen.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "EPG Sincronizado",
                            color = GuateShieldGreen,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }

        // Time Filter Chips
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                EpgTimeFilter.values().forEach { filter ->
                    val isSelected = filter == selectedTimeFilter
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedTimeFilter = filter },
                        label = { Text(filter.label, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                        shape = RoundedCornerShape(16.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = GuateDarkSurface,
                            labelColor = GuateGrayText,
                            selectedContainerColor = GuateBluePrimary,
                            selectedLabelColor = GuateWhite
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = isSelected,
                            borderColor = GuateDarkBorder,
                            selectedBorderColor = GuateBlueSecondary,
                            borderWidth = 1.dp,
                            selectedBorderWidth = 1.dp
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Channels EPG Grid List
        items(
            items = channelsWithGuide,
            key = { it.channel.id }
        ) { channelGuide ->
            ChannelEpgRow(
                channelGuide = channelGuide,
                filter = selectedTimeFilter,
                reminderProgramIds = reminderProgramIds,
                onPlayChannel = { onPlayChannel(channelGuide.channel) },
                onShowClick = { show -> onShowDetail(show, channelGuide) },
                onToggleReminder = { show -> onToggleReminder(show, channelGuide.channel.name) },
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
            )
        }
    }
}

@Composable
fun ChannelEpgRow(
    channelGuide: ChannelWithGuide,
    filter: EpgTimeFilter,
    reminderProgramIds: Set<String>,
    onPlayChannel: () -> Unit,
    onShowClick: (ProgramShow) -> Unit,
    onToggleReminder: (ProgramShow) -> Unit,
    modifier: Modifier = Modifier
) {
    val channel = channelGuide.channel

    // Filter programs based on selected slot
    val displayedPrograms = channelGuide.programs.filter { show ->
        val startHour = show.startHourMin.split(":").firstOrNull()?.toIntOrNull() ?: 0
        when (filter) {
            EpgTimeFilter.ALL -> true
            EpgTimeFilter.LIVE_NOW -> show.isLiveNow
            EpgTimeFilter.MORNING -> startHour in 5..11
            EpgTimeFilter.AFTERNOON -> startHour in 12..17
            EpgTimeFilter.NIGHT -> startHour in 18..23
        }
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = GuateDarkSurface),
        border = BorderStroke(1.dp, GuateDarkBorder.copy(alpha = 0.5f)),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Channel Header Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onPlayChannel() }
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(channel.accentColorHex),
                        modifier = Modifier.size(32.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = channel.logoText,
                                color = GuateWhite,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(
                            text = channel.name,
                            color = GuateWhite,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = channel.alias,
                            color = GuateGrayText,
                            fontSize = 11.sp
                        )
                    }
                }

                // Quick Tune Button
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = GuateBluePrimary.copy(alpha = 0.2f),
                    border = BorderStroke(1.dp, GuateBluePrimary.copy(alpha = 0.5f)),
                    modifier = Modifier.clickable { onPlayChannel() }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null, tint = GuateBlueSecondary, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(3.dp))
                        Text("Ver Señal", color = GuateBlueSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Shows Carousel
            if (displayedPrograms.isEmpty()) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = GuateDarkSurfaceVariant,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Sin programas listados para este bloque horario.",
                        color = GuateGrayText,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            } else {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(
                        items = displayedPrograms,
                        key = { it.id }
                    ) { show ->
                        val hasReminder = reminderProgramIds.contains(show.id)
                        ProgramTimelineCard(
                            show = show,
                            hasReminder = hasReminder,
                            onClick = { onShowClick(show) },
                            onToggleReminder = { onToggleReminder(show) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProgramTimelineCard(
    show: ProgramShow,
    hasReminder: Boolean,
    onClick: () -> Unit,
    onToggleReminder: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (show.isLiveNow) GuateDarkSurfaceVariant else Color(0xFF151C2C)
        ),
        border = BorderStroke(
            1.dp,
            if (show.isLiveNow) GuateBlueSecondary.copy(alpha = 0.8f) else GuateDarkBorder.copy(alpha = 0.4f)
        ),
        modifier = modifier
            .width(220.dp)
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Time Slot
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Schedule, contentDescription = null, tint = GuateBlueSecondary, modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${show.startHourMin} - ${show.endHourMin}",
                        color = if (show.isLiveNow) GuateBlueSecondary else GuateGrayText,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Live Badge or Reminder Icon
                if (show.isLiveNow) {
                    Surface(
                        color = GuateLiveRed,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = "EN VIVO",
                            color = GuateWhite,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Black,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                        )
                    }
                } else {
                    Box(modifier = Modifier.clickable { onToggleReminder() }) {
                        Icon(
                            imageVector = if (hasReminder) Icons.Default.NotificationsActive else Icons.Default.Notifications,
                            contentDescription = "Recordatorio",
                            tint = if (hasReminder) Color(0xFFFBBF24) else GuateGrayText,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = show.title,
                color = GuateWhite,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = show.synopsis,
                color = GuateGrayText,
                fontSize = 11.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 14.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Progress Bar if Live
            if (show.isLiveNow) {
                LinearProgressIndicator(
                    progress = { show.progressPercent },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    color = GuateBlueSecondary,
                    trackColor = Color.Black.copy(alpha = 0.5f)
                )
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = show.category,
                        color = GuateBlueSecondary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = show.rating,
                        color = GuateGrayText,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
