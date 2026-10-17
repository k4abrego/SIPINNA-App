package com.example.sippina_aplicacion.view.perfilusuario

import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sippina_aplicacion.R
import com.example.sippina_aplicacion.model.ReporteModel
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme
import kotlinx.coroutines.delay
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

    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    var folio by remember(folioInicial) { mutableStateOf(folioInicial) }
    var codigoSeguridad by remember(codigoInicial) { mutableStateOf(codigoInicial) }

    // Estado para verificar si hay datos en el portapapeles
    var textoPortapapeles by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        val texto = clipboardManager.getText()?.text
        if (!texto.isNullOrBlank()) {
            textoPortapapeles = texto
        }
    }

    val folioLimpio = folio.trim()
    val codigoLimpio = codigoSeguridad.trim()

    val datosCompletos = folioLimpio.length >= 8 && codigoLimpio.length >= 4

    LaunchedEffect(folioLimpio, codigoLimpio) {
        if (datosCompletos) {
            delay(500)
            onBuscar(folioLimpio, codigoLimpio)
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
                    painter = painterResource(id = R.drawable.logorieti),
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

            Spacer(modifier = Modifier.height(20.dp))

            // BOTÓN INTELIGENTE: Aparece solo si hay contenido copiado en el portapapeles
            if (textoPortapapeles != null) {
                OutlinedButton(
                    onClick = {
                        val texto = textoPortapapeles ?: ""

                        if (texto.contains("Folio:", ignoreCase = true)) {
                            texto.lines().forEach { linea ->
                                if (linea.contains("Folio:", ignoreCase = true)) {
                                    folio = linea.substringAfter("Folio:").trim()
                                }
                                if (linea.contains("Código privado:", ignoreCase = true) || linea.contains("Codigo", ignoreCase = true)) {
                                    codigoSeguridad = linea.substringAfter(":").trim()
                                }
                            }
                            Toast.makeText(context, "Datos pegados automáticamente", Toast.LENGTH_SHORT).show()
                        } else {
                            folio = texto.trim()
                            Toast.makeText(context, "Folio pegado", Toast.LENGTH_SHORT).show()
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, morado),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = morado),
                    modifier = Modifier.padding(bottom = 12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentPaste,
                        contentDescription = "Pegar datos",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Pegar datos del portapapeles",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                Spacer(modifier = Modifier.height(8.dp))
            }

            CampoFormularioBusqueda(
                etiqueta = "Folio",
                valor = folio,
                onValorChange = { folio = it },
                imeAction = ImeAction.Next
            )

            Spacer(modifier = Modifier.height(16.dp))

            CampoFormularioBusqueda(
                etiqueta = "Código de seguridad",
                valor = codigoSeguridad,
                onValorChange = { codigoSeguridad = it },
                imeAction = ImeAction.Done
            )

            Spacer(modifier = Modifier.height(25.dp))

            val mostrarResultados = datosCompletos && (buscando || reporteEncontrado != null || errorBusqueda != null)

            AnimatedVisibility(
                visible = mostrarResultados,
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
                        TarjetaResultadoReporte(
                            folio = reporteEncontrado.folio,
                            onClick = { onVerDetalleReporte(reporteEncontrado.folio) }
                        )
                    } else if (errorBusqueda != null) {
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
                                    text = errorBusqueda,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Red,
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
private fun TarjetaResultadoReporte(
    folio: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val morado = Color(0xFF381DC3)
    val fondo = Color(0xFFEAF4FC)
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    OutlinedCard(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.5.dp, color = morado),
        colors = CardDefaults.outlinedCardColors(containerColor = fondo),
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = {
                    clipboardManager.setText(AnnotatedString(folio))
                    Toast.makeText(context, "Folio copiado al portapapeles", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ContentCopy,
                    contentDescription = "Copiar folio",
                    tint = morado,
                    modifier = Modifier.size(20.dp)
                )
            }

            Text(
                text = folio,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = morado,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                textAlign = TextAlign.Center
            )

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = morado,
                        shape = CircleShape
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
}

@Composable
private fun CampoFormularioBusqueda(
    etiqueta: String,
    valor: String,
    onValorChange: (String) -> Unit,
    imeAction: ImeAction = ImeAction.Default
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
            keyboardOptions = KeyboardOptions(imeAction = imeAction),
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

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Buscar Reporte Existente"
)
@Composable
fun BuscarReporteExistentePreview() {
    SIPPINAAplicacionTheme {
        BuscarReporteExistente()
    }
}