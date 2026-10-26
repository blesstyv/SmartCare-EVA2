package com.example.smartcare.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.EventRepeat
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.smartcare.data.Mantenimiento
import com.example.smartcare.viewmodel.SmartCareViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialScreen(
    viewModel: SmartCareViewModel,
    onVolver: () -> Unit
) {

    val uiState by
    viewModel.uiState
        .collectAsStateWithLifecycle()

    val activo =
        uiState.activoSeleccionado

    var mantenimientoAEliminar by
    remember {
        mutableStateOf<Mantenimiento?>(
            null
        )
    }

    mantenimientoAEliminar?.let {
            mantenimiento ->

        AlertDialog(
            onDismissRequest = {

                mantenimientoAEliminar =
                    null
            },

            icon = {

                Icon(
                    imageVector =
                        Icons.Default.Delete,

                    contentDescription =
                        null
                )
            },

            title = {

                Text(
                    "Eliminar mantenimiento"
                )
            },

            text = {

                Text(
                    "¿Seguro que deseas eliminar este registro del historial?"
                )
            },

            confirmButton = {

                Button(
                    onClick = {

                        viewModel
                            .eliminarMantenimiento(
                                mantenimiento
                            )

                        mantenimientoAEliminar =
                            null
                    }
                ) {

                    Text(
                        "Eliminar"
                    )
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {

                        mantenimientoAEliminar =
                            null
                    }
                ) {

                    Text(
                        "Cancelar"
                    )
                }
            }
        )
    }

    Scaffold(
        topBar = {

            TopAppBar(
                title = {

                    Text(
                        "Historial de mantenimiento"
                    )
                },

                navigationIcon = {

                    IconButton(
                        onClick =
                            onVolver
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.ArrowBack,

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
                .padding(16.dp)
        ) {

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
                                Icons.Default.History,

                            contentDescription =
                                null,

                            tint =
                                MaterialTheme
                                    .colorScheme
                                    .primary
                        )

                        Text(
                            text =
                                activo?.nombre
                                    ?: "Activo",

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
                            "${uiState.mantenimientos.size} mantenimiento(s) registrado(s)",

                        color =
                            MaterialTheme
                                .colorScheme
                                .onSurfaceVariant
                    )
                }
            }

            Spacer(
                Modifier.height(16.dp)
            )

            if (
                uiState.mantenimientos
                    .isEmpty()
            ) {

                Card(
                    modifier =
                        Modifier.fillMaxWidth()
                ) {

                    Column(
                        modifier =
                            Modifier.padding(
                                24.dp
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

                        Spacer(
                            Modifier.height(12.dp)
                        )

                        Text(
                            text =
                                "Sin mantenimientos registrados",

                            style =
                                MaterialTheme
                                    .typography
                                    .titleMedium
                        )

                        Spacer(
                            Modifier.height(4.dp)
                        )

                        Text(
                            text =
                                "Cuando registres una intervención aparecerá automáticamente en este historial.",

                            color =
                                MaterialTheme
                                    .colorScheme
                                    .onSurfaceVariant
                        )
                    }
                }

            } else {

                LazyColumn(
                    modifier =
                        Modifier.fillMaxSize(),

                    verticalArrangement =
                        Arrangement.spacedBy(
                            12.dp
                        )
                ) {

                    items(
                        items =
                            uiState
                                .mantenimientos,

                        key = {
                            it.id
                        }
                    ) { mantenimiento ->

                        Card(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                        ) {

                            Column(
                                modifier =
                                    Modifier.padding(
                                        18.dp
                                    )
                            ) {

                                Text(
                                    text =
                                        mantenimiento
                                            .descripcion,

                                    style =
                                        MaterialTheme
                                            .typography
                                            .titleMedium
                                )

                                Spacer(
                                    Modifier.height(
                                        12.dp
                                    )
                                )

                                Row(
                                    horizontalArrangement =
                                        Arrangement.spacedBy(
                                            8.dp
                                        )
                                ) {

                                    Icon(
                                        imageVector =
                                            Icons.Default.CalendarMonth,

                                        contentDescription =
                                            null,

                                        tint =
                                            MaterialTheme
                                                .colorScheme
                                                .primary
                                    )

                                    Text(
                                        text =
                                            mantenimiento
                                                .fecha
                                    )
                                }

                                if (
                                    mantenimiento
                                        .observaciones
                                        .isNotBlank()
                                ) {

                                    Spacer(
                                        Modifier.height(
                                            10.dp
                                        )
                                    )

                                    Row(
                                        horizontalArrangement =
                                            Arrangement.spacedBy(
                                                8.dp
                                            )
                                    ) {

                                        Icon(
                                            imageVector =
                                                Icons.Default.Notes,

                                            contentDescription =
                                                null
                                        )

                                        Text(
                                            text =
                                                mantenimiento
                                                    .observaciones
                                        )
                                    }
                                }

                                if (
                                    mantenimiento
                                        .proximaFecha
                                        .isNotBlank()
                                ) {

                                    Spacer(
                                        Modifier.height(
                                            10.dp
                                        )
                                    )

                                    Row(
                                        horizontalArrangement =
                                            Arrangement.spacedBy(
                                                8.dp
                                            )
                                    ) {

                                        Icon(
                                            imageVector =
                                                Icons.Default.EventRepeat,

                                            contentDescription =
                                                null,

                                            tint =
                                                MaterialTheme
                                                    .colorScheme
                                                    .secondary
                                        )

                                        Text(
                                            text =
                                                "Próximo: ${mantenimiento.proximaFecha}"
                                        )
                                    }
                                }

                                Spacer(
                                    Modifier.height(
                                        12.dp
                                    )
                                )

                                TextButton(
                                    onClick = {

                                        mantenimientoAEliminar =
                                            mantenimiento
                                    }
                                ) {

                                    Icon(
                                        imageVector =
                                            Icons.Default.Delete,

                                        contentDescription =
                                            null,

                                        tint =
                                            MaterialTheme
                                                .colorScheme
                                                .error
                                    )

                                    Text(
                                        text =
                                            "  Eliminar registro",

                                        color =
                                            MaterialTheme
                                                .colorScheme
                                                .error
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}