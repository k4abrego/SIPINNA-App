package com.example.sippina_aplicacion.view.PerfilAutenticado

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme

@Composable
fun Notificaciones(
    onAtras: () -> Unit = {}
) {
    var notificacionSeleccionada by remember {
        mutableStateOf<Int?>(null)
    }
    Box(modifier = Modifier.fillMaxSize())
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEAF4FC))
                .padding(horizontal = 24.dp, 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                FilledIconButton(
                    onClick = onAtras,
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.CenterStart),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = Color(0xFFEAF4FC),
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
                    painter = painterResource(id = com.example.sippina_aplicacion.R.drawable.imagensippina),
                    contentDescription = "Logo SIPINNA",
                    modifier = Modifier.size(120.dp)
                )
            }
            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Notificaciones",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3D27C9)
            )

            Spacer(modifier = Modifier.height(15.dp))

            OutlinedCard(
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.5.dp, Color(0xFF3D27C9)),
                colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Reporte actualizado",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Tu reporte REP-1234-1234 cambió a En revisión.",
                            fontSize = 12.sp,
                            lineHeight = 15.sp,
                            color = Color.DarkGray
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Hace 10 min",
                            fontSize = 12.sp,
                            color = Color.DarkGray
                        )
                    }

                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Notificación",
                        tint = Color(0xFF3D27C9),
                        modifier = Modifier.size(35.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedCard(
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.5.dp, Color(0xFF3D27C9)),
                colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
                onClick = { notificacionSeleccionada = 2 },
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Caso recibido",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Se recibió correctamente tu reporte con folio REP-1234-1234",
                            fontSize = 12.sp,
                            lineHeight = 15.sp,
                            color = Color.DarkGray
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Ayer",
                            fontSize = 12.sp,
                            color = Color.DarkGray
                        )
                    }
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Notificación",
                        tint = Color(0xFF3D27C9),
                        modifier = Modifier.size(35.dp)
                    )
                }
            }
        }
        if (notificacionSeleccionada != null) {
            AlertDialog(
                onDismissRequest = { notificacionSeleccionada = null },
                title = {
                    Text(
                        text = if (notificacionSeleccionada == 1) {
                            "Reporte actualizado"
                        } else {
                            "Caso recibido"
                               },
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF3D27C9)
                    )
                        }, text = {
                            Text(
                                text = if (notificacionSeleccionada == 1) {
                                    "Tu reporte REP-1234-1234 cambió a \nEn revisión."
                                } else {
                                    "Se recibió correctamente tu reporte con folio REP-1234-1234."
                                       }, color = Color.DarkGray
                            )
                                  },
                confirmButton = {
                    TextButton(
                        onClick = { notificacionSeleccionada = null }
                    ) {
                        Text(
                            text = "Entendido",
                            color = Color(0xFF3D27C9)
                        )
                    }
                }
            )
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