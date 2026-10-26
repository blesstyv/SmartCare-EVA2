package com.example.smartcare.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.smartcare.components.ActivoCard
import com.example.smartcare.viewmodel.SmartCareViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivosScreen(
    viewModel: SmartCareViewModel,
    onVerDetalle: (Int) -> Unit,
    onNuevoActivo: () -> Unit,
    onVolver: () -> Unit
) {

    val uiState by
    viewModel.uiState
        .collectAsStateWithLifecycle()

    Scaffold(
        topBar = {

            TopAppBar(
                title = {

                    Text(
                        "Mis activos"
                    )
                },

                navigationIcon = {

                    IconButton(
                        onClick =
                            onVolver
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

        LazyColumn(
            modifier =
                Modifier.padding(
                    innerPadding
                ),

            contentPadding =
                PaddingValues(
                    horizontal =
                        16.dp,

                    vertical =
                        14.dp
                ),

            verticalArrangement =
                Arrangement.spacedBy(
                    12.dp
                )
        ) {

            item {

                OutlinedTextField(
                    value =
                        uiState.busqueda,

                    onValueChange =
                        viewModel::cambiarBusqueda,

                    label = {

                        Text(
                            "Buscar activo"
                        )
                    },

                    placeholder = {

                        Text(
                            "Nombre o tipo"
                        )
                    },

                    leadingIcon = {

                        Icon(
                            imageVector =
                                Icons.Default.Search,

                            contentDescription =
                                null
                        )
                    },

                    modifier =
                        Modifier.fillMaxWidth(),

                    singleLine =
                        true
                )
            }


            item {

                Text(
                    text =
                        "Filtrar por estado",

                    style =
                        MaterialTheme
                            .typography
                            .labelLarge
                )

                Spacer(
                    Modifier.height(6.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(
                            rememberScrollState()
                        ),

                    horizontalArrangement =
                        Arrangement.spacedBy(
                            8.dp
                        )
                ) {

                    val filtros =
                        listOf(
                            "Todos",
                            "Operativo",
                            "Pendiente",
                            "En mantenimiento"
                        )

                    filtros.forEach {
                            filtro ->

                        FilterChip(
                            selected =
                                uiState
                                    .filtroEstado ==
                                        filtro,

                            onClick = {

                                viewModel
                                    .cambiarFiltroEstado(
                                        filtro
                                    )
                            },

                            label = {

                                Text(
                                    filtro
                                )
                            }
                        )
                    }
                }
            }


            item {

                Button(
                    onClick =
                        onNuevoActivo,

                    modifier =
                        Modifier.fillMaxWidth()
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.Add,

                        contentDescription =
                            null
                    )

                    Text(
                        "  Registrar nuevo activo"
                    )
                }
            }


            item {

                Text(
                    text =
                        "Activos: ${uiState.activosFiltrados.size}",

                    style =
                        MaterialTheme
                            .typography
                            .titleLarge
                )
            }


            if (
                uiState
                    .activosFiltrados
                    .isEmpty()
            ) {

                item {

                    Card(
                        modifier =
                            Modifier.fillMaxWidth()
                    ) {

                        androidx.compose.foundation.layout.Column(
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
                                Modifier.height(
                                    12.dp
                                )
                            )

                            Text(
                                text =
                                    if (
                                        uiState
                                            .activos
                                            .isEmpty()
                                    ) {
                                        "Aún no tienes activos registrados"
                                    } else {
                                        "No encontramos resultados"
                                    },

                                style =
                                    MaterialTheme
                                        .typography
                                        .titleMedium
                            )

                            Spacer(
                                Modifier.height(
                                    4.dp
                                )
                            )

                            Text(
                                text =
                                    if (
                                        uiState
                                            .activos
                                            .isEmpty()
                                    ) {
                                        "Registra tu primer activo para comenzar a gestionar sus mantenimientos."
                                    } else {
                                        "Prueba modificando la búsqueda o seleccionando otro estado."
                                    },

                                color =
                                    MaterialTheme
                                        .colorScheme
                                        .onSurfaceVariant
                            )
                        }
                    }
                }

            } else {

                items(
                    items =
                        uiState
                            .activosFiltrados,

                    key = {
                        it.id
                    }
                ) { activo ->

                    ActivoCard(
                        activo =
                            activo,

                        onVerDetalle = {

                            onVerDetalle(
                                activo.id
                            )
                        },

                        onEliminar = {

                            viewModel
                                .eliminarActivo(
                                    activo
                                )
                        }
                    )
                }
            }


            item {

                Spacer(
                    Modifier.height(
                        20.dp
                    )
                )
            }
        }
    }
}