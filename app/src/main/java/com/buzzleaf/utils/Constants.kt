package com.buzzleaf.utils

object Constants {
    const val DATABASE_NAME = "buzzleaf_database"
    const val DATABASE_VERSION = 1

    const val PREFERENCES_NAME = "buzzleaf_preferences"
    const val KEY_IS_LOGGED_IN = "is_logged_in"
    const val KEY_USER_ID = "user_id"
    const val KEY_THEME_MODE = "theme_mode"

    const val STORAGE_PATH_PLANTS = "plants"
    const val STORAGE_PATH_USERS = "users"

    const val MAX_IMAGE_SIZE_MB = 5
    const val IMAGE_QUALITY = 80

    const val REMINDER_WORK_TAG = "reminder_work"
    const val NOTIFICATION_CHANNEL_ID = "plant_reminders"
    const val NOTIFICATION_CHANNEL_NAME = "Recordatorios de Plantas"

    const val ARG_PLANT_ID = "plantId"
    const val ARG_MODE = "mode"
    const val ARG_START_PAGE = "startPage"

    const val MODE_CREATE = "CREATE"
    const val MODE_EDIT = "EDIT"
}