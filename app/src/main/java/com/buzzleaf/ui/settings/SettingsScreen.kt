package com.buzzleaf.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.buzzleaf.data.remote.FirebaseManager
import com.buzzleaf.ui.auth.AuthViewModel
import com.buzzleaf.ui.auth.AuthViewModelFactory
import com.buzzleaf.ui.auth.AuthState
import com.buzzleaf.ui.navigation.Screen
import com.buzzleaf.ui.theme.*
import com.buzzleaf.utils.PreferencesManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    val context = LocalContext.current
    val firebaseManager = remember {
        FirebaseManager(
            com.google.firebase.auth.FirebaseAuth.getInstance(),
            com.google.firebase.storage.FirebaseStorage.getInstance()
        )
    }
    val preferencesManager = remember { PreferencesManager(context) }

    val viewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(firebaseManager, preferencesManager)
    )

    val authState by viewModel.authState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    val userEmail = remember {
        com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.email ?: "Usuario"
    }

    var showLogoutDialog by remember { mutableStateOf(false) }
    var hasLoggedOut by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.isLoggedIn, hasLoggedOut) {
        if (!uiState.isLoggedIn && hasLoggedOut) {
            navController.navigate(Screen.Welcome.route) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "CONFIGURACIÓN",
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = TextWhite
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = GreenPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BackgroundLight)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        tint = GreenPrimary,
                        modifier = Modifier.size(48.dp)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = "Cuenta",
                            fontSize = 14.sp,
                            color = TextMuted
                        )
                        Text(
                            text = userEmail,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { showLogoutDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red.copy(alpha = 0.1f),
                    contentColor = Color.Red
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    Icons.Default.ExitToApp,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    "Cerrar Sesión",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = "BuzzLeaf v1.0",
                fontSize = 12.sp,
                color = TextMuted,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        if (showLogoutDialog) {
            AlertDialog(
                onDismissRequest = { showLogoutDialog = false },
                title = {
                    Text(
                        "Cerrar Sesión",
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Text("¿Estás seguro que deseas cerrar sesión?")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showLogoutDialog = false
                            hasLoggedOut = true
                            viewModel.logout()
                        }
                    ) {
                        Text(
                            "Cerrar Sesión",
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showLogoutDialog = false }
                    ) {
                        Text(
                            "Cancelar",
                            color = GreenPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                containerColor = Color.White
            )
        }
    }
}