package com.buzzleaf.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.buzzleaf.data.local.dao.CareDao
import com.buzzleaf.data.local.dao.PlantDao
import com.buzzleaf.data.local.dao.ReminderDao
import com.buzzleaf.data.local.dao.UserDao
import com.buzzleaf.data.local.entities.Care
import com.buzzleaf.data.local.entities.Plant
import com.buzzleaf.data.local.entities.Reminder
import com.buzzleaf.data.local.entities.User
import com.buzzleaf.utils.Constants

@Database(
    entities = [Plant::class, Care::class, Reminder::class, User::class],
    version = Constants.DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun plantDao(): PlantDao
    abstract fun careDao(): CareDao
    abstract fun reminderDao(): ReminderDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    Constants.DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}