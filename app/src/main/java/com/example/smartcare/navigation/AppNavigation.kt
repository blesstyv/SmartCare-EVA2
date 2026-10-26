package com.example.smartcare.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.smartcare.screens.ActivosScreen
import com.example.smartcare.screens.DetalleActivoScreen
import com.example.smartcare.screens.HistorialScreen
import com.example.smartcare.screens.InicioScreen
import com.example.smartcare.screens.LoginScreen
import com.example.smartcare.screens.MantenimientoScreen
import com.example.smartcare.screens.NuevoActivoScreen
import com.example.smartcare.screens.RegistroScreen
import com.example.smartcare.viewmodel.AuthViewModel
import com.example.smartcare.viewmodel.SmartCareViewModel

@Composable
fun AppNavigation(
    smartCareViewModel: SmartCareViewModel,
    authViewModel: AuthViewModel
) {

    val authState by
    authViewModel
        .uiState
        .collectAsStateWithLifecycle()


    /*
     * Si Firebase tiene un usuario autenticado,
     * mostramos la aplicación privada.
     *
     * Si no existe sesión,
     * mostramos Login / Registro.
     */

    if (authState.autenticado) {

        SmartCareNavigation(
            smartCareViewModel =
                smartCareViewModel,

            authViewModel =
                authViewModel,

            usuarioEmail =
                authState.usuarioEmail
        )

    } else {

        AuthNavigation(
            authViewModel =
                authViewModel
        )
    }
}


// =========================================================
// NAVEGACIÓN PÚBLICA
// LOGIN + REGISTRO
// =========================================================

@Composable
private fun AuthNavigation(
    authViewModel: AuthViewModel
) {

    val navController =
        rememberNavController()


    NavHost(
        navController =
            navController,

        startDestination =
            Rutas.LOGIN
    ) {


        // =========================
        // LOGIN
        // =========================

        composable(
            route =
                Rutas.LOGIN
        ) {

            LoginScreen(
                viewModel =
                    authViewModel,

                onIrRegistro = {

                    authViewModel
                        .limpiarCredenciales()

                    navController
                        .navigate(
                            Rutas.REGISTRO
                        )
                }
            )
        }


        // =========================
        // REGISTRO
        // =========================

        composable(
            route =
                Rutas.REGISTRO
        ) {

            RegistroScreen(
                viewModel =
                    authViewModel,

                onVolver = {

                    authViewModel
                        .limpiarCredenciales()

                    navController
                        .popBackStack()
                }
            )
        }
    }
}


// =========================================================
// NAVEGACIÓN PRIVADA DE SMARTCARE
// =========================================================

@Composable
private fun SmartCareNavigation(
    smartCareViewModel:
    SmartCareViewModel,

    authViewModel:
    AuthViewModel,

    usuarioEmail:
    String?
) {

    val navController =
        rememberNavController()


    NavHost(
        navController =
            navController,

        startDestination =
            Rutas.INICIO
    ) {


        // =================================================
        // INICIO
        // =================================================

        composable(
            route =
                Rutas.INICIO
        ) {

            InicioScreen(
                usuarioEmail =
                    usuarioEmail,

                onVerActivos = {

                    navController
                        .navigate(
                            Rutas.ACTIVOS
                        )
                },

                onNuevoActivo = {

                    navController
                        .navigate(
                            Rutas.NUEVO_ACTIVO
                        )
                },

                onCerrarSesion = {

                    authViewModel
                        .cerrarSesion()
                }
            )
        }


        // =================================================
        // LISTADO DE ACTIVOS
        // =================================================

        composable(
            route =
                Rutas.ACTIVOS
        ) {

            ActivosScreen(
                viewModel =
                    smartCareViewModel,

                onVerDetalle = { activoId ->

                    navController.navigate(
                        Rutas.detalleActivo(
                            activoId
                        )
                    )
                },

                onNuevoActivo = {

                    navController.navigate(
                        Rutas.NUEVO_ACTIVO
                    )
                },

                onVolver = {

                    navController
                        .popBackStack()
                }
            )
        }


        // =================================================
        // NUEVO ACTIVO
        // =================================================

        composable(
            route =
                Rutas.NUEVO_ACTIVO
        ) {

            NuevoActivoScreen(
                viewModel =
                    smartCareViewModel,

                onGuardado = {

                    /*
                     * Volvemos siempre al listado.
                     * Eliminamos las pantallas intermedias
                     * hasta Inicio y dejamos Activos arriba.
                     */

                    navController.navigate(
                        Rutas.ACTIVOS
                    ) {

                        popUpTo(
                            Rutas.INICIO
                        ) {
                            inclusive =
                                false
                        }

                        launchSingleTop =
                            true
                    }
                },

                onVolver = {

                    navController
                        .popBackStack()
                }
            )
        }


        // =================================================
        // DETALLE DEL ACTIVO
        // =================================================

        composable(
            route =
                Rutas.DETALLE_ACTIVO,

            arguments =
                listOf(

                    navArgument(
                        "activoId"
                    ) {

                        type =
                            NavType.IntType
                    }
                )

        ) { backStackEntry ->

            val activoId =
                backStackEntry
                    .arguments
                    ?.getInt(
                        "activoId"
                    )
                    ?: 0


            /*
             * Cuando entramos al detalle,
             * cargamos el activo desde Room.
             */

            LaunchedEffect(
                activoId
            ) {

                smartCareViewModel
                    .cargarActivo(
                        activoId
                    )
            }


            DetalleActivoScreen(
                viewModel =
                    smartCareViewModel,

                onRegistrarMantenimiento = {
                        id ->

                    navController
                        .navigate(
                            Rutas.mantenimiento(
                                id
                            )
                        )
                },

                onVerHistorial = {
                        id ->

                    navController
                        .navigate(
                            Rutas.historial(
                                id
                            )
                        )
                },

                onEliminado = {

                    navController.navigate(
                        Rutas.ACTIVOS
                    ) {

                        popUpTo(
                            Rutas.ACTIVOS
                        ) {
                            inclusive =
                                false
                        }

                        launchSingleTop =
                            true
                    }
                },

                onVolver = {

                    navController
                        .popBackStack()
                }
            )
        }


        // =================================================
        // REGISTRAR MANTENIMIENTO
        // =================================================

        composable(
            route =
                Rutas.MANTENIMIENTO,

            arguments =
                listOf(

                    navArgument(
                        "activoId"
                    ) {

                        type =
                            NavType.IntType
                    }
                )

        ) { backStackEntry ->

            val activoId =
                backStackEntry
                    .arguments
                    ?.getInt(
                        "activoId"
                    )
                    ?: 0


            /*
             * Recuperamos el activo para mostrar
             * su información en la pantalla.
             */

            LaunchedEffect(
                activoId
            ) {

                smartCareViewModel
                    .cargarActivo(
                        activoId
                    )
            }


            MantenimientoScreen(
                activoId =
                    activoId,

                viewModel =
                    smartCareViewModel,

                onGuardado = {

                    /*
                     * Al guardar regresamos
                     * al detalle del activo.
                     */

                    navController
                        .popBackStack()
                },

                onVolver = {

                    navController
                        .popBackStack()
                }
            )
        }


        // =================================================
        // HISTORIAL DEL ACTIVO
        // =================================================

        composable(
            route =
                Rutas.HISTORIAL,

            arguments =
                listOf(

                    navArgument(
                        "activoId"
                    ) {

                        type =
                            NavType.IntType
                    }
                )

        ) { backStackEntry ->

            val activoId =
                backStackEntry
                    .arguments
                    ?.getInt(
                        "activoId"
                    )
                    ?: 0


            /*
             * Necesitamos:
             *
             * 1. cargar el activo
             * 2. observar sus mantenimientos
             */

            LaunchedEffect(
                activoId
            ) {

                smartCareViewModel
                    .cargarActivo(
                        activoId
                    )

                smartCareViewModel
                    .observarMantenimientos(
                        activoId
                    )
            }


            HistorialScreen(
                viewModel =
                    smartCareViewModel,

                onVolver = {

                    navController
                        .popBackStack()
                }
            )
        }
    }
}