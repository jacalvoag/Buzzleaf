package com.buzzleaf.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class PlantWithDetails(
    @Embedded val plant: Plant,

    @Relation(
        parentColumn = "id",
        entityColumn = "plantId"
    )
    val cares: List<Care>,

    @Relation(
        parentColumn = "id",
        entityColumn = "plantId"
    )
    val reminders: List<Reminder>
)