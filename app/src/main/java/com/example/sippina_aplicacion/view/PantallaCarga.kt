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

@Preview (showBackground = true)
@Composable
fun Pantalla.PantallaCarga
