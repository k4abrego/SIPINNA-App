package com.example.sippina_aplicacion.view

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
//import com.example.sippina_aplicacion.viewmodel.ReporteVM
val colorMorado = Color(0xFF381DC3)

@Composable
fun PantallaInicio(
    modifier: Modifier = Modifier,
    mostrarNotificaciones: Boolean = false,
    //viewModel: ReporteVM = viewModel(),
    accionBoton: () -> Unit = {},
    onNavegarAReporte: () -> Unit = {},
    onNavegarAPerfil: () -> Unit = {},
    onNavegarANotificaciones: () -> Unit = {},
    onQueSituacionesReportar: () -> Unit = {},
    onConoceSIPINNA: () -> Unit = {},
    onBuscarReporte: () -> Unit = {}
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
                    if (mostrarNotificaciones) {
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

            Text(
                text = "¡Hola!",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Gracias por ayudar a proteger a\nlos niños, niñas y adolescentes.",
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(250.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                ElevatedButton(onClick = { onNavegarAReporte() },
                    modifier = Modifier.height(50.dp).fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = colorMorado
                    )
                ) {
                    Text(
                        text = "Generar un reporte",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                ElevatedButton(onClick = onQueSituacionesReportar ,
                    modifier = Modifier.height(50.dp).fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = Color(0xCCDDE9))
                ) {
                    Text(
                        text = "¿Qué situaciones debo de reportar?",
                        fontSize = 18.sp,
                        color = colorMorado,
                        fontWeight = FontWeight.Bold
                    )
                }

                ElevatedButton(onClick = onConoceSIPINNA,
                    modifier = Modifier.height(50.dp).fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = Color(0xCCDDE9))
                ) {
                    Text(
                        text = "Conoce más de SIPINNA",
                        fontSize = 18.sp,
                        color = colorMorado,
                        fontWeight = FontWeight.Bold
                    )
                }

                ElevatedButton(onClick = { accionBoton() },
                    modifier = Modifier.height(50.dp).fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = Color(0xFFC01B1E)
                    )
                ) {
                    Text(
                        text = "EMERGENCIA",
                        fontSize = 18.sp,
                        color = Color(0xFFDDEEFB),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview (
    showBackground = true,
    showSystemUi = true
)

@Composable
fun PantallaPreview () {
    SIPPINAAplicacionTheme {
        PantallaInicio()
    }
}