package com.example.sippina_aplicacion.view.perfilusuario

import android.R.attr.id
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.automirrored.filled.InsertDriveFile
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.InsertDriveFile
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sippina_aplicacion.R
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme

@Composable
fun DetalleReporte(
    folio: String = "REP-1234-1234",
    fechaEnvio: String = "24/08/2026, 10:59:00am",
    onAtras: () -> Unit = {},
    onVerTodasEvidencias: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp, 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                contentAlignment = Alignment.Center
            )
            {
                FilledIconButton(
                    onClick = onAtras,
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.CenterStart),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = Color.Blue,
                        contentColor = Color.White
                    )
                )
                {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Atrás",
                        modifier = Modifier.size(22.dp)
                    )

                }
                Image(
                    painter = painterResource(id = R.drawable.imagensippina),
                    contentDescription = "Logo Sippina",
                    modifier = Modifier.size(120.dp)
                )

            }
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = "Reporte",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray
            )

            Text(
                text = folio,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )

            Text(
                text = "Enviado: $fechaEnvio",
                fontSize = 13.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(20.dp))
            BarraEstadoReporte()
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Su caso fue recibido, estamos revisando\nel reporte",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedCard(
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(1.5.dp, Color.Blue),
                colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            )
            {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            color = Color.Blue,
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier.size(24.dp)
                        )
                        {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Article,
                                    contentDescription = "Articulo",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )

                            }


                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Descripción del incidente",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Blue

                        )

                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Flop y flop y flop Flop y flop y flopFlop y flop y flopFlop y flop y flopFlop y flop y flopFlop y flop y flop",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        lineHeight = 16.sp
                    )

                }

            }
            Spacer(modifier = Modifier.height(14.dp))

            OutlinedCard(
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(1.5.dp, Color.Blue),
                colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            )
            {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Column(modifier = Modifier.weight(1f))
                    {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.MyLocation,
                                contentDescription = "Ubicacion",
                                tint = Color.Blue,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Ubicación",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Blue

                            )

                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Calle Flop, Av. Ware s/n, Col. Papu, C.P 6767",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            lineHeight = 16.sp
                        )


                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background), // Reemplazar con imagen/mapa real
                        contentDescription = "Mapa de ubicación",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(width = 110.dp, height = 65.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.LightGray)
                    )

                }

            }
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedCard(
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(1.5.dp, Color.Blue),
                colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            )
            {
                Column(modifier = Modifier.padding(14.dp))
                {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        Row(verticalAlignment = Alignment.CenterVertically)
                        {
                            Surface(
                                color = Color.Blue,
                                shape = RoundedCornerShape(4.dp),
                                modifier = Modifier.size(16.dp)
                            )
                            {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.InsertDriveFile,
                                        contentDescription = "Icono",
                                        tint = Color.White,
                                        modifier = Modifier.size(16.dp)
                                    )

                                }

                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Evidencias (2)",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Blue
                            )

                        }
                        TextButton(
                            onClick = onVerTodasEvidencias,
                            contentPadding = PaddingValues(0.dp)
                        )
                        {
                            Text(
                                text = "Ver todas",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )

                        }


                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp))
                    {
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher_background), // Reemplazar con foto real
                            contentDescription = "Evidencia 1",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(width = 90.dp, height = 55.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color.Gray)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher_background), // Reemplazar con foto real
                            contentDescription = "Evidencia 2",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(width = 100.dp, height = 55.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color.Gray)
                        )


                    }


                }


            }
            Spacer(modifier = Modifier.height(14.dp))
            OutlinedCard(
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(1.5.dp, Color.Blue),
                colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            )
            {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.CenterStart
                )
                {
                    Text(
                        text = "Etiquetas",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Blue
                    )

                }


            }


        }

    }


}

@Composable
fun BarraEstadoReporte() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        PasoEstado(
            etiqueta = "Recibido",
            icono = Icons.Default.Check,
            activo = true,
            modifier = Modifier.weight(1f)
        )
        LineaEstado(activa = false, modifier = Modifier.weight(0.8f))
        PasoEstado(
            etiqueta = "En\nrevisión",
            icono = Icons.Default.History,
            activo = false,
            modifier = Modifier.weight(1f)
        )
        LineaEstado(activa = false, modifier = Modifier.weight(0.8f))
        PasoEstado(
            etiqueta = "En\natención",
            icono = Icons.Default.Flag,
            activo = false,
            modifier = Modifier.weight(1f)
        )
        LineaEstado(activa = false, modifier = Modifier.weight(0.8f))
        PasoEstado(
            etiqueta = "Cerrado",
            icono = Icons.Default.Flag,
            activo = false,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun PasoEstado(
    etiqueta: String,
    icono: ImageVector,
    activo: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Surface(
            shape = CircleShape,
            color = if (activo) Color.Blue else Color.LightGray,
            modifier = Modifier.size(34.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icono,
                    contentDescription = null,
                    tint = if (activo) Color.White else Color.LightGray,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = etiqueta,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (activo) Color.Black else Color.LightGray,
            textAlign = TextAlign.Center,
            lineHeight = 12.sp
        )
    }
}

@Composable
private fun LineaEstado(
    activa: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(top = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        HorizontalDivider(
            thickness = 2.dp,
            color = if (activa) Color.Blue else Color.LightGray
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Ver Detalle Reporte - Material 3"
)
@Composable
fun DetalleReportePreview() {
    SIPPINAAplicacionTheme {
        DetalleReporte()
    }
}