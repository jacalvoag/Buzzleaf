package com.buzzleaf.data.local.dao

import androidx.room.*
import com.buzzleaf.data.local.entities.Reminder
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {

    @Query("SELECT * FROM reminders WHERE isCompleted = 0 AND startDate <= :currentDate ORDER BY startDate ASC, reminderTime ASC")
    fun getUpcomingReminders(currentDate: Long = System.currentTimeMillis()): Flow<List<Reminder>>

    @Query("SELECT * FROM reminders WHERE plantId = :plantId AND isCompleted = 0 ORDER BY startDate ASC, reminderTime ASC")
    fun getRemindersByPlant(plantId: Int): Flow<List<Reminder>>

    @Query("SELECT * FROM reminders WHERE id = :reminderId")
    fun getReminderById(reminderId: Int): Flow<Reminder?>

    @Query("SELECT * FROM reminders WHERE isCompleted = 1 ORDER BY completedAt DESC LIMIT :limit")
    fun getCompletedReminders(limit: Int = 50): Flow<List<Reminder>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: Reminder): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminders(reminders: List<Reminder>)

    @Update
    suspend fun updateReminder(reminder: Reminder)

    @Delete
    suspend fun deleteReminder(reminder: Reminder)

    @Query("DELETE FROM reminders WHERE id = :reminderId")
    suspend fun deleteReminderById(reminderId: Int)

    @Query("UPDATE reminders SET isCompleted = 1, completedAt = :completedAt WHERE id = :reminderId")
    suspend fun markAsCompleted(reminderId: Int, completedAt: Long = System.currentTimeMillis())

    @Query("DELETE FROM reminders WHERE plantId = :plantId")
    suspend fun deleteAllRemindersForPlant(plantId: Int)
}