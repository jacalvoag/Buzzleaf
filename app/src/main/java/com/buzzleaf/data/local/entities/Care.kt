package com.buzzleaf.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "cares",
    foreignKeys = [
        ForeignKey(
            entity = Plant::class,
            parentColumns = ["id"],
            childColumns = ["plantId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Care(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val plantId: Int,

    val careType: String,
    val frequencyDays: Int,

    val createdAt: Long = System.currentTimeMillis()
)