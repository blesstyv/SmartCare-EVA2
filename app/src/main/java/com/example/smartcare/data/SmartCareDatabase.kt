package com.example.smartcare.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        Activo::class,
        Mantenimiento::class
    ],
    version = 1,
    exportSchema = false
)
abstract class SmartCareDatabase :
    RoomDatabase() {

    abstract fun activoDao():
            ActivoDao

    abstract fun mantenimientoDao():
            MantenimientoDao

    companion object {

        @Volatile
        private var INSTANCE:
                SmartCareDatabase? = null

        fun getDatabase(
            context: Context
        ): SmartCareDatabase {

            return INSTANCE
                ?: synchronized(this) {

                    val instancia =
                        Room.databaseBuilder(
                            context.applicationContext,
                            SmartCareDatabase::class.java,
                            "smartcare_database"
                        )
                            .build()

                    INSTANCE =
                        instancia

                    instancia
                }
        }
    }
}