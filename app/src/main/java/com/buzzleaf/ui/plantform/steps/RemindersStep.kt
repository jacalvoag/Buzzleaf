package com.buzzleaf.ui.plantform.steps

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.buzzleaf.ui.plantform.PlantFormUiState
import androidx.compose.ui.graphics.Color
import com.buzzleaf.ui.theme.GreenPrimary
import com.buzzleaf.ui.theme.PhilosopherFont

@Composable
fun RemindersStep(
    uiState: PlantFormUiState,
    onUpdateReminder: (String, Long, String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Configurar Recordatorios",
            fontFamily = PhilosopherFont,
            fontSize = 24.sp,
            color = GreenPrimary,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Define cuándo quieres empezar a recibir alertas para los cuidados que registraste.",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.careList.isEmpty()) {
            Text("No agregaste cuidados en el paso anterior.", color = Color.Red)
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(uiState.careList) { care ->
                    ReminderConfigCard(care.careType, onUpdate = onUpdateReminder)
                }
            }
        }
    }
}

@Composable
fun ReminderConfigCard(careType: String, onUpdate: (String, Long, String) -> Unit) {
    // Nota: Aquí simplifico la selección de hora y fecha.
    // Idealmente usarías DatePickerDialog y TimePicker de Material3.
    var timeText by remember { mutableStateOf("09:00 AM") }

    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Recordatorio: $careType", fontWeight = FontWeight.Bold, color = GreenPrimary)
            Spacer(modifier = Modifier.height(8.dp))

            // Simulación de selectores
            OutlinedButton(onClick = { /* Abrir TimePicker */ }) {
                Text("Hora: $timeText")
            }
            // Al cambiar, notificar al ViewModel
            // onUpdate(careType, System.currentTimeMillis(), timeText)
        }
    }
}