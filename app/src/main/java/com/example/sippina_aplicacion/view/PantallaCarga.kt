package com.example.sippina_aplicacion.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay

/**
 * Pantalla de carga inicial (Splash Screen) de la aplicación SIPINNA.
 *
 * Simula un tiempo de inicialización de 2 segundos mediante [LaunchedEffect] y una corrutina con [delay],
 * mostrando un fondo con degradado vertical y un indicador de progreso circular ([CircularProgressIndicator]).
 * Al finalizar el tiempo de espera, invoca automáticamente el callback [onNavegarAInicio].
 *
 * @param onNavegarAInicio Callback ejecutado al terminar la carga para navegar hacia la pantalla de inicio.
 * @param modifier [Modifier] opcional para personalizar el diseño del contenedor principal.
 */
@Composable
fun PantallaCarga(
    onNavegarAInicio: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Este es un efecto nada mas hecho de carga
    LaunchedEffect(Unit) {
        delay(2000L)
        onNavegarAInicio()
    }
    val fondoDegradado = Brush.verticalGradient(
        colors = listOf(
            // Mas o menos los colores del socioformador
            Color(0xFFD3E7FF),
            Color(0xFFBCA7E8)
        )
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(fondoDegradado),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = Color.White
        )
    }


}
//
//@Preview (showBackground = true)
//@Composable
//fun PantallaCarga
