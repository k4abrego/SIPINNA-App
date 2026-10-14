package com.example.sippina_aplicacion.view

import  androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sippina_aplicacion.R
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme

val colorMorado = Color(0xFF381DC3)

/**
 * Pantalla principal de inicio de la aplicación SIPINNA.
 *
 * Muestra el saludo de bienvenida, accesos superiores a notificaciones, búsqueda y perfil de usuario.
 * Si el usuario se encuentra autenticado, presenta una tarjeta flotante con el estatus del último reporte registrado.
 * Ofrece botones de navegación hacia la generación de reportes, información institucional, guías de qué reportar,
 * gestión de sesión y un botón de emergencia de acceso rápido.
 *
 * @param modifier [Modifier] opcional para el contenedor principal.
 * @param mostrarNotificaciones Flag que indica si se muestran notificaciones y funciones exclusivas para usuarios autenticados.
 * @param ultimoReporteFolio Folio del último reporte enviado por el usuario, o `null`.
 * @param ultimoReporteEstatus Estado actual del último reporte registrado.
 * @param accionBoton Callback ejecutado al presionar el botón de emergencia.
 * @param onNavegarAReporte Callback para iniciar el asistente de creación de un reporte anónimo.
 * @param onNavegarAPerfil Callback para navegar a la vista de perfil (invitado o autenticado).
 * @param onNavegarANotificaciones Callback para abrir la bandeja de notificaciones.
 * @param onQueSituacionesReportar Callback para consultar la guía de situaciones reportables.
 * @param onConoceSIPINNA Callback para consultar información institucional sobre SIPINNA.
 * @param onBuscarReporte Callback para buscar un reporte existente por folio y código.
 * @param onNavegarALogin Callback para iniciar sesión o registrar cuenta.
 * @param onNavegarCrearCuenta Callback para registrar una nueva cuenta de usuario.
 * @param onCerrarSesion Callback para cerrar la sesión activa actual.
 * @param onVerTodosReportes Callback para listar todos los reportes del usuario.
 * @param onAbrirUltimoReporte Callback para consultar el detalle del último reporte usando su folio.
 * @param usuarioAutenticado Flag que indica si hay una sesión activa en la aplicación.
 */
@Composable
fun PantallaInicio(
    modifier: Modifier = Modifier,
    mostrarNotificaciones: Boolean = false,

    // Parte para la visualizacion del ultimo reporte
    ultimoReporteFolio :String? = null,
    ultimoReporteEstatus: String? = null,



    //viewModel: ReporteVM = viewModel(),
    accionBoton: () -> Unit = {},
    onNavegarAReporte: () -> Unit = {},
    onNavegarAPerfil: () -> Unit = {},
    onNavegarANotificaciones: () -> Unit = {},
    onQueSituacionesReportar: () -> Unit = {},
    onConoceSIPINNA: () -> Unit = {},
    onBuscarReporte: () -> Unit = {},

    // Este es cuando esta registrado
    onNavegarALogin: () -> Unit = {},
    onNavegarCrearCuenta:() -> Unit = {},
    onCerrarSesion:() -> Unit = {},

    // Estas son las navegaciones de la tarjeta
    onVerTodosReportes : () -> Unit = {},
    onAbrirUltimoReporte:(String) -> Unit = {},

    usuarioAutenticado: Boolean = false,
) {
    Box(
        modifier = modifier
            .background(Color(0xFFDDEEFB))
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 15.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, bottom = 23.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.imagensippina),
                    contentDescription = "Logo Sippinna",
                    modifier = Modifier.size(130.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (usuarioAutenticado) {
                        IconButton(
                            onClick = onNavegarANotificaciones
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Notifications,
                                contentDescription = "Notificaciones",
                                tint = colorMorado,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }

                    IconButton(onClick = onBuscarReporte) {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "Buscar reporte",
                            tint = colorMorado,
                            modifier = Modifier.size(38.dp)
                        )
                    }

                    IconButton(onClick = onNavegarAPerfil) {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = "Perfil",
                            tint = colorMorado,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }

            //Distribución diferente según el estado de sesión
            Spacer(
                modifier = Modifier.weight(
                    if (usuarioAutenticado) 0.45f else 0.70f
                )
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "¡Hola!",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Gracias por ayudar a proteger a\nniños, niñas y adolescentes.",
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    lineHeight = 22.sp,
                    color = Color.Black
                )
            }

            Spacer(
                modifier = Modifier.weight(
                    if (mostrarNotificaciones) 0.55f else 1.30f
                )
            )


            if (usuarioAutenticado) {
                Spacer(modifier = Modifier.height(30.dp))
                Card(
                    onClick = {
                        if (ultimoReporteFolio != null) {
                            onAbrirUltimoReporte(ultimoReporteFolio)
                        } else {
                            onVerTodosReportes()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(155.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFCADFEA)
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 3.dp
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(18.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Tus reportes",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color.Black
                            )

                            TextButton(
                                onClick = onVerTodosReportes
                            ) {
                                Text(
                                    text = "Ver todos >",
                                    fontSize = 14.sp,
                                    color = Color.Black
                                )
                            }
                        }

                        if (ultimoReporteFolio != null) {
                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = ultimoReporteFolio,
                                fontSize = 23.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorMorado
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            Text(
                                text = ultimoReporteEstatus ?: "Reporte en proceso",
                                fontSize = 12.sp,
                                color = Color.DarkGray,
                                modifier = Modifier.align(Alignment.End)
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Aún no tienes reportes registrados",
                                    fontSize = 15.sp,
                                    color = Color.DarkGray,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(40.dp))
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if(!usuarioAutenticado){
                    ElevatedButton(
                        onClick = { onNavegarALogin() },
                        modifier = Modifier
                            .height(50.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp))
                    {
                        Text(
                            text = "Iniciar Sesión / Crear Cuenta",
                            color = colorMorado,
                            fontSize = 18.sp
                        )
                    }
                }


                ElevatedButton(
                    onClick = { onNavegarAReporte() },
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = colorMorado
                    )
                ) {
                    Text(
                        text = if(usuarioAutenticado) "Generar un reporte " else "Generar un reporte anónimo",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }

                ElevatedButton(
                    onClick = onQueSituacionesReportar,
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = "¿Qué situaciones debo de reportar?",
                        fontSize = 18.sp,
                        color = colorMorado
                    )
                }

                ElevatedButton(
                    onClick = onConoceSIPINNA,
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = "Conoce más de SIPINNA",
                        fontSize = 18.sp,
                        color = colorMorado
                    )
                }

                ElevatedButton(
                    onClick = { accionBoton() },
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = Color(0xFFC01B1E)
                    )
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "EMERGENCIA",
                            fontSize = 18.sp,
                            color = Color(0xFFDDEEFB),
                            fontWeight = FontWeight.Bold
                        )
//
//                        Spacer(modifier = Modifier.height(-1.dp))
//
//                        Text(
//                            text = "Contacto de emergencia de SIPINNA",
//                            fontSize = 12.sp,
//                            color = Color(0xFFDDEEFB),
//                            fontWeight = FontWeight.Bold
//                        )
                    }
                }
            }
        }
    }
}

/**
 * Previsualización de la pantalla [PantallaInicio] en el entorno de desarrollo de Android Studio.
 */

@Preview(
    name = "Sin iniciar sesión",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun PreviewInvitado() {
    SIPPINAAplicacionTheme {
        PantallaInicio(
            usuarioAutenticado = false
        )
    }
}

@Preview(
    name = "Usuario autenticado",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun PreviewAutenticado() {
    SIPPINAAplicacionTheme {
        PantallaInicio(
            usuarioAutenticado = true,
            ultimoReporteFolio = null
        )
    }
}