package com.example.sippina_aplicacion.view.VistasExtra

import android.content.Intent
import androidx.compose.ui.layout.ContentScale
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme
import com.example.sippina_aplicacion.R

@Composable
fun ConoceSIPINNA (
    onAtras: () -> Unit = {}
){
    var mostrarInformacion by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val urlSipinna = "https://www.facebook.com/GobAtizapan"

    Box(modifier = Modifier.fillMaxSize())
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEAF4FC))
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, 10.dp),
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
            Spacer(modifier = Modifier.height(8.dp))

            Image(
                painter = painterResource(id = R.drawable.gobatizapan),
                contentDescription = "Logo Atizapan",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

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
            Spacer(modifier = Modifier.height(25.dp))

            Button(
                onClick = { mostrarInformacion = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3D27C9))
            ) {
                Text(
                    text = "Conoce más",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
        }
        if (mostrarInformacion) {
            AlertDialog(onDismissRequest = {
                    mostrarInformacion = false
                }, title = {
                    Text(
                        text = "Conoce más sobre SIPINNA",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF3D27C9)
                    )
                }, text = {
                    Text(
                        text = "Se abrirá una página en tu navegador para que puedas conocer más sobre SIPINNA.",
                        color = Color.DarkGray
                    )
                },

                confirmButton = {
                    TextButton(
                        onClick = { mostrarInformacion = false
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(urlSipinna)
                            )
                            context.startActivity(intent)
                        }
                    ) {
                        Text(
                            text = "Continuar",
                            color = Color(0xFF3D27C9),
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { mostrarInformacion = false }
                    ) {
                        Text(
                            text = "Cancelar",
                            color = Color.DarkGray
                        )
                    }
                }
            )
        }
    }
}

@Preview (
    showBackground = true,
    showSystemUi = true
)

@Composable
fun ConocePreview (){
    SIPPINAAplicacionTheme() {
        ConoceSIPINNA()
    }
}