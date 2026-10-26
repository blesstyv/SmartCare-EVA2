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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.smartcare.components.EstadoSelector
import com.example.smartcare.viewmodel.SmartCareViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleActivoScreen(
    viewModel: SmartCareViewModel,
    onRegistrarMantenimiento: (Int) -> Unit,
    onVerHistorial: (Int) -> Unit,
    onEliminado: () -> Unit,
    onVolver: () -> Unit
) {

    val uiState by
    viewModel.uiState.collectAsStateWithLifecycle()

    val activo =
        uiState.activoSeleccionado

    var editando by
    rememberSaveable(activo?.id) {
        mutableStateOf(false)
    }

    var mostrarConfirmacionEliminar by
    rememberSaveable {
        mutableStateOf(false)
    }

    var nombreEditado by
    rememberSaveable(activo?.id) {
        mutableStateOf(
            activo?.nombre.orEmpty()
        )
    }

    var tipoEditado by
    rememberSaveable(activo?.id) {
        mutableStateOf(
            activo?.tipo.orEmpty()
        )
    }

    var descripcionEditada by
    rememberSaveable(activo?.id) {
        mutableStateOf(
            activo?.descripcion.orEmpty()
        )
    }

    var estadoEditado by
    rememberSaveable(activo?.id) {
        mutableStateOf(
            activo?.estado.orEmpty()
        )
    }

    var proximoMantenimientoEditado by
    rememberSaveable(activo?.id) {
        mutableStateOf(
            activo
                ?.proximoMantenimiento
                .orEmpty()
        )
    }

    // =====================================================
    // ACTUALIZACIÓN EXITOSA
    // =====================================================

    LaunchedEffect(
        uiState.actualizacionActivoExitosa
    ) {

        if (
            uiState.actualizacionActivoExitosa
        ) {

            editando =
                false

            viewModel
                .consumirActualizacionActivo()
        }
    }

    // =====================================================
    // DIÁLOGO DE ELIMINACIÓN
    // =====================================================

    if (
        mostrarConfirmacionEliminar &&
        activo != null
    ) {

        AlertDialog(
            onDismissRequest = {

                mostrarConfirmacionEliminar =
                    false
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
                    text =
                        "Eliminar activo"
                )
            },

            text = {

                Text(
                    text =
                        "¿Seguro que deseas eliminar \"${activo.nombre}\"? " +
                                "También se eliminará su historial de mantenimientos."
                )
            },

            confirmButton = {

                Button(
                    onClick = {

                        mostrarConfirmacionEliminar =
                            false

                        viewModel
                            .eliminarActivo(
                                activo
                            )

                        onEliminado()
                    }
                ) {

                    Text(
                        text =
                            "Eliminar"
                    )
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {

                        mostrarConfirmacionEliminar =
                            false
                    }
                ) {

                    Text(
                        text =
                            "Cancelar"
                    )
                }
            }
        )
    }

    // =====================================================
    // PANTALLA
    // =====================================================

    Scaffold(
        topBar = {

            TopAppBar(
                title = {

                    Text(
                        text =
                            if (editando) {
                                "Editar activo"
                            } else {
                                "Detalle del activo"
                            }
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
            // CARGANDO
            // =================================================

            if (
                uiState.cargandoDetalle
            ) {

                CircularProgressIndicator()
            }

            // =================================================
            // ACTIVO NO ENCONTRADO
            // =================================================

            else if (
                activo == null
            ) {

                Text(
                    text =
                        "Activo no encontrado",

                    style =
                        MaterialTheme
                            .typography
                            .titleLarge
                )
            }

            // =================================================
            // MODO EDICIÓN
            // =================================================

            else if (
                editando
            ) {

                Text(
                    text =
                        "Actualiza la información del activo",

                    style =
                        MaterialTheme
                            .typography
                            .headlineSmall
                )

                Spacer(
                    modifier =
                        Modifier.height(
                            20.dp
                        )
                )

                // NOMBRE

                OutlinedTextField(
                    value =
                        nombreEditado,

                    onValueChange = {

                        nombreEditado =
                            it

                        viewModel
                            .limpiarErrorEdicionActivo()
                    },

                    label = {

                        Text(
                            text =
                                "Nombre"
                        )
                    },

                    modifier =
                        Modifier.fillMaxWidth(),

                    singleLine =
                        true
                )

                Spacer(
                    modifier =
                        Modifier.height(
                            12.dp
                        )
                )

                // TIPO

                OutlinedTextField(
                    value =
                        tipoEditado,

                    onValueChange = {

                        tipoEditado =
                            it

                        viewModel
                            .limpiarErrorEdicionActivo()
                    },

                    label = {

                        Text(
                            text =
                                "Tipo"
                        )
                    },

                    modifier =
                        Modifier.fillMaxWidth(),

                    singleLine =
                        true
                )

                Spacer(
                    modifier =
                        Modifier.height(
                            12.dp
                        )
                )

                // DESCRIPCIÓN

                OutlinedTextField(
                    value =
                        descripcionEditada,

                    onValueChange = {

                        descripcionEditada =
                            it

                        viewModel
                            .limpiarErrorEdicionActivo()
                    },

                    label = {

                        Text(
                            text =
                                "Descripción"
                        )
                    },

                    modifier =
                        Modifier.fillMaxWidth(),

                    minLines =
                        3
                )

                Spacer(
                    modifier =
                        Modifier.height(
                            20.dp
                        )
                )

                // ESTADO

                EstadoSelector(
                    estadoSeleccionado =
                        estadoEditado,

                    onEstadoSeleccionado = {

                        estadoEditado =
                            it

                        viewModel
                            .limpiarErrorEdicionActivo()
                    }
                )

                Spacer(
                    modifier =
                        Modifier.height(
                            20.dp
                        )
                )

                // PRÓXIMO MANTENIMIENTO

                OutlinedTextField(
                    value =
                        proximoMantenimientoEditado,

                    onValueChange = {

                        proximoMantenimientoEditado =
                            it

                        viewModel
                            .limpiarErrorEdicionActivo()
                    },

                    label = {

                        Text(
                            text =
                                "Próximo mantenimiento"
                        )
                    },

                    placeholder = {

                        Text(
                            text =
                                "dd/MM/yyyy"
                        )
                    },

                    leadingIcon = {

                        Icon(
                            imageVector =
                                Icons.Default
                                    .CalendarMonth,

                            contentDescription =
                                null
                        )
                    },

                    modifier =
                        Modifier.fillMaxWidth(),

                    singleLine =
                        true
                )

                // ERROR DE VALIDACIÓN

                uiState
                    .errorEdicionActivo
                    ?.let { mensaje ->

                        Spacer(
                            modifier =
                                Modifier.height(
                                    8.dp
                                )
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
                    modifier =
                        Modifier.height(
                            24.dp
                        )
                )

                // CANCELAR

                OutlinedButton(
                    onClick = {

                        nombreEditado =
                            activo.nombre

                        tipoEditado =
                            activo.tipo

                        descripcionEditada =
                            activo.descripcion

                        estadoEditado =
                            activo.estado

                        proximoMantenimientoEditado =
                            activo
                                .proximoMantenimiento

                        viewModel
                            .limpiarErrorEdicionActivo()

                        editando =
                            false
                    },

                    enabled =
                        !uiState.guardandoActivo,

                    modifier =
                        Modifier.fillMaxWidth()
                ) {

                    Text(
                        text =
                            "Cancelar"
                    )
                }

                Spacer(
                    modifier =
                        Modifier.height(
                            10.dp
                        )
                )

                // GUARDAR CAMBIOS

                Button(
                    onClick = {

                        viewModel
                            .actualizarActivo(
                                nombre =
                                    nombreEditado,

                                tipo =
                                    tipoEditado,

                                descripcion =
                                    descripcionEditada,

                                estado =
                                    estadoEditado,

                                proximoMantenimiento =
                                    proximoMantenimientoEditado
                            )
                    },

                    enabled =
                        !uiState.guardandoActivo &&
                                nombreEditado
                                    .isNotBlank() &&
                                tipoEditado
                                    .isNotBlank() &&
                                descripcionEditada
                                    .isNotBlank() &&
                                estadoEditado
                                    .isNotBlank() &&
                                proximoMantenimientoEditado
                                    .isNotBlank(),

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
                            text =
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
                            text =
                                "  Guardar cambios"
                        )
                    }
                }
            }

            // =================================================
            // MODO LECTURA
            // =================================================

            else {

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
                                    activo.nombre,

                                style =
                                    MaterialTheme
                                        .typography
                                        .headlineSmall
                            )
                        }

                        Spacer(
                            modifier =
                                Modifier.height(
                                    18.dp
                                )
                        )

                        Text(
                            text =
                                "Tipo",

                            style =
                                MaterialTheme
                                    .typography
                                    .labelLarge
                        )

                        Text(
                            text =
                                activo.tipo
                        )

                        Spacer(
                            modifier =
                                Modifier.height(
                                    14.dp
                                )
                        )

                        Text(
                            text =
                                "Estado",

                            style =
                                MaterialTheme
                                    .typography
                                    .labelLarge
                        )

                        Text(
                            text =
                                activo.estado,

                            color =
                                MaterialTheme
                                    .colorScheme
                                    .primary
                        )

                        Spacer(
                            modifier =
                                Modifier.height(
                                    14.dp
                                )
                        )

                        Text(
                            text =
                                "Descripción",

                            style =
                                MaterialTheme
                                    .typography
                                    .labelLarge
                        )

                        Text(
                            text =
                                activo.descripcion
                        )

                        Spacer(
                            modifier =
                                Modifier.height(
                                    14.dp
                                )
                        )

                        Text(
                            text =
                                "Próximo mantenimiento",

                            style =
                                MaterialTheme
                                    .typography
                                    .labelLarge
                        )

                        Row(
                            horizontalArrangement =
                                Arrangement.spacedBy(
                                    8.dp
                                )
                        ) {

                            Icon(
                                imageVector =
                                    Icons.Default
                                        .CalendarMonth,

                                contentDescription =
                                    null,

                                tint =
                                    MaterialTheme
                                        .colorScheme
                                        .secondary
                            )

                            Text(
                                text =
                                    activo
                                        .proximoMantenimiento
                            )
                        }
                    }
                }

                Spacer(
                    modifier =
                        Modifier.height(
                            20.dp
                        )
                )

                // EDITAR

                Button(
                    onClick = {

                        nombreEditado =
                            activo.nombre

                        tipoEditado =
                            activo.tipo

                        descripcionEditada =
                            activo.descripcion

                        estadoEditado =
                            activo.estado

                        proximoMantenimientoEditado =
                            activo
                                .proximoMantenimiento

                        viewModel
                            .limpiarErrorEdicionActivo()

                        editando =
                            true
                    },

                    modifier =
                        Modifier.fillMaxWidth()
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.Edit,

                        contentDescription =
                            null
                    )

                    Text(
                        text =
                            "  Editar activo"
                    )
                }

                Spacer(
                    modifier =
                        Modifier.height(
                            10.dp
                        )
                )

                // REGISTRAR MANTENIMIENTO

                OutlinedButton(
                    onClick = {

                        onRegistrarMantenimiento(
                            activo.id
                        )
                    },

                    modifier =
                        Modifier.fillMaxWidth()
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.Build,

                        contentDescription =
                            null
                    )

                    Text(
                        text =
                            "  Registrar mantenimiento"
                    )
                }

                Spacer(
                    modifier =
                        Modifier.height(
                            10.dp
                        )
                )

                // HISTORIAL

                OutlinedButton(
                    onClick = {

                        onVerHistorial(
                            activo.id
                        )
                    },

                    modifier =
                        Modifier.fillMaxWidth()
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.History,

                        contentDescription =
                            null
                    )

                    Text(
                        text =
                            "  Ver historial"
                    )
                }

                Spacer(
                    modifier =
                        Modifier.height(
                            18.dp
                        )
                )

                // ELIMINAR

                TextButton(
                    onClick = {

                        mostrarConfirmacionEliminar =
                            true
                    },

                    modifier =
                        Modifier.fillMaxWidth()
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
                            "  Eliminar activo",

                        color =
                            MaterialTheme
                                .colorScheme
                                .error
                    )
                }
            }

            Spacer(
                modifier =
                    Modifier.height(
                        24.dp
                    )
            )
        }
    }
}