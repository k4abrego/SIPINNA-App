package com.example.sippina_aplicacion.view.VistasExtra

import android.content.Intent
import androidx.compose.ui.layout.ContentScale
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme
import com.example.sippina_aplicacion.R

data class RedSocioformador(
    val nombre: String,
    val url: String
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ConoceSIPINNA(
    onAtras: () -> Unit = {}
) {
    val context = LocalContext.current

    fun abrirEnlace(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }

    val redesSocioformadores = listOf(
        RedSocioformador("Facebook", "https://www.facebook.com/GobAtizapan"),
        RedSocioformador("Instagram", "https://www.instagram.com/gob_atizapan/"),
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEAF4FC))
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 10.dp),
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
                    painter = painterResource(id = R.drawable.logorieti),
                    contentDescription = "Logo SIPINNA",
                    modifier = Modifier.size(120.dp)
                )
            }

            Spacer(modifier = Modifier.height(0.dp))

            Text(
                text = "SIPINNA",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3D27C9)
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "SIPINNA es el Sistema de Protección Integral de Niñas, Niños y Adolescentes a efecto de que el Estado, en sus tres órdenes de gobierno, cumpla con su responsabilidad de garantizar los derechos de niñez y adolescencia.",
                fontSize = 15.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Nuestro Objetivo",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3D27C9),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Garantizar la protección integral promoviendo el respeto de sus derechos, previniendo situaciones de riesgo y coordinando acciones entre instituciones, familias y sociedad para asegurar su bienestar y desarrollo en un entorno seguro.",
                fontSize = 17.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "¿Qué es la Ruta RIETI?",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3D27C9),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Left
            )

            Spacer(modifier = Modifier.height(10.dp))


            Text(
                text = "La ruta RIETI es la Ruta Intermunicipal para la Erradicación del Trabajo Infantil. Es una iniciativa regional surgida principalmente en los municipios del Valle de México (Estado de México).",
                fontSize = 17.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Left
            )
            Spacer(modifier = Modifier.height(10.dp))

            Image(
                painter = painterResource(id = R.drawable.logo_rieti_2),
                contentDescription = "Imagen Ruta RIETI",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(14.dp))
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Relación con SIPINNA",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3D27C9),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "La ruta RIETI opera bajo el paraguas y los principios de SIPINNA, los municipios en conjunto con los sistemas DIF locales ejecutan las acciones de rescate, atención psicológica, reintegración escolar y apoyo a familias en situación de vulnerabilidad económica.",
                fontSize = 17.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "¿Cómo ayudamos?",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3D27C9),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Brinda orientación, facilita el reporte de situaciones de riesgo, promueve la prevención y da seguimiento a los casos para asegurar su atención.",
                fontSize = 17.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = R.drawable.sippina_logo_3),
                contentDescription = "Imagen de apoyo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(14.dp))
            )

            Spacer(modifier = Modifier.height(35.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.sippina_logo_3),
                            contentDescription = "Logo Footer",
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "SIPINNA",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Normal
                        )
                    }

                    redesSocioformadores.forEach { red ->
                        Text(
                            text = red.nombre,
                            fontSize = 12.sp,
                            color = Color(0xFF3D27C9),
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .padding(horizontal = 6.dp)
                                .clickable { abrirEnlace(red.url) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ConocePreview() {
    SIPPINAAplicacionTheme {
        ConoceSIPINNA()
    }
}