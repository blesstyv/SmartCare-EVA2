package com.example.smartcare.screens

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
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
import com.example.smartcare.components.EstadoSelector
import com.example.smartcare.viewmodel.SmartCareViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevoActivoScreen(
    viewModel: SmartCareViewModel,
    onGuardado: () -> Unit,
    onVolver: () -> Unit
) {

    val uiState by
    viewModel.uiState.collectAsStateWithLifecycle()


    // =====================================================
    // NAVEGAR SOLO DESPUÉS DE GUARDAR EN ROOM
    // =====================================================

    LaunchedEffect(
        uiState.activoGuardado
    ) {

        if (
            uiState.activoGuardado
        ) {

            /*
             * Primero consumimos el evento
             * para evitar que vuelva a ejecutarse
             * al entrar nuevamente a la pantalla.
             */
            viewModel
                .consumirActivoGuardado()

            onGuardado()
        }
    }


    Scaffold(
        topBar = {

            TopAppBar(
                title = {

                    Text(
                        "Registrar activo"
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
                        "Información del activo",

                    style =
                        MaterialTheme
                            .typography
                            .headlineSmall
                )
            }

            Spacer(
                Modifier.height(6.dp)
            )

            Text(
                text =
                    "Completa la información necesaria para incorporar el activo a SmartCare.",

                color =
                    MaterialTheme
                        .colorScheme
                        .onSurfaceVariant
            )

            Spacer(
                Modifier.height(24.dp)
            )


            // =================================================
            // NOMBRE
            // =================================================

            OutlinedTextField(
                value =
                    uiState.nombre,

                onValueChange =
                    viewModel::cambiarNombre,

                label = {

                    Text(
                        "Nombre del activo"
                    )
                },

                leadingIcon = {

                    Icon(
                        imageVector =
                            Icons.Default.Build,

                        contentDescription =
                            null
                    )
                },

                isError =
                    uiState.errorNombre !=
                            null,

                supportingText = {

                    uiState
                        .errorNombre
                        ?.let {

                            Text(it)
                        }
                },

                modifier =
                    Modifier.fillMaxWidth(),

                singleLine = true
            )

            Spacer(
                Modifier.height(12.dp)
            )


            // =================================================
            // TIPO
            // =================================================

            OutlinedTextField(
                value =
                    uiState.tipo,

                onValueChange =
                    viewModel::cambiarTipo,

                label = {

                    Text(
                        "Tipo de activo"
                    )
                },

                placeholder = {

                    Text(
                        "Ej: Máquina, vehículo, herramienta"
                    )
                },

                leadingIcon = {

                    Icon(
                        imageVector =
                            Icons.Default.Category,

                        contentDescription =
                            null
                    )
                },

                isError =
                    uiState.errorTipo !=
                            null,

                supportingText = {

                    uiState
                        .errorTipo
                        ?.let {

                            Text(it)
                        }
                },

                modifier =
                    Modifier.fillMaxWidth(),

                singleLine = true
            )

            Spacer(
                Modifier.height(12.dp)
            )


            // =================================================
            // DESCRIPCIÓN
            // =================================================

            OutlinedTextField(
                value =
                    uiState.descripcion,

                onValueChange =
                    viewModel::cambiarDescripcion,

                label = {

                    Text(
                        "Descripción"
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
                        .errorDescripcion !=
                            null,

                supportingText = {

                    uiState
                        .errorDescripcion
                        ?.let {

                            Text(it)
                        }
                },

                modifier =
                    Modifier.fillMaxWidth(),

                minLines = 3,

                maxLines = 5
            )

            Spacer(
                Modifier.height(20.dp)
            )


            // =================================================
            // ESTADO
            // =================================================

            EstadoSelector(
                estadoSeleccionado =
                    uiState.estado,

                onEstadoSeleccionado =
                    viewModel::cambiarEstado
            )


            uiState.errorEstado?.let {
                    mensaje ->

                Spacer(
                    Modifier.height(4.dp)
                )

                Text(
                    text =
                        mensaje,

                    color =
                        MaterialTheme
                            .colorScheme
                            .error,

                    style =
                        MaterialTheme
                            .typography
                            .bodySmall
                )
            }

            Spacer(
                Modifier.height(20.dp)
            )


            // =================================================
            // PRÓXIMA FECHA
            // =================================================

            OutlinedTextField(
                value =
                    uiState
                        .proximoMantenimiento,

                onValueChange =
                    viewModel::
                    cambiarProximoMantenimiento,

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
                            Icons.Default.CalendarMonth,

                        contentDescription =
                            null
                    )
                },

                isError =
                    uiState
                        .errorProximoMantenimiento !=
                            null,

                supportingText = {

                    uiState
                        .errorProximoMantenimiento
                        ?.let {

                            Text(it)
                        }
                        ?: Text(
                            "Ejemplo: 20/11/2026"
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

                    /*
                     * La pantalla NO valida ni navega.
                     *
                     * El ViewModel será responsable
                     * de validar y guardar.
                     */
                    viewModel
                        .registrarActivo()
                },

                enabled =
                    !uiState.guardandoActivo,

                modifier =
                    Modifier.fillMaxWidth()
            ) {

                if (
                    uiState.guardandoActivo
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
                        "  Registrar activo"
                    )
                }
            }

            Spacer(
                Modifier.height(24.dp)
            )
        }
    }
}