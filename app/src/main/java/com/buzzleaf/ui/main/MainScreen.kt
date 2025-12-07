package com.buzzleaf.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.buzzleaf.ui.navigation.AppNavigation
import com.buzzleaf.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    startDestination: String = Screen.Catalog.route,
    onLogout: () -> Unit = {}
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route

    val showBottomNav = currentRoute in listOf(
        Screen.Catalog.route,
        Screen.Reminders.route
    )

    val showFab = currentRoute == Screen.Catalog.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = getTitleForRoute(currentRoute))
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.Notifications.route)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notificaciones"
                        )
                    }

                    IconButton(onClick = {
                        navController.navigate(Screen.Settings.route)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Configuración"
                        )
                    }
                }
            )
        },
        bottomBar = {
            if (showBottomNav) {
                BottomNavigationBar(
                    navController = navController,
                    currentDestination = currentDestination
                )
            }
        },
        floatingActionButton = {
            if (showFab) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(Screen.PlantForm.createRoute())
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Añadir planta"
                    )
                }
            }
        }
    ) { paddingValues ->
        AppNavigation(
            navController = navController,
            paddingValues = paddingValues,
            startDestination = startDestination
        )
    }
}

@Composable
private fun BottomNavigationBar(
    navController: NavController,
    currentDestination: androidx.navigation.NavDestination?
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentDestination?.hierarchy?.any {
                it.route == Screen.Catalog.route
            } == true,
            onClick = {
                navController.navigate(Screen.Catalog.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Eco,
                    contentDescription = "Catálogo"
                )
            },
            label = { Text("Catálogo") }
        )

        NavigationBarItem(
            selected = currentDestination?.hierarchy?.any {
                it.route == Screen.Reminders.route
            } == true,
            onClick = {
                navController.navigate(Screen.Reminders.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = "Recordatorios"
                )
            },
            label = { Text("Recordatorios") }
        )
    }
}

private fun getTitleForRoute(route: String?): String {
    return when (route) {
        Screen.Catalog.route -> "Mis Plantas"
        Screen.Reminders.route -> "Recordatorios"
        Screen.Notifications.route -> "Notificaciones"
        Screen.Settings.route -> "Configuración"
        else -> "BuzzLeaf"
    }
}