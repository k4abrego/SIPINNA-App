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
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sippina_aplicacion.R
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme
import com.example.sippina_aplicacion.viewmodel.ReporteVM

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

    BackHandler {// se deja vacio para evitar que se duplique
    }

    val colorMorado = Color(0xFF381DC3)
    val moradoClaro = Color(0xFFD0BCFF)
    val colorBlanco = Color.White

    LaunchedEffect(Unit) {
        if(state.folio.isEmpty() && !state.enviando){
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
    // 2. ESTADO DE ERROR DE CONEXIÓN
    else if (state.errorEnvio != null) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(colorMorado)
                .padding(24.dp)
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
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
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = colorMorado),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(text = "Reintentar envío", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
    // 3. ESTADO DE ÉXITO (MOSTRAR DATOS)
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
                painter = painterResource(id = R.drawable.imagensippina),
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

            // BOTÓN COPIAR USANDO EL CLIPBOARD NATIVO
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

            Text(
                text = "Página de inicio",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .clickable {
                        viewModel.reiniciarEstadoReporte()
                        onVolverInicio()
                    }
                    .padding(8.dp)
            )
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