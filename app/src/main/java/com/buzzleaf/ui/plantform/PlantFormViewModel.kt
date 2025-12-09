package com.buzzleaf.ui.plantform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buzzleaf.data.local.entities.Care
import com.buzzleaf.data.local.entities.Plant
import com.buzzleaf.data.local.entities.Reminder
import com.buzzleaf.data.remote.FirebaseManager // [IMPORTANTE] Asegura este import
import com.buzzleaf.data.repository.PlantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlantFormViewModel @Inject constructor(
    private val repository: PlantRepository,
    private val firebaseManager: FirebaseManager // [IMPORTANTE] Inyección de Firebase
) : ViewModel() {

    // --- ESTADO DE LA UI (Esta es la parte que probablemente faltaba) ---
    private val _uiState = MutableStateFlow(PlantFormUiState())
    val uiState: StateFlow<PlantFormUiState> = _uiState.asStateFlow()

    // --- Funciones de Navegación ---
    fun nextStep() {
        if (_uiState.value.currentStep < 2) {
            _uiState.update { it.copy(currentStep = it.currentStep + 1) }
        }
    }

    fun previousStep() {
        if (_uiState.value.currentStep > 0) {
            _uiState.update { it.copy(currentStep = it.currentStep - 1) }
        }
    }

    // --- Paso 1: Datos Básicos ---
    fun updateBasicInfo(
        name: String? = null,
        type: String? = null,
        sun: String? = null,
        water: String? = null,
        soil: String? = null,
        imageUri: String? = null
    ) {
        _uiState.update { state ->
            state.copy(
                plantName = name ?: state.plantName,
                plantType = type ?: state.plantType,
                sunAmount = sun ?: state.sunAmount,
                waterAmount = water ?: state.waterAmount,
                soilType = soil ?: state.soilType,
                imageUri = imageUri ?: state.imageUri
            )
        }
    }

    // --- Paso 2: Cuidados ---
    fun addCare(type: String, frequency: Int) {
        val newCare = Care(
            plantId = 0, // Se asigna al guardar
            careType = type,
            frequencyDays = frequency
        )
        _uiState.update { it.copy(careList = it.careList + newCare) }
    }

    fun removeCare(care: Care) {
        _uiState.update { it.copy(careList = it.careList - care) }
    }

    // --- Paso 3: Recordatorios ---
    fun updateReminder(careType: String, startDate: Long, time: String) {
        val currentReminders = _uiState.value.reminderList.toMutableList()
        val index = currentReminders.indexOfFirst { it.careToRemind == careType }

        val newReminder = Reminder(
            plantId = 0,
            plantName = _uiState.value.plantName,
            careToRemind = careType,
            startDate = startDate,
            reminderTime = time
        )

        if (index != -1) {
            currentReminders[index] = newReminder
        } else {
            currentReminders.add(newReminder)
        }

        _uiState.update { it.copy(reminderList = currentReminders) }
    }

    // --- Guardar Todo (Con ID de Usuario Real) ---
    fun savePlant(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val state = _uiState.value

            // Obtenemos el ID real del usuario logueado en Firebase
            val currentUserId = firebaseManager.getCurrentUser()?.uid ?: "anonymous"

            val plant = Plant(
                userId = currentUserId, // Guardamos con el ID correcto
                commonName = state.plantName,
                plantType = state.plantType,
                sunAmount = state.sunAmount,
                waterAmount = state.waterAmount,
                soilType = state.soilType,
                imageUrl = state.imageUri
            )

            repository.savePlantWithDetails(plant, state.careList, state.reminderList)
            onSuccess()
        }
    }

    // --- Funciones para Edición ---
    fun setStep(step: Int) {
        _uiState.update { it.copy(currentStep = step) }
    }

    fun loadPlantForEditing(plantId: Int) {
        viewModelScope.launch {
            repository.getPlantById(plantId).collect { details ->
                _uiState.update {
                    it.copy(
                        plantName = details.plant.commonName,
                        plantType = details.plant.plantType,
                        sunAmount = details.plant.sunAmount,
                        waterAmount = details.plant.waterAmount,
                        soilType = details.plant.soilType,
                        imageUri = details.plant.imageUrl,
                        careList = details.cares,
                        reminderList = details.reminders
                    )
                }
            }
        }
    }

    fun saveEditedSection(plantId: Int, section: Int, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val state = _uiState.value
            // Mantenemos el ID de usuario original al editar (o podrías validarlo)
            val currentUserId = firebaseManager.getCurrentUser()?.uid ?: "anonymous"

            when (section) {
                0 -> { // Info Básica
                    val updatedPlant = Plant(
                        id = plantId,
                        userId = currentUserId, // Aseguramos mantener el usuario
                        commonName = state.plantName,
                        plantType = state.plantType,
                        sunAmount = state.sunAmount,
                        waterAmount = state.waterAmount,
                        soilType = state.soilType,
                        imageUrl = state.imageUri
                    )
                    repository.updatePlant(updatedPlant)
                }
                1 -> { // Cuidados
                    repository.updateCares(plantId, state.careList)
                }
                2 -> { // Recordatorios
                    repository.updateReminders(plantId, state.reminderList)
                }
            }
            onSuccess()
        }
    }
}

data class PlantFormUiState(
    val currentStep: Int = 0,
    // Paso 1
    val plantName: String = "",
    val plantType: String = "",
    val sunAmount: String = "",
    val waterAmount: String = "",
    val soilType: String = "",
    val imageUri: String? = null,
    // Paso 2
    val careList: List<Care> = emptyList(),
    // Paso 3
    val reminderList: List<Reminder> = emptyList()
)