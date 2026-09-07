package com.example.sippina_aplicacion.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme
import com.example.sippina_aplicacion.view.PantallasReporte.Detalles2
import com.example.sippina_aplicacion.view.PantallasReporte.DondeOcurrio
import com.example.sippina_aplicacion.view.PantallasReporte.Evidencia
import com.example.sippina_aplicacion.view.PantallasReporte.PrivacidadSeguimiento
import com.example.sippina_aplicacion.view.PantallasReporte.ReporteGenerado
import com.example.sippina_aplicacion.view.reportes.Detalles

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
fun AppPrincipal(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
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

        composable(Pantalla.PantallaInicio.ruta) {
            PantallaInicio(
                onNavegarAReporte = {
                    navController.navigate(Pantalla.DondeOcurrio.ruta)
                }
            )
        }


        composable(Pantalla.DondeOcurrio.ruta) {
            DondeOcurrio(
                onSiguiente = {
                    navController.navigate(Pantalla.Detalles.ruta)
                },
                onAtras = {
                    navController.popBackStack()
                }
            )
        }


        composable(Pantalla.Detalles.ruta) {
            Detalles(
                onSiguiente = {
                    navController.navigate(Pantalla.Detalles2.ruta)
                },
                onAtras = {
                    navController.popBackStack()
                }
            )
        }


        composable(Pantalla.Detalles2.ruta) {
            Detalles2(
                onSiguiente = {
                    navController.navigate(Pantalla.Evidencia.ruta)
                },
                onAtras = {
                    navController.popBackStack()
                }
            )
        }


        composable(Pantalla.Evidencia.ruta) {
            Evidencia(
                onSiguiente = {
                    navController.navigate(Pantalla.PrivacidadSeguimiento.ruta)
                },
                onAtras = {
                    navController.popBackStack()
                }
            )
        }

        composable(Pantalla.PrivacidadSeguimiento.ruta) {
            PrivacidadSeguimiento(
                onSiguiente = {
                    navController.navigate(Pantalla.ReporteGenerado.ruta)
                },
                onAtras = {
                    navController.popBackStack()
                }
            )
        }


        composable(Pantalla.ReporteGenerado.ruta) {
            ReporteGenerado(
                onVolverInicio = {
                    navController.navigate(Pantalla.PantallaInicio.ruta) {
                        popUpTo(Pantalla.PantallaInicio.ruta) { inclusive = true }
                    }
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AppPrincipalPreview() {
    SIPPINAAplicacionTheme {
        AppPrincipal()
    }
}