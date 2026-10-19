package com.example.sippina_aplicacion.view.reportes

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sippina_aplicacion.R
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme
import com.example.sippina_aplicacion.view.PantallasReporte.BarraDePasos
import com.example.sippina_aplicacion.view.PantallasReporte.EncabezadoReporte
import com.example.sippina_aplicacion.viewmodel.ReporteVM
import java.util.Locale
import kotlin.math.roundToInt

/**
 * Pantalla de captura de los detalles del incidente (Paso 2 del flujo de generación de reporte).
 *
 * Permite al usuario especificar el horario aproximado de los hechos mediante un deslizador ([Slider]),
 * ajustar la cantidad de menores involucrados, seleccionar si hubo un adulto presente ("Sí", "No", "No sé")
 * y redactar la descripción narrativa de los hechos.
 *
 * Incluye validación del campo de descripción obligatoria y gestiona la navegación con [BackHandler].
 *
 * @param viewModel Instancia de [ReporteVM] que mantiene el estado global del reporte.
 * @param modifier [Modifier] opcional para aplicar modificaciones de diseño al contenedor principal.
 * @param onSiguiente Callback ejecutado tras validar con éxito los datos para avanzar al paso del registro del menor.
 * @param onAtras Callback para regresar al paso anterior (Ubicación).
 */
@Composable
fun Detalles(
    viewModel: ReporteVM = viewModel(),
    modifier: Modifier = Modifier,
    onSiguiente: () -> Unit = {},
    onAtras: () -> Unit = {}
) {

    val state by viewModel.uiState.collectAsState()

    //Este es para el manejo de boton de retroceso
    BackHandler() {
        onAtras()
    }

    val colorMorado = Color(0xFF381DC3)
    val colorFondo = Color(0xFFEAF4FC)
    val colorFondoBotonSiguiente = Color(0xFFD3E4F4)
    val colorTextoBotonSiguiente = Color(0xFF3B719F)
    val colorFondoBotonAtras = Color(0xFFE2E2E2)
    val colorTextoBotonAtras = Color(0xFF5A5A5A)
    val colorBarra = Color(0xFF3C8DC6)

    var horaSlider by remember {
        mutableFloatStateOf(12f)
    }

    var mostrarError by remember {
        mutableStateOf(false)
    }

    val tieneError = mostrarError &&
            state.descripcionHechos.trim().isEmpty()

    val appContext = LocalContext.current

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
            EncabezadoReporte(onGuardarBorrador = {viewModel.guardarBorrador()
                Toast.makeText(appContext, "Borrador guardado exitosamente", Toast.LENGTH_SHORT).show()})

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Cuéntanos ¿qué pasó?",
                color = colorMorado,
                fontSize = 30.sp,
                lineHeight = 35.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            BarraDePasos(pasoActual = 1)

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Horario aprox.",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorMorado
                    )
                    Box(
                        modifier = Modifier.background(colorMorado, shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = formatearHora(horaSlider),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Slider(
                    value = horaSlider,
                    onValueChange = { nuevoValor ->
                        val horaRedondeada = (nuevoValor * 2).roundToInt() / 2f
                        horaSlider = horaRedondeada
                        viewModel.setHorarioAprox(formatearHora(horaRedondeada))
                    },

                    valueRange = 0f..24f,
                    colors = SliderDefaults.colors(
                        thumbColor = colorMorado,
                        activeTrackColor = colorBarra,
                        inactiveTrackColor = colorMorado
                    )
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),

                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "00:00",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "12:00",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "24:00",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Cantidad de menores involucrados",
                    fontSize = 18.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorMorado
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedCard(
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.5.dp, colorMorado),
                        colors = CardDefaults.outlinedCardColors(
                            containerColor = Color.White
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {

                        Box(
                            modifier = Modifier.fillMaxSize(),

                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text(
                                text = state.cantidadMenores.toString(),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = colorMorado,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }

                    OutlinedButton(
                        onClick = { viewModel.cambiarCantidadMenores(1) },
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.5.dp, colorMorado),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Aumentar cantidad",
                            tint = colorMorado,
                            modifier = Modifier.size(26.dp)
                        )
                    }

                    OutlinedButton(
                        onClick = { viewModel.cambiarCantidadMenores(-1) },
                        enabled = state.cantidadMenores > 1,
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.5.dp, colorMorado),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "Disminuir cantidad",
                            tint = if (state.cantidadMenores > 1) {
                                colorMorado
                            } else {
                                Color.Gray
                            },
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "¿Estaba algún adulto involucrado?",
                    fontSize = 18.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorMorado
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    val opciones = listOf(
                        "Sí",
                        "No",
                        "No sé"
                    )
                    opciones.forEach { opcion ->
                        val seleccionado =
                            state.adultoInvolucrados == opcion

                        OutlinedButton(
                            onClick = { viewModel.setAdultoInvolucrado(opcion) },
                            shape = RoundedCornerShape(10.dp),
                            border = if (seleccionado) {
                                null
                            } else {
                                BorderStroke(1.5.dp, colorMorado) },
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
                            ),
                            contentPadding = PaddingValues(horizontal = 4.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                        ) {

                            Text(
                                text = opcion,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Describe lo que viste",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (tieneError) {
                        Color.Red
                    } else {
                        colorMorado
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                /**
                 * Descripción con límite de 100 caracteres y contador en la esquina inferior.
                 * El estado continúa gestionándose en ReporteVM.
                 */
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = state.descripcionHechos,
                        onValueChange = { texto ->
                            viewModel.setDescripcionHechos(texto.take(100))
                            if (texto.trim().isNotBlank()) {
                                mostrarError = false
                            }
                        },
                        isError = tieneError,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 165.dp),
                        placeholder = {
                            Text(
                                text = "Escribe aquí los detalles"
                            )
                        },

                        trailingIcon = {
                            if (tieneError) {
                                Icon(
                                    imageVector = Icons.Default.Error,
                                    contentDescription = "Campo requerido",
                                    tint = Color.Red
                                )
                            }
                        },

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
                            unfocusedBorderColor = colorMorado,
                            errorBorderColor = Color.Red,
                            errorContainerColor = Color(0xFFFFF5F5),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        )
                    )
                    Text(
                        text = "${state.descripcionHechos.length}/100 caracteres",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 16.dp, bottom = if (tieneError) 42.dp else 12.dp)
                    )
                }
            }
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
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorFondoBotonAtras,
                    contentColor = colorTextoBotonAtras),
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
                onClick = {
                    if (
                        state.descripcionHechos
                            .trim()
                            .isNotBlank()
                    ) {
                        mostrarError = false
                        viewModel.irAlPrimerMenor()
                        onSiguiente()
                    } else {
                        mostrarError = true
                    }
                },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorFondoBotonSiguiente,
                    contentColor = colorTextoBotonSiguiente),
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            ) {
                Text(
                    text = "Siguiente",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

/**
 * Convierte un valor de hora representado como número flotante a una cadena de texto en formato `HH:mm`.
 *
 * @param valor Valor en coma flotante entre 0.0 y 24.0 que representa las horas y fracción de minutos.
 * @return Cadena de texto con la hora formateada (ej. `"12:30"`).
 */
private fun formatearHora(valor: Float): String {
    val horas = valor.toInt()
    val minutos = ((valor - horas) * 60).toInt()
    return String.format(
        Locale.getDefault(),
        "%02d:%02d",
        horas,
        minutos
    )
}

/**
 * Previsualización de la pantalla [Detalles] en el entorno de desarrollo de Android Studio.
 */
@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Donde Ocurrió"
)
@Composable
fun DondeOcurrioPreview() {
    SIPPINAAplicacionTheme {
        Detalles()
    }
}
