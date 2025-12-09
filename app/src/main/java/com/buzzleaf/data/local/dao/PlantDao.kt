package com.buzzleaf.data.local.dao

import androidx.room.*
import com.buzzleaf.data.local.entities.Plant
import com.buzzleaf.data.local.entities.PlantWithDetails // Import necesario
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantDao {
    @Query("SELECT * FROM plants ORDER BY createdAt DESC")
    fun getAllPlants(): Flow<List<Plant>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlant(plant: Plant): Long

    @Update
    suspend fun updatePlant(plant: Plant)

    @Delete
    suspend fun deletePlant(plant: Plant)

    // [IMPORTANTE] Esta función es la que usa el detalle
    @Transaction
    @Query("SELECT * FROM plants WHERE id = :plantId")
    fun getPlantWithDetails(plantId: Int): Flow<PlantWithDetails>
}