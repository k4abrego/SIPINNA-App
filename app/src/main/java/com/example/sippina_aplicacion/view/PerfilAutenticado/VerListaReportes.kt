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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sippina_aplicacion.R
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme


@Composable
fun VerListaReportes(
    onAtras: () -> Unit = {},
    onBuscarClick: () -> Unit = {},
    onReporteClick: (String) -> Unit = {}
) {
    val morado = Color(0xFF381DC3)
    val fondo = Color(0xFFEAF4FC)

    Box(modifier = Modifier.fillMaxSize())
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(fondo)
                .padding(24.dp, 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            )
            {
                FilledIconButton(
                    onClick = onAtras,
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.CenterStart),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = fondo,
                        contentColor = Color(0xFF3D27C9)
                    )
                )
                {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Atrás",
                        modifier = Modifier.size(22.dp)
                    )


                }
                Image(
                    painter = painterResource(
                        id = com.example.sippina_aplicacion.R.drawable.imagensippina),
                    contentDescription = "Logo SIPINNA",
                    modifier = Modifier.size(120.dp)
                )

            }
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Reportes ",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                color = morado,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedCard(
                onClick = onBuscarClick,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(width = 1.5.dp, color = morado),
                colors = CardDefaults.outlinedCardColors(containerColor = fondo),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar",
                        tint = morado,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Buscar por folio y código",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = morado
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Hoy",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 4.dp, bottom = 6.dp)
                )

                ItemReporteCard(
                    folio = "REP-1234-1234",
                    icono = Icons.AutoMirrored.Filled.Send,
                    colorIcono = Color(0xFF9D91D7),
                    bordeColor = morado,
                    grosorBorde = 2.5.dp,
                    onClick = { onReporteClick("REP-1234-1234") }
                )

            }
            Spacer(modifier = Modifier.height(20.dp))

            Column(modifier = Modifier.fillMaxWidth()){
                Text(
                    text = "Anteriores",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 4.dp, bottom = 6.dp)
                )

                ItemReporteCard(
                    folio = "REP-1234-1235",
                    icono = Icons.Default.History,
                    colorIcono = Color(0xFF7A65E0),
                    bordeColor = morado,
                    onClick = { onReporteClick("REP-1234-1235") })

                Spacer(modifier = Modifier.height(12.dp))

                ItemReporteCard(
                    folio = "REP-1234-1236",
                    icono = Icons.Default.History,
                    colorIcono = Color(0xFF7A65E0),
                    bordeColor = morado,
                    onClick = { onReporteClick("REP-1234-1236") }
                )

                Spacer(modifier = Modifier.height(12.dp))

                ItemReporteCard(
                    folio = "REP-1234-1235",
                    icono = Icons.Default.Flag,
                    colorIcono = Color(0xFF5F43E4),
                    bordeColor = morado,
                    onClick = { onReporteClick("REP-1234-1235") }
                )
            }

            Spacer(modifier = Modifier.height(45.dp))

            HorizontalDivider(
                thickness = 1.dp,
                color = morado.copy(alpha = 0.5f)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Estado de tu reporte",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ItemEstadoLeyenda(
                    etiqueta = "Enviado",
                    icono = Icons.AutoMirrored.Filled.Send,
                    colorFondo = Color(0xFF9D91D7)
                )
                ItemEstadoLeyenda(
                    etiqueta = "En proceso",
                    icono = Icons.Default.History,
                    colorFondo = Color(0xFF7A65E0)
                )
                ItemEstadoLeyenda(
                    etiqueta = "En revisión",
                    icono = Icons.Default.Flag,
                    colorFondo = Color(0xFF5F43E4)
                )
                ItemEstadoLeyenda(
                    etiqueta = "Cerrado",
                    icono = Icons.Default.Check,
                    colorFondo = Color(0xFF381DC3)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun ItemReporteCard(
    folio: String,
    icono: ImageVector,
    colorIcono: Color,
    bordeColor: Color,
    grosorBorde: Dp = 1.5.dp,
    onClick: () -> Unit
) {
    var seleccionado by remember {
        mutableStateOf(false)
    }

    val colorFondo by animateColorAsState(
        targetValue = if (seleccionado) { Color(0xFFE0DBFA) }
        else {
            Color(0xFFEAF4FC)
        },
        animationSpec = tween(
            durationMillis = 250
        ),
        label = "AnimacionTarjetaReporte"
    )

    OutlinedCard(
        onClick = {
            seleccionado = true
            onClick()
        },
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(grosorBorde, bordeColor),
        colors = CardDefaults.outlinedCardColors(containerColor = colorFondo),
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = folio,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF381DC3),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            )
            Surface(
                shape = CircleShape,
                color = colorIcono,
                modifier = Modifier.size(42.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icono,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ItemEstadoLeyenda(
    etiqueta: String,
    icono: ImageVector,
    colorFondo: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = etiqueta,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        Surface(
            shape = CircleShape,
            color = colorFondo,
            modifier = Modifier.size(38.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icono,
                    contentDescription = etiqueta,
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Lista de Reportes - Material 3"
)
@Composable
fun ListaReportesPreview() {
    SIPPINAAplicacionTheme {
        VerListaReportes()
    }
}

