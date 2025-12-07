package com.buzzleaf.ui.catalog

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.buzzleaf.ui.theme.*

@Composable
fun CatalogScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
    ) {
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
                Text(
                    text = "💤",
                    fontSize = 72.sp,
                    color = EmptyStateIcon,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "No hay nada por aquí de momento.",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = EmptyStateText,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "El jardín parece estar tranquilo...",
                    fontSize = 14.sp,
                    color = EmptyStateText.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(48.dp))

                Text(
                    text = "Registra tu primera planta\nhaciendo clic en este botón",
                    fontSize = 14.sp,
                    color = EmptyStateText,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "↓",
                    fontSize = 32.sp,
                    color = GreenAccent,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}