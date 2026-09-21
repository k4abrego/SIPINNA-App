package com.example.sippina_aplicacion.view.VistasExtra

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.EscalatorWarning
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Report
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedCard
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
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme

@Composable
fun QueSituacionesReportar(
    onAtras: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize())
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEAF4FC))
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                FilledIconButton(
                    onClick = onAtras,
                    modifier = Modifier.size(50.dp)
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


            Text(
                text = "¿Qué situaciones debo reportar?",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3D27C9),
                textAlign = TextAlign.Center,
                lineHeight = 38.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Si observas alguna situación que pueda poner en riesgo a un niño, niña o adolescente puedes hacer un reporte.",
                fontSize = 15.sp,
                lineHeight = 17.sp,
                color = Color.DarkGray,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(26.dp))

            OutlinedCard(
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.5.dp, Color(0xFF3D27C9)),
                colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(95.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        imageVector = Icons.Outlined.EscalatorWarning,
                        contentDescription = "Menores de edad solos",
                        tint = Color(0xFF3D27C9),
                        modifier = Modifier.size(32.dp)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = "Menores de edad solos",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "En vía pública, transporte o espacios públicos sin compañía de una persona adulta.",
                            fontSize = 13.sp,
                            lineHeight = 15.sp,
                            color = Color.DarkGray
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedCard(
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.5.dp, Color(0xFF3D27C9)),
                colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(95.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Report,
                        contentDescription = "Posible situación de violencia",
                        tint = Color(0xFF3D27C9),
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Posible situación de violencia",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Maltrato físico, psicológico, verbal o sexual.",
                            fontSize = 13.sp,
                            lineHeight = 15.sp,
                            color = Color.DarkGray
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedCard(
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.5.dp, Color(0xFF3D27C9)),
                colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(95.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Home,
                        contentDescription = "Posible situación de abandono",
                        tint = Color(0xFF3D27C9),
                        modifier = Modifier.size(32.dp)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Posible situación de abandono",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Sin cuidados básicos o sin una persona responsable.",
                            fontSize = 13.sp,
                            lineHeight = 15.sp,
                            color = Color.DarkGray
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedCard(
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.5.dp, Color(0xFF3D27C9)),
                colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(95.dp)
            ){
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Warning,
                        contentDescription = "Riesgo a su integridad",
                        tint = Color(0xFF3D27C9),
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Riesgo a su integridad",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Peligro, explotación, trabajo infantil o trata.",
                            fontSize = 13.sp,
                            lineHeight = 15.sp,
                            color = Color.DarkGray
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "Cualquier hecho que vulnere sus derechos o seguridad.",
                fontSize = 15.sp,
                lineHeight = 17.sp,
                color = Color.DarkGray,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true
)

@Composable
fun QueSituacionesReportarPreview(){
    SIPPINAAplicacionTheme {
        QueSituacionesReportar()
    }
}