package com.buzzleaf.ui.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buzzleaf.data.remote.FirebaseManager
import com.buzzleaf.data.local.entities.Plant
import com.buzzleaf.data.repository.PlantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val repository: PlantRepository,
    private val firebaseManager: FirebaseManager
) : ViewModel() {

    fun savePlant(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val state = _uiState.value

            val currentUserId = firebaseManager.getCurrentUser()?.uid ?: "anonymous"

            val plant = Plant(
                userId = currentUserId,
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

    val plants: StateFlow<List<Plant>> = repository.getAllPlants()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
}