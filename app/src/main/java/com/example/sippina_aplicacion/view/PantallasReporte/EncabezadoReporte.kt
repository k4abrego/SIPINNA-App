package com.example.sippina_aplicacion.view.PantallasReporte

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sippina_aplicacion.R

@Composable
fun EncabezadoReporte(
    onGuardarBorrador: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colorMorado = Color(0xFF381DC3)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Logo SIPINNA en la esquina superior izquierda
        Image(
            painter = painterResource(id = R.drawable.logorieti),
            contentDescription = "Logo SIPINNA",
            modifier = Modifier.size(120.dp)
        )

        OutlinedButton(
            onClick = onGuardarBorrador,
            shape = CircleShape,
            border = BorderStroke(1.5.dp, colorMorado),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = colorMorado
            )
        ) {
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Guardar",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = colorMorado
            )
        }
    }
}