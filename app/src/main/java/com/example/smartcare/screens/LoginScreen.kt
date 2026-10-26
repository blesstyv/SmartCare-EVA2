package com.example.smartcare.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.smartcare.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onIrRegistro: () -> Unit
) {

    val uiState by
    viewModel.uiState.collectAsStateWithLifecycle()

    var mostrarPassword by
    rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        verticalArrangement =
            Arrangement.Center,

        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        Icon(
            imageVector =
                Icons.Default.Build,

            contentDescription =
                null,

            modifier =
                Modifier.size(58.dp),

            tint =
                MaterialTheme
                    .colorScheme
                    .primary
        )

        Spacer(
            Modifier.height(12.dp)
        )

        Text(
            text =
                "SmartCare",

            style =
                MaterialTheme
                    .typography
                    .headlineLarge
        )

        Text(
            text =
                "Gestión inteligente de mantenimiento",

            color =
                MaterialTheme
                    .colorScheme
                    .onSurfaceVariant
        )

        Spacer(
            Modifier.height(28.dp)
        )

        Card(
            modifier =
                Modifier.fillMaxWidth()
        ) {

            Column(
                modifier =
                    Modifier.padding(20.dp)
            ) {

                Text(
                    text =
                        "Bienvenido",

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
                        "Inicia sesión para administrar tus activos.",

                    color =
                        MaterialTheme
                            .colorScheme
                            .onSurfaceVariant
                )

                Spacer(
                    Modifier.height(20.dp)
                )

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
                                    if (
                                        mostrarPassword
                                    ) {
                                        "Ocultar contraseña"
                                    } else {
                                        "Mostrar contraseña"
                                    }
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
                        viewModel::iniciarSesion,

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
                            "  Ingresando..."
                        )

                    } else {

                        Text(
                            "Iniciar sesión"
                        )
                    }
                }

                Spacer(
                    Modifier.height(8.dp)
                )

                TextButton(
                    onClick = {

                        viewModel
                            .limpiarCredenciales()

                        onIrRegistro()
                    },

                    modifier =
                        Modifier.fillMaxWidth()
                ) {

                    Text(
                        "¿No tienes cuenta? Crear cuenta"
                    )
                }
            }
        }
    }
}