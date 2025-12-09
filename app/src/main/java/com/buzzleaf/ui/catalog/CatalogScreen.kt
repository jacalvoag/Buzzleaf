package com.buzzleaf.ui.catalog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.buzzleaf.ui.catalog.components.PlantCard
import com.buzzleaf.ui.navigation.Screen

@Composable
fun CatalogScreen(
    navController: NavController,
    viewModel: CatalogViewModel = hiltViewModel()
) {
    val plants by viewModel.plants.collectAsState(initial = emptyList())

    // Eliminamos el Scaffold y el FloatingActionButton de aquí para evitar duplicados.
    // Asumimos que MainScreen ya tiene el botón o que lo agregarás allí si falta.

    Box(modifier = Modifier.fillMaxSize()) {
        if (plants.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No tienes plantas aún. ¡Agrega una desde el botón +!")
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(150.dp),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(plants) { plant ->
                    PlantCard(
                        plant = plant,
                        onClick = {
                            navController.navigate(Screen.PlantDetail.createRoute(plant.id))
                        }
                    )
                }
            }
        }
    }
}