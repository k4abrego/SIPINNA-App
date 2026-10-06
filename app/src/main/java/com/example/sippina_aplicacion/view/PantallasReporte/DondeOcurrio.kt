package com.example.sippina_aplicacion.view.PantallasReporte

import android.annotation.SuppressLint

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.activity.compose.BackHandler
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sippina_aplicacion.R
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme
import com.example.sippina_aplicacion.viewmodel.ReporteVM
import androidx.compose.ui.platform.LocalContext

@SuppressLint("MissingPermission")
@Composable
fun DondeOcurrio(
    viewModel: ReporteVM = viewModel(),
    modifier: Modifier = Modifier,
    onSiguiente: () -> Unit = {},
    onAtras: () -> Unit = {}
) {
    val appContext = LocalContext.current

    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.iniciarlizarBorradorStorage(appContext)
    }

    var mostrarDialogoSalida by remember {
        mutableStateOf(false)
    }
    BackHandler {
        mostrarDialogoSalida = true
    }

    val colorMorado = Color(0xFF381DC3)
    val colorFondo = Color(0xFFEAF4FC)
    val colorFondoBotonSiguiente = Color(0xFFD3E4F4)
    val colorTextoBotonSiguiente = Color(0xFF3B719F)
    val colorFondoBotonAtras = Color(0xFFE2E2E2)
    val colorTextoBotonAtras = Color(0xFF5A5A5A)

    // OPCIÓN DE UBICACIÓN SELECCIONADA

    val opcionUbicacionActual = state.opcionUbicacion.ifEmpty {
        "Seleccionar en el mapa"
    }

    // CONTENEDOR PRINCIPAL

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorFondo)
            .imePadding()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        // Este contenido se puede desplazar por si existe un desfase en el tamaño del dispositivo

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

            // Parte del título
            Text(
                text = "¿Dónde ocurrió?",
                color = colorMorado,
                fontSize = 35.sp,
                lineHeight = 38.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))
            // Esta es la barrra de progreso de la pantalla

            BarraDePasos(pasoActual = 0)

            Spacer(modifier = Modifier.height(16.dp))

            // Son las instrucciones a seguir para el usuarui

            Text(
                text = "Puedes seleccionar en el mapa, usar tu ubicación actual o buscar una dirección",
                fontSize = 16.sp,
                lineHeight = 20.sp,
                color = colorMorado,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Esta es para la parte del mapa
            Card(
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEBEEF8)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Mapa",
                        color = Color.Gray,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Opciones de ubicación

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                val opciones = listOf(
                    "Usar mi ubicación actual",
                    "Seleccionar en el mapa",
                    "Buscar dirección"
                )

                opciones.forEach { opcion ->

                    val seleccionado =
                        opcionUbicacionActual == opcion

                    OutlinedButton(
                        onClick = {
                            viewModel.seleccionarOpcionUbicacion(
                                opcion
                            )
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),

                        shape = CircleShape,

                        border = if (seleccionado) {
                            null
                        } else {
                            BorderStroke(
                                width = 1.5.dp,
                                color = colorMorado
                            )
                        },

                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (seleccionado) {
                                colorMorado
                            } else {
                                Color.Transparent
                            },

                            contentColor = if (seleccionado) {
                                Color.White
                            } else {
                                colorMorado
                            }
                        )
                    ) {

                        Text(
                            text = opcion,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }


        // Estos son los botoenes de la parte inferior

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { mostrarDialogoSalida = true} ,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorFondoBotonAtras,
                    contentColor = colorTextoBotonAtras
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
                colors = ButtonDefaults.buttonColors(containerColor = colorFondoBotonSiguiente, contentColor = colorTextoBotonSiguiente),
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            ) {

                Text(
                    text = "Siguiente",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold)
            }
        }
    }


    if (mostrarDialogoSalida) {

        AlertDialog(
            onDismissRequest = {
                mostrarDialogoSalida = false
            },

            title = {
                Text(
                    text = "¿Seguro que deseas salir?",
                    color = colorMorado,
                    fontWeight = FontWeight.Bold
                )
            },

            text = {
                Text(
                    text = "Tu progreso quedará guardado en Borradores, " +
                            "disponibles en tu perfil en este dispositivo."
                )
            },

            confirmButton = {

                Button(
                    onClick = {
                        mostrarDialogoSalida = false
                        viewModel.guardarBorrador()
                        onAtras()
                    },

                    colors = ButtonDefaults.buttonColors(containerColor = colorMorado)
                ) {
                    Text(text = "Sí, guardar y salir")
                }
            },

            dismissButton = {
                OutlinedButton(
                    onClick = { mostrarDialogoSalida = false }
                ) {

                    Text(text = "No, continuar")
                }
            },
            shape = RoundedCornerShape(20.dp),
            containerColor = Color(0xFFEAF4FC)
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Donde Ocurrió - Diseño Exacto"
)
@Composable
fun DondeOcurrioPreview() {
    SIPPINAAplicacionTheme {
        DondeOcurrio()
    }
}