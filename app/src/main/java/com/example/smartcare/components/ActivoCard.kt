package com.example.smartcare.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartcare.data.Activo
import com.example.smartcare.ui.theme.EstadoMantenimiento
import com.example.smartcare.ui.theme.EstadoMantenimientoContainer
import com.example.smartcare.ui.theme.EstadoOperativo
import com.example.smartcare.ui.theme.EstadoOperativoContainer
import com.example.smartcare.ui.theme.EstadoPendiente
import com.example.smartcare.ui.theme.EstadoPendienteContainer

@Composable
fun ActivoCard(
    activo: Activo,
    onVerDetalle: () -> Unit,
    onEliminar: () -> Unit
) {

    var mostrarConfirmacion by
    remember {
        mutableStateOf(false)
    }

    if (mostrarConfirmacion) {

        AlertDialog(
            onDismissRequest = {
                mostrarConfirmacion =
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
                    "Eliminar activo"
                )
            },

            text = {

                Text(
                    "¿Seguro que deseas eliminar \"${activo.nombre}\"?"
                )
            },

            confirmButton = {

                Button(
                    onClick = {

                        mostrarConfirmacion =
                            false

                        onEliminar()
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

                        mostrarConfirmacion =
                            false
                    }
                ) {

                    Text(
                        "Cancelar"
                    )
                }
            }
        )
    }

    val colorEstado =
        when (
            activo.estado
                .lowercase()
        ) {

            "operativo" ->
                EstadoOperativo

            "pendiente" ->
                EstadoPendiente

            else ->
                EstadoMantenimiento
        }

    val contenedorEstado =
        when (
            activo.estado
                .lowercase()
        ) {

            "operativo" ->
                EstadoOperativoContainer

            "pendiente" ->
                EstadoPendienteContainer

            else ->
                EstadoMantenimientoContainer
        }

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
                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.spacedBy(
                        12.dp
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

                Column(
                    modifier =
                        Modifier.weight(1f)
                ) {

                    Text(
                        text =
                            activo.nombre,

                        style =
                            MaterialTheme
                                .typography
                                .titleMedium
                    )

                    Text(
                        text =
                            activo.tipo,

                        color =
                            MaterialTheme
                                .colorScheme
                                .onSurfaceVariant
                    )
                }

                Surface(
                    color =
                        contenedorEstado,

                    shape =
                        MaterialTheme
                            .shapes
                            .small
                ) {

                    Text(
                        text =
                            activo.estado,

                        color =
                            colorEstado,

                        style =
                            MaterialTheme
                                .typography
                                .labelLarge,

                        modifier =
                            Modifier.padding(
                                horizontal =
                                    10.dp,

                                vertical =
                                    6.dp
                            )
                    )
                }
            }

            Spacer(
                Modifier.height(14.dp)
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
                            .secondary
                )

                Text(
                    text =
                        "Próximo: ${activo.proximoMantenimiento}"
                )
            }

            Spacer(
                Modifier.height(16.dp)
            )

            Row(
                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween
            ) {

                Button(
                    onClick =
                        onVerDetalle
                ) {

                    Text(
                        "Ver detalle"
                    )

                    Icon(
                        imageVector =
                            Icons.Default.KeyboardArrowRight,

                        contentDescription =
                            null
                    )
                }

                TextButton(
                    onClick = {

                        mostrarConfirmacion =
                            true
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
                            "Eliminar",

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