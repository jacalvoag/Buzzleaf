package com.buzzleaf.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.buzzleaf.ui.navigation.Screen

@Composable
fun RegisterScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Register Screen")

        Spacer(modifier = Modifier.height(32.dp))

        // Placeholder - implementar registro
        Button(
            onClick = {
                // TODO: Implementar registro
                navController.navigate(Screen.Catalog.route) {
                    popUpTo(Screen.Welcome.route) { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrarse (Temporal)")
        }
    }
}