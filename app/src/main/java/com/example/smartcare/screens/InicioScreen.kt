package com.example.smartcare.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InicioScreen(
    usuarioEmail: String?,
    onVerActivos: () -> Unit,
    onNuevoActivo: () -> Unit,
    onCerrarSesion: () -> Unit
) {

    Scaffold(
        topBar = {

            TopAppBar(
                title = {

                    Text(
                        "SmartCare"
                    )
                },

                actions = {

                    IconButton(
                        onClick =
                            onCerrarSesion
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.ExitToApp,

                            contentDescription =
                                "Cerrar sesión"
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
                    "Panel principal",

                style =
                    MaterialTheme
                        .typography
                        .headlineLarge
            )

            Spacer(
                Modifier.height(4.dp)
            )

            Text(
                text =
                    "Controla el estado y mantenimiento de tus activos.",

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
                            18.dp
                        )
                ) {

                    Text(
                        text =
                            "Sesión iniciada",

                        style =
                            MaterialTheme
                                .typography
                                .labelLarge
                    )

                    Spacer(
                        Modifier.height(4.dp)
                    )

                    Text(
                        text =
                            usuarioEmail
                                ?: "Usuario SmartCare",

                        color =
                            MaterialTheme
                                .colorScheme
                                .primary
                    )
                }
            }

            Spacer(
                Modifier.height(24.dp)
            )

            Text(
                text =
                    "¿Qué deseas hacer?",

                style =
                    MaterialTheme
                        .typography
                        .headlineSmall
            )

            Spacer(
                Modifier.height(14.dp)
            )

            Card(
                modifier =
                    Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier =
                        Modifier.padding(
                            18.dp
                        )
                ) {

                    Row(
                        horizontalArrangement =
                            Arrangement.spacedBy(
                                10.dp
                            )
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.Build,

                            contentDescription =
                                null,

                            tint =
                                MaterialTheme
                                    .colorScheme
                                    .primary
                        )

                        Text(
                            text =
                                "Gestión de activos",

                            style =
                                MaterialTheme
                                    .typography
                                    .titleLarge
                        )
                    }

                    Spacer(
                        Modifier.height(8.dp)
                    )

                    Text(
                        text =
                            "Consulta tus equipos, revisa su estado, edita información y administra mantenimientos.",

                        color =
                            MaterialTheme
                                .colorScheme
                                .onSurfaceVariant
                    )

                    Spacer(
                        Modifier.height(16.dp)
                    )

                    Button(
                        onClick =
                            onVerActivos,

                        modifier =
                            Modifier.fillMaxWidth()
                    ) {

                        Text(
                            "Ver mis activos"
                        )
                    }
                }
            }

            Spacer(
                Modifier.height(14.dp)
            )

            Card(
                modifier =
                    Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier =
                        Modifier.padding(
                            18.dp
                        )
                ) {

                    Row(
                        horizontalArrangement =
                            Arrangement.spacedBy(
                                10.dp
                            )
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.AddCircle,

                            contentDescription =
                                null,

                            tint =
                                MaterialTheme
                                    .colorScheme
                                    .secondary
                        )

                        Text(
                            text =
                                "Nuevo activo",

                            style =
                                MaterialTheme
                                    .typography
                                    .titleLarge
                        )
                    }

                    Spacer(
                        Modifier.height(8.dp)
                    )

                    Text(
                        text =
                            "Registra un nuevo activo y define su estado y próxima fecha de mantenimiento.",

                        color =
                            MaterialTheme
                                .colorScheme
                                .onSurfaceVariant
                    )

                    Spacer(
                        Modifier.height(16.dp)
                    )

                    OutlinedButton(
                        onClick =
                            onNuevoActivo,

                        modifier =
                            Modifier.fillMaxWidth()
                    ) {

                        Text(
                            "Registrar activo"
                        )
                    }
                }
            }

            Spacer(
                Modifier.height(24.dp)
            )

            OutlinedButton(
                onClick =
                    onCerrarSesion,

                modifier =
                    Modifier.fillMaxWidth()
            ) {

                Icon(
                    imageVector =
                        Icons.Default.ExitToApp,

                    contentDescription =
                        null
                )

                Text(
                    "  Cerrar sesión"
                )
            }

            Spacer(
                Modifier.height(24.dp)
            )
        }
    }
}