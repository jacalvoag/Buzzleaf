package com.buzzleaf.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
fun LoginScreen(navController: NavController) {
    android.util.Log.d("LoginScreen", "LoginScreen composing")

    val context = LocalContext.current
    val firebaseManager = remember {
        android.util.Log.d("LoginScreen", "Creating FirebaseManager")
        FirebaseManager(
            com.google.firebase.auth.FirebaseAuth.getInstance(),
            com.google.firebase.storage.FirebaseStorage.getInstance()
        )
    }
    val preferencesManager = remember {
        android.util.Log.d("LoginScreen", "Creating PreferencesManager")
        PreferencesManager(context)
    }

    val viewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(firebaseManager, preferencesManager)
    )

    android.util.Log.d("LoginScreen", "ViewModel created successfully")

    val authState by viewModel.authState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val horizontalPadding = responsiveHorizontalPadding()
    val verticalSpacing = responsiveVerticalSpacing()
    val titleSize = responsiveTitleSize()
    val bodyTextSize = responsiveBodyTextSize()
    val largeTextSize = responsiveLargeTextSize()
    val buttonHeight = responsiveButtonHeight()

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
            snackbarHostState.showSnackbar(
                message = error,
                duration = SnackbarDuration.Short
            )
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
                .background(Color(0xFFF5F5F5))
        ) {

            Image(
                painter = painterResource(id = R.drawable.login_register),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.7f
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = horizontalPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(verticalSpacing * 4))

                Text(
                    text = "INICIAR SESIÓN",
                    fontSize = titleSize,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PhilosopherFont,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    letterSpacing = 2.sp
                )

                Spacer(modifier = Modifier.height(verticalSpacing * 0.5f))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = "¿Aún no tienes una cuenta? ",
                        fontSize = bodyTextSize,
                        color = Color.Black
                    )
                    Text(
                        text = "¡Regístrate aquí!",
                        fontSize = bodyTextSize,
                        color = GreenPrimary,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.Register.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(verticalSpacing * 2))

                Text(
                    text = "¡Bienvenido de nuevo!",
                    fontSize = largeTextSize,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(verticalSpacing * 0.25f))

                Text(
                    text = "Nos alegra volver a tenerte por aquí.",
                    fontSize = bodyTextSize,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Vuelve a ingresar a BuzzLeaf y retoma tus plantas justo donde te quedaste.",
                    fontSize = bodyTextSize,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontFamily = PhilosopherFont,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Spacer(modifier = Modifier.height(verticalSpacing * 2))

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        "Correo electrónico",
                        fontSize = bodyTextSize,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("someone@example.com", fontSize = bodyTextSize, color = Color.Gray) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.Black,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        ),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )
                }

                Spacer(modifier = Modifier.height(verticalSpacing))

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        "Contraseña",
                        fontSize = bodyTextSize,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Mínimo 8 caracteres", fontSize = bodyTextSize, color = Color.Gray) },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = if (passwordVisible) "Ocultar" else "Mostrar",
                                    tint = Color.Black
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.Black,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        ),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )
                }

                Spacer(modifier = Modifier.height(verticalSpacing * 2))

                Button(
                    onClick = {
                        android.util.Log.d("LoginScreen", "Login button clicked")
                        android.util.Log.d("LoginScreen", "Email: $email, Password length: ${password.length}")
                        viewModel.loginWithEmail(email, password)
                    },
                    modifier = Modifier
                        .widthIn(min = 160.dp, max = 200.dp)
                        .height(buttonHeight),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GreenPrimary,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp),
                    enabled = !uiState.isLoading
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White
                        )
                    } else {
                        Text(
                            "¡Iniciar sesión!",
                            fontSize = largeTextSize,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(verticalSpacing * 2))
            }

            IconButton(
                onClick = { navController.navigateUp() },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopStart)
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color.Black
                )
            }
        }
    }
}