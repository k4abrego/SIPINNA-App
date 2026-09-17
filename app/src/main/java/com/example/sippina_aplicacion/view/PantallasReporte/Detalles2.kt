package com.example.sippina_aplicacion.view.PantallasReporte

import android.R.attr.padding
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sippina_aplicacion.R
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme
import com.example.sippina_aplicacion.view.reportes.Detalles
import com.example.sippina_aplicacion.viewmodel.ReporteVM

@Composable
fun Detalles2(
    viewModel: ReporteVM = viewModel(),
    modifier: Modifier = Modifier,
    onSiguiente: () -> Unit = {},
    onAtras: () -> Unit = {}
) {
    // Escuchar el estado centralizado
    val state by viewModel.uiState.collectAsState()

    val colorMorado = Color(0xFF381EBD)
    val colorFondoBotonSiguiente = Color(0xFFD3E4F4)
    val colorTextoBotonSiguiente = Color(0xFF3B719F)
    val colorFondoBotonAtras = Color(0xFFE2E2E2)
    val colorTextoBotonAtras = Color(0xFF5A5A5A)

    val opcionesEdad = listOf("0-4", "5-9", "10-14", "15-18")
    val opcionesGenero = listOf("Masculino", "Femenino", "Desconocido")

    // Asignaciones por defecto
    val edadActual = state.rangoEdad.ifEmpty { "0-4" }
    val generoActual = state.genero.ifEmpty { "Masculino" }

    // Validación sobre el estado de la descripción física
    var mostrarError by remember { mutableStateOf(false) }
    val tieneError = mostrarError && state.descripcionFisica.trim().isEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .padding(top = 12.dp, bottom = 16.dp)
            .imePadding()
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.imagensippina),
                contentDescription = "Logo Sippina",
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Descripción del menor",
                color = colorMorado,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(14.dp))

            BarraDePasos(pasoActual = 2)

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Descripción : Menor 1",
                fontWeight = FontWeight.Bold,
                color = colorMorado,
                fontSize = 18.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(20.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Edad aprox",
                    color = colorMorado,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    opcionesEdad.forEach { rango ->
                        val seleccionado = edadActual == rango
                        OutlinedButton(
                            onClick = { viewModel.setRangoEdad(rango)},
                            shape = RoundedCornerShape(10.dp),
                            border = if (seleccionado) null else BorderStroke(1.5.dp, colorMorado),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = if (seleccionado) colorMorado else Color.Transparent,
                                contentColor = if (seleccionado) Color.White else colorMorado
                            ),
                            contentPadding = PaddingValues(horizontal = 2.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(34.dp)
                        ) {
                            Text(text = rango, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // GÉNERO
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Género",
                    color = colorMorado,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
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
                            contentPadding = PaddingValues(horizontal = 2.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(34.dp)
                        ) {
                            Text(text = genero, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // DESCRIPCIÓN FÍSICA
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Descripción Física",
                    color = if (tieneError) Color.Red else colorMorado,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

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
                        .heightIn(min = 110.dp),
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

        // MARGEN FIJO CON RESPECTO A LOS BOTONES
        Spacer(modifier = Modifier.height(16.dp))

        // --- 2. BOTONES FIJOS EN EL BORDE INFERIOR ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { onAtras() },
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
                        onSiguiente()
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
                Text(text = "Siguiente", fontSize = 15.sp, fontWeight = FontWeight.Bold)
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