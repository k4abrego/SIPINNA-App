package com.example.sippina_aplicacion.view.PantallasReporte

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.example.sippina_aplicacion.viewmodel.ReporteVM

/**
 * Pantalla final del flujo de reporte que procesa la transmisión y muestra el resultado del registro.
 *
 * Administra tres estados dinámicos de la interfaz de usuario:
 * - **Enviando**: Muestra un [CircularProgressIndicator] bloqueante mientras la petición es procesada por el servidor.
 * - **Error**: Presenta una interfaz de fallo con la opción de reintentar la transmisión hacia la API backend.
 * - **Éxito**: Despliega las credenciales resultantes (Folio y Código Privado), ofreciendo la posibilidad de copiarlas
 *   al portapapeles mediante [ClipboardManager].
 *
 * Bloquea la navegación de regreso con un [BackHandler] vacío para evitar envíos duplicados del mismo reporte,
 * e invoca `viewModel.reiniciarEstadoReporte` al navegar hacia la pantalla de inicio.
 *
 * @param viewModel Instancia de [ReporteVM] que expone el estado reactivo del proceso de transmisión.
 * @param modifier [Modifier] opcional para personalizar el diseño del contenedor principal.
 * @param onVolverInicio Callback ejecutado al presionar "Página de inicio" para regresar al inicio de la aplicación.
 * @param setPrimaryClip Función de extensión opcional para inyectar o mockear la interacción con el portapapeles.
 */
@SuppressLint("ServiceCast")
@Composable
fun ReporteGenerado(
    viewModel: ReporteVM = viewModel(),
    modifier: Modifier = Modifier,
    onVolverInicio: () -> Unit = {},
    setPrimaryClip: ClipboardManager.(ClipData) -> Unit = ClipboardManager::setPrimaryClip
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    BackHandler {
        onVolverInicio()
    }

    val colorMorado = Color(0xFF381DC3)
    val moradoClaro = Color(0xFFD0BCFF)

    LaunchedEffect(Unit) {
        if (state.folio.isEmpty() && !state.enviando) {
            viewModel.enviarReporteAlBackEnd(context)
        }
    }

    if (state.enviando) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(colorMorado)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(
                    color = Color.White,
                    strokeWidth = 4.dp,
                    modifier = Modifier.size(60.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Registrando tu reporte...",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Por favor, no cierres la aplicación.",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 14.sp
                )
            }
        }
    }
    else if (state.errorEnvio != null) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(colorMorado)
                .padding(24.dp)
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.imagensippina),
                contentDescription = "Logo Sippina",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Icon(
                imageVector = Icons.Default.Error,
                contentDescription = "Error",
                tint = Color(0xFFFFB4AB),
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Ocurrió un error al enviar el reporte",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = state.errorEnvio ?: "Error de conexión con el servidor.",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.9f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = { viewModel.enviarReporteAlBackEnd(context) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = colorMorado
                ),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(text = "Reintentar envío", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = {
                    viewModel.guardarBorrador()
                    Toast.makeText(context, "Borrador guardado exitosamente", Toast.LENGTH_SHORT).show()
                },
                border = BorderStroke(1.5.dp, Color.White),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Save,
                    contentDescription = "Guardar borrador",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Guardar borrador", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            IconButton(
                onClick = {
                    viewModel.guardarBorrador()
                    onVolverInicio()
                },
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.White.copy(alpha = 0.2f), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Página de inicio",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
    else {
        val folioMostrar = state.folio.ifEmpty { "REP-1234-1234" }
        val codigoMostrar = state.codigoPrivado.ifEmpty { "KEFD-2P32" }

        val datosReporte = """
            Folio: $folioMostrar
            Código privado: $codigoMostrar
        """.trimIndent()

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(colorMorado)
                .padding(horizontal = 24.dp, vertical = 24.dp)
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logorieti),
                contentDescription = "Logo Sippina",
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "¡Gracias!",
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "¡Tu reporte ha sido registrado con\néxito!",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(15.dp))

            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Éxito",
                tint = Color.White,
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.height(25.dp))

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = moradoClaro),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Folio",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colorMorado
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = folioMostrar,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = colorMorado,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = moradoClaro),
                border = BorderStroke(1.5.dp, moradoClaro),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Código privado",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colorMorado
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = codigoMostrar,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = colorMorado,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Guarda estos datos. Los necesitarás para consultar el estado de tu reporte. No compartas tu código privado.",
                        color = colorMorado,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedButton(
                onClick = {
                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("Datos del reporte", datosReporte)
                    clipboard.setPrimaryClip(clip)

                    Toast.makeText(
                        context,
                        "Datos del reporte copiados al portapapeles",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                border = BorderStroke(1.5.dp, Color.White),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.ContentCopy,
                    contentDescription = "Copiar datos",
                    tint = Color.White
                )

                Text(
                    text = "Copiar datos del reporte",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            IconButton(
                onClick = { onVolverInicio() },
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.White.copy(alpha = 0.2f), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Página de inicio",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Reporte Generado con Éxito"
)
@Composable
fun ReporteExitosoPreview() {
    SIPPINAAplicacionTheme {
        ReporteGenerado()
    }
}