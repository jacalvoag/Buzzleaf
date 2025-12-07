package com.buzzleaf.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.buzzleaf.ui.auth.LoginScreen
import com.buzzleaf.ui.auth.RegisterScreen
import com.buzzleaf.ui.auth.WelcomeScreen
import com.buzzleaf.ui.catalog.CatalogScreen
import com.buzzleaf.ui.notifications.NotificationsScreen
import com.buzzleaf.ui.plantdetail.PlantDetailScreen
import com.buzzleaf.ui.plantform.PlantFormScreen
import com.buzzleaf.ui.reminders.RemindersScreen
import com.buzzleaf.ui.settings.SettingsScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    paddingValues: PaddingValues,
    startDestination: String = Screen.Welcome.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.padding(paddingValues)
    ) {

        composable(Screen.Welcome.route) {
            WelcomeScreen(navController = navController)
        }

        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }

        composable(Screen.Register.route) {
            RegisterScreen(navController = navController)
        }

        composable(Screen.Catalog.route) {
            CatalogScreen(navController = navController)
        }

        composable(Screen.Reminders.route) {
            RemindersScreen(navController = navController)
        }

        composable(
            route = Screen.PlantDetail.route,
            arguments = listOf(
                navArgument("plantId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val plantId = backStackEntry.arguments?.getInt("plantId") ?: -1
            PlantDetailScreen(
                navController = navController,
                plantId = plantId
            )
        }

        composable(
            route = Screen.PlantForm.route,
            arguments = listOf(
                navArgument("plantId") {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument("mode") {
                    type = NavType.StringType
                    defaultValue = "CREATE"
                },
                navArgument("startPage") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) { backStackEntry ->
            val plantId = backStackEntry.arguments?.getInt("plantId") ?: -1
            val mode = backStackEntry.arguments?.getString("mode") ?: "CREATE"
            val startPage = backStackEntry.arguments?.getInt("startPage") ?: 0

            PlantFormScreen(
                navController = navController,
                plantId = if (plantId == -1) null else plantId,
                mode = mode,
                startPage = startPage
            )
        }

        composable(Screen.Notifications.route) {
            NotificationsScreen(navController = navController)
        }

        composable(Screen.Settings.route) {
            SettingsScreen(navController = navController)
        }
    }
}