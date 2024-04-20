package com.example.orientationoneapp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [OrientationData::class], version = 1, exportSchema = false)
abstract class OrientationDatabase : RoomDatabase() {

    abstract fun orientationDao(): OrientationDao

    companion object {
        @Volatile
        private var INSTANCE: OrientationDatabase? = null

        fun getDatabase(context: Context): OrientationDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OrientationDatabase::class.java,
                    "orientation_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
