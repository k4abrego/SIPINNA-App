package com.example.sippina_aplicacion.view.PantallasReporte

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sippina_aplicacion.R
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme
import com.example.sippina_aplicacion.viewmodel.ReporteVM

private val FondoMorado = Color(0xFF381EC9)

@Composable
fun ReporteGenerado(
    viewModel: ReporteVM = viewModel(),
    modifier: Modifier = Modifier,
    folio: String = "ATJ-2026-0823",
    codigoPrivado: String = "KEFD-2P32",
    onVolverInicio: () -> Unit = {}
) {
    // Suscripcion al estado de ViewModel para leer los datos globales
    val state by viewModel.uiState.collectAsState()

    // Obtención de los datos con valor de respaldo
    val folioMostrar = folio.ifEmpty { "ATJ-2026-0823" }
    val codigoMostrar = codigoPrivado.ifEmpty { "KEFD-2P32" }






    val colorMorado = Color(0xFF381EBD)
    val colorFondoBotonSiguiente = Color(0xFFD3E4F4)
    val colorTextoBotonSiguiente = Color(0xFF3B719F)
    val colorFondoBotonAtras = Color(0xFFE2E2E2)
    val colorTextoBotonAtras = Color(0xFF5A5A5A)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorMorado)
            .padding(horizontal = 28.dp, vertical = 24.dp)
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally

    )
    {
        Image(
            painter = painterResource(id = R.drawable.imagensippina),
            contentDescription = "Logo Sippina",
            modifier = Modifier.size(100.dp)
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
                    color = colorMorado
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = folio,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = colorMorado
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
                    text = folioMostrar,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorMorado
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = codigoMostrar,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = colorMorado
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Guarda el código para dar seguimiento a tu reporte",                    color = colorMorado,
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