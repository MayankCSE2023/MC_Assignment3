package com.example.orientationoneapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orientation_data")
data class OrientationData(
    val x: Float,
    val y: Float,
    val z: Float,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)