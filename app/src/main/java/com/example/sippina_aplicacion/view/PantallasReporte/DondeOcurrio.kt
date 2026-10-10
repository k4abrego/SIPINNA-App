package com.example.sippina_aplicacion.view.PantallasReporte

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.activity.compose.BackHandler
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sippina_aplicacion.R
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme
import com.example.sippina_aplicacion.viewmodel.ReporteVM
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import com.example.sippina_aplicacion.model.EstadoReporte
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraMoveStartedReason
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

/**
 * Pantalla principal del inicio del reporte (Paso 1: Ubicación).
 *
 * Permite establecer la geolocalización del incidente mediante tres modalidades: seleccionando directamente
 * en un mapa interactivo ([MapaInteractivo]), consultando la ubicación GPS actual en tiempo real mediante
 * [LocationServices.getFusedLocationProviderClient] o ingresando una dirección.
 *
 * Captura el intento de salida con [BackHandler] desplegando un cuadro de diálogo ([AlertDialog]) que guarda
 * automáticamente el borrador actual en el dispositivo antes de abandonar el flujo.
 *
 * @param viewModel Instancia de [ReporteVM] encargada de la gestión del estado del reporte y la persistencia de borradores.
 * @param modifier [Modifier] opcional para modificar el contenedor del diseño.
 * @param onSiguiente Callback ejecutado para continuar hacia la pantalla de detalles del incidente.
 * @param onAtras Callback invocado tras confirmar la salida de la pantalla.
 */
@SuppressLint("MissingPermission")
@Composable
fun DondeOcurrio(
    viewModel: ReporteVM = viewModel(),
    modifier: Modifier = Modifier,
    onSiguiente: () -> Unit = {},
    onAtras: () -> Unit = {}
) {
    val appContext = LocalContext.current
    val state by viewModel.uiState.collectAsState()

    // Este cliente es para obtener la ubicacion GPS en tiempo real
    val fusedLocationClient = remember{
        LocationServices.getFusedLocationProviderClient(appContext)
    }

    LaunchedEffect(Unit) {
        viewModel.iniciarlizarBorradorStorage(appContext)
    }

    var mostrarDialogoSalida by remember {
        mutableStateOf(false)
    }
    BackHandler {
        mostrarDialogoSalida = true
    }

    val colorMorado = Color(0xFF381DC3)
    val colorFondo = Color(0xFFEAF4FC)
    val colorFondoBotonSiguiente = Color(0xFFD3E4F4)
    val colorTextoBotonSiguiente = Color(0xFF3B719F)
    val colorFondoBotonAtras = Color(0xFFE2E2E2)
    val colorTextoBotonAtras = Color(0xFF5A5A5A)

    val coroutineScope = rememberCoroutineScope()


    val opcionUbicacionActual = state.opcionUbicacion.ifEmpty {
        "Seleccionar en el mapa"
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorFondo)
            .imePadding()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        // Este contenido se puede desplazar por si existe un desfase en el tamaño del dispositivo

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(top = 8.dp, bottom = 12.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.imagensippina),
                contentDescription = "Logo SIPINNA",
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Parte del título
            Text(
                text = "¿Dónde ocurrió?",
                color = colorMorado,
                fontSize = 35.sp,
                lineHeight = 38.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))
            // Esta es la barrra de progreso de la pantalla

            BarraDePasos(pasoActual = 0)

            Spacer(modifier = Modifier.height(16.dp))

            // Son las instrucciones a seguir para el usuarui

            Text(
                text = "Puedes seleccionar en el mapa, usar tu ubicación actual o buscar una dirección",
                fontSize = 16.sp,
                lineHeight = 20.sp,
                color = colorMorado,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Esta es para la parte del mapa
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEBEEF8)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                MapaInteractivo(
                    context = appContext,
                    state = state,
                    onUbicacionCambiada = { lat, lng, direccion ->
                        viewModel.setUbicacion(lat, lng, direccion)
                    }
                )
            }

            if (state.ubicacionConfirmada) {
                Spacer(modifier = modifier.height(10.dp))
                Card(
                    modifier = modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(8.dp)
                )
                {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "Direccion: ${state.direccion.ifEmpty { " Sin direccion exacta" }}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorMorado,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = modifier.height(4.dp))
                        Text(
                            text = "Coordenadas: Lat ${
                                String.format(
                                    Locale.US,
                                    "%.5f",
                                    state.latitud
                                )
                            }, Lng ${String.format(Locale.US, "%.5f", state.longitud)}",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }

            }

            Spacer(modifier = Modifier.height(18.dp))

            // Opciones de ubicación

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                val opciones = listOf(
                    "Usar mi ubicación actual",
                    "Seleccionar en el mapa",
                    "Buscar dirección"
                )

                opciones.forEach { opcion ->

                    val seleccionado = opcionUbicacionActual == opcion

                    OutlinedButton(
                        onClick = {
                            viewModel.seleccionarOpcionUbicacion(opcion)
                            if(opcion == "Usar mi ubicación actual")
                            {
                                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                                    if(location != null){
                                        coroutineScope.launch(Dispatchers.IO){
                                            val direccion = obtenerDireccionTexto(
                                                appContext,
                                                location.latitude,
                                                location.longitude
                                            )
                                            withContext(Dispatchers.Main){
                                                viewModel.setUbicacion(location.latitude, location.longitude, direccion)

                                            }
                                        }
                                    }
                                }
                            }
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),

                        shape = CircleShape,

                        border = if (seleccionado) {
                            null
                        } else {
                            BorderStroke(
                                width = 1.5.dp,
                                color = colorMorado
                            )
                        },

                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (seleccionado) {
                                colorMorado
                            } else {
                                Color.Transparent
                            },

                            contentColor = if (seleccionado) {
                                Color.White
                            } else {
                                colorMorado
                            }
                        )
                    ) {

                        Text(
                            text = opcion,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }


        // Estos son los botones de la parte inferior

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { mostrarDialogoSalida = true },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorFondoBotonAtras,
                    contentColor = colorTextoBotonAtras
                ),

                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            ) {

                Text(
                    text = "Atrás",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Button(
                onClick = onSiguiente,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorFondoBotonSiguiente,
                    contentColor = colorTextoBotonSiguiente
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            ) {

                Text(
                    text = "Siguiente",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }


    if (mostrarDialogoSalida) {

        AlertDialog(
            onDismissRequest = {
                mostrarDialogoSalida = false
            },

            title = {
                Text(
                    text = "¿Seguro que deseas salir?",
                    color = colorMorado,
                    fontWeight = FontWeight.Bold
                )
            },

            text = {
                Text(
                    text = "Tu progreso quedará guardado en Borradores, " +
                            "disponibles en tu perfil en este dispositivo."
                )
            },

            confirmButton = {

                Button(
                    onClick = {
                        mostrarDialogoSalida = false
                        viewModel.guardarBorrador()
                        onAtras()
                    },

                    colors = ButtonDefaults.buttonColors(containerColor = colorMorado)
                ) {
                    Text(text = "Sí, guardar y salir")
                }
            },

            dismissButton = {
                OutlinedButton(
                    onClick = { mostrarDialogoSalida = false }
                ) {

                    Text(text = "No, continuar")
                }
            },
            shape = RoundedCornerShape(20.dp),
            containerColor = Color(0xFFEAF4FC)
        )
    }
}


/**
 * Subcomponente Composable que renderiza un mapa interactivo de Google Maps ([GoogleMap]).
 *
 * Muestra la posición geográfica elegida y detecta cuando el usuario desplaza o arrastra la cámara
 * ([CameraMoveStartedReason.GESTURE]), ejecutando una geocodificación inversa ([obtenerDireccionTexto])
 * en un hilo en segundo plano para actualizar la ubicación seleccionada mediante [onUbicacionCambiada].
 *
 * @param context Contexto [Context] de Android necesario para geocodificación.
 * @param state Instancia de [EstadoReporte] que contiene la posición en latitud y longitud actual.
 * @param onUbicacionCambiada Callback invocado al cambiar el centro del mapa pasando `(latitud, longitud, direccionTexto)`.
 */
@Composable
fun MapaInteractivo(
    context: Context,
    state: EstadoReporte,
    onUbicacionCambiada: (Double, Double, String) -> Unit
) {
    val posicionInicial = remember {
        if (state.latitud != 0.0 && state.longitud != 0.0) {
            LatLng(state.latitud, state.longitud)
        } else {
            LatLng(19.559167, -99.283333) // Este es el centro de Atizapan de Zaragoza

        }
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(posicionInicial, 15f)

    }
    val coroutineScope = rememberCoroutineScope()

    // Este detecta cuando se finaliza el movimeinto de arrastrado poe el usuario

    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving && cameraPositionState.cameraMoveStartedReason == CameraMoveStartedReason.GESTURE) {
            val centroActual = cameraPositionState.position.target
            coroutineScope.launch(Dispatchers.IO) {
                val direccion =
                    obtenerDireccionTexto(context, centroActual.latitude, centroActual.longitude)
                withContext(Dispatchers.Main) {
                    onUbicacionCambiada(centroActual.latitude, centroActual.longitude, direccion)
                }

            }
        }
    }

    // Se anima la camara si las coordenadas cambian en el viewModel
    LaunchedEffect(state.latitud, state.longitud) {
        if (state.latitud != 0.0 && state.longitud != 0.0) {
            val nuevaPosicion = LatLng(state.latitud, state.longitud)
            if (cameraPositionState.position.target != nuevaPosicion) {
                cameraPositionState.animate(
                    update = CameraUpdateFactory.newLatLng(nuevaPosicion),
                    durationMs = 800
                )
            }
        }
    }


    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(), cameraPositionState = cameraPositionState,
            onMapClick = { latLng ->
                coroutineScope.launch {
                    cameraPositionState.animate(
                        update = CameraUpdateFactory.newLatLng(latLng),
                        durationMs = 600
                    )
                }
            }
        ) {
            if (state.ubicacionConfirmada) {
                val posicion = LatLng(state.latitud, state.longitud)

                val markerState = remember(state.latitud, state.longitud){
                    MarkerState(position = posicion)
                }
                Marker(
                    state = markerState,
                    title = state.direccion,
                    snippet = state.direccion.ifEmpty { "${state.latitud}, ${state.longitud}" }
                )
            }
        }
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Pin del centro del mapa ",
            tint = Color(0xFFE53935),
            modifier = Modifier
                .size(42.dp)
                .align(Alignment.Center)
        )


    }
}

/**
 * Función auxiliar privada que realiza la geocodificación inversa de coordenadas a una dirección postal textual.
 *
 * Utiliza [Geocoder.getFromLocation] en un contexto bloqueante de E/S. En caso de fallo o falta de resultados,
 * retorna una cadena formateada con los valores numéricos de latitud y longitud.
 *
 * @param context Contexto [Context] para instanciar [Geocoder].
 * @param lat Coordenada de latitud.
 * @param lng Coordenada de longitud.
 * @return La dirección obtenida en texto plano o una cadena formateada con las coordenadas.
 */
private fun obtenerDireccionTexto(context: Context, lat: Double, lng: Double): String {
    return try {
        val geocoder = Geocoder(context, Locale.getDefault())
        val direcciones = geocoder.getFromLocation(lat, lng, 1)
        if (!direcciones.isNullOrEmpty()) {
            direcciones[0].getAddressLine(0) ?: "Ubicación sin nombre"
        } else {
            "Lat: ${String.format(Locale.US, "%.4f", lat)}, Lng: ${String.format(Locale.US, "%.4f", lng)}"
        }
    } catch (e: Exception) {
        "Lat: ${String.format(Locale.US, "%.4f", lat)}, Lng: ${String.format(Locale.US, "%.4f", lng)}"
    }
}


/**
 * Previsualización de la pantalla [DondeOcurrio] en el entorno de desarrollo de Android Studio.
 */
@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Donde Ocurrió - Diseño Exacto"
)
@Composable
fun DondeOcurrioPreview() {
    SIPPINAAplicacionTheme {
        DondeOcurrio()
    }
}
