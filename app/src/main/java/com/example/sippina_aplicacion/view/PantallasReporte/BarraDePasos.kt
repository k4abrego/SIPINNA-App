package com.example.sippina_aplicacion.view.PantallasReporte

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.FileUpload
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme

// Estos son colores que se van a usar
val ColorMoradoBarra = Color(0xFF3D27C9)
val ColorGrisBarra = Color(0xFF9c9c9c)

/**
 * Componente visual que muestra un indicador de progreso por pasos para el flujo de generación de un reporte.
 *
 * Renderiza 5 etapas consecutivas representadas mediante iconos interactivos (Ubicación, Descripción,
 * Menor involucrado, Evidencia y Privacidad) unidos por líneas conectoras. Los elementos cambian dinámicamente
 * entre el color activo ([ColorMoradoBarra]) y el inactivo ([ColorGrisBarra]) según el [pasoActual].
 *
 * @param pasoActual Índice de base cero (0 a 4) que representa la etapa activa en el formulario.
 * @param modifier [Modifier] opcional para personalizar el diseño o comportamiento del contenedor.
 */
@Composable
fun BarraDePasos(
    pasoActual: Int = 0,
    modifier: Modifier = Modifier
) {
    val iconos = listOf(
        Icons.Outlined.LocationOn,
        Icons.Outlined.Description,
        Icons.Outlined.Face,
        Icons.Outlined.FileUpload,
        Icons.Outlined.Shield
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        for (i in 0 until 5) {
            val estaActivo = i <= pasoActual

            // Círculo con el ícono del paso
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(if (estaActivo) ColorMoradoBarra else Color.White)
                    .border(
                        width = 2.dp,
                        color = if (estaActivo) ColorMoradoBarra else ColorGrisBarra,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = iconos[i],
                    contentDescription = "Paso ${i + 1}",
                    tint = if (estaActivo) Color.White else ColorGrisBarra,
                    modifier = Modifier.size(20.dp)
                )
            }

            if (i < 4) {
                val lineaCompletada = i < pasoActual
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(3.dp)
                        .background(if (lineaCompletada) ColorMoradoBarra else ColorGrisBarra)
                )
            }
        }
    }
}

/**
 * Previsualización del componente [BarraDePasos] en el entorno de desarrollo de Android Studio.
 */
@Preview (
    showBackground = true,
    showSystemUi = true
)
@Composable
fun BarraPreview(){
    SIPPINAAplicacionTheme{
        BarraDePasos()
    }
}
