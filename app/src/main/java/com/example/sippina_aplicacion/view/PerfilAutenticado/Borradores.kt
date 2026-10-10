package com.example.sippina_aplicacion.view.PerfilAutenticado

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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.FileUpload
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sippina_aplicacion.R
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme
import com.example.sippina_aplicacion.model.BorradorGuardado
import com.example.sippina_aplicacion.model.EstadoReporte


/**
 * Contenedor con estado (Stateful) para la pantalla de gestión de borradores de reportes.
 *
 * Conecta la lógica de almacenamiento local de [BorradorGuardado] con la interfaz [Borradores].
 * Lee el borrador guardado en el dispositivo y actualiza reactivamente la vista al eliminarlo.
 *
 * @param onAtras Callback para regresar a la vista anterior.
 * @param onContinuar Callback ejecutado para retomar la captura del reporte borrador.
 */
@Composable
fun BorradoresScreen(
    onAtras: () -> Unit = {},
    onContinuar: () -> Unit = {}
) {
    val context = LocalContext.current
    val borradorStorage = remember { BorradorGuardado(context) }

    // Se obtiene  el borrador guardado localmente
    var borradorActual by remember { mutableStateOf(borradorStorage.obtenerBorradorLocal()) }

    Borradores(
        borrador = borradorActual,
        onAtras = onAtras,
        onContinuar = onContinuar,
        onEliminar = {
            borradorStorage.eliminarBorradorLocal()
            borradorActual = null // Actualiza la UI para mostrar una pantalla vacia
        }
    )
}

/**
 * Componente stateless que presenta la interfaz para consultar o descartar borradores guardados.
 *
 * Despliega la tarjeta [BorradorCard] si existe un [EstadoReporte] borrador activo, o un mensaje informativo
 * si no hay reportes pendientes por completar.
 *
 * @param modifier [Modifier] opcional para el contenedor principal.
 * @param borrador Instancia de [EstadoReporte] con los datos del borrador guardado; `null` si no existe ninguno.
 * @param onAtras Callback invocado al pulsar el botón de regresar.
 * @param onContinuar Callback invocado para reanudar el borrador.
 * @param onEliminar Callback invocado para purgar el borrador almacenado.
 */
@Composable
fun Borradores(
    modifier: Modifier = Modifier,
    borrador: EstadoReporte? = null,
    onAtras: () -> Unit = {},
    onContinuar: () -> Unit = {},
    onEliminar: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFEAF4FC))
            .verticalScroll(rememberScrollState())
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
                    containerColor = Color(0xFFEAF4FC),
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
                painter = painterResource(id = R.drawable.imagensippina),
                contentDescription = "Logo SIPINNA",
                modifier = Modifier.size(120.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Continuar reporte",
            color = Color(0xFF3D27C9),
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = 38.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Si tiene un reporte en progreso, puedes continuar desde donde lo dejaste",
            color = Color.Gray,
            fontSize = 15.sp,
            lineHeight = 19.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(30.dp))

        if (borrador != null) {
            val nombrePaso = when (borrador.pasoActual) {
                0 -> "Marcar ubicación"
                1 -> "Cuéntanos qué pasó"
                2 -> "Descripción del menor"
                3 -> "Agregar evidencia"
                4 -> "Privacidad y seguimiento"
                else -> "Reporte en progreso"
            }

            BorradorCard(
                paso = nombrePaso,
                fecha = "Guardado en este dispositivo",
                pasoActual = borrador.pasoActual,
                onContinuar = onContinuar,
                onEliminar = onEliminar
            )
        } else {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No hay reportes pendientes de completar",
                    color = Color.Gray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center

                )
            }
            }
        Spacer(modifier = modifier.height(20.dp))

        }

    }

/**
 * Tarjeta gráfica que muestra el resumen visual del estado de un borrador guardado.
 *
 * Presenta una barra con iconos representativos de las 5 etapas del reporte destacando los pasos completados
 * según [pasoActual], además de botones para eliminar o continuar el proceso.
 *
 * @param paso Nombre descriptivo de la etapa en la que se interrumpió el reporte.
 * @param fecha Texto descriptivo sobre el almacenamiento o fecha del borrador.
 * @param pasoActual Índice entero del paso activo dentro del flujo.
 * @param onContinuar Callback ejecutado al presionar el botón "Continuar".
 * @param onEliminar Callback ejecutado al presionar el botón "Eliminar".
 */
@Composable
fun BorradorCard(
    paso: String,
    fecha: String,
    pasoActual: Int,
    onContinuar: () -> Unit = {},
    onEliminar: () -> Unit = {}
)
{
    val morado = Color(0xFF3D27C9)

    val iconos = listOf(
        Icons.Outlined.LocationOn,
        Icons.Outlined.Description,
        Icons.Outlined.Face,
        Icons.Outlined.FileUpload,
        Icons.Outlined.Shield
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(235.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Description,
                    contentDescription = "Reporte en progreso",
                    tint = morado,
                    modifier = Modifier.size(32.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Reporte en progreso",
                        color = morado,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = fecha,
                        color = Color.DarkGray,
                        fontSize = 12.sp,
                        lineHeight = 15.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "Continuar en $paso",
                color = Color.DarkGray,
                fontSize = 11.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(38.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(Color.Gray)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    iconos.forEachIndexed { index, icono ->
                        val completado = index <= pasoActual

                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(
                                    color = if (completado) morado else Color(0xFFE5E5E5),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = icono,
                                contentDescription = "Etapa ${index + 1}",
                                tint = if (completado) Color.White else morado,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = onEliminar,
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp),
                    shape = RoundedCornerShape(25.dp),
                    border = BorderStroke(1.dp, Color.Red),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.White,
                        contentColor = Color.Red
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        modifier = Modifier.size(17.dp)
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    Text(
                        text = "Eliminar",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Button(
                    onClick = onContinuar,
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = morado
                    )
                ) {
                    Text(
                        text = "Continuar",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }

}


/**
 * Previsualización de la pantalla [Borradores] en el entorno de desarrollo de Android Studio.
 */
@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun BorradoresPreview() {

    SIPPINAAplicacionTheme {
        Borradores(
        )
    }
}
