package com.example.orientationoneapp.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface OrientationDao {
    @Insert
    suspend fun insert(orientationData: OrientationData)

    @Query("SELECT * FROM orientation_data ORDER BY timestamp DESC")
    fun getAllOrientationData(): LiveData<List<OrientationData>>

    @Query("SELECT * FROM orientation_data ORDER BY id ASC LIMIT 500")
    fun get500OrientationData(): List<OrientationData>
}
