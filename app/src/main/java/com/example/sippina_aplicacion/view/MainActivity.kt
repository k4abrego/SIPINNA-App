package com.example.sippina_aplicacion.view

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sippina_aplicacion.viewmodel.ReporteVM

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

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val reporteViewModel: ReporteVM = viewModel()

    //estado temporal hasta implementar la autenticación.
    var cuentaCreadaDemo by rememberSaveable {
        mutableStateOf(false)
    }
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
            PantallaInicio(
                mostrarNotificaciones = cuentaCreadaDemo,
                onNavegarAReporte = {
                    navController.navigate(
                        route = Pantalla.DondeOcurrio.ruta
                    )
                },
                onBuscarReporte = {
                    navController.navigate(Pantalla.BuscarReporte.ruta)
                },

                onNavegarAPerfil = {
                    if (cuentaCreadaDemo) {
                        navController.navigate(
                            Pantalla.VistaPerfil2.ruta
                        )
                    } else {
                        navController.navigate(
                            Pantalla.VistaPerfil1.ruta
                        )
                    }
                },

                onNavegarANotificaciones = {
                    if (cuentaCreadaDemo) {
                        navController.navigate(
                            Pantalla.Notificaciones.ruta
                        )
                    }
                },

                onQueSituacionesReportar = {
                    navController.navigate(
                        Pantalla.QueSituacionesReportar.ruta
                    )
                },

                onConoceSIPINNA = {
                    navController.navigate(
                        Pantalla.ConoceSIPINNA.ruta
                    )
                }
                //callback del botón de EMERGENCIA
            )
        }
        composable(Pantalla.QueSituacionesReportar.ruta) {
            QueSituacionesReportar(
                onAtras = {
                    navController.popBackStack()
                }
            )
        }

        composable(Pantalla.ConoceSIPINNA.ruta) {
            ConoceSIPINNA(
                onAtras = {
                    navController.popBackStack()
                }
            )
        }

        composable(Pantalla.AvisoPrivacidad.ruta) {
            AvisoPrivacidad(
                onAtras = {
                    navController.popBackStack()
                },
                onEntendido = {
                    navController.popBackStack()
                }
            )
        }

        composable(Pantalla.VistaPerfil1.ruta) {
            VistaPerfil1(
                onAtras = {
                    navController.popBackStack()
                },
                onBorradores = {
                    navController.navigate(
                        Pantalla.Borradores.ruta
                    )
                },

                onBuscarReporte = {
                    navController.navigate(
                        Pantalla.BuscarReporteExis.ruta
                    )
                },

                onAvisoPrivacidad = {
                    navController.navigate(
                        Pantalla.AvisoPrivacidad.ruta
                    )
                },

                onCrearCuenta = {
                    navController.navigate(
                        Pantalla.RegistroNuevoPerfil.ruta
                    )
                }
            )
        }
        composable(Pantalla.RegistroNuevoPerfil.ruta) {
            RegistroNuevoPerfil(
                onAtras = {
                    navController.popBackStack()
                },

                onSiguiente = {
                    //simula una cuenta creada para probar las vistas
                    cuentaCreadaDemo = true
                    navController.navigate(Pantalla.VistaPerfil2.ruta
                    ) {
                        popUpTo(Pantalla.RegistroNuevoPerfil.ruta
                        ) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = Pantalla.VistaPerfil2.ruta) {
            VistaPerfil2(
                onAtras = {
                    navController.navigate(
                        route = Pantalla.PantallaInicio.ruta
                    ) {
                        popUpTo(
                            route = Pantalla.PantallaInicio.ruta
                        ) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                },
                onBorradores = {
                    navController.navigate(
                        Pantalla.Borradores.ruta
                    )
                },
                onMisReportes = {
                    navController.navigate(
                        Pantalla.MisReportes.ruta
                    )
                },

                onNotificaciones = {
                    navController.navigate(
                        Pantalla.Notificaciones.ruta
                    )
                },

                onAvisoPrivacidad = {
                    navController.navigate(
                        Pantalla.AvisoPrivacidad.ruta
                    )
                },

                onEditarPerfil = {
                    navController.navigate(
                        Pantalla.EditarPerfil.ruta
                    )
                },

                onEliminarCuenta = {
                    //pendiente
                }
            )
        }

        composable(Pantalla.EditarPerfil.ruta) {
            EditarPerfil(
                onAtras = {
                    navController.popBackStack()
                },
                onGuardar = { datosActualizados, nuevaContrasena ->
                    //pendiente: conectarlos con Model/ViewModel.
                }
            )
        }

        composable(Pantalla.Borradores.ruta) {
            Borradores(
                onAtras = {
                    navController.popBackStack()
                },
                onContinuar = {
                    //pendiente: identificar qué borrador se eligió
                },

                onEliminar = {
                    // pendiente identificar el borrador elegido
                }
            )
        }

        composable(Pantalla.MisReportes.ruta) {
            VerListaReportes(
                onAtras = {
                    navController.popBackStack()
                },
                onBuscarClick = {
                    navController.navigate(
                        Pantalla.BuscarReporte.ruta
                    )
                },
                onReporteClick = { folio ->
                    abrirDetalleReporte(folio)
                }
            )
        }

        composable(Pantalla.BuscarReporteExis.ruta) {
            BuscarReporteExistente(
                onAtras = {
                    navController.popBackStack()
                },
                onVerDetalleReporte = { folio ->
                    navController.navigate(
                        Pantalla.DetallesReporte.ruta
                    )
                }
            )
        }

        composable(
            route = "${Pantalla.DetallesReporte.ruta}/{folio}",
            arguments = listOf(
                navArgument("folio") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val folioSeleccionado =
                backStackEntry.arguments
                    ?.getString("folio")
                    .orEmpty()

            DetalleReporte(
                folio = folioSeleccionado,
                onAtras = {
                    navController.popBackStack()
                }
            )
        }

        composable(Pantalla.Notificaciones.ruta) {
            Notificaciones(
                onAtras = {
                    navController.popBackStack()
                },
                onAbrirReporte = { folio ->
                    abrirDetalleReporte(folio)
                }
            )
        }

        composable(Pantalla.DondeOcurrio.ruta) {
            DondeOcurrio(
                viewModel = reporteViewModel,
                onSiguiente = {
                    navController.navigate(
                        Pantalla.Detalles.ruta
                    )
                },
                onAtras = {
                    navController.popBackStack()
                }
            )
        }

        composable(Pantalla.Detalles.ruta) {
            Detalles(
                viewModel = reporteViewModel,
                onSiguiente = {
                    reporteViewModel.irAlPrimerMenor()
                    navController.navigate(
                        Pantalla.Detalles2.ruta
                    )
                },
                onAtras = { navController.popBackStack() }
            )
        }

        composable(Pantalla.Detalles2.ruta) {
            Detalles2(
                viewModel = reporteViewModel,
                onSiguiente = {
                    val hayOtroMenor =
                        reporteViewModel.avanzarAlSiguienteMenor()
                    if (!hayOtroMenor) {
                        navController.navigate(
                            Pantalla.Evidencia.ruta
                        )
                    }
                },
                onAtras = {
                    val hayMenorAnterior =
                        reporteViewModel.regresarAlMenorAnterior()
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
                    navController.navigate(
                        Pantalla.PrivacidadSeguimiento.ruta
                    )
                },
                onAtras = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = Pantalla.PrivacidadSeguimiento.ruta) {
            PrivacidadSeguimiento(
                viewModel = reporteViewModel,
                onAtras = {
                    navController.popBackStack() },
                onSiguiente = {
                    navController.navigate(Pantalla.ReporteGenerado.ruta) },
                onAvisoPrivacidad = {
                    navController.navigate(Pantalla.AvisoPrivacidad.ruta) }
            )
        }

        composable(Pantalla.ReporteGenerado.ruta) {
            ReporteGenerado(
                viewModel = reporteViewModel,
                onVolverInicio = {
                    navController.navigate(
                        Pantalla.PantallaInicio.ruta
                    ) {
                        popUpTo(
                            Pantalla.PantallaInicio.ruta
                        ) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
