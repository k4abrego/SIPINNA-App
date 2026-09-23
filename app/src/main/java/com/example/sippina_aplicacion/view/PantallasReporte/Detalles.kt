package com.example.sippina_aplicacion.view.reportes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FloatingActionButtonDefaults.elevation
import androidx.compose.material3.FloatingActionButtonDefaults.shape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sippina_aplicacion.R
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme
import com.example.sippina_aplicacion.view.PantallasReporte.BarraDePasos
import com.example.sippina_aplicacion.viewmodel.ReporteVM
import java.util.Locale


@Composable
fun Detalles(
    viewModel: ReporteVM = viewModel(), modifier: Modifier = Modifier, onSiguiente: () -> Unit = {},
    onAtras: () -> Unit = {}
) {

    // Suscripción al estado global
    val state by viewModel.uiState.collectAsState()

    // Color para los bordes y botones
    val colorMorado = Color(0xFF3D27C9)
    val colorFondoBotonSiguiente = Color(0xFFD3E4F4)
    val colorTextoBotonSiguiente = Color(0xFF3B719F)
    val colorFondoBotonAtras = Color(0xFFE2E2E2)
    val colorTextoBotonAtras = Color(0xFF5A5A5A)
    val colorBarra = Color(0xFF3C8DC6)
    // Estado de acuerdo a los campos interactivos
    // Estados locales para los campos interactivos
    var horaSlider by remember { mutableFloatStateOf(12.00f) }
    var cantidadMenores by remember { mutableIntStateOf(1) }
    var descripcionText by remember { mutableStateOf("") }

    // Variables para la validación para asegurarse que la descripción no este vacia
    var mostrarError by remember { mutableStateOf(false) }
    val tieneError = mostrarError && state.descripcionHechos.trim().isEmpty()

    // Se agrego el verticalScroll + imePaddgin para que se desplaze de manera dinamica con el teclado desplegado,
    // y navigationBarsPadding para no ser tapado por los gestos del sistema
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 15.dp)
            .background(Color(0xFFEAF4FC))
            .imePadding()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,

        )
    {
        Image(
            painter = painterResource(id = R.drawable.imagensippina),
            contentDescription = "Logo Sippina",
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "Cuéntanos ¿qué pasó?",
            color = colorMorado,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(14.dp))

        BarraDePasos(pasoActual = 1)

        Spacer(modifier = Modifier.height(20.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Text(text = "Horario aprox.", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = colorMorado)
                Box(
                    modifier = Modifier
                        .background(colorMorado, shape = RoundedCornerShape(6.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = formatearHora(horaSlider),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )


                }

            }
            Slider(
                value = horaSlider,
                onValueChange = {
                    horaSlider = it
                    viewModel.setHorarioAprox(formatearHora(it))
                },
                valueRange = 0f..24f,
                colors = SliderDefaults.colors(
                    thumbColor = colorMorado,
                    activeTrackColor = colorBarra,
                    inactiveTrackColor = colorMorado
                )
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("00:00", fontSize = 11.sp, color = Color.Gray)
            Text("12:00", fontSize = 11.sp, color = Color.Gray)
            Text("24:00", fontSize = 11.sp, color = Color.Gray)

        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(modifier = modifier.fillMaxWidth()) {
            Text(
                text = "Cantidad de menores involucrados",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorMorado
            )

            Spacer(modifier = modifier.height(15.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            )
            {
                OutlinedCard(
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.5.dp, color = colorMorado),
                    colors = CardDefaults.outlinedCardColors(White),
                    modifier = Modifier
                        .weight(1f)
                        .height(34.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart)
                    {
                        Text(
                            text = state.cantidadMenores.toString(),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = colorMorado,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }

                }
                OutlinedButton(
                    onClick = { viewModel.cambiarCantidadMenores((1)) },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.size(34.dp),
                    border = BorderStroke(1.5.dp, colorMorado),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        tint = colorMorado,
                        contentDescription = "Aumentar",
                    )
                }
                OutlinedButton(
                    onClick = { viewModel.cambiarCantidadMenores(-1) },
                    enabled = cantidadMenores > 1,
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.5.dp, colorMorado),
                    modifier = Modifier.size(34.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Remove,
                        contentDescription = "Disminuir",
                        tint = colorMorado

                    )
                }


            }


        }

        Spacer(modifier = Modifier.height(25.dp))

        Column(modifier = Modifier.fillMaxWidth())
        {
            Text(
                text = "Estaba algún adulto involucrado?", fontSize = 18.sp,
                fontWeight = FontWeight.Bold, color = colorMorado
            )

        }
        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp))
        {
            val opciones = listOf("Sí", "No", "No sé")
            opciones.forEach { opcion ->
                val seleccionado = state.adultoInvolucrados == opcion
                OutlinedButton(
                    onClick = { viewModel.setAdultoInvolucrado(opcion) },
                    shape = RoundedCornerShape(10.dp),
                    border = if (seleccionado) null else BorderStroke(1.5.dp, colorMorado),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (seleccionado) colorMorado else Color.Transparent,
                        contentColor = if (seleccionado) Color.White else colorMorado
                    ), modifier = Modifier
                        .weight(1f)
                        .height(34.dp)
                ) {
                    Text(opcion, fontSize = 15.sp, fontWeight = FontWeight.Bold)

                }
            }


        }
        Spacer(modifier = Modifier.height(25.dp))

        Column(modifier = Modifier.fillMaxWidth())
        {
            Text(
                text = "Describe lo que viste", fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = if (tieneError) Color.Red else colorMorado
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = state.descripcionHechos,
                onValueChange = { texto ->
                    viewModel.setDescripcionHechos(texto)
                    if (texto.trim().isNotBlank()) mostrarError = false
                },
                isError = tieneError,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 110.dp),
                // Lógica para el error
                placeholder = { Text("Escribre aquí los detalles") },
                trailingIcon = {
                    if (tieneError) {
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = "Campo requerido",
                            tint = Color.Red
                        )
                    }
                },
                // Texto  de error
                supportingText = {
                    if (tieneError) {
                        Text(
                            text = "* Campo obligatorio para poder continuar",
                            color = Color.Red,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                },

                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorMorado,
                    unfocusedBorderColor = Color.LightGray,
                    errorBorderColor = Color.Red,               // Controla el borde rojo en error
                    errorContainerColor = Color(0xFFFFF5F5),    // Fondo rojizo en error
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { onAtras() },
                colors = ButtonDefaults.buttonColors(colorFondoBotonAtras, colorTextoBotonAtras),
                modifier = Modifier
                    .weight(1f)
                    .height(46.dp)
            ) {
                Text(text = "Atrás", fontSize = 15.sp, fontWeight = FontWeight.Bold)

            }
            // Lógica para no dejar avanzar al usuario si no lo completa
            Button(
                onClick = {
                    if (descripcionText.trim().isNotBlank()) {
                        onSiguiente()
                    } else {
                        mostrarError = true
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    colorFondoBotonSiguiente,
                    colorTextoBotonSiguiente
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(46.dp)
            ) {
                Text(text = "Siguiente", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }
        }


    }


}

private fun formatearHora(valor: Float): String {
    val horas = valor.toInt()
    val minutos = ((valor - horas) * 60).toInt()
    return String.format(Locale.getDefault(), "%02d:%02d", horas, minutos)
}


@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Donde Ocurrió - Diseño Exacto"
)
@Composable
fun DondeOcurrioPreview() {
    SIPPINAAplicacionTheme {
        Detalles()
    }
}