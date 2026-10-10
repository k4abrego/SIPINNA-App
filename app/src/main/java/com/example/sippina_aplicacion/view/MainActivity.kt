package com.example.sippina_aplicacion.view

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme
import com.example.sippina_aplicacion.view.PantallasReporte.Detalles2
import com.example.sippina_aplicacion.view.PantallasReporte.DondeOcurrio
import com.example.sippina_aplicacion.view.PantallasReporte.Evidencia
import com.example.sippina_aplicacion.view.PantallasReporte.PrivacidadSeguimiento
import com.example.sippina_aplicacion.view.PantallasReporte.ReporteGenerado
import com.example.sippina_aplicacion.view.reportes.Detalles
import com.example.sippina_aplicacion.view.PerfilAutenticado.Borradores
import com.example.sippina_aplicacion.view.perfilusuario.RegistroNuevoPerfil
import com.example.sippina_aplicacion.view.SinAutenticacion.VistaPerfil1
import com.example.sippina_aplicacion.view.PerfilAutenticado.VistaPerfil2
import com.example.sippina_aplicacion.view.PerfilAutenticado.EditarPerfil
import com.example.sippina_aplicacion.view.PerfilAutenticado.VerListaReportes
import com.example.sippina_aplicacion.view.PerfilAutenticado.Notificaciones
import com.example.sippina_aplicacion.view.perfilusuario.BuscarReporteExistente
import com.example.sippina_aplicacion.view.perfilusuario.DetalleReporte
import com.example.sippina_aplicacion.view.VistasExtra.AvisoPrivacidad
import com.example.sippina_aplicacion.view.VistasExtra.ConoceSIPINNA
import com.example.sippina_aplicacion.view.VistasExtra.QueSituacionesReportar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sippina_aplicacion.view.perfilusuario.mapearEstatusAInt
import com.example.sippina_aplicacion.viewmodel.AuthVM
import com.example.sippina_aplicacion.viewmodel.ReporteVM

/**
 * Actividad principal de la aplicación SIPINNA.
 *
 * Configura el soporte de pantalla completa de borde a borde ([enableEdgeToEdge]) y establece
 * la jerarquía raíz de la interfaz de usuario basada en Jetpack Compose.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SIPPINAAplicacionTheme {
                AppPrincipal()
            }
        }
    }
}

/**
 * Componente Composable raíz que encapsula el andamiaje de la interfaz ([Scaffold])
 * y el controlador de navegación de la aplicación.
 *
 * @param modifier [Modifier] opcional para el diseño del contenedor principal.
 */
@Composable
fun AppPrincipal(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

/**
 * Define el grafo de navegación ([NavHost]) y las rutas de la aplicación SIPINNA.
 *
 * Conecta los distintos flujos de usuario incluyendo la pantalla de carga, inicio, asistente de reportes en 5 pasos,
 * autenticación, perfiles (invitado y autenticado), notificaciones, historial de reportes y vistas informativas extra.
 * Administra instancias compartidas de [ReporteVM] y [AuthVM] para la gestión de estados globales y llamadas de red.
 *
 * @param navController Controlador de navegación [NavHostController] encargado de la pila de pantallas.
 * @param modifier [Modifier] opcional para personalizar el contenedor de navegación.
 */
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val reporteViewModel: ReporteVM = viewModel()
    val authViewModel: AuthVM = viewModel()

    val authState by authViewModel.state.collectAsState()

    // Estado temporal hasta implementar la autenticación
    val usuarioAutenticado = authState.autenticado

    fun abrirDetalleReporte(folio: String) {
        navController.navigate(
            "${Pantalla.DetallesReporte.ruta}/${Uri.encode(folio)}"
        )
    }

    NavHost(
        navController = navController,
        startDestination = Pantalla.PantallaCarga.ruta,
        modifier = modifier
    ) {

        composable(Pantalla.PantallaCarga.ruta) {
            PantallaCarga(
                onNavegarAInicio = {
                    navController.navigate(Pantalla.PantallaInicio.ruta) {
                        popUpTo(Pantalla.PantallaCarga.ruta) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Pantalla.PantallaInicio.ruta) {
            LaunchedEffect(usuarioAutenticado) {
                if (usuarioAutenticado) {
                        reporteViewModel.obtenerMisReportesDelBackEnd()
                }
            }
            val listaReportes by reporteViewModel.misReportes.collectAsState()
            val ultimoReporte = listaReportes.lastOrNull()

            PantallaInicio(
                usuarioAutenticado = usuarioAutenticado,
                mostrarNotificaciones = usuarioAutenticado,
                ultimoReporteFolio = ultimoReporte?.folio,
                ultimoReporteEstatus = ultimoReporte?.estatus,
                onNavegarALogin = {
                    if (usuarioAutenticado) {
                        authViewModel.cerrarSesion()
                    } else {
                        navController.navigate(Pantalla.RegistroNuevoPerfil.ruta)
                    }
                },
                onCerrarSesion ={
                    authViewModel.cerrarSesion()
                },
                onNavegarAReporte = {
                    navController.navigate(Pantalla.DondeOcurrio.ruta)
                },
                onBuscarReporte = {
                    navController.navigate(Pantalla.BuscarReporteExis.ruta)
                },
                onNavegarAPerfil = {
                    if (usuarioAutenticado) {
                        navController.navigate(Pantalla.VistaPerfil2.ruta)
                    } else {
                        navController.navigate(Pantalla.VistaPerfil1.ruta)
                    }
                },
                onNavegarANotificaciones = {
                    if (usuarioAutenticado) {
                        navController.navigate(Pantalla.Notificaciones.ruta)
                    }
                },
                onQueSituacionesReportar = {
                    navController.navigate(Pantalla.QueSituacionesReportar.ruta)
                },
                onConoceSIPINNA = {
                    navController.navigate(Pantalla.ConoceSIPINNA.ruta)
                },
                onVerTodosReportes = {
                    navController.navigate(Pantalla.MisReportes.ruta)
                },
                onAbrirUltimoReporte = { folio ->
                    abrirDetalleReporte(folio)
                }
            )
        }

        // Definición única de RegistroNuevoPerfil
        composable(Pantalla.RegistroNuevoPerfil.ruta) {
            RegistroNuevoPerfil(
                authVM = authViewModel,
                onAtras = { navController.popBackStack() },
                onSiguiente = {
                    navController.navigate(Pantalla.PantallaInicio.ruta) {
                        popUpTo(Pantalla.RegistroNuevoPerfil.ruta) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Pantalla.QueSituacionesReportar.ruta) {
            QueSituacionesReportar(
                onAtras = { navController.popBackStack() }
            )
        }

        composable(Pantalla.ConoceSIPINNA.ruta) {
            ConoceSIPINNA(
                onAtras = { navController.popBackStack() }
            )
        }

        composable(Pantalla.AvisoPrivacidad.ruta) {
            AvisoPrivacidad(
                onAtras = { navController.popBackStack() },
                onEntendido = { navController.popBackStack() }
            )
        }

        composable(Pantalla.VistaPerfil1.ruta) {
            VistaPerfil1(
                onAtras = { navController.popBackStack() },
                onBorradores = { navController.navigate(Pantalla.Borradores.ruta) },
                onBuscarReporte = { navController.navigate(Pantalla.BuscarReporteExis.ruta) },
                onAvisoPrivacidad = { navController.navigate(Pantalla.AvisoPrivacidad.ruta) },
                onCrearCuenta = { navController.navigate(Pantalla.RegistroNuevoPerfil.ruta) }
            )
        }

        composable(route = Pantalla.VistaPerfil2.ruta) {
            VistaPerfil2(
                onAtras = {
                    navController.navigate(route = Pantalla.PantallaInicio.ruta) {
                        popUpTo(route = Pantalla.PantallaInicio.ruta) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                onBorradores = { navController.navigate(Pantalla.Borradores.ruta) },
                onMisReportes = { navController.navigate(Pantalla.MisReportes.ruta) },
                onNotificaciones = { navController.navigate(Pantalla.Notificaciones.ruta) },
                onAvisoPrivacidad = { navController.navigate(Pantalla.AvisoPrivacidad.ruta) },
                onEditarPerfil = { navController.navigate(Pantalla.EditarPerfil.ruta) },
                onEliminarCuenta = { /* Pendiente */ }
            )
        }

        composable(Pantalla.EditarPerfil.ruta) {
            EditarPerfil(
                onAtras = { navController.popBackStack() },
                onGuardar = { datosActualizados, nuevaContrasena -> /* Pendiente */ }
            )
        }

        composable(Pantalla.Borradores.ruta) {
            Borradores(
                onAtras = { navController.popBackStack() },
                onContinuar = { /* Pendiente */ },
                onEliminar = { /* Pendiente */ }
            )
        }

        composable(Pantalla.MisReportes.ruta) {
            LaunchedEffect(Unit) {
                reporteViewModel.obtenerMisReportesDelBackEnd()
            }
            val listaReportesState by reporteViewModel.misReportes.collectAsState()
            val cargandoState by reporteViewModel.cargando.collectAsState()

            VerListaReportes(
                listaReportes = listaReportesState,
                cargando = cargandoState,
                onAtras = { navController.popBackStack() },
                onReporteClick = { folio -> abrirDetalleReporte(folio) }
            )
        }

        composable(Pantalla.BuscarReporteExis.ruta) {
            val reporteEncontradoState by reporteViewModel.reporteEncontrado.collectAsState()
            val buscando by reporteViewModel.buscandoReporte.collectAsState()
            val errrorBusqueda by reporteViewModel.errorBusqueda.collectAsState()

            BuscarReporteExistente(
                onAtras = { navController.popBackStack() },
                onVerDetalleReporte = { folio -> abrirDetalleReporte(folio) },
                onBuscar = { folio, codigo -> reporteViewModel.buscarReportePorFolio(folio, codigo) },
                reporteEncontrado = reporteEncontradoState,
                buscando = buscando,
                errorBusqueda = errrorBusqueda
            )
        }

        // Definición única de DetallesReporte conecta lo que es el viewModel con el BackEnd
        composable(
            route = "${Pantalla.DetallesReporte.ruta}/{folio}",
            arguments = listOf(
                navArgument("folio") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val folioSeleccionado = backStackEntry.arguments?.getString("folio").orEmpty()

            LaunchedEffect(folioSeleccionado) {
                reporteViewModel.obtenerDetalleReportePorFolio(folioSeleccionado)
            }

            val reporteState by reporteViewModel.detalleReporte.collectAsState()
            val cargandoState by reporteViewModel.cargando.collectAsState()

            if (cargandoState || reporteState == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFF381DC3))
                }
            } else {
                reporteState?.let { reporte ->
                    DetalleReporte(
                        folio = reporte.folio,
                        fechaEnvio = reporte.fechaEnvio,
                        estadoActual = mapearEstatusAInt(reporte.estatus),
                        descripcion = reporte.descripcion,
                        direccion = reporte.direccion,
                        onAtras = { navController.popBackStack() }
                    )
                }
            }
        }

        composable(Pantalla.Notificaciones.ruta) {
            Notificaciones(
                onAtras = { navController.popBackStack() },
                onAbrirReporte = { folio -> abrirDetalleReporte(folio) }
            )
        }

        composable(Pantalla.DondeOcurrio.ruta) {
            DondeOcurrio(
                viewModel = reporteViewModel,
                onSiguiente = { navController.navigate(Pantalla.Detalles.ruta) },
                onAtras = { navController.popBackStack() }
            )
        }

        composable(Pantalla.Detalles.ruta) {
            Detalles(
                viewModel = reporteViewModel,
                onSiguiente = {
                    reporteViewModel.irAlPrimerMenor()
                    navController.navigate(Pantalla.Detalles2.ruta)
                },
                onAtras = { navController.popBackStack() }
            )
        }

        composable(Pantalla.Detalles2.ruta) {
            Detalles2(
                viewModel = reporteViewModel,
                onSiguiente = {
                    val hayOtroMenor = reporteViewModel.avanzarAlSiguienteMenor()
                    if (!hayOtroMenor) {
                        navController.navigate(Pantalla.Evidencia.ruta)
                    }
                },
                onAtras = {
                    val hayMenorAnterior = reporteViewModel.regresarAlMenorAnterior()
                    if (!hayMenorAnterior) {
                        navController.popBackStack()
                    }
                }
            )
        }

        composable(Pantalla.Evidencia.ruta) {
            Evidencia(
                viewModel = reporteViewModel,
                onSiguiente = { navController.navigate(Pantalla.PrivacidadSeguimiento.ruta) },
                onAtras = { navController.popBackStack() }
            )
        }

        composable(route = Pantalla.PrivacidadSeguimiento.ruta) {
            PrivacidadSeguimiento(
                viewModel = reporteViewModel,
                onAtras = { navController.popBackStack() },
                onSiguiente = { navController.navigate(Pantalla.ReporteGenerado.ruta) },
                onAvisoPrivacidad = { navController.navigate(Pantalla.AvisoPrivacidad.ruta) }
            )
        }

        composable(Pantalla.ReporteGenerado.ruta) {
            ReporteGenerado(
                viewModel = reporteViewModel,
            ) {
                navController.navigate(Pantalla.PantallaInicio.ruta) {
                    popUpTo(Pantalla.PantallaInicio.ruta) { inclusive = false }
                    launchSingleTop = true
                }
            }
        }
    }
}
