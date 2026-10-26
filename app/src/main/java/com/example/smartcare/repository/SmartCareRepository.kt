package com.example.smartcare.repository

import com.example.smartcare.data.Activo
import com.example.smartcare.data.ActivoDao
import com.example.smartcare.data.Mantenimiento
import com.example.smartcare.data.MantenimientoDao
import kotlinx.coroutines.flow.Flow

class SmartCareRepository(
    private val activoDao: ActivoDao,
    private val mantenimientoDao: MantenimientoDao
) {

    // =========================
    // OPERACIONES DE ACTIVOS
    // =========================

    fun obtenerActivos(): Flow<List<Activo>> {
        return activoDao.obtenerActivos()
    }

    suspend fun obtenerActivoPorId(
        id: Int
    ): Activo? {
        return activoDao.obtenerActivoPorId(id)
    }

    suspend fun insertarActivo(
        activo: Activo
    ) {
        activoDao.insertar(activo)
    }

    suspend fun actualizarActivo(
        activo: Activo
    ) {
        activoDao.actualizar(activo)
    }

    suspend fun eliminarActivo(
        activo: Activo
    ) {
        activoDao.eliminar(activo)
    }

    // =========================
    // OPERACIONES DE MANTENIMIENTO
    // =========================

    fun obtenerMantenimientosPorActivo(
        activoId: Int
    ): Flow<List<Mantenimiento>> {

        return mantenimientoDao
            .obtenerMantenimientosPorActivo(
                activoId
            )
    }

    suspend fun obtenerMantenimientoPorId(
        id: Int
    ): Mantenimiento? {

        return mantenimientoDao
            .obtenerMantenimientoPorId(id)
    }

    suspend fun insertarMantenimiento(
        mantenimiento: Mantenimiento
    ) {
        mantenimientoDao.insertar(
            mantenimiento
        )
    }

    suspend fun actualizarMantenimiento(
        mantenimiento: Mantenimiento
    ) {
        mantenimientoDao.actualizar(
            mantenimiento
        )
    }

    suspend fun eliminarMantenimiento(
        mantenimiento: Mantenimiento
    ) {
        mantenimientoDao.eliminar(
            mantenimiento
        )
    }
}