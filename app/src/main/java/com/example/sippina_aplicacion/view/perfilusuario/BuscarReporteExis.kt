package com.example.sippina_aplicacion.view.perfilusuario

import android.graphics.fonts.Font
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.R
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme
import com.example.sippina_aplicacion.viewmodel.ReporteModel

@Composable
fun BuscarReporteExistente(
    onAtras: () -> Unit = {},
    onVerDetalleReporte: (String) -> Unit = {},
    onBuscar: (folio: String, codigo: String) -> Unit = { _, _ -> },
    reporteEncontrado: ReporteModel? = null,
    buscando: Boolean = false,
    errorBusqueda: String? = null,
    folioInicial: String = "",
    codigoInicial: String = ""
) {
    val morado = Color(0xFF381DC3)
    val fondo = Color(0xFFEAF4FC)
    val gris = Color(0xFF777777)

    var folio by remember(folioInicial) { mutableStateOf(folioInicial) }
    var codigoSeguridad by remember(codigoInicial) { mutableStateOf(codigoInicial) }

    val datosCompletos = folio.isNotBlank() && codigoSeguridad.isNotBlank()

    // Busqueda al backend cuando el usuario llena ambos campos
    LaunchedEffect(folio, codigoSeguridad) {
        if (datosCompletos) {
            onBuscar(folio, codigoSeguridad)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                FilledIconButton(
                    onClick = onAtras,
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.CenterStart),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = fondo,
                        contentColor = Color(0xFF3D27C9)
                    )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Atrás",
                        modifier = Modifier.size(22.dp)
                    )
                }
                Image(
                    painter = painterResource(id = com.example.sippina_aplicacion.R.drawable.imagensippina),
                    contentDescription = "Logo SIPINNA",
                    modifier = Modifier.size(120.dp)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Reportes",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = morado,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Ingrese el folio y código de seguridad para dar seguimiento y detalles de tu reporte",
                fontSize = 18.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = gris,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(28.dp))

            CampoFormularioBusqueda(
                etiqueta = "Folio",
                valor = folio,
                onValorChange = { folio = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            CampoFormularioBusqueda(
                etiqueta = "Código de seguridad",
                valor = codigoSeguridad,
                onValorChange = { codigoSeguridad = it }
            )

            Spacer(modifier = Modifier.height(25.dp))

            AnimatedVisibility(
                visible = datosCompletos,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Resultados:",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = gris,
                        modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
                    )

                    if (buscando) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = morado)
                        }
                    } else if (reporteEncontrado != null) {
                        OutlinedCard(
                            onClick = { onVerDetalleReporte(reporteEncontrado.folio) },
                            shape = RoundedCornerShape(16.dp),
                            border = BorderStroke(1.5.dp, color = morado),
                            colors = CardDefaults.outlinedCardColors(containerColor = fondo),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 16.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = reporteEncontrado.folio,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = morado,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(start = 12.dp),
                                    textAlign = TextAlign.Center
                                )

                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(
                                            color = morado,
                                            shape = RoundedCornerShape(50)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.Send,
                                        contentDescription = "Ver detalles del reporte",
                                        tint = Color.White,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    } else {
                        OutlinedCard(
                            shape = RoundedCornerShape(16.dp),
                            border = BorderStroke(width = 1.5.dp, color = morado),
                            colors = CardDefaults.outlinedCardColors(containerColor = fondo),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = errorBusqueda ?: "Sin reportes",
                                    fontSize = 19.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = morado,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(44.dp))

            val textoPie = buildAnnotatedString {
                append("Recuerda que el folio y el código de seguridad es único para cada caso. Cualquier aclaración contactar a las autoridades de ")
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append("SIPINNA")
                }
            }
            Text(
                text = textoPie,
                fontSize = 16.sp,
                lineHeight = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = gris,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

@Composable
private fun CampoFormularioBusqueda(
    etiqueta: String,
    valor: String,
    onValorChange: (String) -> Unit
) {
    val morado = Color(0xFF381DC3)

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = etiqueta,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        OutlinedTextField(
            value = valor,
            onValueChange = onValorChange,
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            textStyle = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray,
                textAlign = TextAlign.Center
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = morado,
                unfocusedBorderColor = Color.Gray,
                focusedTextColor = morado,
                unfocusedTextColor = morado
            ),
            modifier = Modifier
                .fillMaxWidth(0.88f)
                .height(52.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Buscar reporte - Encontrado")
@Composable
fun BuscarReporteEncontradoPreview() {
    SIPPINAAplicacionTheme {
        BuscarReporteExistente(
            folioInicial = "REP-1234-1234",
            codigoInicial = "KEFD-2P32",
            reporteEncontrado = ReporteModel(folio = "REP-1234-1234", estatus = "En proceso")
        )
    }
}