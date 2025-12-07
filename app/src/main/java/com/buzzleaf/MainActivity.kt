package com.buzzleaf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.buzzleaf.ui.main.MainScreen
import com.buzzleaf.ui.navigation.Screen
import com.buzzleaf.ui.theme.BuzzLeafTheme
import com.buzzleaf.utils.PreferencesManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            BuzzLeafTheme {
                val isLoggedIn by preferencesManager.isLoggedInFlow.collectAsState(initial = false)

                val startDestination = if (isLoggedIn) {
                    Screen.Catalog.route
                } else {
                    Screen.Welcome.route
                }

                MainScreen(
                    startDestination = startDestination
                )
            }
        }
    }
}