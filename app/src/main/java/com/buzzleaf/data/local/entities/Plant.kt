package com.buzzleaf.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plants")
data class Plant(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val userId: String,

    val commonName: String,
    val plantType: String,
    val sunAmount: String,
    val waterAmount: String,
    val soilType: String,
    val imageUrl: String? = null,

    // Metadata
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)