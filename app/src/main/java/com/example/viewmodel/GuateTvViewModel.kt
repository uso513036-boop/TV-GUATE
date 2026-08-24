package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.GuatemalaTvRepository
import com.example.data.local.FavoriteChannelEntity
import com.example.data.local.GuateTvDatabase
import com.example.data.local.ProgramReminderEntity
import com.example.data.local.WatchHistoryEntity
import com.example.model.Channel
import com.example.model.ChannelCategory
import com.example.model.ChannelWithGuide
import com.example.model.ProgramShow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class GuateTvUiState(
    val channelsWithGuide: List<ChannelWithGuide> = emptyList(),
    val selectedCategory: ChannelCategory = ChannelCategory.TODOS,
    val searchQuery: String = "",
    val activePlayingChannel: Channel? = null,
    val isPlayerFullscreen: Boolean = false,
    val isGuideLoading: Boolean = false,
    val selectedChannelForGuideDetail: ChannelWithGuide? = null,
    val selectedProgramDetail: ProgramShow? = null
)

class GuateTvViewModel(application: Application) : AndroidViewModel(application) {

    private val database = GuateTvDatabase.getDatabase(application)
    private val favoriteDao = database.favoriteChannelDao()
    private val reminderDao = database.programReminderDao()
    private val historyDao = database.watchHistoryDao()

    private val _uiState = MutableStateFlow(GuateTvUiState())
    val uiState: StateFlow<GuateTvUiState> = _uiState.asStateFlow()

    // Favorite channel IDs set flow
    val favoriteChannelIds: StateFlow<Set<String>> = favoriteDao.getAllFavorites()
        .map { list -> list.map { it.channelId }.toSet() }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptySet())

    // Program reminder IDs set flow
    val reminderProgramIds: StateFlow<Set<String>> = reminderDao.getAllReminders()
        .map { list -> list.map { it.programId }.toSet() }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptySet())

    init {
        loadChannelsAndGuide()
        startPeriodicEpgUpdate()
    }

    fun loadChannelsAndGuide() {
        val guides = GuatemalaTvRepository.getAllChannelsWithGuide()
        _uiState.value = _uiState.value.copy(
            channelsWithGuide = guides
        )
    }

    private fun startPeriodicEpgUpdate() {
        viewModelScope.launch(Dispatchers.Default) {
            while (true) {
                delay(60_000) // Update show progress and live badge every minute
                val updatedGuides = GuatemalaTvRepository.getAllChannelsWithGuide()
                _uiState.value = _uiState.value.copy(channelsWithGuide = updatedGuides)
            }
        }
    }

    fun selectCategory(category: ChannelCategory) {
        _uiState.value = _uiState.value.copy(selectedCategory = category)
    }

    fun updateSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }

    fun playChannel(channel: Channel) {
        _uiState.value = _uiState.value.copy(
            activePlayingChannel = channel,
            isPlayerFullscreen = false
        )
        // Record watch history
        viewModelScope.launch(Dispatchers.IO) {
            historyDao.insertHistory(
                WatchHistoryEntity(
                    channelId = channel.id,
                    channelName = channel.name
                )
            )
        }
    }

    fun closePlayer() {
        _uiState.value = _uiState.value.copy(
            activePlayingChannel = null,
            isPlayerFullscreen = false
        )
    }

    fun togglePlayerFullscreen() {
        _uiState.value = _uiState.value.copy(
            isPlayerFullscreen = !_uiState.value.isPlayerFullscreen
        )
    }

    fun playNextChannel() {
        val currentChannel = _uiState.value.activePlayingChannel ?: return
        val allChannels = GuatemalaTvRepository.channels
        val currentIndex = allChannels.indexOfFirst { it.id == currentChannel.id }
        if (currentIndex != -1) {
            val nextIndex = (currentIndex + 1) % allChannels.size
            playChannel(allChannels[nextIndex])
        }
    }

    fun playPreviousChannel() {
        val currentChannel = _uiState.value.activePlayingChannel ?: return
        val allChannels = GuatemalaTvRepository.channels
        val currentIndex = allChannels.indexOfFirst { it.id == currentChannel.id }
        if (currentIndex != -1) {
            val prevIndex = if (currentIndex - 1 < 0) allChannels.size - 1 else currentIndex - 1
            playChannel(allChannels[prevIndex])
        }
    }

    fun toggleFavorite(channel: Channel) {
        viewModelScope.launch(Dispatchers.IO) {
            val isFav = favoriteChannelIds.value.contains(channel.id)
            if (isFav) {
                favoriteDao.removeFavorite(channel.id)
            } else {
                favoriteDao.insertFavorite(
                    FavoriteChannelEntity(
                        channelId = channel.id,
                        channelName = channel.name
                    )
                )
            }
        }
    }

    fun toggleProgramReminder(program: ProgramShow, channelName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val hasRem = reminderProgramIds.value.contains(program.id)
            if (hasRem) {
                reminderDao.removeReminder(program.id)
            } else {
                reminderDao.insertReminder(
                    ProgramReminderEntity(
                        programId = program.id,
                        channelId = program.channelId,
                        channelName = channelName,
                        programTitle = program.title,
                        startHourMin = program.startHourMin,
                        endHourMin = program.endHourMin
                    )
                )
            }
        }
    }

    fun showProgramDetail(program: ProgramShow, channelWithGuide: ChannelWithGuide) {
        _uiState.value = _uiState.value.copy(
            selectedProgramDetail = program,
            selectedChannelForGuideDetail = channelWithGuide
        )
    }

    fun dismissProgramDetail() {
        _uiState.value = _uiState.value.copy(
            selectedProgramDetail = null
        )
    }
}
