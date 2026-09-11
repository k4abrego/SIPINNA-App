package com.example.sippina_aplicacion.view.perfilusuario

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.R
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme


@Composable
fun VerListaReportes(
    onAtras: () -> Unit = {},
    onBuscarClick: () -> Unit = {},
    onReporteClick: (String) -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize())
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp, 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                contentAlignment = Alignment.Center
            )
            {
                FilledIconButton(
                    onClick = onAtras,
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.CenterStart),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = Color.Blue,
                        contentColor = Color.White
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
                    painter = painterResource(id = com.example.sippina_aplicacion.R.drawable.sipinnalogosinfondo),
                    contentDescription = "LogoSippina",
                    modifier = Modifier.size(120.dp)
                )

            }
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Reportes ",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedCard(
                onClick = onBuscarClick,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.5.dp, Color.Blue),
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
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar",
                        tint = Color.Blue,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Buscar por folio y código",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Blue

                    )


                }


            }
            Spacer(modifier = Modifier.height(20.dp))

            Column(modifier = Modifier.fillMaxWidth()){
                Text(
                    text = "Hoy",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 4.dp, bottom = 6.dp)
                )

                ItemReporteCard(
                    folio = "REP-1234-1234",
                    icono = Icons.AutoMirrored.Filled.Send,
                    bordeColor = Color.Blue,
                    grosorBorde = 2.5.dp,
                    onClick = { onReporteClick("REP-1234-1234") }
                )

            }
            Spacer(modifier = Modifier.height(20.dp))

            Column(modifier = Modifier.fillMaxWidth()){
                Text(
                    text = "Anteriores",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 4.dp, bottom = 6.dp)
                )

                ItemReporteCard(
                    folio = "REP-1234-1235",
                    icono = Icons.Default.History,
                    bordeColor = Color.Blue,
                    onClick = { onReporteClick("REP-1234-1235") })

                Spacer(modifier = Modifier.height(12.dp))

                ItemReporteCard(
                    folio = "REP-1234-1236",
                    icono = Icons.Default.History,
                    bordeColor = Color.Blue,
                    onClick = { onReporteClick("REP-1234-1236") }
                )

                Spacer(modifier = Modifier.height(12.dp))
                ItemReporteCard(
                    folio = "REP-1234-1235",
                    icono = Icons.Default.Flag,
                    bordeColor = Color.Blue,
                    onClick = { onReporteClick("REP-1234-1235") }
                )


            }
            Spacer(modifier = Modifier.height(28.dp))

            HorizontalDivider(
                thickness = 1.dp,
                color = Color.Blue.copy(alpha = 0.5f)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Estado de tu reporte",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ItemEstadoLeyenda(
                    etiqueta = "Enviado",
                    icono = Icons.AutoMirrored.Filled.Send,
                    colorFondo = Color.Blue
                )
                ItemEstadoLeyenda(
                    etiqueta = "En proceso",
                    icono = Icons.Default.History,
                    colorFondo = Color.Blue
                )
                ItemEstadoLeyenda(
                    etiqueta = "En revisión",
                    icono = Icons.Default.Flag,
                    colorFondo = Color.Blue
                )
                ItemEstadoLeyenda(
                    etiqueta = "Cerrado",
                    icono = Icons.Default.Check,
                    colorFondo = Color.Blue
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
    bordeColor: Color,
    grosorBorde: androidx.compose.ui.unit.Dp = 1.5.dp,
    onClick: () -> Unit
) {
    OutlinedCard(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(grosorBorde, bordeColor),
        colors = CardDefaults.outlinedCardColors(containerColor = Color.Gray),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = folio,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue,
                modifier = Modifier.padding(start = 12.dp)
            )

            Box(
                modifier = Modifier.size(40.dp),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    shape = androidx.compose.foundation.shape.CircleShape,
                    color = Color.Blue,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(contentAlignment = Alignment.Center) {
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
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        Surface(
            shape = androidx.compose.foundation.shape.CircleShape,
            color = colorFondo,
            modifier = Modifier.size(38.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
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

