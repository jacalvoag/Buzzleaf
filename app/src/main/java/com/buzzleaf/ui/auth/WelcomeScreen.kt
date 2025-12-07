package com.buzzleaf.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.buzzleaf.R
import com.buzzleaf.ui.navigation.Screen
import com.buzzleaf.ui.theme.*

@Composable
fun WelcomeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
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
                        fontFamily = com.buzzleaf.ui.theme.PhilosopherFont,
                        color = TextWhite,
                        textAlign = TextAlign.Center,

                        )

                }


                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Cuida tus plantas",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = com.buzzleaf.ui.theme.PhilosopherFont,
                    color = TextWhite.copy(alpha = 0.9f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Cuida tu calma",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = com.buzzleaf.ui.theme.PhilosopherFont,
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
                    shape = RoundedCornerShape(16.dp)
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
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        "Registrarse",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}