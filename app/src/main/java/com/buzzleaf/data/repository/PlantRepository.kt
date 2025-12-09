package com.buzzleaf.data.repository

import com.buzzleaf.data.local.dao.PlantDao
import com.buzzleaf.data.local.dao.CareDao
import com.buzzleaf.data.local.dao.ReminderDao
import com.buzzleaf.data.local.entities.Plant
import com.buzzleaf.data.local.entities.Care
import com.buzzleaf.data.local.entities.Reminder
import com.buzzleaf.data.local.entities.PlantWithDetails // Import necesario
import kotlinx.coroutines.flow.Flow // Import necesario
import javax.inject.Inject

class PlantRepository @Inject constructor(
    private val plantDao: PlantDao,
    private val careDao: CareDao,
    private val reminderDao: ReminderDao
) {
    fun getAllPlants(): Flow<List<Plant>> = plantDao.getAllPlants()

    fun getPlantById(id: Int): Flow<PlantWithDetails> = plantDao.getPlantWithDetails(id)

    fun getPlantsForUser(userId: String): Flow<List<Plant>> = plantDao.getPlantsForUser(userId)

    suspend fun savePlantWithDetails(plant: Plant, cares: List<Care>, reminders: List<Reminder>) {
        val plantId = plantDao.insertPlant(plant).toInt()

        val caresWithId = cares.map { it.copy(plantId = plantId) }
        careDao.insertCares(caresWithId)

        val remindersWithId = reminders.map { it.copy(plantId = plantId) }
        reminderDao.insertReminders(remindersWithId)
    }

    suspend fun updatePlant(plant: Plant) = plantDao.updatePlant(plant)

    suspend fun updateCares(plantId: Int, newCares: List<Care>) {
        careDao.deleteCaresByPlantId(plantId)
        val caresWithId = newCares.map { it.copy(plantId = plantId, id = 0) }
        careDao.insertCares(caresWithId)
    }

    suspend fun updateReminders(plantId: Int, newReminders: List<Reminder>) {
        reminderDao.deleteRemindersByPlantId(plantId)
        val remindersWithId = newReminders.map { it.copy(plantId = plantId, id = 0) }
        reminderDao.insertReminders(remindersWithId)
    }
}