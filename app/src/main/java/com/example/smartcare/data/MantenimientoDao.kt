package com.example.smartcare.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MantenimientoDao {

    @Query(
        """
        SELECT * FROM mantenimientos
        WHERE activoId = :activoId
        ORDER BY id DESC
        """
    )
    fun obtenerMantenimientosPorActivo(
        activoId: Int
    ): Flow<List<Mantenimiento>>

    @Query(
        """
        SELECT * FROM mantenimientos
        WHERE id = :id
        LIMIT 1
        """
    )
    suspend fun obtenerMantenimientoPorId(
        id: Int
    ): Mantenimiento?

    @Insert
    suspend fun insertar(
        mantenimiento: Mantenimiento
    )

    @Update
    suspend fun actualizar(
        mantenimiento: Mantenimiento
    )

    @Delete
    suspend fun eliminar(
        mantenimiento: Mantenimiento
    )
}