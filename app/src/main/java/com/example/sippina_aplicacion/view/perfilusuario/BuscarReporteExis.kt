package com.example.sippina_aplicacion.view.perfilusuario

import android.graphics.fonts.Font
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
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.R
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme

@Composable
fun BuscarReporteExistente(onAtras: () -> Unit = {}, onVerDetalleReporte: (String) -> Unit = {}) {
    var folio by remember { mutableStateOf("REP-1234-1234") }
    var codigoSeguridad by remember { mutableStateOf("KEFD-2P32") }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
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
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Atras",
                        modifier = Modifier.size(22.dp)
                    )
                }
                Image(
                    painter = painterResource(id = com.example.sippina_aplicacion.R.drawable.imagensippina),
                    contentDescription = "LogoSippina",
                    modifier = Modifier.size(120.dp)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Reportes",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Ingrese el folio y código de\nSeguridad para dar seguimiento\ny detalles de tú reporte",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )
            Spacer(modifier = Modifier.height(28.dp))

            CampoFormularioBusqueda(
                etiqueta = "Folio",
                valor = folio,
                onValorChange = { folio = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            CampoFormularioBusqueda(
                etiqueta = "Código de seguridad",
                valor = codigoSeguridad,
                onValorChange = { codigoSeguridad = it }
            )

            Spacer(modifier = Modifier.height(16.dp))
            Column(modifier = Modifier.fillMaxWidth())
            {
                Text(
                    text = "Resultados:",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
                )
                OutlinedCard(
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.5.dp, Color.Blue),
                    colors = CardDefaults.outlinedCardColors(
                        containerColor = Color.LightGray
                    ),
                    modifier = Modifier.fillMaxWidth()

                )
                {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    )
                    {
                        Text(
                            text = folio.ifEmpty { "REP-1234-1234" },
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Blue,
                            modifier = Modifier.padding(start = 12.dp)
                        )

                        FilledIconButton(
                            onClick = { onVerDetalleReporte(folio) },
                            modifier = Modifier.size(40.dp),
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = Color.Blue,
                                contentColor = Color.White)) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = "Ver detalles del reporte",
                                modifier = Modifier.size(18.dp)
                            )


                        }

                    }


                }

            }
            Spacer(modifier = Modifier.height(44.dp))
            val textoPie = buildAnnotatedString {
                append("Recuerda que el folio y el código de seguridad es único para cada caso. Cualquier aclaración contactar a las autoridades de ")
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append("SIPINNA")
                }
            }
            Text(
                text = textoPie,
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                lineHeight = 16.sp,
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            Spacer(modifier = Modifier.height(28.dp))




        }

    }


}


@Composable
private fun CampoFormularioBusqueda(
    etiqueta: String,
    valor: String,
    onValorChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = etiqueta,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        OutlinedTextField(
            value = valor,
            onValueChange = onValorChange,
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = Color.Blue,
                unfocusedBorderColor = Color.Gray,
                focusedTextColor = Color.Blue,
                unfocusedTextColor = Color.Blue
            ),
            modifier = Modifier
                .fillMaxWidth(0.88f)
                .height(52.dp)
        )
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Buscar Reporte Existente - Material 3"
)
@Composable
fun BuscarReportePreview() {
    SIPPINAAplicacionTheme {
        BuscarReporteExistente()
    }
}
