package com.buzzleaf.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.buzzleaf.ui.navigation.AppNavigation
import com.buzzleaf.ui.navigation.Screen
import com.buzzleaf.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    startDestination: String = Screen.Welcome.route,
    onLogout: () -> Unit = {}
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route

    val authRoutes = listOf(
        Screen.Welcome.route,
        Screen.Login.route,
        Screen.Register.route
    )

    val hasOwnTopBar = currentRoute == Screen.Notifications.route ||
            currentRoute == Screen.Settings.route ||
            currentRoute?.startsWith("plant_detail") == true ||
            currentRoute?.startsWith("plant_form") == true

    val showTopBar = currentRoute !in authRoutes && !hasOwnTopBar

    val showBottomNav = currentRoute in listOf(
        Screen.Catalog.route,
        Screen.Reminders.route
    )

    val showFab = currentRoute == Screen.Catalog.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (showTopBar) {
                TopAppBar(
                    title = {
                        Text(
                            text = getTitleForRoute(currentRoute),
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = com.buzzleaf.ui.theme.GreenPrimary,
                        titleContentColor = com.buzzleaf.ui.theme.TextWhite,
                        actionIconContentColor = com.buzzleaf.ui.theme.TextWhite
                    ),
                    actions = {
                        IconButton(onClick = {
                            navController.navigate(Screen.Notifications.route)
                        }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_notifications),
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
            }
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
                    },
                    containerColor = com.buzzleaf.ui.theme.GreenAccent,
                    contentColor = com.buzzleaf.ui.theme.TextWhite
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
    NavigationBar(
        containerColor = com.buzzleaf.ui.theme.GreenPrimary,
        contentColor = com.buzzleaf.ui.theme.TextWhite
    ) {
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
                    painter = painterResource(id = R.drawable.ic_potted_plant),
                    contentDescription = "Catálogo"
                )
            },
            label = { Text("Catálogo") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = com.buzzleaf.ui.theme.TextWhite,
                selectedTextColor = com.buzzleaf.ui.theme.TextWhite,
                unselectedIconColor = com.buzzleaf.ui.theme.GreenMuted,
                unselectedTextColor = com.buzzleaf.ui.theme.GreenMuted,
                indicatorColor = com.buzzleaf.ui.theme.GreenSecondary
            )
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
                    painter = painterResource(R.drawable.ic_calendar),
                    contentDescription = "Recordatorios"
                )
            },
            label = { Text("Recordatorios") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = com.buzzleaf.ui.theme.TextWhite,
                selectedTextColor = com.buzzleaf.ui.theme.TextWhite,
                unselectedIconColor = com.buzzleaf.ui.theme.GreenMuted,
                unselectedTextColor = com.buzzleaf.ui.theme.GreenMuted,
                indicatorColor = com.buzzleaf.ui.theme.GreenSecondary
            )
        )
    }
}

private fun getTitleForRoute(route: String?): String {
    return when (route) {
        Screen.Catalog.route -> "CATÁLOGO DE PLANTAS"
        Screen.Reminders.route -> "PRÓXIMOS CUIDADOS"
        Screen.Notifications.route -> "NOTIFICACIONES"
        Screen.Settings.route -> "CONFIGURACIÓN"
        else -> "BuzzLeaf"
    }
}