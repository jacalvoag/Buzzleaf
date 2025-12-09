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
    // Insertar planta y sus relaciones en una "transacción" lógica
    suspend fun savePlantWithDetails(plant: Plant, cares: List<Care>, reminders: List<Reminder>) {
        // 1. Insertar planta y obtener el ID generado
        val plantId = plantDao.insertPlant(plant).toInt()

        // 2. Asignar el ID de la planta a los cuidados e insertarlos
        val caresWithId = cares.map { it.copy(plantId = plantId) }
        careDao.insertCares(caresWithId) // Asumiendo que creaste un método para insertar lista

        // 3. Asignar el ID de la planta a los recordatorios e insertarlos
        // Nota: En un caso real complejo, tendrías que mapear qué recordatorio va con qué cuidado específico
        // Por simplicidad ahora, asignamos el plantId general.
        val remindersWithId = reminders.map { it.copy(plantId = plantId) }
        reminderDao.insertReminders(remindersWithId)
    }
}