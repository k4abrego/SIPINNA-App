package com.example.sippina_aplicacion.view.PantallasReporte

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.remote.creation.dsl.first
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import com.example.sippina_aplicacion.model.EstadoReporte
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.maps.android.compose.CameraMoveStartedReason
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
import com.google.android.gms.maps.MapsInitializer

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

    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(appContext)
    }

    var mostrarDialogoSalida by remember { mutableStateOf(false) }
    var mostrarDialogoDireccionManual by remember { mutableStateOf(false) }
    var campoDireccionManual by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.iniciarlizarBorradorStorage(appContext)
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
    var textoBusqueda by remember { mutableStateOf("") }

    val opcionUbicacionActual = state.opcionUbicacion.ifEmpty {
        "Seleccionar en el mapa"
    }

    val ejecutarBusqueda = {
        if (textoBusqueda.isNotBlank()) {
            coroutineScope.launch(Dispatchers.IO) {
                val coords = obtenerCoordenadasDeTexto(appContext, textoBusqueda)
                withContext(Dispatchers.Main) {
                    if (coords != null) {
                        viewModel.setUbicacion(coords.first, coords.second, coords.third)
                    } else {
                        Toast.makeText(
                            appContext,
                            "No se encontró la ubicación. Intenta agregar una dirección más específica",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorFondo)
            .imePadding()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(top = 8.dp, bottom = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EncabezadoReporte(
                onGuardarBorrador = {
                    viewModel.guardarBorrador()
                    Toast.makeText(appContext, "Borrador guardado exitosamente", Toast.LENGTH_SHORT).show()
                }
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "¿Dónde ocurrió?",
                color = colorMorado,
                fontSize = 35.sp,
                lineHeight = 38.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            BarraDePasos(pasoActual = 0)

            Spacer(modifier = Modifier.height(16.dp))

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

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
            ) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFEBEEF8)),
                    modifier = Modifier.fillMaxSize()
                ) {
                    MapaInteractivo(
                        context = appContext,
                        state = state,
                        onUbicacionCambiada = { lat, lng, direccion ->
                            viewModel.setUbicacion(lat, lng, direccion)
                        }
                    )
                }

                Card(
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .align(Alignment.TopCenter)
                ) {
                    OutlinedTextField(
                        value = textoBusqueda,
                        onValueChange = { textoBusqueda = it },
                        placeholder = {
                            Text(
                                text = "Buscar calle, colonia o municipio",
                                fontSize = 13.sp,
                                color = Color.Gray
                            )
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(onSearch = { ejecutarBusqueda() }),
                        trailingIcon = {
                            IconButton(onClick = { ejecutarBusqueda() }) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Buscar ubicación",
                                    tint = colorMorado
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        )
                    )
                }
            }

            if (state.ubicacionConfirmada) {
                Spacer(modifier = Modifier.height(10.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "Dirección: ${state.direccion.ifEmpty { "Sin dirección exacta" }}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorMorado,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Coordenadas: Lat ${String.format(Locale.US, "%.5f", state.latitud)}, Lng ${String.format(Locale.US, "%.5f", state.longitud)}",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val opciones = listOf(
                    "Usar mi ubicación actual",
                    "Seleccionar en el mapa",
                    "Ingresar dirección manualmente"
                )
                opciones.forEach { opcion ->
                    val seleccionado = opcionUbicacionActual == opcion

                    OutlinedButton(
                        onClick = {
                            viewModel.seleccionarOpcionUbicacion(opcion)

                            if (opcion == "Ingresar dirección manualmente") {
                                mostrarDialogoDireccionManual = true
                            } else if (opcion == "Usar mi ubicación actual") {
                                Toast.makeText(appContext, "Obteniendo ubicación GPS...", Toast.LENGTH_SHORT).show()

                                try {
                                    fusedLocationClient.lastLocation
                                        .addOnSuccessListener { location ->
                                            if (location != null) {
                                                coroutineScope.launch(Dispatchers.IO) {
                                                    val direccion = obtenerDireccionTexto(appContext, location.latitude, location.longitude)
                                                    withContext(Dispatchers.Main) {
                                                        viewModel.setUbicacion(location.latitude, location.longitude, direccion)
                                                    }
                                                }
                                            } else {
                                                fusedLocationClient.getCurrentLocation(
                                                    Priority.PRIORITY_HIGH_ACCURACY,
                                                    CancellationTokenSource().token
                                                ).addOnSuccessListener { currentLocation ->
                                                    if (currentLocation != null) {
                                                        coroutineScope.launch(Dispatchers.IO) {
                                                            val direccion = obtenerDireccionTexto(appContext, currentLocation.latitude, currentLocation.longitude)
                                                            withContext(Dispatchers.Main) {
                                                                viewModel.setUbicacion(currentLocation.latitude, currentLocation.longitude, direccion)
                                                            }
                                                        }
                                                    } else {
                                                        Toast.makeText(appContext, "Error al obtener ubicación GPS", Toast.LENGTH_SHORT).show()
                                                    }
                                                }.addOnFailureListener {
                                                    Toast.makeText(appContext, "No se pudo obtener la ubicación GPS", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        }
                                        .addOnFailureListener {
                                            Toast.makeText(appContext, "Error al consultar GPS", Toast.LENGTH_SHORT).show()
                                        }
                                } catch (e: SecurityException) {
                                    Toast.makeText(appContext, "Sin permisos de ubicación concedidos", Toast.LENGTH_SHORT).show()
                                } catch (e: Exception) {
                                    Toast.makeText(appContext, "Error al acceder al servicio de ubicación", Toast.LENGTH_SHORT).show()
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = CircleShape,
                        border = if (seleccionado) null else BorderStroke(1.5.dp, colorMorado),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (seleccionado) colorMorado else Color.Transparent,
                            contentColor = if (seleccionado) Color.White else colorMorado
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
                Text(text = "Atrás", fontSize = 16.sp, fontWeight = FontWeight.Bold)
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
                Text(text = "Siguiente", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }

    if (mostrarDialogoDireccionManual) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoDireccionManual = false },
            title = {
                Text(text = "Escribir dirección", color = colorMorado, fontWeight = FontWeight.Bold)
            },
            text = {
                Column {
                    Text(
                        text = "Ingresa la calle, número, colonia y municipio:",
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = campoDireccionManual,
                        onValueChange = { campoDireccionManual = it },
                        placeholder = {
                            Text(
                                text = "Ej. Av 1 de Mayo, Col. Centro, Atizapán",
                                fontSize = 13.sp,
                                color = Color.Gray
                            )
                        },
                        singleLine = false,
                        maxLines = 3,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorMorado,
                            unfocusedBorderColor = Color.Gray,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (campoDireccionManual.isNotBlank()) {
                            coroutineScope.launch(Dispatchers.IO) {
                                val coords = obtenerCoordenadasDeTexto(appContext, campoDireccionManual)
                                withContext(Dispatchers.Main) {
                                    if (coords != null) {
                                        viewModel.setUbicacion(coords.first, coords.second, coords.third)
                                    } else {
                                        viewModel.setUbicacion(0.0, 0.0, campoDireccionManual)
                                    }
                                    mostrarDialogoDireccionManual = false
                                }
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = colorMorado)
                ) {
                    Text("Guardar dirección")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { mostrarDialogoDireccionManual = false }) {
                    Text("Cancelar")
                }
            },
            shape = RoundedCornerShape(20.dp),
            containerColor = Color(0xFFEAF4FC)
        )
    }

    if (mostrarDialogoSalida) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoSalida = false },
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
                OutlinedButton(onClick = { mostrarDialogoSalida = false }) {
                    Text(text = "No, continuar")
                }
            },
            shape = RoundedCornerShape(20.dp),
            containerColor = Color(0xFFEAF4FC)
        )
    }
}

@Composable
fun MapaInteractivo(
    context: Context,
    state: EstadoReporte,
    onUbicacionCambiada: (Double, Double, String) -> Unit
) {
    // Inicialización explícita del SDK de mapas para evitar NullPointerException en CameraUpdateFactory
    LaunchedEffect(Unit) {
        try {
            MapsInitializer.initialize(context, MapsInitializer.Renderer.LATEST) {}
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    val hayConexion = remember(context) { esRedDisponible(context) }
    val apiMapasDisponible = remember(context) { ServicioDisponible(context) }

    Box(modifier = Modifier.fillMaxSize()) {
        if (hayConexion && apiMapasDisponible) {
            val posicionInicial = remember {
                if (state.latitud != 0.0 && state.longitud != 0.0) {
                    LatLng(state.latitud, state.longitud)
                } else {
                    LatLng(19.559167, -99.283333)
                }
            }
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(posicionInicial, 15f)
            }
            val coroutineScope = rememberCoroutineScope()

            LaunchedEffect(cameraPositionState.isMoving) {
                if (!cameraPositionState.isMoving && cameraPositionState.cameraMoveStartedReason == CameraMoveStartedReason.GESTURE) {
                    val centroActual = cameraPositionState.position.target
                    coroutineScope.launch(Dispatchers.IO) {
                        val direccion = obtenerDireccionTexto(context, centroActual.latitude, centroActual.longitude)
                        withContext(Dispatchers.Main) {
                            onUbicacionCambiada(centroActual.latitude, centroActual.longitude, direccion)
                        }
                    }
                }
            }

            LaunchedEffect(state.latitud, state.longitud) {
                if (state.latitud != 0.0 && state.longitud != 0.0) {
                    val nuevaPosicion = LatLng(state.latitud, state.longitud)
                    if (cameraPositionState.position.target != nuevaPosicion) {
                        try {
                            cameraPositionState.animate(
                                update = CameraUpdateFactory.newLatLng(nuevaPosicion),
                                durationMs = 800
                            )
                        } catch (e: Exception) {
                            cameraPositionState.position = CameraPosition.fromLatLngZoom(nuevaPosicion, 15f)
                        }
                    }
                }
            }

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                onMapClick = { latLng ->
                    coroutineScope.launch {
                        try {
                            cameraPositionState.animate(
                                update = CameraUpdateFactory.newLatLng(latLng),
                                durationMs = 600
                            )
                        } catch (e: Exception) {
                            cameraPositionState.position = CameraPosition.fromLatLngZoom(latLng, 15f)
                        }
                    }
                }
            ) {
                if (state.ubicacionConfirmada) {
                    val posicion = LatLng(state.latitud, state.longitud)
                    val markerState = remember(state.latitud, state.longitud) {
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
                contentDescription = "Pin del centro del mapa",
                tint = Color(0xFFE53935),
                modifier = Modifier
                    .size(42.dp)
                    .align(Alignment.Center)
            )
        } else {
            val mensajeDetalle = when {
                !hayConexion -> "Mapa no disponible sin conexión a internet"
                !apiMapasDisponible -> "Servicio de mapa no disponible"
                else -> "Servicio de ubicación no disponible"
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFE8ECEF))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Sin conexión",
                    tint = Color(0xFFC01B1E),
                    modifier = Modifier.size(44.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Servicio no disponible",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFC01B1E),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = mensajeDetalle,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

private fun obtenerDireccionTexto(context: Context, lat: Double, lng: Double): String {
    return try {
        val geocoder = Geocoder(context, Locale.getDefault())
        @Suppress("DEPRECATION")
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

private fun obtenerCoordenadasDeTexto(
    context: Context,
    direccionTexto: String
): Triple<Double, Double, String>? {
    return try {
        val geocoder = Geocoder(context, Locale.getDefault())
        @Suppress("DEPRECATION")
        val direcciones = geocoder.getFromLocationName(direccionTexto, 1)
        val primera = direcciones?.firstOrNull()
        if (primera != null) {
            Triple(primera.latitude, primera.longitude, primera.getAddressLine(0) ?: direccionTexto)
        } else null
    } catch (e: Exception) {
        null
    }
}

@SuppressLint("ServiceCast")
private fun esRedDisponible(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}

private fun ServicioDisponible(context: Context): Boolean {
    val googleApiAvailability = GoogleApiAvailability.getInstance()
    val resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context)
    return resultCode == ConnectionResult.SUCCESS
}

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