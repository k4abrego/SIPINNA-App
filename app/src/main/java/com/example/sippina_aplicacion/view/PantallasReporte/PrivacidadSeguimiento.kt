package com.example.sippina_aplicacion.view.PantallasReporte

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sippina_aplicacion.R
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme
import com.example.sippina_aplicacion.viewmodel.ReporteVM

@Composable
fun PrivacidadSeguimiento(
    viewModel: ReporteVM = viewModel(),
    modifier: Modifier = Modifier,
    onSiguiente: () -> Unit = {},
    onAtras: () -> Unit = {},
    onAvisoPrivacidad: () -> Unit = {}
) {
    val colorMorado = Color(0xFF381DC3)
    val colorFondo = Color(0xFFEAF4FC)
    val colorFondoBotonSiguiente = Color(0xFFD3E4F4)
    val colorTextoBotonSiguiente = Color(0xFF3B719F)

    val colorFondoBotonAtras = Color(0xFFE2E2E2)
    val colorTextoBotonAtras = Color(0xFF5A5A5A)


    val state by viewModel.uiState.collectAsState()
    val opcionSeleccionada = state.opcionPrivacidad

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorFondo)
            .imePadding()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(top = 8.dp, bottom = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.imagensippina),
                contentDescription = "Logo SIPINNA",
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Privacidad y seguimiento",
                fontSize = 28.sp,
                lineHeight = 34.sp,
                fontWeight = FontWeight.Bold,
                color = colorMorado,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            BarraDePasos(pasoActual = 4)

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Elige cómo deseas dar seguimiento a tu reporte",
                fontSize = 16.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorMorado,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(18.dp))

            TarjetaOpcionPrivacidad(
                titulo = "Reporte anónimo",
                descripcion = "Podrás consultar tu reporte con el folio y el código privado, sin proporcionar tus datos de contacto.",
                icon = Icons.Default.Person,
                seleccionado = opcionSeleccionada == "anonimo",
                onClick = {
                    viewModel.actualizarOpcionPrivacidad("anonimo")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            TarjetaOpcionPrivacidad(
                titulo = "Me interesa recibir actualizaciones",
                descripcion = "En esta modalidad podrás proporcionar un correo para recibir información sobre el seguimiento de tu reporte.",
                icon = Icons.Default.Email,
                seleccionado = opcionSeleccionada == "actualizaciones",
                onClick = {
                    viewModel.actualizarOpcionPrivacidad("actualizaciones")
                }
            )

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "Tu información debe ser tratada únicamente por el personal autorizado, conforme al aviso de privacidad.",
                fontSize = 15.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(14.dp))

            val textoAviso = buildAnnotatedString {
                append("Consulta nuestro ")
                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline,
                        color = colorMorado
                    )
                ) {
                    append("aviso de privacidad")
                }
            }
            Text(
                text = textoAviso,
                fontSize = 14.sp,
                lineHeight = 19.sp,
                color = Color.DarkGray,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onAvisoPrivacidad() }
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = onAtras,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = colorFondoBotonAtras, contentColor = colorTextoBotonAtras
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            ) {
                Text(
                    text = "Atrás",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = onSiguiente,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorFondoBotonSiguiente,
                    contentColor = colorTextoBotonSiguiente
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            ) {
                Text(
                    text = "Finalizar reporte",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
            }
        }
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
    val colorMorado = Color(0xFF381DC3)

    OutlinedCard(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = if (seleccionado) 2.dp else 1.5.dp,
            color = colorMorado
        ),
        colors = CardDefaults.outlinedCardColors(
            containerColor = if (seleccionado) {
                Color(0xFFE4DCFF)
            } else {
                Color.White
            }
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
                    fontSize = 17.sp,
                    lineHeight = 21.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorMorado
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = descripcion,
                    fontSize = 14.sp,
                    lineHeight = 19.sp,
                    color = Color.DarkGray
                )

                if (seleccionado) {
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Opción seleccionada",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorMorado
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = colorMorado,
                modifier = Modifier.size(38.dp)
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Privacidad y seguimiento"
)
@Composable
fun PrivacidadSeguimientoPreview() {
    SIPPINAAplicacionTheme {
        PrivacidadSeguimiento()
    }
}