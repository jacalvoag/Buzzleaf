package com.buzzleaf.ui.plantform

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun PlantFormScreen(
    navController: NavController,
    plantId: Int? = null,
    mode: String = "CREATE",
    startPage: Int = 0
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Formulario de Planta")
        Text("Mode: $mode")
        Text("Plant ID: ${plantId ?: "Nuevo"}")
        Text("Start Page: $startPage")
        Text("(Implementar en Fase 5)")
    }
}