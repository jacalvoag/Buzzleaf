package com.buzzleaf.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.buzzleaf.R
import com.buzzleaf.data.remote.FirebaseManager
import com.buzzleaf.ui.navigation.Screen
import com.buzzleaf.ui.theme.*
import com.buzzleaf.utils.PreferencesManager

@Composable
fun WelcomeScreen(navController: NavController) {
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

    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            navController.navigate(Screen.Catalog.route) {
                popUpTo(Screen.Welcome.route) { inclusive = true }
            }
        }
    }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.error) {
        uiState.error?.let { error ->
            snackbarHostState.showSnackbar(error)
            viewModel.clearError()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.Black)
        ) {
            Image(
                painter = painterResource(id = R.drawable.bienvenida),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.7f
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.height(60.dp))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f),
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ){

                        Image(
                            painter = painterResource(R.drawable.logobuzzleaf),
                            contentDescription = "Buzzleaf logo",
                            modifier = Modifier
                                .size(70.dp)
                                .padding(end = 12.dp)

                        )

                        Text(
                            text = "BuzzLeaf",
                            fontSize = 60.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = PhilosopherFont,
                            color = TextWhite,
                            textAlign = TextAlign.Center,
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Cuida tus plantas",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PhilosopherFont,
                        color = TextWhite.copy(alpha = 0.9f),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Cuida tu calma",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PhilosopherFont,
                        color = TextWhite.copy(alpha = 0.9f),
                        textAlign = TextAlign.Center
                    )
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = { navController.navigate(Screen.Login.route) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black.copy(alpha = 0.5f),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(16.dp),
                        enabled = !uiState.isLoading
                    ) {
                        Text(
                            "Iniciar Sesión",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = { navController.navigate(Screen.Register.route) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black.copy(alpha = 0.5f),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(16.dp),
                        enabled = !uiState.isLoading
                    ) {
                        Text(
                            "Registrarse",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Divider(
                            modifier = Modifier.weight(1f),
                            color = Color.White.copy(alpha = 0.5f),
                            thickness = 1.dp
                        )
                        Text(
                            text = "O",
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Divider(
                            modifier = Modifier.weight(1f),
                            color = Color.White.copy(alpha = 0.5f),
                            thickness = 1.dp
                        )
                    }

                    Button(
                        onClick = {
                            viewModel.loginWithGoogle(context)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .border(
                                width = 1.dp,
                                color = Color.White.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(16.dp)
                            ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(16.dp),
                        enabled = !uiState.isLoading
                    ) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = GreenPrimary
                            )
                        } else {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "G",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = GreenPrimary,
                                    modifier = Modifier.padding(end = 12.dp)
                                )
                                Text(
                                    "Continuar con Google",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}