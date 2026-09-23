package com.example.sippina_aplicacion.view.PerfilAutenticado

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sippina_aplicacion.R
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme


// datos temporales de notificaciones
private data class NotificacionTemporal(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val fecha: String,
    val folio: String,
    val leida: Boolean = false
)


@Composable
fun Notificaciones(
    onAtras: () -> Unit = {},

    //se ejecutará cuando el usuario seleccione un reporte
    onAbrirReporte: (String) -> Unit = {}
) {
    val morado = Color(0xFF3D27C9)
    val fondo = Color(0xFFEAF4FC)

    //temporal
    val notificaciones = remember {
        mutableStateListOf(
            NotificacionTemporal(
                id = 1,
                titulo = "Reporte actualizado",
                descripcion = "Tu reporte REP-1234-1234 cambió a En revisión.",
                fecha = "Hace 10 min",
                folio = "REP-1234-1234"
            ),
            NotificacionTemporal(
                id = 2,
                titulo = "Caso recibido",
                descripcion = "Se recibió correctamente tu reporte con folio REP-1234-1234",
                fecha = "Ayer",
                folio = "REP-1234-1234"
            )
        )
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(fondo)
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
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = fondo, contentColor = morado)
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

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Notificaciones",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                color = morado,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(25.dp))

            //lista de notificaciones

            if (notificaciones.isEmpty()) {

                Spacer(modifier = Modifier.height(35.dp))

                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(55.dp)
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = "No tienes notificaciones",
                    fontSize = 16.sp,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Center
                )

            } else {
                notificaciones.forEachIndexed { index, notificacion ->

                    //animacioness

                    val colorTarjeta by animateColorAsState(
                        targetValue = if (notificacion.leida) {
                            Color.White
                        } else {
                            Color(0xFFF7F5FF)
                        },
                        animationSpec = tween(durationMillis = 350),
                        label = "AnimacionFondoNotificacion"
                    )

                    //para el borde
                    val colorBorde by animateColorAsState(
                        targetValue = if (notificacion.leida) {
                            Color(0xFFB6AFE8)
                        } else { morado
                        },
                        animationSpec = tween(durationMillis = 350),
                        label = "AnimacionBordeNotificacion"
                    )

                    //para el ícono
                    val colorIcono by animateColorAsState(
                        targetValue = if (notificacion.leida) {
                            Color(0xFF9289CC)
                        } else { morado
                        },
                        animationSpec = tween(durationMillis = 350),
                        label = "AnimacionIconoNotificacion"
                    )

                    //esto ya es la tarjeta
                    OutlinedCard(shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(width = 1.5.dp, color = colorBorde),
                        colors = CardDefaults.outlinedCardColors(containerColor = colorTarjeta),
                        onClick = {
                            //marca la notificación como leída
                            if (!notificacion.leida) {
                                notificaciones[index] =
                                    notificacion.copy(
                                        leida = true
                                    )
                            }
                            //solicita la apertura del reporte
                            onAbrirReporte(notificacion.folio)
                        },

                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 15.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = notificacion.titulo,
                                        fontSize = 17.sp,
                                        fontWeight = if (notificacion.leida) {
                                            FontWeight.Medium
                                        } else {
                                            FontWeight.Bold
                                        },
                                        color = Color.DarkGray,
                                        modifier = Modifier.weight(1f, fill = false)
                                    )

                                    //notificaiocn no leída

                                    if (!notificacion.leida) {
                                        Box(
                                            modifier = Modifier
                                                .padding(start = 8.dp)
                                                .size(7.dp)
                                                .background(
                                                    color = morado,
                                                    shape = CircleShape
                                                )
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = notificacion.descripcion,
                                    fontSize = 14.sp,
                                    lineHeight = 17.sp,
                                    color = Color.DarkGray
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = notificacion.fecha,
                                    fontSize = 12.sp,
                                    color = Color.DarkGray
                                )
                            }

                            Icon(
                                imageVector = Icons.Outlined.Notifications,
                                contentDescription = if (notificacion.leida) { "Notificación leída"
                                } else {
                                    "Notificación sin leer"
                                },
                                tint = colorIcono,
                                modifier = Modifier.size(35.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(22.dp))
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun NotificacionesPreview(){
    SIPPINAAplicacionTheme {
        Notificaciones ()
    }
}