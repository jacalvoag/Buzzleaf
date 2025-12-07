package com.buzzleaf.ui.navigation

sealed class Screen(val route: String) {

    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object Register : Screen("register")

    object Catalog : Screen("catalog")
    object Reminders : Screen("reminders")

    object PlantDetail : Screen("plant_detail/{plantId}") {
        fun createRoute(plantId: Int) = "plant_detail/$plantId"
    }

    object PlantForm : Screen("plant_form?plantId={plantId}&mode={mode}&startPage={startPage}") {
        fun createRoute(
            plantId: Int? = null,
            mode: String = "CREATE",
            startPage: Int = 0
        ): String {
            return "plant_form?plantId=${plantId ?: -1}&mode=$mode&startPage=$startPage"
        }
    }

    object Notifications : Screen("notifications")
    object Settings : Screen("settings")
}