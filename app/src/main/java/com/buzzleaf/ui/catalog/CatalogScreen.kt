package com.buzzleaf.ui.catalog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.buzzleaf.R
import com.buzzleaf.ui.catalog.components.PlantCard
import com.buzzleaf.ui.navigation.Screen
import com.buzzleaf.ui.theme.*

@Composable
fun CatalogScreen(
    navController: NavController,
    viewModel: CatalogViewModel = hiltViewModel()
) {
    // 1. Obtener la lista real de plantas desde la base de datos
    val plants by viewModel.plants.collectAsState(initial = emptyList())

    // 2. Estado del buscador
    var searchQuery by remember { mutableStateOf("") }

    // 3. Lógica de filtrado: Si hay texto en el buscador, filtramos la lista original
    val filteredPlants = remember(plants, searchQuery) {
        if (searchQuery.isBlank()) {
            plants
        } else {
            plants.filter { it.commonName.contains(searchQuery, ignoreCase = true) }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
    ) {
        // --- ENCABEZADO: BUSCADOR Y FILTROS (Diseño de PruebaScreen) ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text(
                        "BUSCAR POR NOMBRE",
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Buscar",
                        tint = GreenMuted
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = SearchBackground,
                    unfocusedContainerColor = SearchBackground,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            OutlinedButton(
                onClick = { /* TODO: Mostrar filtros */ },
                modifier = Modifier.height(56.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = FilterBackground
                ),
                border = null,
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    Icons.Default.FilterList,
                    contentDescription = "Filtrar",
                    tint = GreenMuted
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    "FILTRAR POR",
                    fontSize = 11.sp,
                    color = TextMuted
                )
            }
        }

        // --- CONTENIDO: GRILLA O GRILLO ---
        Box(modifier = Modifier.fillMaxSize()) {

            // ESCENARIO A: Lista vacía (Ya sea porque no hay plantas o el filtro no encontró nada)
            if (filteredPlants.isEmpty()) {
                // Si la búsqueda está vacía y realmente no hay plantas, mostramos mensaje de bienvenida
                // Si hay búsqueda pero no resultados, mostramos mensaje de "no encontrado"
                val isSearching = searchQuery.isNotEmpty()

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.grillo),
                            contentDescription = "No hay nada",
                            modifier = Modifier
                                .size(80.dp)
                                .padding(end = 12.dp)
                        )

                        Text(
                            text = if (isSearching) "No encontramos esa planta." else "No hay nada por aquí de momento.",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = EmptyStateText,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = if (isSearching) "Intenta con otro nombre." else "El jardín parece estar tranquilo...",
                            fontSize = 14.sp,
                            color = EmptyStateText.copy(alpha = 0.7f),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            // ESCENARIO B: Hay plantas -> Mostramos las Cards (Funcionalidad del Repo)
            else {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(150.dp),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filteredPlants) { plant ->
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
}