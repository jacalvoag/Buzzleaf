package com.buzzleaf.di

import android.content.Context
import androidx.room.Room
import com.buzzleaf.data.local.database.AppDatabase
import com.buzzleaf.data.remote.FirebaseManager
import com.buzzleaf.utils.Constants
import com.buzzleaf.utils.PreferencesManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseManager(
        auth: FirebaseAuth,
        storage: FirebaseStorage
    ): FirebaseManager {
        return FirebaseManager(auth, storage)
    }

    @Provides
    @Singleton
    fun providePreferencesManager(
        @ApplicationContext context: Context
    ): PreferencesManager {
        return PreferencesManager(context)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            Constants.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providePlantDao(database: AppDatabase) = database.plantDao()

    @Provides
    @Singleton
    fun provideCareDao(database: AppDatabase) = database.careDao()

    @Provides
    @Singleton
    fun provideReminderDao(database: AppDatabase) = database.reminderDao()

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase) = database.userDao()
}