package com.buzzleaf.ui.plantdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.buzzleaf.ui.navigation.Screen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue // Para usar 'by'
import com.buzzleaf.data.local.entities.PlantWithDetails // Si es necesario referenciarlo explícitamente
import com.buzzleaf.ui.theme.GreenPrimary
import com.buzzleaf.ui.theme.PhilosopherFont

@Composable
fun PlantDetailScreen(
    navController: NavController,
    plantId: Int,
    viewModel: PlantDetailViewModel = hiltViewModel()
) {
    // Cargar datos al iniciar
    LaunchedEffect(plantId) {
        viewModel.loadPlant(plantId)
    }

    val uiState by viewModel.uiState.collectAsState()
    val plant = uiState.plantDetails?.plant

    if (plant == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = GreenPrimary)
        }
        return
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // --- Header con Imagen ---
            Box(modifier = Modifier.height(300.dp).fillMaxWidth()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(plant.imageUrl)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                // Botón atrás
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .padding(16.dp)
                        .statusBarsPadding()
                        .background(Color.White.copy(alpha = 0.7f), CircleShape)
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                }
            }

            Column(modifier = Modifier.padding(24.dp)) {

                // --- Sección: Información Básica ---
                SectionHeader(title = "Información Básica") {
                    // Navegar al Formulario en modo EDIT, página 0 (Info)
                    navController.navigate(
                        Screen.PlantForm.createRoute(plantId = plant.id, mode = "EDIT", startPage = 0)
                    )
                }
                Text(text = plant.commonName, fontSize = 24.sp, fontFamily = PhilosopherFont, color = GreenPrimary)
                Text(text = "Tipo: ${plant.plantType}", color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    InfoChip(label = "Sol", value = plant.sunAmount)
                    InfoChip(label = "Agua", value = plant.waterAmount)
                }

                Spacer(modifier = Modifier.height(24.dp))

                // --- Sección: Cuidados ---
                SectionHeader(title = "Cuidados") {
                    // Navegar al Formulario en modo EDIT, página 1 (Cuidados)
                    navController.navigate(
                        Screen.PlantForm.createRoute(plantId = plant.id, mode = "EDIT", startPage = 1)
                    )
                }
                uiState.plantDetails?.cares?.forEach { care ->
                    Text("• ${care.careType}: Cada ${care.frequencyDays} días", modifier = Modifier.padding(vertical = 4.dp))
                }

                Spacer(modifier = Modifier.height(24.dp))

                // --- Sección: Recordatorios ---
                SectionHeader(title = "Recordatorios") {
                    // Navegar al Formulario en modo EDIT, página 2 (Recordatorios)
                    navController.navigate(
                        Screen.PlantForm.createRoute(plantId = plant.id, mode = "EDIT", startPage = 2)
                    )
                }
                uiState.plantDetails?.reminders?.forEach { reminder ->
                    Text("• ${reminder.careToRemind}: ${reminder.reminderTime}", modifier = Modifier.padding(vertical = 4.dp))
                }
            }
        }
    }
}

@Composable
fun SectionHeader(title: String, onEdit: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = GreenPrimary)
        IconButton(onClick = onEdit) {
            Icon(Icons.Default.Edit, contentDescription = "Editar $title", tint = GreenPrimary)
        }
    }
}

@Composable
fun InfoChip(label: String, value: String) {
    Surface(
        color = Color(0xFFF0F0F0),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(end = 8.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) {
            Text(text = label, fontSize = 10.sp, color = Color.Gray)
            Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        }
    }
}