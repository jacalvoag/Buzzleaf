package com.buzzleaf.ui.plantform.steps

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.buzzleaf.ui.plantform.PlantFormUiState
import com.buzzleaf.ui.theme.GreenPrimary
import com.buzzleaf.ui.theme.PhilosopherFont

@Composable
fun BasicInfoStep(
    uiState: PlantFormUiState,
    onInfoChange: (String?, String?, String?, String?, String?, String?) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "¿Cómo es tu planta?",
            fontFamily = PhilosopherFont,
            fontSize = 24.sp,
            color = GreenPrimary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Placeholder para la foto
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFF0F0F0))
                .border(BorderStroke(1.dp, GreenPrimary), RoundedCornerShape(16.dp))
                .clickable { /* TODO: Implementar selector de imagen */ },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.AddPhotoAlternate,
                contentDescription = "Agregar Foto",
                tint = GreenPrimary,
                modifier = Modifier.size(40.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Campos de texto reutilizables
        PlantTextField(value = uiState.plantName, label = "Nombre común", onValueChange = { onInfoChange(it, null, null, null, null, null) })
        PlantTextField(value = uiState.plantType, label = "Tipo de planta", onValueChange = { onInfoChange(null, it, null, null, null, null) })

        // Simulación de Dropdowns para Sol y Agua (Simplificado por ahora como TextField)
        PlantTextField(value = uiState.sunAmount, label = "Cantidad de Sol (Baja, Media, Alta)", onValueChange = { onInfoChange(null, null, it, null, null, null) })
        PlantTextField(value = uiState.waterAmount, label = "Riego (Poco, Regular, Frecuente)", onValueChange = { onInfoChange(null, null, null, it, null, null) })
        PlantTextField(value = uiState.soilType, label = "Tipo de Suelo", onValueChange = { onInfoChange(null, null, null, null, it, null) })
    }
}

@Composable
fun PlantTextField(value: String, label: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = GreenPrimary,
            focusedLabelColor = GreenPrimary,
            cursorColor = GreenPrimary
        ),
        shape = RoundedCornerShape(12.dp)
    )
}