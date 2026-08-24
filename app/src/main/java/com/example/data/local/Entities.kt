package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_channels")
data class FavoriteChannelEntity(
    @PrimaryKey val channelId: String,
    val channelName: String,
    val addedTimestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "program_reminders")
data class ProgramReminderEntity(
    @PrimaryKey val programId: String,
    val channelId: String,
    val channelName: String,
    val programTitle: String,
    val startHourMin: String,
    val endHourMin: String,
    val createdTimestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "watch_history")
data class WatchHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val channelId: String,
    val channelName: String,
    val watchedTimestamp: Long = System.currentTimeMillis(),
    val durationSeconds: Long = 0
)
