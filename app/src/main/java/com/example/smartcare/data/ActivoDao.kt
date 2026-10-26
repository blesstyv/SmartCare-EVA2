package com.example.smartcare.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivoDao {

    @Query(
        "SELECT * FROM activos ORDER BY id DESC"
    )
    fun obtenerActivos(): Flow<List<Activo>>

    @Query(
        "SELECT * FROM activos WHERE id = :id LIMIT 1"
    )
    suspend fun obtenerActivoPorId(
        id: Int
    ): Activo?

    @Insert
    suspend fun insertar(
        activo: Activo
    )

    @Update
    suspend fun actualizar(
        activo: Activo
    )

    @Delete
    suspend fun eliminar(
        activo: Activo
    )
}