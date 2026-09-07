package com.example.sippina_aplicacion.view.PantallasReporte

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sippina_aplicacion.R
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme
import com.example.sippina_aplicacion.viewmodel.ReporteVM

@Composable
fun PrivacidadSeguimiento(
    viewModel: ReporteVM = viewModel(), modifier: Modifier = Modifier,
    onSiguiente: () -> Unit = { },
    onAtras: () -> Unit = { }
) {
    var opcionSeleccionada by remember { mutableStateOf("anonimo") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Image(
            painter = painterResource(id = R.drawable.sipinnalogosinfondo),
            contentDescription = "Logo Sippina",
            modifier = Modifier.size(120.dp))
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Privacidad y Seguimiento",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Blue
        )
        Spacer(modifier = Modifier.height(14.dp))
        BarraDePasos(pasoActual = 4)
        Spacer(modifier = Modifier.height(24.dp))

        TarjetaOpcionPrivacidad(
            titulo = "Reporte anónimo",
            descripcion = "No guardaremos tus datos personales. Podrás consultar tu reporte con el folio y código",
            icon = Icons.Default.Person,
            seleccionado = opcionSeleccionada == "anonimo",
            onClick = { opcionSeleccionada = "anonimo" }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TarjetaOpcionPrivacidad(
            titulo = "Me interesa recibir actualizaciones",
            descripcion = "Te pediremos tu número para enviarte el seguimiento y actualizaciones.",
            icon = Icons.Default.Email,
            seleccionado = opcionSeleccionada == "actualizaciones",
            onClick = { opcionSeleccionada = "actualizaciones" }
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "Tus datos están protegidos y solo el equipo autorizado podrá verlos",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 12.dp))
        Spacer(modifier = Modifier.height(12.dp))


        val textoAviso = buildAnnotatedString {
            append("Si quieres saber más, da click ")
            withStyle(style = SpanStyle(
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            )
            ) {
                append("aquí")
            }
            append(" para nuestro aviso de privacidad")
        }
        Spacer(modifier = Modifier.width(100.dp))

        Text(
            text = textoAviso,
            fontSize = 12.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .clickable { /* Abrir enlace */ }
                .padding(horizontal = 16.dp),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(80.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween)
        {
            Button(onClick = {onAtras()}, colors = ButtonDefaults.buttonColors(Color.LightGray, Color.Blue), modifier = Modifier.width(150.dp)){
                Text(text = "Atrás", fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
            Button(onClick = {onSiguiente()},colors = ButtonDefaults.buttonColors(Color.LightGray, Color.Blue), modifier = Modifier.width(150.dp)){
                Text(text = "Siguiente ", fontSize = 13.sp, fontWeight = FontWeight.Bold)


            }


        }
        Spacer(modifier = Modifier.height(36.dp))














    }


}

@Composable
private fun TarjetaOpcionPrivacidad(
    titulo: String,
    descripcion: String,
    icon: ImageVector,
    seleccionado: Boolean,
    onClick: () -> Unit
) {
    OutlinedCard(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.5.dp, Color.Blue ),
        colors = CardDefaults.outlinedCardColors(
            containerColor = if (seleccionado) Color.LightGray else Color.LightGray
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = titulo,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = descripcion,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    lineHeight = 16.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Icon(
                imageVector = icon,
                contentDescription = titulo,
                tint = Color.Gray,
                modifier = Modifier.size(45.dp)
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Privacidad y Seguimiento"
)
@Composable
fun PrivacidadSeguimientoPreview() {
    SIPPINAAplicacionTheme {
        PrivacidadSeguimiento()
    }
}
