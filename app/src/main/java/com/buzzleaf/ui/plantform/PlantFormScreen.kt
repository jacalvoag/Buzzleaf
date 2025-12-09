package com.buzzleaf.ui.plantform

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.buzzleaf.ui.plantform.steps.BasicInfoStep
import com.buzzleaf.ui.plantform.steps.CareStep
import com.buzzleaf.ui.plantform.steps.RemindersStep
import com.buzzleaf.ui.theme.GreenPrimary
import com.buzzleaf.ui.theme.GreenLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantFormScreen(
    navController: NavController,
    plantId: Int? = null,
    mode: String = "CREATE",
    startPage: Int = 0,
    viewModel: PlantFormViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Cargar datos si es modo edición y aún no se han cargado
    LaunchedEffect(plantId, mode) {
        if (mode == "EDIT" && plantId != null) {
            viewModel.loadPlantForEditing(plantId)
            viewModel.setStep(startPage) // Forzar ir a la página específica
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (mode == "CREATE") "Nueva Planta"
                        else when(startPage) {
                            0 -> "Editar Información"
                            1 -> "Editar Cuidados"
                            2 -> "Editar Recordatorios"
                            else -> "Editar"
                        }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        bottomBar = {
            // Barra inferior diferente según el modo
            if (mode == "CREATE") {
                // ... (Tu lógica existente de navegación Next/Prev) ...
                Row( /* ... */ ) {
                    // ... Botones Anterior / Siguiente ...
                }
            } else {
                // MODO EDICIÓN: Solo botón de guardar esa sección
                Button(
                    onClick = {
                        viewModel.saveEditedSection(plantId!!, startPage) {
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
                ) {
                    Text("Guardar Cambios")
                }
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {

            // Mostrar indicador de pasos SOLO si es CREATE
            if (mode == "CREATE") {
                StepIndicator(currentStep = uiState.currentStep)
            }

            // Mostrar el contenido.
            // Si es CREATE, usa uiState.currentStep.
            // Si es EDIT, usa startPage directamente (ya que no navegamos).
            val stepToShow = if (mode == "CREATE") uiState.currentStep else startPage

            when (stepToShow) {
                0 -> BasicInfoStep(
                    uiState = uiState,
                    onInfoChange = { n, t, s, w, so, i -> viewModel.updateBasicInfo(n, t, s, w, so, i) }
                )
                1 -> CareStep(
                    uiState = uiState,
                    onAddCare = { t, f -> viewModel.addCare(t, f) },
                    onRemoveCare = { viewModel.removeCare(it) }
                )
                2 -> RemindersStep(
                    uiState = uiState,
                    onUpdateReminder = { c, d, t -> viewModel.updateReminder(c, d, t) }
                )
            }
        }
    }
}

@Composable
fun StepIndicator(currentStep: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(3) { index ->
            val color = if (index <= currentStep) GreenPrimary else GreenLight
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(color, CircleShape)
            )
            if (index < 2) {
                Spacer(modifier = Modifier.width(8.dp))
                Divider(
                    modifier = Modifier
                        .width(40.dp)
                        .height(2.dp),
                    color = if (index < currentStep) GreenPrimary else GreenLight
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}