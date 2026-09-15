package com.example.sippina_aplicacion.view.PerfilAutenticado

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
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
import com.example.sippina_aplicacion.R
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme

@Composable
fun Borradores(
    modifier: Modifier = Modifier,
    onAtras: () -> Unit = {},
    onContinuar: () -> Unit = {},
    onEliminar: () -> Unit = {}
) {

    Column(
        modifier = modifier.fillMaxSize().background(Color(0xFFEAF4FC)).padding(horizontal = 24.dp, vertical = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            OutlinedButton(
                onClick = onAtras,
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF3D27C9)
                )
            ) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Regresar",
                    modifier = Modifier.size(30.dp)
                )
            }

            Image(
                painter = painterResource(
                    id = R.drawable.imagensippina
                ),
                contentDescription = "Logo SIPINNA",
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.size(50.dp))
        }

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "Continuar reporte",
            color = Color(0xFF3D20C9),
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Si tiene un reporte en progreso, puedes\ncontinuar desde donde lo dejaste",
            color = Color.Gray,
            fontSize = 15.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(30.dp))

        BorradorCard(
            paso = "Descripción del menor",
            fecha = "Creado: Martes 31 agosto 2026, 12:36 pm",
            onContinuar = onContinuar,
            onEliminar = onEliminar
        )

        Spacer(modifier = Modifier.height(40.dp))

        BorradorCard(
            paso = "Marcar ubicación",
            fecha = "Creado: Martes 1 septiembre 2026, 17:33 pm",
            onContinuar = onContinuar,
            onEliminar = onEliminar
        )
    }
}

@Composable
fun BorradorCard(
    paso: String,
    fecha: String,
    onContinuar: () -> Unit = {},
    onEliminar: () -> Unit = {}
) {

    Card(
        modifier = Modifier.fillMaxWidth().height(175.dp),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier.fillMaxSize().padding(15.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Reporte en progreso",
                    tint = Color(0xFF3D20C9),
                    modifier = Modifier.size(28.dp)
                )

                Spacer(modifier = Modifier.size(5.dp))

                Column {

                    Text(
                        text = "Reporte en progreso",
                        color = Color(0xFF3D20C9),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = fecha,
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Continuar en $paso",
                color = Color.Gray,
                fontSize = 10.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Paso",
                    tint = Color(0xFF3D20C9),
                    modifier = Modifier.size(24.dp)
                )

                Text(
                    text = "—",
                    color = Color.Gray
                )

                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Paso",
                    tint = Color(0xFF3D20C9),
                    modifier = Modifier.size(24.dp)
                )

                Text(
                    text = "—",
                    color = Color.Gray
                )

                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Paso",
                    tint = Color.LightGray,
                    modifier = Modifier.size(24.dp)
                )

                Text(
                    text = "—",
                    color = Color.Gray
                )

                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Paso",
                    tint = Color.LightGray,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "—",
                    color = Color.Gray
                )

                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Paso",
                    tint = Color.LightGray,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Button(
                    onClick = onEliminar,
                    modifier = Modifier.height(38.dp).weight(1f),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Red
                    )
                ) {

                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        modifier = Modifier.size(15.dp)
                    )

                    Spacer(modifier = Modifier.size(4.dp))

                    Text(
                        text = "Eliminar",
                        fontSize = 11.sp
                    )
                }

                Spacer(modifier = Modifier.size(25.dp))

                Button(
                    onClick = onContinuar,
                    modifier = Modifier
                        .height(38.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF3D20C9)
                    )
                ) {
                    Text(
                        text = "Continuar",
                        fontSize = 11.sp
                    )
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
fun BorradoresPreview() {
    SIPPINAAplicacionTheme {
        Borradores()
    }
}