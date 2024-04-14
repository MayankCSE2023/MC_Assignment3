package com.example.orientationoneapp.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [OrientationData::class], version = 1)
abstract class OrientationDatabase : RoomDatabase() {
    abstract fun orientationDao(): OrientationDao
}
