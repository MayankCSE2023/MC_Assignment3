package com.example.orientationoneapp.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface OrientationDao {
    @Insert
    suspend fun insert(orientationData: OrientationData)

    @Query("SELECT * FROM orientation_data")
    suspend fun getAllOrientationData(): List<OrientationData>
}
