package com.buzzleaf.data.local.dao

import androidx.room.*
import com.buzzleaf.data.local.entities.Care
import kotlinx.coroutines.flow.Flow

@Dao
interface CareDao {

    @Query("SELECT * FROM cares WHERE plantId = :plantId ORDER BY careType ASC")
    fun getCaresByPlant(plantId: Int): Flow<List<Care>>

    @Query("SELECT * FROM cares WHERE id = :careId")
    fun getCareById(careId: Int): Flow<Care?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCare(care: Care): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCares(cares: List<Care>)

    @Update
    suspend fun updateCare(care: Care)

    @Delete
    suspend fun deleteCare(care: Care)

    @Query("DELETE FROM cares WHERE id = :careId")
    suspend fun deleteCareById(careId: Int)

    @Query("DELETE FROM cares WHERE plantId = :plantId")
    suspend fun deleteAllCaresForPlant(plantId: Int)
}