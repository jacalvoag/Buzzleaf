package com.buzzleaf.ui.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome_screen")
    object Login : Screen("login_screen")
    object Register : Screen("register_screen")
    object Catalog : Screen("catalog_screen")
    object Reminders : Screen("reminders_screen")
    object Notifications : Screen("notifications_screen")
    object Settings : Screen("settings_screen")

    // Objetos con funciones helper para navegar con argumentos
    object PlantDetail : Screen("plant_detail_screen/{plantId}") {
        fun createRoute(plantId: Int) = "plant_detail_screen/$plantId"
    }

    object PlantForm : Screen("plant_form_screen?plantId={plantId}&mode={mode}&startPage={startPage}") {
        fun createRoute(plantId: Int? = null, mode: String = "CREATE", startPage: Int = 0): String {
            // Si el ID es nulo, pasamos -1 (que AppNavigation interpreta como nueva planta)
            val idVal = plantId ?: -1
            return "plant_form_screen?plantId=$idVal&mode=$mode&startPage=$startPage"
        }
    }
}