package com.example.smartcare.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartcare.data.Activo
import com.example.smartcare.data.Mantenimiento
import com.example.smartcare.repository.SmartCareRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class SmartCareViewModel(
    private val repository: SmartCareRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            SmartCareUiState()
        )

    val uiState: StateFlow<SmartCareUiState> =
        _uiState.asStateFlow()

    private var mantenimientosJob: Job? = null


    init {
        observarActivos()
    }


    // =========================================================
    // ROOM - OBSERVAR LISTADO DE ACTIVOS
    // =========================================================

    private fun observarActivos() {

        viewModelScope.launch {

            repository
                .obtenerActivos()
                .collect { lista ->

                    _uiState.update { estadoActual ->

                        estadoActual.copy(

                            activos =
                                lista,

                            activosFiltrados =
                                filtrarActivos(
                                    activos =
                                        lista,

                                    busqueda =
                                        estadoActual.busqueda,

                                    filtroEstado =
                                        estadoActual.filtroEstado
                                )
                        )
                    }
                }
        }
    }


    // =========================================================
    // FORMULARIO DE NUEVO ACTIVO
    // =========================================================

    fun cambiarNombre(
        nuevoNombre: String
    ) {

        _uiState.update {

            it.copy(
                nombre =
                    nuevoNombre,

                errorNombre =
                    null
            )
        }
    }


    fun cambiarTipo(
        nuevoTipo: String
    ) {

        _uiState.update {

            it.copy(
                tipo =
                    nuevoTipo,

                errorTipo =
                    null
            )
        }
    }


    fun cambiarDescripcion(
        nuevaDescripcion: String
    ) {

        _uiState.update {

            it.copy(
                descripcion =
                    nuevaDescripcion,

                errorDescripcion =
                    null
            )
        }
    }


    fun cambiarEstado(
        nuevoEstado: String
    ) {

        _uiState.update {

            it.copy(
                estado =
                    nuevoEstado,

                errorEstado =
                    null
            )
        }
    }


    fun cambiarProximoMantenimiento(
        nuevaFecha: String
    ) {

        _uiState.update {

            it.copy(
                proximoMantenimiento =
                    nuevaFecha,

                errorProximoMantenimiento =
                    null
            )
        }
    }


    // =========================================================
    // CREATE - REGISTRAR ACTIVO
    // =========================================================

    fun registrarActivo() {

        val estadoActual =
            _uiState.value


        val errorNombre =

            if (
                estadoActual.nombre
                    .isBlank()
            ) {

                "Ingrese el nombre del activo"

            } else {

                null
            }


        val errorTipo =

            if (
                estadoActual.tipo
                    .isBlank()
            ) {

                "Ingrese el tipo de activo"

            } else {

                null
            }


        val errorDescripcion =

            if (
                estadoActual.descripcion
                    .isBlank()
            ) {

                "Ingrese una descripción"

            } else {

                null
            }


        val errorEstado =

            if (
                estadoActual.estado
                    .isBlank()
            ) {

                "Seleccione el estado del activo"

            } else {

                null
            }


        val errorFecha =

            when {

                estadoActual
                    .proximoMantenimiento
                    .isBlank() -> {

                    "Ingrese la próxima fecha de mantenimiento"
                }


                !esFechaValida(
                    estadoActual
                        .proximoMantenimiento
                ) -> {

                    "Ingrese una fecha válida en formato dd/MM/yyyy"
                }


                else -> {

                    null
                }
            }


        _uiState.update {

            it.copy(

                errorNombre =
                    errorNombre,

                errorTipo =
                    errorTipo,

                errorDescripcion =
                    errorDescripcion,

                errorEstado =
                    errorEstado,

                errorProximoMantenimiento =
                    errorFecha
            )
        }


        val formularioValido =

            errorNombre == null &&
                    errorTipo == null &&
                    errorDescripcion == null &&
                    errorEstado == null &&
                    errorFecha == null


        if (
            !formularioValido
        ) {

            return
        }


        val nuevoActivo =
            Activo(

                nombre =
                    estadoActual
                        .nombre
                        .trim(),

                tipo =
                    estadoActual
                        .tipo
                        .trim(),

                descripcion =
                    estadoActual
                        .descripcion
                        .trim(),

                estado =
                    estadoActual
                        .estado
                        .trim(),

                proximoMantenimiento =
                    estadoActual
                        .proximoMantenimiento
                        .trim()
            )


        _uiState.update {

            it.copy(
                guardandoActivo =
                    true,

                activoGuardado =
                    false
            )
        }


        viewModelScope.launch {

            try {

                repository
                    .insertarActivo(
                        nuevoActivo
                    )


                _uiState.update {

                    it.copy(

                        nombre = "",

                        tipo = "",

                        descripcion = "",

                        estado = "",

                        proximoMantenimiento = "",

                        errorNombre = null,

                        errorTipo = null,

                        errorDescripcion = null,

                        errorEstado = null,

                        errorProximoMantenimiento =
                            null,

                        guardandoActivo =
                            false,

                        activoGuardado =
                            true
                    )
                }

            } catch (
                _: Exception
            ) {

                _uiState.update {

                    it.copy(

                        guardandoActivo =
                            false,

                        errorProximoMantenimiento =
                            "No fue posible guardar el activo"
                    )
                }
            }
        }
    }


    fun consumirActivoGuardado() {

        _uiState.update {

            it.copy(
                activoGuardado =
                    false
            )
        }
    }


    // =========================================================
    // READ - CARGAR ACTIVO POR ID
    // =========================================================

    fun cargarActivo(
        id: Int
    ) {

        _uiState.update {

            it.copy(

                activoSeleccionado =
                    null,

                cargandoDetalle =
                    true,

                errorEdicionActivo =
                    null,

                actualizacionActivoExitosa =
                    false
            )
        }


        viewModelScope.launch {

            val activo =
                repository
                    .obtenerActivoPorId(
                        id
                    )


            _uiState.update {

                it.copy(

                    activoSeleccionado =
                        activo,

                    cargandoDetalle =
                        false
                )
            }
        }
    }


    // =========================================================
    // UPDATE - ACTUALIZAR ACTIVO
    // =========================================================

    fun actualizarActivo(
        nombre: String,
        tipo: String,
        descripcion: String,
        estado: String,
        proximoMantenimiento: String
    ) {

        val activoActual =
            _uiState.value
                .activoSeleccionado
                ?: return


        if (
            nombre.isBlank() ||
            tipo.isBlank() ||
            descripcion.isBlank() ||
            estado.isBlank() ||
            proximoMantenimiento.isBlank()
        ) {

            _uiState.update {

                it.copy(
                    errorEdicionActivo =
                        "Complete todos los campos"
                )
            }

            return
        }


        if (
            !esFechaValida(
                proximoMantenimiento
            )
        ) {

            _uiState.update {

                it.copy(
                    errorEdicionActivo =
                        "Ingrese una fecha válida en formato dd/MM/yyyy"
                )
            }

            return
        }


        val activoActualizado =
            activoActual.copy(

                nombre =
                    nombre.trim(),

                tipo =
                    tipo.trim(),

                descripcion =
                    descripcion.trim(),

                estado =
                    estado.trim(),

                proximoMantenimiento =
                    proximoMantenimiento.trim()
            )


        _uiState.update {

            it.copy(

                guardandoActivo =
                    true,

                errorEdicionActivo =
                    null,

                actualizacionActivoExitosa =
                    false
            )
        }


        viewModelScope.launch {

            try {

                repository
                    .actualizarActivo(
                        activoActualizado
                    )


                _uiState.update {

                    it.copy(

                        activoSeleccionado =
                            activoActualizado,

                        guardandoActivo =
                            false,

                        errorEdicionActivo =
                            null,

                        actualizacionActivoExitosa =
                            true
                    )
                }

            } catch (
                _: Exception
            ) {

                _uiState.update {

                    it.copy(

                        guardandoActivo =
                            false,

                        errorEdicionActivo =
                            "No fue posible actualizar el activo"
                    )
                }
            }
        }
    }


    fun limpiarErrorEdicionActivo() {

        _uiState.update {

            it.copy(
                errorEdicionActivo =
                    null
            )
        }
    }


    fun consumirActualizacionActivo() {

        _uiState.update {

            it.copy(
                actualizacionActivoExitosa =
                    false
            )
        }
    }


    // =========================================================
    // DELETE - ELIMINAR ACTIVO
    // =========================================================

    fun eliminarActivo(
        activo: Activo
    ) {

        viewModelScope.launch {

            repository
                .eliminarActivo(
                    activo
                )


            if (
                _uiState.value
                    .activoSeleccionado
                    ?.id == activo.id
            ) {

                _uiState.update {

                    it.copy(
                        activoSeleccionado =
                            null
                    )
                }
            }
        }
    }


    // =========================================================
    // FUNCIONALIDAD PROPIA
    // BÚSQUEDA
    // =========================================================

    fun cambiarBusqueda(
        nuevaBusqueda: String
    ) {

        _uiState.update {
                estadoActual ->

            estadoActual.copy(

                busqueda =
                    nuevaBusqueda,

                activosFiltrados =
                    filtrarActivos(

                        activos =
                            estadoActual.activos,

                        busqueda =
                            nuevaBusqueda,

                        filtroEstado =
                            estadoActual.filtroEstado
                    )
            )
        }
    }


    // =========================================================
    // FUNCIONALIDAD PROPIA
    // FILTRO POR ESTADO
    // =========================================================

    fun cambiarFiltroEstado(
        nuevoFiltro: String
    ) {

        _uiState.update {
                estadoActual ->

            estadoActual.copy(

                filtroEstado =
                    nuevoFiltro,

                activosFiltrados =
                    filtrarActivos(

                        activos =
                            estadoActual.activos,

                        busqueda =
                            estadoActual.busqueda,

                        filtroEstado =
                            nuevoFiltro
                    )
            )
        }
    }


    private fun filtrarActivos(
        activos: List<Activo>,
        busqueda: String,
        filtroEstado: String
    ): List<Activo> {

        return activos.filter {
                activo ->


            val coincideBusqueda =

                busqueda.isBlank() ||

                        activo.nombre.contains(
                            busqueda,
                            ignoreCase = true
                        ) ||

                        activo.tipo.contains(
                            busqueda,
                            ignoreCase = true
                        )


            val coincideEstado =

                filtroEstado ==
                        "Todos" ||

                        activo.estado.equals(
                            filtroEstado,
                            ignoreCase = true
                        )


            coincideBusqueda &&
                    coincideEstado
        }
    }


    // =========================================================
    // FORMULARIO DE MANTENIMIENTO
    // =========================================================

    fun cambiarMantenimientoDescripcion(
        nuevaDescripcion: String
    ) {

        _uiState.update {

            it.copy(

                mantenimientoDescripcion =
                    nuevaDescripcion,

                errorMantenimientoDescripcion =
                    null
            )
        }
    }


    fun cambiarMantenimientoFecha(
        nuevaFecha: String
    ) {

        _uiState.update {

            it.copy(

                mantenimientoFecha =
                    nuevaFecha,

                errorMantenimientoFecha =
                    null
            )
        }
    }


    fun cambiarMantenimientoObservaciones(
        nuevasObservaciones: String
    ) {

        _uiState.update {

            it.copy(
                mantenimientoObservaciones =
                    nuevasObservaciones
            )
        }
    }


    fun cambiarMantenimientoProximaFecha(
        nuevaFecha: String
    ) {

        _uiState.update {

            it.copy(

                mantenimientoProximaFecha =
                    nuevaFecha,

                errorMantenimientoProximaFecha =
                    null
            )
        }
    }


    // =========================================================
    // CREATE - REGISTRAR MANTENIMIENTO
    // =========================================================

    fun registrarMantenimiento(
        activoId: Int
    ) {

        val estadoActual =
            _uiState.value


        val errorDescripcion =

            if (
                estadoActual
                    .mantenimientoDescripcion
                    .isBlank()
            ) {

                "Ingrese una descripción"

            } else {

                null
            }


        val errorFecha =

            when {

                estadoActual
                    .mantenimientoFecha
                    .isBlank() -> {

                    "Ingrese la fecha del mantenimiento"
                }


                !esFechaValida(
                    estadoActual
                        .mantenimientoFecha
                ) -> {

                    "Ingrese una fecha válida en formato dd/MM/yyyy"
                }


                else -> {

                    null
                }
            }


        val errorProximaFecha =

            when {

                estadoActual
                    .mantenimientoProximaFecha
                    .isBlank() -> {

                    null
                }


                !esFechaValida(
                    estadoActual
                        .mantenimientoProximaFecha
                ) -> {

                    "Ingrese una fecha válida en formato dd/MM/yyyy"
                }


                else -> {

                    null
                }
            }


        _uiState.update {

            it.copy(

                errorMantenimientoDescripcion =
                    errorDescripcion,

                errorMantenimientoFecha =
                    errorFecha,

                errorMantenimientoProximaFecha =
                    errorProximaFecha
            )
        }


        val formularioValido =

            errorDescripcion == null &&
                    errorFecha == null &&
                    errorProximaFecha == null


        if (
            !formularioValido
        ) {

            return
        }


        val mantenimiento =
            Mantenimiento(

                activoId =
                    activoId,

                descripcion =
                    estadoActual
                        .mantenimientoDescripcion
                        .trim(),

                fecha =
                    estadoActual
                        .mantenimientoFecha
                        .trim(),

                observaciones =
                    estadoActual
                        .mantenimientoObservaciones
                        .trim(),

                proximaFecha =
                    estadoActual
                        .mantenimientoProximaFecha
                        .trim()
            )


        _uiState.update {

            it.copy(

                guardandoMantenimiento =
                    true,

                mantenimientoGuardado =
                    false
            )
        }


        viewModelScope.launch {

            try {

                repository
                    .insertarMantenimiento(
                        mantenimiento
                    )


                if (
                    estadoActual
                        .mantenimientoProximaFecha
                        .isNotBlank()
                ) {

                    val activo =
                        repository
                            .obtenerActivoPorId(
                                activoId
                            )


                    activo?.let {

                        val activoActualizado =
                            it.copy(

                                proximoMantenimiento =
                                    estadoActual
                                        .mantenimientoProximaFecha
                                        .trim()
                            )


                        repository
                            .actualizarActivo(
                                activoActualizado
                            )


                        _uiState.update {
                                estado ->

                            estado.copy(
                                activoSeleccionado =
                                    activoActualizado
                            )
                        }
                    }
                }


                _uiState.update {

                    it.copy(

                        mantenimientoDescripcion =
                            "",

                        mantenimientoFecha =
                            "",

                        mantenimientoObservaciones =
                            "",

                        mantenimientoProximaFecha =
                            "",

                        errorMantenimientoDescripcion =
                            null,

                        errorMantenimientoFecha =
                            null,

                        errorMantenimientoProximaFecha =
                            null,

                        guardandoMantenimiento =
                            false,

                        mantenimientoGuardado =
                            true
                    )
                }

            } catch (
                _: Exception
            ) {

                _uiState.update {

                    it.copy(

                        guardandoMantenimiento =
                            false,

                        errorMantenimientoDescripcion =
                            "No fue posible guardar el mantenimiento"
                    )
                }
            }
        }
    }


    fun consumirMantenimientoGuardado() {

        _uiState.update {

            it.copy(
                mantenimientoGuardado =
                    false
            )
        }
    }


    // =========================================================
    // READ - HISTORIAL DEL ACTIVO
    // =========================================================

    fun observarMantenimientos(
        activoId: Int
    ) {

        mantenimientosJob?.cancel()


        _uiState.update {

            it.copy(
                mantenimientos =
                    emptyList()
            )
        }


        mantenimientosJob =
            viewModelScope.launch {

                repository
                    .obtenerMantenimientosPorActivo(
                        activoId
                    )
                    .collect {
                            lista ->

                        _uiState.update {

                            it.copy(
                                mantenimientos =
                                    lista
                            )
                        }
                    }
            }
    }


    // =========================================================
    // UPDATE - MANTENIMIENTO
    // =========================================================

    fun actualizarMantenimiento(
        mantenimiento: Mantenimiento
    ) {

        viewModelScope.launch {

            repository
                .actualizarMantenimiento(
                    mantenimiento
                )
        }
    }


    // =========================================================
    // DELETE - MANTENIMIENTO
    // =========================================================

    fun eliminarMantenimiento(
        mantenimiento: Mantenimiento
    ) {

        viewModelScope.launch {

            repository
                .eliminarMantenimiento(
                    mantenimiento
                )
        }
    }


    // =========================================================
    // VALIDACIÓN DE FECHAS
    // =========================================================

    private fun esFechaValida(
        fecha: String
    ): Boolean {

        val valor =
            fecha.trim()


        if (
            !Regex(
                """^\d{2}/\d{2}/\d{4}$"""
            ).matches(
                valor
            )
        ) {

            return false
        }


        return try {

            val formato =
                SimpleDateFormat(
                    "dd/MM/yyyy",
                    Locale.getDefault()
                ).apply {

                    isLenient =
                        false
                }


            formato.parse(
                valor
            )


            true

        } catch (
            _: ParseException
        ) {

            false
        }
    }
}