package com.buzzleaf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.buzzleaf.ui.main.MainScreen
import com.buzzleaf.ui.theme.BuzzLeafTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BuzzLeafTheme {
                MainScreen()
            }
        }
    }
}