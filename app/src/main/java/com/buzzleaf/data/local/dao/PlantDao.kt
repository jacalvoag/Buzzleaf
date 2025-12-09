package com.buzzleaf.data.local.dao

import androidx.room.*
import com.buzzleaf.data.local.entities.Plant
import kotlinx.coroutines.flow.Flow
import com.buzzleaf.data.local.entities.PlantWithDetails

@Dao
interface PlantDao {

    @Query("SELECT * FROM plants ORDER BY createdAt DESC")
    fun getAllPlants(): Flow<List<Plant>>

    @Transaction
    @Query("SELECT * FROM plants WHERE id = :plantId")
    fun getPlantWithDetails(plantId: Int): Flow<PlantWithDetails>

    @Query("SELECT * FROM plants WHERE id = :plantId")
    fun getPlantById(plantId: Int): Flow<Plant?>

    @Query("SELECT * FROM plants WHERE userId = :userId AND commonName LIKE '%' || :query || '%' ORDER BY commonName ASC")
    fun searchPlants(userId: String, query: String): Flow<List<Plant>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlant(plant: Plant): Long

    @Update
    suspend fun updatePlant(plant: Plant)

    @Delete
    suspend fun deletePlant(plant: Plant)

    @Query("DELETE FROM plants WHERE id = :plantId")
    suspend fun deletePlantById(plantId: Int)
}