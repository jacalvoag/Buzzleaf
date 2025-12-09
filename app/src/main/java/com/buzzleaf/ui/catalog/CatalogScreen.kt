package com.buzzleaf.ui.catalog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.buzzleaf.ui.theme.*
import com.buzzleaf.R
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle // O collectAsState normal

@Composable
fun CatalogScreen(
    navController: NavController,
    viewModel: CatalogViewModel = hiltViewModel()
) {
    val plants by viewModel.plants.collectAsState(initial = emptyList())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // Navegar al formulario en modo CREATE
                    navController.navigate(Screen.PlantForm.createRoute())
                },
                containerColor = GreenPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Planta", tint = Color.White)
            }
        }
    ) { padding ->
        if (plants.isEmpty()) {
            // Mostrar tu vista vacía aquí
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No tienes plantas aún. ¡Agrega una!")
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(150.dp),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(padding)
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