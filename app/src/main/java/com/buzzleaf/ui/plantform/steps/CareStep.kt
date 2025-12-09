package com.buzzleaf.ui.plantform.steps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.buzzleaf.data.local.entities.Care
import com.buzzleaf.ui.plantform.PlantFormUiState
import com.buzzleaf.ui.theme.GreenPrimary
import com.buzzleaf.ui.theme.GreenSecondary
import com.buzzleaf.ui.theme.PhilosopherFont

@Composable
fun CareStep(
    uiState: PlantFormUiState,
    onAddCare: (String, Int) -> Unit,
    onRemoveCare: (Care) -> Unit
) {
    var tempType by remember { mutableStateOf("") }
    var tempFrequency by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Cuidados necesarios",
            fontFamily = PhilosopherFont,
            fontSize = 24.sp,
            color = GreenPrimary,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Formulario pequeño para agregar cuidado
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = tempType,
                    onValueChange = { tempType = it },
                    label = { Text("Tipo (ej. Riego, Fertilizante)") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = GreenPrimary)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = tempFrequency,
                        onValueChange = { if (it.all { char -> char.isDigit() }) tempFrequency = it },
                        label = { Text("Días (Frecuencia)") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = GreenPrimary)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (tempType.isNotEmpty() && tempFrequency.isNotEmpty()) {
                                onAddCare(tempType, tempFrequency.toInt())
                                tempType = ""
                                tempFrequency = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.height(56.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de Cuidados Agregados
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(uiState.careList) { care ->
                CareItem(care, onDelete = { onRemoveCare(care) })
            }
        }
    }
}

@Composable
fun CareItem(care: Care, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.WaterDrop, contentDescription = null, tint = GreenSecondary)
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = care.careType, fontWeight = FontWeight.Bold, color = GreenPrimary)
                Text(text = "Cada ${care.frequencyDays} días", fontSize = 12.sp, color = Color.Gray)
            }
        }
        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Gray)
        }
    }
}