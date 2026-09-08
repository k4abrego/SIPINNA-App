package com.example.sippina_aplicacion.view.PantallasReporte

import android.R.attr.padding
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults.contentPadding
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sippina_aplicacion.R
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme
import com.example.sippina_aplicacion.view.PantallasReporte.Detalles
import com.example.sippina_aplicacion.viewmodel.ReporteVM

@Composable
fun Detalles2(
    viewModel: ReporteVM = viewModel(),
    modifier: Modifier = Modifier,
    onSiguiente: () -> Unit = {},
    onAtras: () -> Unit = {}
) {
    var edadSeleccionada by remember { mutableStateOf("0-4") }
    var generoSeleccionado by remember { mutableStateOf("Masculino") }
    var descripcionFisica by remember { mutableStateOf("") }
    val opcionesEdad = listOf("0-4", "5-9", "10-14", "15-18")
    val opcionesGenero = listOf("Masculino", "Femenino", "Desconocido")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Image(
            painter = painterResource(id = R.drawable.sipinnalogosinfondo),
            contentDescription = "Logo Sippina",
            modifier = Modifier.size(120.dp)
        )

        BarraDePasos(pasoActual = 2)

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Descripción del menor ", color = Color.Blue, fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            "Descripción : Menor 1",
            fontWeight = FontWeight.Bold,
            color = Color.Blue,
            fontSize = 19.sp
        )
        Spacer(modifier = Modifier.height(35.dp))
        Text(
            text = "Edad aprox",
            textAlign = TextAlign.Left,
            color = Color.Blue,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))


        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp))
        {
            opcionesEdad.forEach { rango ->
                val seleccionado = edadSeleccionada == rango
                OutlinedButton(
                    onClick = { edadSeleccionada = rango },
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.5.dp, Color.Blue),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (seleccionado) Color(0xFFE8F1FC) else Color.Transparent,
                        contentColor = Color.Blue
                    ), contentPadding = PaddingValues(horizontal = 2.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(34.dp)

                ) {
                    Text(text = rango, fontSize = 14.sp, fontWeight = FontWeight.Bold)


                }
            }


        }
        Spacer(modifier = Modifier.height(23.dp))
        Text(text = "Genero")
        Spacer(modifier = Modifier.height(15.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            opcionesGenero.forEach { genero ->
                val seleccionado = generoSeleccionado == genero
                OutlinedButton(
                    onClick = { generoSeleccionado = genero },
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.5.dp, Color.Blue),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (seleccionado) Color(0xFFE8F1FC) else Color.Transparent,
                        contentColor = Color.Blue
                    ),
                    contentPadding = PaddingValues(horizontal = 2.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(34.dp)
                ) {
                    Text(text = genero, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }


            }


        }
        Spacer(modifier = Modifier.height(23.dp))


        Text(text = "Descripción Física")

        OutlinedTextField(
            value = descripcionFisica,
            onValueChange = { descripcionFisica = it },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Blue,
                unfocusedBorderColor = Color.Blue,
                focusedContainerColor = Color(0xFFF7FAFE),
                unfocusedContainerColor = Color(0xFFF7FAFE)
            )
        )
        Spacer(modifier = Modifier.height(85.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
            Button(onClick = {onAtras()}, colors = ButtonDefaults.buttonColors(Color.LightGray, Color.Blue), modifier = Modifier.width(150.dp)){
                Text(text = "Atrás", fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
            Button(onClick = {onSiguiente()},colors = ButtonDefaults.buttonColors(Color.LightGray, Color.Blue), modifier = Modifier.width(150.dp)){
                Text(text = "Siguiente ", fontSize = 13.sp, fontWeight = FontWeight.Bold)


            }


        }

        




    }


}


@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Donde Ocurrió - Diseño Exacto"
)
@Composable
fun Detalles2Preview() {
    SIPPINAAplicacionTheme {
        Detalles2()
    }
}