package com.example.smartcare.viewmodel

import com.example.smartcare.data.Activo
import com.example.smartcare.data.Mantenimiento

data class SmartCareUiState(

    // =====================================================
    // FORMULARIO DE ACTIVO
    // =====================================================

    val nombre: String = "",

    val tipo: String = "",

    val descripcion: String = "",

    val estado: String = "",

    val proximoMantenimiento: String = "",


    // =====================================================
    // VALIDACIONES DEL ACTIVO
    // =====================================================

    val errorNombre: String? = null,

    val errorTipo: String? = null,

    val errorDescripcion: String? = null,

    val errorEstado: String? = null,

    val errorProximoMantenimiento: String? = null,


    // =====================================================
    // ESTADO DE GUARDADO / EDICIÓN
    // =====================================================

    val guardandoActivo: Boolean = false,

    val activoGuardado: Boolean = false,

    val errorEdicionActivo: String? = null,

    val actualizacionActivoExitosa: Boolean = false,


    // =====================================================
    // LISTADO
    // =====================================================

    val activos: List<Activo> = emptyList(),

    val activosFiltrados: List<Activo> = emptyList(),


    // =====================================================
    // BÚSQUEDA Y FILTRO
    // =====================================================

    val busqueda: String = "",

    val filtroEstado: String = "Todos",


    // =====================================================
    // DETALLE DEL ACTIVO
    // =====================================================

    val activoSeleccionado: Activo? = null,

    val cargandoDetalle: Boolean = false,


    // =====================================================
    // FORMULARIO DE MANTENIMIENTO
    // =====================================================

    val mantenimientoDescripcion: String = "",

    val mantenimientoFecha: String = "",

    val mantenimientoObservaciones: String = "",

    val mantenimientoProximaFecha: String = "",


    // =====================================================
    // VALIDACIONES DE MANTENIMIENTO
    // =====================================================

    val errorMantenimientoDescripcion: String? = null,

    val errorMantenimientoFecha: String? = null,

    val errorMantenimientoProximaFecha: String? = null,


    // =====================================================
    // ESTADO DEL GUARDADO DE MANTENIMIENTO
    // =====================================================

    val guardandoMantenimiento: Boolean = false,

    val mantenimientoGuardado: Boolean = false,


    // =====================================================
    // HISTORIAL
    // =====================================================

    val mantenimientos: List<Mantenimiento> = emptyList()
)