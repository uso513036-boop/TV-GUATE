package com.example.ui.screens

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material.icons.outlined.Tv
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.GuatemalaTvRepository
import com.example.model.Channel
import com.example.model.StreamType
import com.example.ui.components.GuateTopBar
import com.example.ui.player.CleanWebStreamPlayer
import com.example.ui.player.NativeTvPlayer
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
import com.example.viewmodel.GuateTvViewModel

enum class MainNavTab(val title: String, val activeIcon: ImageVector, val inactiveIcon: ImageVector) {
    CHANNELS("En Vivo", Icons.Filled.Tv, Icons.Outlined.Tv),
    EPG_GUIDE("Guía EPG", Icons.Filled.DateRange, Icons.Outlined.DateRange),
    GEO_SCANNER("Geo-Scanner", Icons.Filled.Security, Icons.Outlined.Security),
    FAVORITES("Favoritos", Icons.Filled.Star, Icons.Outlined.StarBorder)
}

@Composable
fun MainAppScreen(
    viewModel: GuateTvViewModel = viewModel()
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val uiState by viewModel.uiState.collectAsState()
    val favoriteIds by viewModel.favoriteChannelIds.collectAsState()
    val reminderIds by viewModel.reminderProgramIds.collectAsState()

    var selectedTab by remember { mutableStateOf(MainNavTab.CHANNELS) }

    // Lock/Unlock landscape on fullscreen player
    LaunchedEffect(uiState.isPlayerFullscreen) {
        activity?.requestedOrientation = if (uiState.isPlayerFullscreen) {
            ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        } else {
            ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    // Handle system Back Press gracefully
    BackHandler(enabled = uiState.activePlayingChannel != null || uiState.isPlayerFullscreen) {
        if (uiState.isPlayerFullscreen) {
            viewModel.togglePlayerFullscreen()
        } else {
            viewModel.closePlayer()
        }
    }

    Scaffold(
        containerColor = GuateDarkBackground,
        topBar = {
            if (uiState.activePlayingChannel == null || !uiState.isPlayerFullscreen) {
                GuateTopBar(
                    searchQuery = uiState.searchQuery,
                    onSearchQueryChange = { viewModel.updateSearchQuery(it) },
                    onGeoScanClick = { selectedTab = MainNavTab.GEO_SCANNER }
                )
            }
        },
        bottomBar = {
            if (uiState.activePlayingChannel == null || !uiState.isPlayerFullscreen) {
                GuateBottomNavigationBar(
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Main Content Tabs
            when (selectedTab) {
                MainNavTab.CHANNELS -> {
                    LiveChannelsScreen(
                        channelsWithGuide = uiState.channelsWithGuide,
                        selectedCategory = uiState.selectedCategory,
                        searchQuery = uiState.searchQuery,
                        favoriteChannelIds = favoriteIds,
                        onSelectCategory = { viewModel.selectCategory(it) },
                        onChannelClick = { viewModel.playChannel(it) },
                        onToggleFavorite = { viewModel.toggleFavorite(it) },
                        onViewGuideClick = { guide ->
                            selectedTab = MainNavTab.EPG_GUIDE
                        }
                    )
                }
                MainNavTab.EPG_GUIDE -> {
                    ProgramGuideScreen(
                        channelsWithGuide = uiState.channelsWithGuide,
                        reminderProgramIds = reminderIds,
                        onPlayChannel = { viewModel.playChannel(it) },
                        onShowDetail = { show, guide ->
                            viewModel.showProgramDetail(show, guide)
                        },
                        onToggleReminder = { show, channelName ->
                            viewModel.toggleProgramReminder(show, channelName)
                        }
                    )
                }
                MainNavTab.GEO_SCANNER -> {
                    GeoScannerScreen(
                        channels = GuatemalaTvRepository.channels,
                        diagnostics = uiState.geoDiagnostics,
                        isScanning = uiState.isGeoScanInProgress,
                        onStartScan = { viewModel.runGeoDiagnostics() },
                        onPlayChannel = { viewModel.playChannel(it) }
                    )
                }
                MainNavTab.FAVORITES -> {
                    FavoritesScreen(
                        channelsWithGuide = uiState.channelsWithGuide,
                        favoriteChannelIds = favoriteIds,
                        reminderProgramIds = reminderIds,
                        onChannelClick = { viewModel.playChannel(it) },
                        onToggleFavorite = { viewModel.toggleFavorite(it) },
                        onViewGuideClick = { selectedTab = MainNavTab.EPG_GUIDE },
                        onToggleReminder = { show, channelName ->
                            viewModel.toggleProgramReminder(show, channelName)
                        }
                    )
                }
            }

            // Mini Player Bar (shown at bottom above nav bar when a channel is active and not fullscreen)
            val playingChannel = uiState.activePlayingChannel
            if (playingChannel != null && !uiState.isPlayerFullscreen) {
                MiniPlayerBar(
                    channel = playingChannel,
                    onExpand = { viewModel.togglePlayerFullscreen() },
                    onClose = { viewModel.closePlayer() },
                    onNext = { viewModel.playNextChannel() },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }

            // Fullscreen Player Overlay
            if (playingChannel != null && uiState.isPlayerFullscreen) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                ) {
                    if (playingChannel.streamType == StreamType.CLEAN_SANDBOX) {
                        CleanWebStreamPlayer(channel = playingChannel)
                    } else {
                        NativeTvPlayer(
                            channel = playingChannel,
                            onClose = { viewModel.closePlayer() },
                            onPreviousChannel = { viewModel.playPreviousChannel() },
                            onNextChannel = { viewModel.playNextChannel() },
                            isFullscreen = true,
                            onToggleFullscreen = { viewModel.togglePlayerFullscreen() }
                        )
                    }
                }
            }

            // Show Program Details Modal Sheet
            val selectedProgram = uiState.selectedProgramDetail
            val selectedGuide = uiState.selectedChannelForGuideDetail
            if (selectedProgram != null && selectedGuide != null) {
                ProgramDetailModal(
                    program = selectedProgram,
                    channelWithGuide = selectedGuide,
                    hasReminder = reminderIds.contains(selectedProgram.id),
                    onDismiss = { viewModel.dismissProgramDetail() },
                    onPlayChannel = { channel ->
                        viewModel.dismissProgramDetail()
                        viewModel.playChannel(channel)
                    },
                    onToggleReminder = { show, channelName ->
                        viewModel.toggleProgramReminder(show, channelName)
                    }
                )
            }
        }
    }
}

@Composable
fun MiniPlayerBar(
    channel: Channel,
    onExpand: () -> Unit,
    onClose: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = GuateDarkSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, GuateBluePrimary.copy(alpha = 0.5f)),
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onExpand() }
            .testTag("mini_player_bar")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(channel.accentColorHex),
                    modifier = Modifier.size(36.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(channel.logoText, color = GuateWhite, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = channel.name,
                            color = GuateWhite,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            color = GuateLiveRed,
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "LIVE",
                                color = GuateWhite,
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Black,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                            )
                        }
                    }
                    Text(
                        text = if (channel.currentShowTitle.isNotEmpty()) channel.currentShowTitle else channel.alias,
                        color = GuateBlueSecondary,
                        fontSize = 11.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            // Quick actions: Expand to Fullscreen & Close
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = onExpand,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Fullscreen,
                        contentDescription = "Pantalla Completa",
                        tint = GuateWhite,
                        modifier = Modifier.size(22.dp)
                    )
                }

                IconButton(
                    onClick = onClose,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cerrar",
                        tint = GuateGrayText,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun GuateBottomNavigationBar(
    selectedTab: MainNavTab,
    onTabSelected: (MainNavTab) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        containerColor = GuateDarkSurface,
        contentColor = GuateWhite,
        tonalElevation = 8.dp,
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
    ) {
        MainNavTab.values().forEach { tab ->
            val isSelected = tab == selectedTab

            NavigationBarItem(
                selected = isSelected,
                onClick = { onTabSelected(tab) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) tab.activeIcon else tab.inactiveIcon,
                        contentDescription = tab.title,
                        modifier = Modifier.size(22.dp)
                    )
                },
                label = {
                    Text(
                        text = tab.title,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = GuateWhite,
                    selectedTextColor = GuateBlueSecondary,
                    indicatorColor = GuateBluePrimary,
                    unselectedIconColor = GuateGrayText,
                    unselectedTextColor = GuateGrayText
                ),
                modifier = Modifier.testTag("nav_tab_${tab.name}")
            )
        }
    }
}
