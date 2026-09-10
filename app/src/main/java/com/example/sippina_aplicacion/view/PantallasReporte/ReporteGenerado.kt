package com.example.sippina_aplicacion.view.PantallasReporte

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sippina_aplicacion.R
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme

private val FondoMorado = Color(0xFF381EC9)

@Composable
fun ReporteGenerado(
    folio: String = "ATJ-2026-0823",
    codigoPrivado: String = "KEFD-2P32",
    onVolverInicio: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue)
            .padding(horizontal = 28.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    )
    {
        Image(
            painter = painterResource(id = R.drawable.sipinnalogosinfondo),
            contentDescription = "Logo Sippina",
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "¡Gracias!",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "¡Tu reporte ha sido registrado con\néxito!",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(15.dp))

        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Éxito",
            tint = Color.White,
            modifier = Modifier.size(72.dp)
        )

        Spacer(modifier = Modifier.height(25.dp))

        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.LightGray),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Folio",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Blue
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = folio,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Blue
                )


            }


        }
        Spacer(modifier = Modifier.height(24.dp))
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.LightGray),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Código Privado",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Blue
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = codigoPrivado,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Blue
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Guarda el código para dar seguimiento de tu reporte",
                    color = Color.Blue,
                    textAlign = TextAlign.Center
                )


            }


        }
        Spacer(modifier = Modifier.height(36.dp))
        Text(
            text = "Página de inicio",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .clickable { onVolverInicio() }
                .padding(8.dp)
        )




    }



}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Reporte Generado con Éxito"
)
@Composable
fun ReporteExitosoPreview() {
    SIPPINAAplicacionTheme {
        ReporteGenerado()
    }
}