package com.example.smartcare.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AuthViewModel : ViewModel() {

    private val auth =
        FirebaseAuth.getInstance()


    private val _uiState =
        MutableStateFlow(
            AuthUiState(
                autenticado =
                    auth.currentUser != null,

                usuarioEmail =
                    auth.currentUser?.email
            )
        )


    val uiState: StateFlow<AuthUiState> =
        _uiState.asStateFlow()


    // =====================================================
    // FORMULARIO
    // =====================================================

    fun cambiarEmail(
        email: String
    ) {

        _uiState.update {

            it.copy(
                email = email,
                error = null
            )
        }
    }


    fun cambiarPassword(
        password: String
    ) {

        _uiState.update {

            it.copy(
                password = password,
                error = null
            )
        }
    }


    fun cambiarConfirmarPassword(
        password: String
    ) {

        _uiState.update {

            it.copy(
                confirmarPassword =
                    password,

                error = null
            )
        }
    }


    // =====================================================
    // REGISTRO
    // =====================================================

    fun registrar() {

        val estadoActual =
            _uiState.value

        val email =
            estadoActual.email.trim()


        if (
            !Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches()
        ) {

            mostrarError(
                "Ingrese un correo válido"
            )

            return
        }


        if (
            estadoActual.password.length < 6
        ) {

            mostrarError(
                "La contraseña debe tener al menos 6 caracteres"
            )

            return
        }


        if (
            estadoActual.password !=
            estadoActual.confirmarPassword
        ) {

            mostrarError(
                "Las contraseñas no coinciden"
            )

            return
        }


        _uiState.update {

            it.copy(
                cargando = true,
                error = null
            )
        }


        auth.createUserWithEmailAndPassword(
            email,
            estadoActual.password
        )
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    _uiState.update {

                        it.copy(
                            cargando = false,

                            autenticado = true,

                            usuarioEmail =
                                auth.currentUser
                                    ?.email,

                            password = "",

                            confirmarPassword = "",

                            error = null
                        )
                    }

                } else {

                    _uiState.update {

                        it.copy(
                            cargando = false,

                            error =
                                task.exception
                                    ?.localizedMessage
                                    ?: "No fue posible crear la cuenta"
                        )
                    }
                }
            }
    }


    // =====================================================
    // LOGIN
    // =====================================================

    fun iniciarSesion() {

        val estadoActual =
            _uiState.value

        val email =
            estadoActual.email.trim()


        if (
            !Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches()
        ) {

            mostrarError(
                "Ingrese un correo válido"
            )

            return
        }


        if (
            estadoActual.password.isBlank()
        ) {

            mostrarError(
                "Ingrese su contraseña"
            )

            return
        }


        _uiState.update {

            it.copy(
                cargando = true,
                error = null
            )
        }


        auth.signInWithEmailAndPassword(
            email,
            estadoActual.password
        )
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    _uiState.update {

                        it.copy(
                            cargando = false,

                            autenticado = true,

                            usuarioEmail =
                                auth.currentUser
                                    ?.email,

                            password = "",

                            confirmarPassword = "",

                            error = null
                        )
                    }

                } else {

                    _uiState.update {

                        it.copy(
                            cargando = false,

                            error =
                                task.exception
                                    ?.localizedMessage
                                    ?: "Correo o contraseña incorrectos"
                        )
                    }
                }
            }
    }


    // =====================================================
    // LOGOUT
    // =====================================================

    fun cerrarSesion() {

        auth.signOut()

        _uiState.value =
            AuthUiState()
    }


    // =====================================================
    // LIMPIEZA ENTRE LOGIN / REGISTRO
    // =====================================================

    fun limpiarCredenciales() {

        _uiState.update {

            it.copy(
                password = "",
                confirmarPassword = "",
                error = null
            )
        }
    }


    // =====================================================
    // ERROR
    // =====================================================

    private fun mostrarError(
        mensaje: String
    ) {

        _uiState.update {

            it.copy(
                cargando = false,
                error = mensaje
            )
        }
    }
}