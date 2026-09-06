package com.example.sippina_aplicacion.view.reportes

import android.R.attr.id
import android.R.attr.start
import android.view.View
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FloatingActionButtonDefaults.elevation
import androidx.compose.material3.FloatingActionButtonDefaults.shape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sippina_aplicacion.R
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme
import com.example.sippina_aplicacion.view.PantallasReporte.BarraDePasos
import com.example.sippina_aplicacion.view.colorAzul
import com.example.sippina_aplicacion.viewmodel.ReporteVM


@Composable
fun Detalles(
    viewModel: ReporteVM = viewModel(), modifier: Modifier = Modifier, onSiguiente: () -> Unit = {},
    onAtras: () -> Unit = {}
) { // Estado de acuerdo a los campos interactivos
// Estados locales para los campos interactivos
    var horaSlider by remember { mutableFloatStateOf(12.16f) }
    var cantidadMenores by remember { mutableIntStateOf(1) }
    var opcionAdulto by remember { mutableStateOf("Sí") }
    var descripcionText by remember { mutableStateOf("") }
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
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Cuentanos qué pasó?", color = Color.Blue, fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(14.dp))

        BarraDePasos(pasoActual = 1)
        Spacer(modifier = Modifier.height(20.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Text(text = "Horario aprox", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Box(modifier = Modifier.background(Color.Blue, shape = RoundedCornerShape(6.dp))) {
                    Text(
                        text = "12",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )


                }

            }
            Slider(
                value = horaSlider,
                onValueChange = { horaSlider = it },
                valueRange = 0f..24f,
                colors = SliderDefaults.colors(
                    thumbColor = Color.Blue,
                    activeTrackColor = Color.Blue,
                    inactiveTrackColor = Color.Blue
                )
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("00:00", fontSize = 11.sp, color = Color.Gray)
            Text("12:00", fontSize = 11.sp, color = Color.Gray)
            Text("24:00", fontSize = 11.sp, color = Color.Gray)

        }
        Spacer(modifier = modifier.height(20.dp))
        Column(modifier = modifier.fillMaxWidth()) {
            Text(
                text = "Cantidad de menores involucrados",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )

            Spacer(modifier = modifier.height(15.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            )
            {
                OutlinedCard(
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.5.dp, color = Color.Blue),
                    colors = CardDefaults.outlinedCardColors(Color.Red),
                    modifier = Modifier
                        .weight(1f)
                        .height(14.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart)
                    {
                        Text(
                            text = cantidadMenores.toString(),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                    OutlinedIconButton(
                        onClick = { cantidadMenores++ },
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.5.dp, Color.Blue),
                        modifier = Modifier.size(44.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Aumentar",
                            tint = Color.White
                        )
                    }



                }


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
fun DondeOcurrioPreview() {
    SIPPINAAplicacionTheme {
        Detalles()
    }
}