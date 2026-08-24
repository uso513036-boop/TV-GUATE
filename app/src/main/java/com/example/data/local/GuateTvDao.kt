package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteChannelDao {
    @Query("SELECT * FROM favorite_channels ORDER BY addedTimestamp DESC")
    fun getAllFavorites(): Flow<List<FavoriteChannelEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_channels WHERE channelId = :channelId)")
    fun isFavorite(channelId: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteChannelEntity)

    @Query("DELETE FROM favorite_channels WHERE channelId = :channelId")
    suspend fun removeFavorite(channelId: String)
}

@Dao
interface ProgramReminderDao {
    @Query("SELECT * FROM program_reminders ORDER BY startHourMin ASC")
    fun getAllReminders(): Flow<List<ProgramReminderEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM program_reminders WHERE programId = :programId)")
    fun hasReminder(programId: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: ProgramReminderEntity)

    @Query("DELETE FROM program_reminders WHERE programId = :programId")
    suspend fun removeReminder(programId: String)
}

@Dao
interface WatchHistoryDao {
    @Query("SELECT * FROM watch_history ORDER BY watchedTimestamp DESC LIMIT 20")
    fun getRecentHistory(): Flow<List<WatchHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: WatchHistoryEntity)

    @Query("DELETE FROM watch_history")
    suspend fun clearHistory()
}
