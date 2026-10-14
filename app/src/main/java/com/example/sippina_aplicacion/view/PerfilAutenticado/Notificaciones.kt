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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.sippina_aplicacion.model.EstadoNotificacion

/**
 * Pantalla que despliega el historial de notificaciones y avisos del sistema emitidos para el usuario.
 *
 * Administra tres estados visuales:
 * - **Carga**: Despliega un [CircularProgressIndicator] mientras se consultan las notificaciones.
 * - **Vacío**: Muestra un aviso cuando no existen notificaciones en la lista.
 * - **Listado dinámico**: Renderiza tarjetas [OutlinedCard] animadas con `animateColorAsState` que diferencian
 *   visualmente las notificaciones no leídas de las leídas. Al hacer clic, marca el elemento como leído
 *   mediante [onMarcarLeida] y navega al reporte relacionado con [onAbrirReporte].
 *
 * @param modifier [Modifier] opcional para el contenedor principal de la pantalla.
 * @param notificaciones Lista de objetos [EstadoNotificacion] con los avisos a mostrar.
 * @param cargando Flag que indica si el proceso de consulta está en curso.
 * @param onAtras Callback para regresar a la pantalla del perfil o menú previo.
 * @param onAbrirReporte Callback invocado con el folio del reporte para navegar hacia sus detalles.
 * @param onMarcarLeida Callback invocado con el identificador único para marcar la notificación como leída.
 */
@Composable
fun Notificaciones(
    modifier: Modifier = Modifier,
    notificaciones: List<EstadoNotificacion> = emptyList(),
    cargando: Boolean = false,
    onAtras: () -> Unit = {},
    // se ejecutará cuando el usuario seleccione un reporte
    onAbrirReporte: (String) -> Unit = {},
    onMarcarLeida: (Int) -> Unit = {}
) {
    val morado = Color(0xFF3D27C9)
    val fondo = Color(0xFFEAF4FC)

    Box(
        modifier = modifier
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
            // Cabecera con botón atrás y logo
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
                        contentColor = morado
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

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Notificaciones",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                color = morado,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(25.dp))

            // Estado de carga
            if (cargando) {
                Spacer(modifier = Modifier.height(40.dp))
                CircularProgressIndicator(color = morado)
            }
            // Estado sin notificaciones
            else if (notificaciones.isEmpty()) {
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
            }
            // Lista dinámica recibida por parámetros
            else {
                notificaciones.forEach { notificacion ->

                    val colorTarjeta by animateColorAsState(
                        targetValue = if (notificacion.leida) Color.White else Color(0xFFF7F5FF),
                        animationSpec = tween(durationMillis = 350),
                        label = "AnimacionFondoNotificacion"
                    )

                    val colorBorde by animateColorAsState(
                        targetValue = if (notificacion.leida) Color(0xFFB6AFE8) else morado,
                        animationSpec = tween(durationMillis = 350),
                        label = "AnimacionBordeNotificacion"
                    )

                    val colorIcono by animateColorAsState(
                        targetValue = if (notificacion.leida) Color(0xFF9289CC) else morado,
                        animationSpec = tween(durationMillis = 350),
                        label = "AnimacionIconoNotificacion"
                    )

                    OutlinedCard(
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(width = 1.5.dp, color = colorBorde),
                        colors = CardDefaults.outlinedCardColors(containerColor = colorTarjeta),
                        onClick = {
                            if (!notificacion.leida) {
                                onMarcarLeida(notificacion.id)
                            }
                            if (notificacion.folio.isNotBlank()) {
                                onAbrirReporte(notificacion.folio)
                            }
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
                                        fontWeight = if (notificacion.leida) FontWeight.Medium else FontWeight.Bold,
                                        color = Color.DarkGray,
                                        modifier = Modifier.weight(1f, fill = false)
                                    )

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
                                contentDescription = if (notificacion.leida) "Notificación leída" else "Notificación sin leer",
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

/**
 * Previsualización de la pantalla [Notificaciones] en el entorno de desarrollo de Android Studio.
 */
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
