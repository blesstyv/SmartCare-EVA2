package com.example.smartcare.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.smartcare.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    viewModel: AuthViewModel,
    onVolver: () -> Unit
) {

    val uiState by
    viewModel.uiState.collectAsStateWithLifecycle()

    var mostrarPassword by
    rememberSaveable {
        mutableStateOf(false)
    }

    var mostrarConfirmacion by
    rememberSaveable {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {

            TopAppBar(
                title = {
                    Text(
                        "Crear cuenta"
                    )
                },

                navigationIcon = {

                    IconButton(
                        onClick = {

                            viewModel
                                .limpiarCredenciales()

                            onVolver()
                        }
                    ) {

                        Icon(
                            imageVector =
                                Icons.AutoMirrored
                                    .Filled
                                    .ArrowBack,

                            contentDescription =
                                "Volver"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(20.dp)
        ) {

            Text(
                text =
                    "Únete a SmartCare",

                style =
                    MaterialTheme
                        .typography
                        .headlineSmall
            )

            Spacer(
                Modifier.height(4.dp)
            )

            Text(
                text =
                    "Crea una cuenta para gestionar tus activos y mantenimientos.",

                color =
                    MaterialTheme
                        .colorScheme
                        .onSurfaceVariant
            )

            Spacer(
                Modifier.height(20.dp)
            )

            Card(
                modifier =
                    Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier =
                        Modifier.padding(
                            20.dp
                        )
                ) {

                    OutlinedTextField(
                        value =
                            uiState.email,

                        onValueChange =
                            viewModel::cambiarEmail,

                        label = {
                            Text(
                                "Correo electrónico"
                            )
                        },

                        leadingIcon = {

                            Icon(
                                imageVector =
                                    Icons.Default.Email,

                                contentDescription =
                                    null
                            )
                        },

                        keyboardOptions =
                            KeyboardOptions(
                                keyboardType =
                                    KeyboardType.Email
                            ),

                        modifier =
                            Modifier.fillMaxWidth(),

                        singleLine =
                            true
                    )

                    Spacer(
                        Modifier.height(12.dp)
                    )

                    OutlinedTextField(
                        value =
                            uiState.password,

                        onValueChange =
                            viewModel::cambiarPassword,

                        label = {
                            Text(
                                "Contraseña"
                            )
                        },

                        supportingText = {
                            Text(
                                "Mínimo 6 caracteres"
                            )
                        },

                        leadingIcon = {

                            Icon(
                                imageVector =
                                    Icons.Default.Lock,

                                contentDescription =
                                    null
                            )
                        },

                        trailingIcon = {

                            IconButton(
                                onClick = {

                                    mostrarPassword =
                                        !mostrarPassword
                                }
                            ) {

                                Icon(
                                    imageVector =
                                        if (
                                            mostrarPassword
                                        ) {
                                            Icons.Default.VisibilityOff
                                        } else {
                                            Icons.Default.Visibility
                                        },

                                    contentDescription =
                                        null
                                )
                            }
                        },

                        visualTransformation =
                            if (
                                mostrarPassword
                            ) {
                                VisualTransformation.None
                            } else {
                                PasswordVisualTransformation()
                            },

                        keyboardOptions =
                            KeyboardOptions(
                                keyboardType =
                                    KeyboardType.Password
                            ),

                        modifier =
                            Modifier.fillMaxWidth(),

                        singleLine =
                            true
                    )

                    Spacer(
                        Modifier.height(12.dp)
                    )

                    OutlinedTextField(
                        value =
                            uiState.confirmarPassword,

                        onValueChange =
                            viewModel::
                            cambiarConfirmarPassword,

                        label = {
                            Text(
                                "Confirmar contraseña"
                            )
                        },

                        leadingIcon = {

                            Icon(
                                imageVector =
                                    Icons.Default.Lock,

                                contentDescription =
                                    null
                            )
                        },

                        trailingIcon = {

                            IconButton(
                                onClick = {

                                    mostrarConfirmacion =
                                        !mostrarConfirmacion
                                }
                            ) {

                                Icon(
                                    imageVector =
                                        if (
                                            mostrarConfirmacion
                                        ) {
                                            Icons.Default.VisibilityOff
                                        } else {
                                            Icons.Default.Visibility
                                        },

                                    contentDescription =
                                        null
                                )
                            }
                        },

                        visualTransformation =
                            if (
                                mostrarConfirmacion
                            ) {
                                VisualTransformation.None
                            } else {
                                PasswordVisualTransformation()
                            },

                        keyboardOptions =
                            KeyboardOptions(
                                keyboardType =
                                    KeyboardType.Password
                            ),

                        modifier =
                            Modifier.fillMaxWidth(),

                        singleLine =
                            true
                    )

                    uiState.error?.let {
                            mensaje ->

                        Spacer(
                            Modifier.height(10.dp)
                        )

                        Text(
                            text =
                                mensaje,

                            color =
                                MaterialTheme
                                    .colorScheme
                                    .error
                        )
                    }

                    Spacer(
                        Modifier.height(20.dp)
                    )

                    Button(
                        onClick =
                            viewModel::registrar,

                        enabled =
                            !uiState.cargando,

                        modifier =
                            Modifier.fillMaxWidth()
                    ) {

                        if (
                            uiState.cargando
                        ) {

                            CircularProgressIndicator(
                                modifier =
                                    Modifier.size(
                                        20.dp
                                    ),

                                strokeWidth =
                                    2.dp
                            )

                            Text(
                                "  Creando cuenta..."
                            )

                        } else {

                            Text(
                                "Crear cuenta"
                            )
                        }
                    }
                }
            }

            Spacer(
                Modifier.height(24.dp)
            )
        }
    }
}