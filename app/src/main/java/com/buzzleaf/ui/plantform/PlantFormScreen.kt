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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (mode == "CREATE") "Nueva Planta" else "Editar Planta") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    titleContentColor = GreenPrimary,
                    navigationIconContentColor = GreenPrimary
                )
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (uiState.currentStep > 0) {
                    OutlinedButton(
                        onClick = { viewModel.previousStep() },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = GreenPrimary)
                    ) {
                        Text("Anterior")
                    }
                } else {
                    Spacer(modifier = Modifier.width(8.dp))
                }

                Button(
                    onClick = {
                        if (uiState.currentStep < 2) {
                            viewModel.nextStep()
                        } else {
                            viewModel.savePlant {
                                navController.popBackStack()
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
                ) {
                    Text(if (uiState.currentStep == 2) "Guardar" else "Siguiente")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            // Indicador de Pasos
            StepIndicator(currentStep = uiState.currentStep)

            // Contenido dinámico
            when (uiState.currentStep) {
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