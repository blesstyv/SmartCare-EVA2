package com.example.smartcare.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.EventRepeat
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Save
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.smartcare.viewmodel.SmartCareViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MantenimientoScreen(
    activoId: Int,
    viewModel: SmartCareViewModel,
    onGuardado: () -> Unit,
    onVolver: () -> Unit
) {

    val uiState by
    viewModel.uiState
        .collectAsStateWithLifecycle()


    /*
     * Evitamos mostrar accidentalmente
     * un activo anterior mientras Room
     * carga el activo actual.
     */
    val activo =
        uiState
            .activoSeleccionado
            ?.takeIf {

                it.id ==
                        activoId
            }


    // =====================================================
    // NAVEGAR SOLO SI ROOM GUARDÓ CORRECTAMENTE
    // =====================================================

    LaunchedEffect(
        uiState.mantenimientoGuardado
    ) {

        if (
            uiState.mantenimientoGuardado
        ) {

            viewModel
                .consumirMantenimientoGuardado()

            onGuardado()
        }
    }


    Scaffold(
        topBar = {

            TopAppBar(
                title = {

                    Text(
                        "Registrar mantenimiento"
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
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(20.dp)
        ) {


            // =================================================
            // ACTIVO ASOCIADO
            // =================================================

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

                    Row {

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
                                "  ${
                                    activo
                                        ?.nombre
                                        ?: "Cargando activo..."
                                }",

                            style =
                                MaterialTheme
                                    .typography
                                    .titleLarge
                        )
                    }


                    activo?.let {

                        Spacer(
                            Modifier.height(
                                6.dp
                            )
                        )

                        Text(
                            text =
                                it.tipo,

                            color =
                                MaterialTheme
                                    .colorScheme
                                    .onSurfaceVariant
                        )

                        Text(
                            text =
                                "Estado actual: ${it.estado}"
                        )
                    }
                }
            }


            Spacer(
                Modifier.height(24.dp)
            )


            Text(
                text =
                    "Datos del mantenimiento",

                style =
                    MaterialTheme
                        .typography
                        .headlineSmall
            )

            Spacer(
                Modifier.height(16.dp)
            )


            // =================================================
            // DESCRIPCIÓN
            // =================================================

            OutlinedTextField(
                value =
                    uiState
                        .mantenimientoDescripcion,

                onValueChange =
                    viewModel::
                    cambiarMantenimientoDescripcion,

                label = {

                    Text(
                        "Descripción del mantenimiento"
                    )
                },

                leadingIcon = {

                    Icon(
                        imageVector =
                            Icons.Default.Description,

                        contentDescription =
                            null
                    )
                },

                isError =
                    uiState
                        .errorMantenimientoDescripcion !=
                            null,

                supportingText = {

                    uiState
                        .errorMantenimientoDescripcion
                        ?.let {

                            Text(it)
                        }
                },

                modifier =
                    Modifier.fillMaxWidth(),

                minLines = 2
            )


            Spacer(
                Modifier.height(12.dp)
            )


            // =================================================
            // FECHA
            // =================================================

            OutlinedTextField(
                value =
                    uiState
                        .mantenimientoFecha,

                onValueChange =
                    viewModel::
                    cambiarMantenimientoFecha,

                label = {

                    Text(
                        "Fecha del mantenimiento"
                    )
                },

                placeholder = {

                    Text(
                        "dd/MM/yyyy"
                    )
                },

                leadingIcon = {

                    Icon(
                        imageVector =
                            Icons.Default.CalendarMonth,

                        contentDescription =
                            null
                    )
                },

                isError =
                    uiState
                        .errorMantenimientoFecha !=
                            null,

                supportingText = {

                    uiState
                        .errorMantenimientoFecha
                        ?.let {

                            Text(it)
                        }
                        ?: Text(
                            "Ejemplo: 19/10/2026"
                        )
                },

                modifier =
                    Modifier.fillMaxWidth(),

                singleLine = true
            )


            Spacer(
                Modifier.height(12.dp)
            )


            // =================================================
            // OBSERVACIONES
            // =================================================

            OutlinedTextField(
                value =
                    uiState
                        .mantenimientoObservaciones,

                onValueChange =
                    viewModel::
                    cambiarMantenimientoObservaciones,

                label = {

                    Text(
                        "Observaciones"
                    )
                },

                leadingIcon = {

                    Icon(
                        imageVector =
                            Icons.Default.Notes,

                        contentDescription =
                            null
                    )
                },

                modifier =
                    Modifier.fillMaxWidth(),

                minLines = 3,

                maxLines = 5
            )


            Spacer(
                Modifier.height(12.dp)
            )


            // =================================================
            // PRÓXIMO MANTENIMIENTO
            // =================================================

            OutlinedTextField(
                value =
                    uiState
                        .mantenimientoProximaFecha,

                onValueChange =
                    viewModel::
                    cambiarMantenimientoProximaFecha,

                label = {

                    Text(
                        "Próximo mantenimiento"
                    )
                },

                placeholder = {

                    Text(
                        "dd/MM/yyyy"
                    )
                },

                leadingIcon = {

                    Icon(
                        imageVector =
                            Icons.Default.EventRepeat,

                        contentDescription =
                            null
                    )
                },

                isError =
                    uiState
                        .errorMantenimientoProximaFecha !=
                            null,

                supportingText = {

                    uiState
                        .errorMantenimientoProximaFecha
                        ?.let {

                            Text(it)
                        }
                        ?: Text(
                            "Opcional. Ejemplo: 20/12/2026"
                        )
                },

                modifier =
                    Modifier.fillMaxWidth(),

                singleLine = true
            )


            Spacer(
                Modifier.height(24.dp)
            )


            // =================================================
            // GUARDAR
            // =================================================

            Button(
                onClick = {

                    viewModel
                        .registrarMantenimiento(
                            activoId
                        )
                },

                enabled =
                    !uiState
                        .guardandoMantenimiento,

                modifier =
                    Modifier.fillMaxWidth()
            ) {

                if (
                    uiState
                        .guardandoMantenimiento
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
                        "  Guardando..."
                    )

                } else {

                    Icon(
                        imageVector =
                            Icons.Default.Save,

                        contentDescription =
                            null
                    )

                    Text(
                        "  Guardar mantenimiento"
                    )
                }
            }


            Spacer(
                Modifier.height(24.dp)
            )
        }
    }
}