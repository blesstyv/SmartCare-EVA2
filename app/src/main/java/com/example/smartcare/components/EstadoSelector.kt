package com.example.smartcare.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EstadoSelector(
    estadoSeleccionado: String,
    onEstadoSeleccionado: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    val estados =
        listOf(
            "Operativo",
            "Pendiente",
            "En mantenimiento"
        )

    Column(
        modifier =
            modifier.fillMaxWidth()
    ) {

        Text(
            text =
                "Estado del activo"
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

            estados.forEach { estado ->

                val icono =
                    when (estado) {

                        "Operativo" ->
                            Icons.Default.CheckCircle

                        "Pendiente" ->
                            Icons.Default.Warning

                        else ->
                            Icons.Default.Build
                    }

                FilterChip(
                    selected =
                        estadoSeleccionado ==
                                estado,

                    onClick = {

                        onEstadoSeleccionado(
                            estado
                        )
                    },

                    label = {

                        Text(
                            estado
                        )
                    },

                    leadingIcon = {

                        Icon(
                            imageVector =
                                icono,

                            contentDescription =
                                null
                        )
                    }
                )
            }
        }
    }
}