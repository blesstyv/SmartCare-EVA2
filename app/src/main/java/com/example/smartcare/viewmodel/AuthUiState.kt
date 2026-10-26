package com.example.smartcare.viewmodel

data class AuthUiState(

    val email: String = "",

    val password: String = "",

    val confirmarPassword: String = "",

    val cargando: Boolean = false,

    val error: String? = null,

    val autenticado: Boolean = false,

    val usuarioEmail: String? = null
)