package com.example.sippina_app.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sippina_app.ui.theme.SiPPINAAppTheme
import com.example.sippina_app.viewmodel.AplicacionSIPPINAVM
import com.example.sippina_app.viewmodel.EstadoAplicacionSIPPINA

class MainActivity : ComponentActivity() {
    private val viewModel: AplicacionSIPPINAVM by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SiPPINAAppTheme {
                AppPrincipal(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun AppPrincipal(viewModel: AplicacionSIPPINAVM, modifier: Modifier = Modifier) {

    // Creacion e inicializacion del controlador de navegación
    val navController = rememberNavController()

    Scaffold{ innerPadding ->
        AppNavHost(
            viewModel = viewModel,
            navController = navController,
            modifier = modifier.padding(innerPadding)
        )
    }
}


@Composable
fun AppNavHost(viewModel: AplicacionSIPPINAVM, navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Pantallas.RUTA_LOGO,
        modifier = modifier
    ) {
        // Pantalla de logo de inicio
        composable(Pantallas.RUTA_LOGO) {
            PantallaLogo(navController = navController)

        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SiPPINAAppTheme {
        PantallaLogo(navController = rememberNavController())

    }
}