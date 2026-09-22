package com.example.sippina_aplicacion.view.PantallasReporte

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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

@Composable
fun Evidencia(
    viewModel: ReporteVM = viewModel(),
    modifier: Modifier = Modifier,
    onSiguiente: () -> Unit = { },
    onAtras: () -> Unit = { }
) {
    // Suscripcion al estado centralizado de viewModel
    val state by viewModel.uiState.collectAsState()


    val colorMorado = Color(0xFF381EBD)
    val colorFondoBotonSiguiente = Color(0xFFD3E4F4)
    val colorTextoBotonSiguiente = Color(0xFF3B719F)
    val colorFondoBotonAtras = Color(0xFFE2E2E2)
    val colorTextoBotonAtras = Color(0xFF5A5A5A)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 15.dp)
            .verticalScroll(rememberScrollState())
            .imePadding()
            .background(Color(0xFFEDF7FF))
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.imagensippina),
            contentDescription = "Logo Sippina",
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Evidencia",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = colorMorado
        )
        Spacer(modifier = Modifier.height(10.dp))

        BarraDePasos(pasoActual = 3)

        Spacer(modifier = Modifier.height(35.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TarjetaOpcionMedia(
                icon = Icons.Default.PhotoCamera,
                contentDescription = "Tomar Foto",
                modifier = Modifier.weight(1f),
                onClick = { })

            TarjetaOpcionMedia(
                icon = Icons.Default.Image,
                contentDescription = "Galeria",
                modifier = Modifier.weight(1f),
                onClick = {
                })

        }
        Spacer(modifier = Modifier.height(25.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TarjetaOpcionMedia(
                icon = Icons.Default.Videocam,
                contentDescription = "Grabar Video",
                modifier = Modifier.weight(1f),
                onClick = { })

            TarjetaOpcionMedia(
                icon = Icons.Default.Mic,
                contentDescription = "Grabar Audio",
                modifier = Modifier.weight(1f),
                onClick = {
                })

        }

        Spacer(modifier = Modifier.height(25.dp))

        OutlinedCard(
            onClick = {}, shape = RoundedCornerShape(15.dp),
            border = BorderStroke(1.5.dp, colorMorado),
            //colors = CardDefaults.outlinedCardColors(containerColor = Color.LightGray),
            modifier = Modifier
                .fillMaxWidth()
                .height(105.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Icon(
                    imageVector = Icons.Default.AttachFile,
                    contentDescription = "Adjuntar archivo",
                    tint = Color.Gray,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Adjuntar o subir archivo",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )

            }


        }

        Spacer(modifier = Modifier.height(83.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp))
        {
            Button(
                onClick = { onAtras() },
                colors = ButtonDefaults.buttonColors(colorFondoBotonAtras, colorTextoBotonAtras),
                modifier = Modifier
                    .weight(1f)
                    .height(46.dp)
            ) {
                Text(text = "Atrás", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }
            Button(
                onClick = { onSiguiente() },
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

@Composable
private fun TarjetaOpcionMedia(
    icon: ImageVector,
    contentDescription: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    OutlinedCard(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.5.dp, Color(0xFF381EBD)),
        //colors = CardDefaults.outlinedCardColors(containerColor = Color.LightGray),
        modifier = modifier.height(115.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = Color.Gray,
                modifier = Modifier.size(42.dp)
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Evidencia Opcional"
)

@Composable
fun EvidenciaPreview() {
    SIPPINAAplicacionTheme {
        Evidencia()
    }
}
