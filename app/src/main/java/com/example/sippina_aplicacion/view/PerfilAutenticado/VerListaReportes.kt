package com.example.sippina_aplicacion.view.PantallasReporte

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sippina_aplicacion.R
import com.example.sippina_aplicacion.model.ReporteModel
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme
import com.example.sippina_aplicacion.viewmodel.ReporteVM

/**
 * Pantalla contenedora con estado (Stateful)
 * Se suscribe a los flujos del ViewModel y solicita los reportes al backend.
 */
@Composable
fun VerListaReportes(
    viewModel: ReporteVM = viewModel(),
    onAtras: () -> Unit = {},
    onReporteClick: (String) -> Unit = {}
) {
    val listaReportes by viewModel.misReportes.collectAsState()
    val cargando by viewModel.cargando.collectAsState()

    // Solicita los reportes al servidor backend al entrar a la pantalla
    LaunchedEffect(Unit) {
        viewModel.obtenerMisReportesDelBackEnd()
    }

    BackHandler { onAtras() }

    VerListaReportesContent(
        listaReportes = listaReportes,
        cargando = cargando,
        onAtras = onAtras,
        onReporteClick = onReporteClick
    )
}

/**
 * Componente visual sin estado (Stateless)
 * Recibe los datos filtrados e hipervincula las acciones a la interfaz.
 */
@Composable
fun VerListaReportesContent(
    listaReportes: List<ReporteModel> = emptyList(),
    cargando: Boolean = false,
    onAtras: () -> Unit = {},
    onReporteClick: (String) -> Unit = {}
) {
    val morado = Color(0xFF381DC3)
    val fondo = Color(0xFFEAF4FC)

    // Estado del buscador local
    var textoBusqueda by remember { mutableStateOf("") }

    // Filtrado automático para la búsqueda por Folio o Estatus
    val reportesFiltrados = listaReportes.filter {
        it.folio.contains(textoBusqueda, ignoreCase = true) ||
                it.estatus.contains(textoBusqueda, ignoreCase = true)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                FilledIconButton(
                    onClick = onAtras,
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.CenterStart),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = fondo,
                        contentColor = Color(0xFF3D27C9)
                    )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Atrás",
                        modifier = Modifier.size(22.dp)
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.logorieti),
                    contentDescription = "Logo SIPINNA",
                    modifier = Modifier.size(120.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Reportes",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                color = morado,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = textoBusqueda,
                onValueChange = { textoBusqueda = it },
                placeholder = {
                    Text(
                        text = "Buscar por folio o estatus...",
                        fontSize = 15.sp,
                        color = morado.copy(alpha = 0.7f)
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar",
                        tint = morado
                    )
                },
                trailingIcon = {
                    if (textoBusqueda.isNotEmpty()) {
                        IconButton(onClick = { textoBusqueda = "" }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Limpiar",
                                tint = morado
                            )
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = fondo,
                    unfocusedContainerColor = fondo,
                    focusedBorderColor = morado,
                    unfocusedBorderColor = morado,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (cargando) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = morado)
                }
            } else if (reportesFiltrados.isEmpty()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (textoBusqueda.isEmpty()) "No tienes reportes registrados." else "No se encontraron coincidencias.",
                        fontSize = 15.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(reportesFiltrados) { reporte ->
                        val icono = obtenerIconoPorEstatus(reporte.estatus)

                        ItemReporteCard(
                            folio = reporte.folio,
                            icono = icono,
                            colorIcono = morado,
                            bordeColor = morado,
                            onClick = { onReporteClick(reporte.folio) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            HorizontalDivider(
                thickness = 1.dp,
                color = morado.copy(alpha = 0.5f)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Estado de tu reporte",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ItemEstadoLeyenda("Enviado", Icons.AutoMirrored.Filled.Send, morado)
                ItemEstadoLeyenda("En proceso", Icons.Default.History, morado)
                ItemEstadoLeyenda("En revisión", Icons.Default.Flag, morado)
                ItemEstadoLeyenda("Cerrado", Icons.Default.Check, morado)
            }

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

/**
 * Mapea la cadena del estado devuelto por el servidor al icono vectorial correspondiente.
 */
private fun obtenerIconoPorEstatus(estatus: String): ImageVector {
    return when (estatus.lowercase().trim()) {
        "enviado" -> Icons.AutoMirrored.Filled.Send
        "en proceso" -> Icons.Default.History
        "en revisión", "en revision" -> Icons.Default.Flag
        "cerrado", "concluido" -> Icons.Default.Check
        else -> Icons.Default.History
    }
}

/**
 * Tarjeta individual para mostrar el folio y el icono descriptivo del estado de un reporte.
 */
@Composable
private fun ItemReporteCard(
    folio: String,
    icono: ImageVector,
    colorIcono: Color,
    bordeColor: Color,
    grosorBorde: Dp = 1.5.dp,
    onClick: () -> Unit
) {
    var seleccionado by remember { mutableStateOf(false) }

    val colorFondo by animateColorAsState(
        targetValue = if (seleccionado) Color(0xFFE0DBFA) else Color(0xFFEAF4FC),
        animationSpec = tween(durationMillis = 250),
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

/**
 * Elemento gráfico de la leyenda explicativa de estados.
 */
@Composable
private fun ItemEstadoLeyenda(
    etiqueta: String,
    icono: ImageVector,
    colorFondo: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
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

/**
 * Previsualización en el entorno de desarrollo Android Studio.
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListaReportesPreview() {
    val listaEjemplo = listOf(
        ReporteModel("REP-1234-1234", "Enviado"),
        ReporteModel("REP-1234-1235", "En proceso"),
        ReporteModel("REP-1234-1236", "En revisión"),
        ReporteModel("REP-1234-1237", "Cerrado")
    )
    SIPPINAAplicacionTheme {
        VerListaReportesContent(listaReportes = listaEjemplo)
    }
}