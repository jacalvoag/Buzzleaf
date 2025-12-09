package com.buzzleaf.data.repository

import com.buzzleaf.data.local.dao.PlantDao
import com.buzzleaf.data.local.dao.CareDao
import com.buzzleaf.data.local.dao.ReminderDao
import com.buzzleaf.data.local.entities.Plant
import com.buzzleaf.data.local.entities.Care
import com.buzzleaf.data.local.entities.Reminder
import javax.inject.Inject

class PlantRepository @Inject constructor(
    private val plantDao: PlantDao,
    private val careDao: CareDao,
    private val reminderDao: ReminderDao
) {
    // ... (tu función savePlantWithDetails existente)

    fun getAllPlants() = plantDao.getAllPlants()

    fun getPlantById(id: Int) = plantDao.getPlantWithDetails(id)

    // Funciones para actualizar secciones individuales
    suspend fun updatePlant(plant: Plant) = plantDao.updatePlant(plant) // Asegúrate de tener @Update en tu DAO

    suspend fun updateCares(plantId: Int, newCares: List<Care>) {
        // Estrategia simple: borrar anteriores y poner nuevos para evitar conflictos de IDs
        careDao.deleteCaresByPlantId(plantId) // Necesitas crear esta Query en CareDao
        val caresWithId = newCares.map { it.copy(plantId = plantId, id = 0) }
        careDao.insertCares(caresWithId)
    }

    suspend fun updateReminders(plantId: Int, newReminders: List<Reminder>) {
        reminderDao.deleteRemindersByPlantId(plantId) // Crear Query en ReminderDao
        val remindersWithId = newReminders.map { it.copy(plantId = plantId, id = 0) }
        reminderDao.insertReminders(remindersWithId)
    }
}