package com.example.sippina_aplicacion.view.PantallasReporte

import android.R.attr.padding
import android.text.style.UnderlineSpan
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults.contentPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sippina_aplicacion.R
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme
import com.example.sippina_aplicacion.view.reportes.Detalles
import com.example.sippina_aplicacion.viewmodel.ReporteVM

/**
 * Pantalla para la captura de características descriptivas e identificación de los menores involucrados (Paso 3 del reporte).
 *
 * Permite seleccionar el rango de edad aproximado, género y escribir la descripción física detallada de cada menor.
 * Gestiona dinámicamente un flujo iterativo cuando hay múltiples menores especificados en el reporte (`cantidadMenores`),
 * permitiendo navegar secuencialmente entre cada menor ("Siguiente menor") o avanzar a la siguiente sección ("Siguiente")
 * al completar el último registro.
 *
 * Incluye validación de campos obligatorios y soporte para navegación con el botón físico de retroceso ([BackHandler]).
 *
 * @param viewModel Instancia de [ReporteVM] que gestiona el estado y la lista iterativa de menores.
 * @param modifier [Modifier] opcional para personalizar el contenedor principal.
 * @param onSiguiente Callback ejecutado cuando se han completado las descripciones de todos los menores para avanzar al paso de Evidencia.
 * @param onAtras Callback para regresar a la pantalla de Detalles previos cuando se está en el primer menor.
 */
@Composable
fun Detalles2(
    viewModel: ReporteVM = viewModel(),
    modifier: Modifier = Modifier,
    onSiguiente: () -> Unit = {},
    onAtras: () -> Unit = {}
) {
    val appContext = LocalContext.current
    val state by viewModel.uiState.collectAsState()

    val colorMorado = Color(0xFF381EBD)
    val colorFondoBotonSiguiente = Color(0xFFD3E4F4)
    val colorTextoBotonSiguiente = Color(0xFF3B719F)
    val colorFondoBotonAtras = Color(0xFFE2E2E2)
    val colorTextoBotonAtras = Color(0xFF5A5A5A)

    val opcionesEdad = listOf("0-4", "5-9", "10-14", "15-18")
    val opcionesGenero = listOf("Masculino", "Femenino", "Desconocido")
    val opcionesPeligro = listOf("Sí", "No", "No sé")

    // Asignaciones de valores desde el estado
    val edadActual = state.rangoEdad
    val generoActual = state.genero
    val peligroActual = state.peligroInmediato

    var mostrarError by remember { mutableStateOf(false) }
    val tieneError = mostrarError && state.descripcionFisica.trim().isEmpty()

    val manejarAtras = {
        mostrarError = false
        if (!viewModel.regresarAlMenorAnterior()) {
            onAtras()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.iniciarlizarBorradorStorage(appContext)
    }

    BackHandler { manejarAtras() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .background(Color(0xFFEAF4FC))
            .padding(horizontal = 24.dp)
            .padding(top = 15.dp, bottom = 43.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EncabezadoReporte(
                onGuardarBorrador = {
                    viewModel.guardarBorrador()
                    Toast.makeText(appContext, "Borrador guardado exitosamente", Toast.LENGTH_SHORT).show()
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Descripción del menor",
                color = colorMorado,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            BarraDePasos(pasoActual = 2)

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Descripción : Menor ${state.indiceMenorActual + 1} de ${state.cantidadMenores}",
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
                color = colorMorado,
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(25.dp))

            // EDAD APROXIMADA
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Edad aprox.",
                    color = colorMorado,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    opcionesEdad.forEach { rango ->
                        val seleccionado = edadActual == rango
                        OutlinedButton(
                            onClick = { viewModel.setRangoEdad(rango) },
                            shape = RoundedCornerShape(10.dp),
                            border = if (seleccionado) null else BorderStroke(1.5.dp, colorMorado),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = if (seleccionado) colorMorado else Color.Transparent,
                                contentColor = if (seleccionado) Color.White else colorMorado
                            ),
                            contentPadding = PaddingValues(horizontal = 4.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(34.dp)
                        ) {
                            Text(text = rango, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            // GÉNERO
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Género",
                    color = colorMorado,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    opcionesGenero.forEach { genero ->
                        val seleccionado = generoActual == genero
                        OutlinedButton(
                            onClick = { viewModel.setGenero(genero) },
                            shape = RoundedCornerShape(10.dp),
                            border = if (seleccionado) null else BorderStroke(1.5.dp, colorMorado),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = if (seleccionado) colorMorado else Color.Transparent,
                                contentColor = if (seleccionado) Color.White else colorMorado
                            ),
                            contentPadding = PaddingValues(horizontal = 4.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(34.dp)
                        ) {
                            Text(text = genero, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            // PELIGRO INMEDIATO
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "¿El menor se encuentra en peligro inmediato?",
                    color = colorMorado,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    opcionesPeligro.forEach { opcion ->
                        val seleccionado = peligroActual == opcion
                        OutlinedButton(
                            onClick = { viewModel.setPeligroInmediato(opcion) },
                            shape = RoundedCornerShape(10.dp),
                            border = if (seleccionado) null else BorderStroke(1.5.dp, colorMorado),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = if (seleccionado) colorMorado else Color.Transparent,
                                contentColor = if (seleccionado) Color.White else colorMorado
                            ),
                            contentPadding = PaddingValues(horizontal = 4.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(34.dp)
                        ) {
                            Text(text = opcion, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            // DESCRIPCIÓN FÍSICA
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Descripción Física",
                    color = if (tieneError) Color.Red else colorMorado,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = state.descripcionFisica,
                    onValueChange = { texto ->
                        viewModel.setDescripcionFisica(texto)
                        if (texto.trim().isNotBlank()) mostrarError = false
                    },
                    isError = tieneError,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 160.dp),
                    placeholder = { Text("Escribe aquí la descripción física") },
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
                        unfocusedBorderColor = Color.LightGray,
                        errorBorderColor = Color.Red,
                        errorContainerColor = Color(0xFFFFF5F5),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // BOTONES ATRÁS / SIGUIENTE
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { manejarAtras() },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorFondoBotonAtras,
                    contentColor = colorTextoBotonAtras
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(46.dp)
            ) {
                Text(text = "Atrás", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = {
                    if (state.descripcionFisica.trim().isNotBlank()) {
                        mostrarError = false
                        if (!viewModel.avanzarAlSiguienteMenor()) {
                            onSiguiente()
                        }
                    } else {
                        mostrarError = true
                    }
                },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorFondoBotonSiguiente,
                    contentColor = colorTextoBotonSiguiente
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(46.dp)
            ) {
                Text(
                    text = if (state.indiceMenorActual < state.cantidadMenores - 1) {
                        "Siguiente menor"
                    } else {
                        "Siguiente"
                    },
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Detalles 2 - Alineación Fija"
)
@Composable
fun Detalles2Preview() {
    SIPPINAAplicacionTheme {
        Detalles2()
    }
}