package com.buzzleaf.ui.plantdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buzzleaf.data.local.entities.PlantWithDetails
import com.buzzleaf.data.repository.PlantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlantDetailViewModel @Inject constructor(
    private val repository: PlantRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlantDetailUiState())
    val uiState: StateFlow<PlantDetailUiState> = _uiState.asStateFlow()

    fun loadPlant(plantId: Int) {
        viewModelScope.launch {
            repository.getPlantById(plantId).collect { details ->
                _uiState.update { currentState ->
                    currentState.copy(plantDetails = details)
                }
            }
        }
    }

    fun deletePlant(plant: com.buzzleaf.data.local.entities.Plant, onSuccess: () -> Unit) {
        viewModelScope.launch {
            repository.deletePlant(plant)
            onSuccess()
        }
    }
}

data class PlantDetailUiState(
    val plantDetails: PlantWithDetails? = null
)