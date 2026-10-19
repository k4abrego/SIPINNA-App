package com.example.sippina_aplicacion.view

import android.content.Intent
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.example.sippina_aplicacion.view.PantallasReporte.VerListaReportes
import com.example.sippina_aplicacion.view.PerfilAutenticado.BorradoresScreen
import com.example.sippina_aplicacion.view.PerfilAutenticado.EditarPerfil
import com.example.sippina_aplicacion.view.PerfilAutenticado.Notificaciones
import com.example.sippina_aplicacion.view.PerfilAutenticado.VistaPerfil2
import com.example.sippina_aplicacion.view.SinAutenticacion.VistaPerfil1
import com.example.sippina_aplicacion.view.VistasExtra.AvisoPrivacidad
import com.example.sippina_aplicacion.view.VistasExtra.ConoceSIPINNA
import com.example.sippina_aplicacion.view.VistasExtra.QueSituacionesReportar
import com.example.sippina_aplicacion.view.perfilusuario.BuscarReporteExistente
import com.example.sippina_aplicacion.view.perfilusuario.DetalleReporte
import com.example.sippina_aplicacion.view.perfilusuario.PantallaInicioSesion
import com.example.sippina_aplicacion.view.perfilusuario.RegistroNuevoPerfil
import com.example.sippina_aplicacion.view.perfilusuario.mapearEstatusAInt
import com.example.sippina_aplicacion.view.reportes.Detalles
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
 * Conecta los distintos flujos de usuario incluyendo la pantalla de carga, inicio, asistente de reportes en 5 pasos,
 * autenticación, perfiles (invitado y autenticado), notificaciones, historial de reportes y vistas informativas extra.
 * Administra instancias compartidas de [ReporteVM] y [AuthVM] para la gestión de estados globales y llamadas de red.
 */
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val reporteViewModel: ReporteVM = viewModel()
    val authViewModel: AuthVM = viewModel()

    val authState by authViewModel.state.collectAsState()
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
            val context = LocalContext.current

            // 1. Pasa authState.token al consultar los reportes en el inicio
            LaunchedEffect(usuarioAutenticado) {
                if (usuarioAutenticado) {
                    reporteViewModel.obtenerMisReportesDelBackEnd(token = authState.token)
                }
            }
            val listaReportes by reporteViewModel.misReportes.collectAsState()
            val ultimoReporte = listaReportes.firstOrNull()

            PantallaInicio(
                usuarioAutenticado = usuarioAutenticado,
                mostrarNotificaciones = usuarioAutenticado,
                ultimoReporteFolio = ultimoReporte?.folio,
                ultimoReporteEstatus = ultimoReporte?.estatus,
                accionBoton = {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:55591484607")
                    }
                    context.startActivity(intent)
                },
                onNavegarAReporte = {
                    reporteViewModel.limpiarFormulario()
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

        // PANTALLA DE INICIO DE SESIÓN
        composable(Pantalla.PantallaInicioSesion.ruta) {
            PantallaInicioSesion(
                authVM = authViewModel,
                onAtras = { navController.popBackStack() },
                onRegistrarse = {
                    navController.navigate(Pantalla.RegistroNuevoPerfil.ruta)
                },
                onSiguiente = {
                    navController.navigate(Pantalla.PantallaInicio.ruta) {
                        popUpTo(Pantalla.PantallaInicioSesion.ruta) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Pantalla.RegistroNuevoPerfil.ruta) {
            RegistroNuevoPerfil(
                authVM = authViewModel,
                onAtras = { navController.popBackStack() },
                onSiguiente = {
                    navController.navigate(Pantalla.PantallaInicio.ruta) {
                        popUpTo(Pantalla.RegistroNuevoPerfil.ruta) { inclusive = true }
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
                onCrearCuenta = { navController.navigate(Pantalla.PantallaInicioSesion.ruta) },
                onIniciarSesion = { navController.navigate(Pantalla.PantallaInicioSesion.ruta) }
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
                onAvisoPrivacidad = { navController.navigate(Pantalla.AvisoPrivacidad.ruta) },
                onEditarPerfil = { navController.navigate(Pantalla.EditarPerfil.ruta) },
                onCerrarSesion = {
                    // AuthVM notifica al repositorio y limpia el estado local de sesión.
                    authViewModel.cerrarSesion()
                    // Vuelve al inicio sin permitir retroceder al perfil autenticado.
                    navController.navigate(Pantalla.PantallaInicio.ruta) {
                        popUpTo(Pantalla.PantallaInicio.ruta) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Pantalla.EditarPerfil.ruta) {
            EditarPerfil(
                onAtras = { navController.popBackStack() },
                onGuardar = { datosActualizados, nuevaContrasena -> /* Pendiente */ }
            )
        }

        composable(Pantalla.Borradores.ruta) {
            BorradoresScreen(
                viewModel = reporteViewModel,
                onAtras = { navController.popBackStack() },
                onContinuar = {
                    val paso = reporteViewModel.uiState.value.pasoActual
                    when (paso) {
                        0 -> navController.navigate(Pantalla.DondeOcurrio.ruta)
                        1 -> navController.navigate(Pantalla.Detalles.ruta)
                        2 -> navController.navigate(Pantalla.Detalles2.ruta)
                        3 -> navController.navigate(Pantalla.Evidencia.ruta)
                        4 -> navController.navigate(Pantalla.PrivacidadSeguimiento.ruta)
                        else -> navController.navigate(Pantalla.DondeOcurrio.ruta)
                    }
                }
            )
        }

        composable(Pantalla.MisReportes.ruta) {
            VerListaReportes(
                viewModel = reporteViewModel,
                token = authState.token,
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
                onBuscar = { folio, codigo ->
                    reporteViewModel.obtenerDetalleReportePorFolio(folio, codigo)
                },
                reporteEncontrado = reporteEncontradoState,
                buscando = buscando,
                errorBusqueda = errrorBusqueda
            )
        }

        composable(
            route = "${Pantalla.DetallesReporte.ruta}/{folio}",
            arguments = listOf(
                navArgument("folio") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val folioSeleccionado = backStackEntry.arguments?.getString("folio").orEmpty()

            LaunchedEffect(folioSeleccionado) {
                reporteViewModel.obtenerDetalleReportePorFolio(folioSeleccionado, token = authState.token)
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
                        latitud = reporte.latitud,
                        longitud = reporte.longitud,
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
                onSiguiente = {
                    reporteViewModel.siguientePaso()
                    navController.navigate(Pantalla.Detalles.ruta)
                },
                onAtras = { navController.popBackStack() }
            )
        }

        composable(Pantalla.Detalles.ruta) {
            Detalles(
                viewModel = reporteViewModel,
                onSiguiente = {
                    reporteViewModel.siguientePaso()
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
                        reporteViewModel.siguientePaso()
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
                onSiguiente = {
                    reporteViewModel.siguientePaso()
                    navController.navigate(Pantalla.PrivacidadSeguimiento.ruta)
                },
                onAtras = { navController.popBackStack() }
            )
        }

        composable(route = Pantalla.PrivacidadSeguimiento.ruta) {
            val context = LocalContext.current

            PrivacidadSeguimiento(
                viewModel = reporteViewModel,
                estaAutenticado = usuarioAutenticado,
                token = authState.token,
                onAtras = { navController.popBackStack() },
                onSiguiente = {
                    reporteViewModel.enviarReporteAlBackEnd(
                        context = context,
                        token = authState.token,
                        userId = authState.usuarioId?.toLongOrNull()
                    )
                    navController.navigate(Pantalla.ReporteGenerado.ruta)
                },
                onAvisoPrivacidad = { navController.navigate(Pantalla.AvisoPrivacidad.ruta) }
            )
        }

        composable(Pantalla.ReporteGenerado.ruta) {
            ReporteGenerado(
                viewModel = reporteViewModel,
                token = authState.token,
                userIdString = authState.usuarioId,
                onVolverInicio = {
                    navController.navigate(Pantalla.PantallaInicio.ruta) {
                        popUpTo(Pantalla.PantallaInicio.ruta) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}