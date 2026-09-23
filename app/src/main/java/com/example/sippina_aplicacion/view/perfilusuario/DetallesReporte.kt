package com.example.sippina_aplicacion.view.perfilusuario

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.sippina_aplicacion.R
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme


@Composable
fun DetalleReporte(
    folio: String = "REP-1234-1234",
    fechaEnvio: String = "24/08/2026, 10:59:00 am",

    // 0 = Recibido
    // 1 = En revisión
    // 2 = En atención
    // 3 = Cerrado
    estadoActual: Int = 0,

    descripcion: String = "Menor de edad encontrado solo en la vía pública, sin la supervisión de una persona adulta.",

    direccion: String = "Calle Flop, Av. Ware s/n, Col. Papu, C.P. 6767",

    onAtras: () -> Unit = {}
) {

    val morado = Color(0xFF381DC3)
    val fondo = Color(0xFFEAF4FC)
    val gris = Color(0xFF777777)
    val mensajeEstado = when (estadoActual) {
        0 -> "Su caso fue recibido, estamos revisando el reporte"
        1 -> "Su reporte se encuentra en revisión"
        2 -> "Su reporte se encuentra en atención"
        3 -> "Su reporte ha sido cerrado"
        else -> "Consulta el estado de tu reporte"
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
                        .size(50.dp)
                        .align(Alignment.CenterStart),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = fondo,
                        contentColor = Color(0xFF3D27C9)
                    )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Botón atrás",
                        modifier = Modifier.size(22.dp)
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.imagensippina),
                    contentDescription = "Logo SIPINNA",
                    modifier = Modifier.size(120.dp)
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Reporte",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = gris
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = folio,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = morado
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Enviado: $fechaEnvio",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = gris
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            BarraEstadoReporte(estadoActual = estadoActual)

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = mensajeEstado,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 20.sp,
                color = Color.DarkGray,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedCard(
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(width = 1.5.dp, color = morado),
                colors = CardDefaults.outlinedCardColors(containerColor = fondo),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Description,
                            contentDescription = null,
                            tint = morado,
                            modifier = Modifier.size(22.dp)
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = "Descripción del incidente",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = descripcion,
                        fontSize = 13.sp,
                        lineHeight = 17.sp,
                        color = gris,
                        modifier = Modifier.padding(start = 32.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            OutlinedCard(
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(width = 1.5.dp, color = morado),
                colors = CardDefaults.outlinedCardColors(containerColor = fondo),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.MyLocation,
                            contentDescription = null,
                            tint = morado,
                            modifier = Modifier.size(22.dp)
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = "Ubicación",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = direccion,
                        fontSize = 13.sp,
                        lineHeight = 17.sp,
                        color = gris,
                        modifier = Modifier.padding(start = 32.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .background(color = Color(0xFFDDE9F2), shape = RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.LocationOn,
                                contentDescription = "Ubicación del reporte",
                                tint = morado,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Mapa de ubicación",
                                fontSize = 13.sp,
                                color = gris,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(25.dp))
        }
    }
}

@Composable
fun BarraEstadoReporte(
    estadoActual: Int = 0
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        PasoEstado(
            etiqueta = "Recibido",
            icono = Icons.Default.Check,
            activo = estadoActual >= 0,
            modifier = Modifier.weight(1f)
        )
        LineaEstado(
            activa = estadoActual >= 1,
            modifier = Modifier.weight(0.35f)
        )

        PasoEstado(
            etiqueta = "En\nrevisión",
            icono = Icons.Default.History,
            activo = estadoActual >= 1,
            modifier = Modifier.weight(1f)
        )

        LineaEstado(
            activa = estadoActual >= 2,
            modifier = Modifier.weight(0.35f)
        )

        PasoEstado(
            etiqueta = "En\natención",
            icono = Icons.Default.Flag,
            activo = estadoActual >= 2,
            modifier = Modifier.weight(1f)
        )

        LineaEstado(
            activa = estadoActual >= 3,
            modifier = Modifier.weight(0.35f)
        )

        PasoEstado(
            etiqueta = "Cerrado",
            icono = Icons.Default.Flag,
            activo = estadoActual >= 3,
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
    val morado = Color(0xFF381DC3)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shape = CircleShape,
            color = if (activo) {
                morado
            } else {
                Color(0xFFE0E0E0)
            },
            modifier = Modifier.size(38.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icono,
                    contentDescription = etiqueta.replace("\n", " "),
                    tint = if (activo) {
                        Color.White
                    } else {
                        morado
                    },
                    modifier = Modifier.size(23.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(7.dp))

        Text(
            text = etiqueta,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (activo) {
                Color.DarkGray
            } else {
                Color.Gray
            },
            textAlign = TextAlign.Center,
            lineHeight = 13.sp
        )
    }
}

@Composable
private fun LineaEstado(
    activa: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.height(38.dp),
        contentAlignment = Alignment.Center
    ) {
        HorizontalDivider(
            thickness = 1.5.dp,
            color = if (activa) {
                Color(0xFF381DC3)
            } else {
                Color.Gray
            }
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Detalle de reporte - Recibido"
)
@Composable
fun DetalleReportePreview() {

    SIPPINAAplicacionTheme {

        DetalleReporte(
            estadoActual = 0
        )
    }
}




//// PREVIEW: REPORTE EN REVISIÓN
//
//@Preview(
//    showBackground = true,
//    showSystemUi = true,
//    name = "Detalle de reporte - En revisión"
//)
//@Composable
//fun DetalleReporteRevisionPreview() {
//
//    SIPPINAAplicacionTheme {
//
//        DetalleReporte(
//            estadoActual = 1
//        )
//    }
//}


// PREVIEW: REPORTE EN ATENCIÓN

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Detalle de reporte - En atención"
)
@Composable
fun DetalleReporteAtencionPreview() {

    SIPPINAAplicacionTheme {

        DetalleReporte(
            estadoActual = 2
        )
    }
}


//// PREVIEW: REPORTE CERRADO
//
//@Preview(
//    showBackground = true,
//    showSystemUi = true,
//    name = "Detalle de reporte - Cerrado"
//)
//@Composable
//fun DetalleReporteCerradoPreview() {
//
//    SIPPINAAplicacionTheme {
//
//        DetalleReporte(
//            estadoActual = 3
//        )
//    }
//}