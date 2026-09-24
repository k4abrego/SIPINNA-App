package com.example.sippina_aplicacion.view.PantallasReporte

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
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

@Composable
fun ReporteGenerado(
    viewModel: ReporteVM = viewModel(),
    modifier: Modifier = Modifier,
    folio: String = "REP-1234-1234",
    codigoPrivado: String = "KEFD-2P32",
    onVolverInicio: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()

    val folioMostrar = folio.ifEmpty { "REP-1234-1234" }
    val codigoMostrar = codigoPrivado.ifEmpty { "KEFD-2P32" }

    val colorMorado = Color(0xFF381DC3)
    val moradoClaro = Color(0xFFD0BCFF)
    val colorBlanco = Color.White

    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    val datosReporte = """
        Folio: $folioMostrar
        Código privado: $codigoMostrar
        """.trimIndent()

    Column(
        modifier = Modifier
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

        OutlinedButton(
            onClick = {
                clipboardManager.setText(AnnotatedString(datosReporte))
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

            Spacer(modifier = Modifier.height(0.dp))

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
                .clickable { onVolverInicio() }
                .padding(8.dp)
        )
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