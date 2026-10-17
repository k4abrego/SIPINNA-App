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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState


@Composable
fun DetalleReporte(
    folio: String,
    fechaEnvio: String,
    estadoActual: Int,
    descripcion: String,
    direccion: String,
    latitud: Double,
    longitud: Double,
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
                    painter = painterResource(id = R.drawable.logorieti),
                    contentDescription = "Logo SIPINNA",
                    modifier = Modifier.size(120.dp)
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
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

            // TARJETA 1: DESCRIPCIÓN DEL INCIDENTE
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
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

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = descripcion.ifBlank { "Sin descripción disponible" },
                        fontSize = 14.sp,
                        lineHeight = 18.sp,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(start = 32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            // TARJETA 2: DIRECCIÓN Y MAPA REAL
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.MyLocation,
                            contentDescription = null,
                            tint = morado,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Ubicación del reporte",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = direccion.ifBlank { "Sin dirección especificada" },
                        fontSize = 14.sp,
                        lineHeight = 18.sp,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(start = 32.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    val posicionInicial = LatLng(
                        if (latitud != 0.0) latitud else 19.5583,
                        if (longitud != 0.0) longitud else -99.2483
                    )

                    val cameraPositionState = rememberCameraPositionState {
                        position = CameraPosition.fromLatLngZoom(posicionInicial, 16f)
                    }

                    val markerState = rememberMarkerState(position = posicionInicial)

                    LaunchedEffect(latitud, longitud) {
                        if (latitud != 0.0 && longitud != 0.0) {
                            val nuevaUbicacion = LatLng(latitud, longitud)
                            markerState.position = nuevaUbicacion
                            cameraPositionState.animate(
                                CameraUpdateFactory.newLatLngZoom(nuevaUbicacion, 16f)
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(10.dp))
                    ) {
                        GoogleMap(
                            modifier = Modifier.fillMaxSize(),
                            cameraPositionState = cameraPositionState,
                            uiSettings = MapUiSettings(
                                zoomControlsEnabled = false,
                                myLocationButtonEnabled = false
                            )
                        ) {
                            Marker(
                                state = markerState,
                                title = "Lugar del incidente",
                                snippet = direccion.ifBlank { "Ubicación del reporte" }
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
            color = if (activo) morado else Color(0xFFE0E0E0),
            modifier = Modifier.size(38.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icono,
                    contentDescription = etiqueta.replace("\n", " "),
                    tint = if (activo) Color.White else morado,
                    modifier = Modifier.size(23.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(7.dp))

        Text(
            text = etiqueta,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (activo) Color.DarkGray else Color.Gray,
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
            color = if (activa) Color(0xFF381DC3) else Color.Gray
        )
    }
}

fun mapearEstatusAInt(estatus: String): Int {
    return when (estatus.lowercase().trim()) {
        "enviado", "recibido" -> 0
        "en revisión", "en revision" -> 1
        "en proceso", "en atención", "en atencion" -> 2
        "cerrado", "concluido" -> 3
        else -> 0
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Detalle Reporte - Cargando Backend"
)
@Composable
fun DetalleReporteCargandoPreview() {
    SIPPINAAplicacionTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEAF4FC)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color(0xFF381DC3))
        }
    }
}