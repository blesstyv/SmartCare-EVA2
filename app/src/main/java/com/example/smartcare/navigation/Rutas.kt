package com.example.smartcare.navigation

object Rutas {

    // =========================
    // AUTENTICACIÓN
    // =========================

    const val LOGIN = "login"

    const val REGISTRO = "registro"


    // =========================
    // SMARTCARE
    // =========================

    const val INICIO = "inicio"

    const val ACTIVOS = "activos"

    const val NUEVO_ACTIVO = "nuevo_activo"


    // =========================
    // RUTAS CON PARÁMETROS
    // =========================

    const val DETALLE_ACTIVO =
        "detalle_activo/{activoId}"

    const val MANTENIMIENTO =
        "mantenimiento/{activoId}"

    const val HISTORIAL =
        "historial/{activoId}"


    // =========================
    // CONSTRUCTORES DE RUTAS
    // =========================

    fun detalleActivo(
        activoId: Int
    ): String {

        return "detalle_activo/$activoId"
    }


    fun mantenimiento(
        activoId: Int
    ): String {

        return "mantenimiento/$activoId"
    }


    fun historial(
        activoId: Int
    ): String {

        return "historial/$activoId"
    }
}