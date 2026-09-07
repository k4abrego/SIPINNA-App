package com.example.sippina_aplicacion.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sippina_aplicacion.viewmodel.ReporteVM

@Composable
fun PantallaInicio(
    modifier: Modifier = Modifier,
    viewModel: ReporteVM = viewModel(),
    accionBoton: () -> Unit = {}
) {
    val fondoDegradado = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFD3E7FF),
            Color(0xFFBCA7E8)
        )
    )

    Box(
        modifier = modifier
            .background(fondoDegradado)
            .fillMaxSize()
    )
    {
        Column(modifier = modifier.padding(16.dp)) {
            Text(text = "¡Hola!")
            Text(
                text = "Gracias por ayudar a proteger a los niños , niñas y adolescentes",
                textAlign = TextAlign.Left
            )

            ElevatedButton(
                onClick = { accionBoton() }
            ) {
                Text(text = "Generar un reporte")
            }
            ElevatedButton(onClick = { accionBoton() }) {
                Text(text = "¿Qué situaciones debo de reportar")
            }
            ElevatedButton(onClick = { accionBoton() }) {
                Text(text = "Conoce mas de SIPINNA")
            }
        }


    }


}


@Preview (showBackground = true)
@Composable
fun Pantalla.PantallaInicio