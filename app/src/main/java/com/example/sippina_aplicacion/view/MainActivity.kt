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
import androidx.navigation.compose.rememberNavController
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost

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
            PantallaInicio()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SIPPINAAplicacionTheme {

    }
}