package com.example.sippina_aplicacion.view.PantallasReporte

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.InsertDriveFile
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.sippina_aplicacion.R
import com.example.sippina_aplicacion.ui.theme.SIPPINAAplicacionTheme
import com.example.sippina_aplicacion.model.EvidenciaReporte
import com.example.sippina_aplicacion.viewmodel.ReporteVM
import java.io.File

/**
 * Pantalla opcional para la selección y carga de archivos de evidencia multimedia (Paso 4 del reporte).
 *
 * Permite al usuario adjuntar hasta un máximo de 2 elementos de evidencia multimedia
 * capturando fotografías o grabando videos con la cámara del dispositivo, seleccionando imágenes de la galería,
 * o adjuntando archivos de audio y documentos.
 *
 * Gestiona permisos en tiempo de ejecución y utiliza [FileProvider] para generar [Uri] temporales seguras.
 *
 * @param viewModel Instancia de [ReporteVM] que mantiene el estado y los archivos de evidencia.
 * @param modifier [Modifier] opcional para personalizar el contenedor de diseño.
 * @param onSiguiente Callback ejecutado al presionar el botón "Siguiente" para continuar al paso de Privacidad.
 * @param onAtras Callback para regresar al paso previo de Descripción del Menor.
 */
@Composable
fun Evidencia(
    viewModel: ReporteVM = viewModel(),
    modifier: Modifier = Modifier,
    onSiguiente: () -> Unit = {},
    onAtras: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.iniciarlizarBorradorStorage(context)
    }
    BackHandler { onAtras() }

    // Se define el maximo de evidencias de 2 para el usuario
    val maxEvidencias = 2
    val limiteAlcanzado = state.evidencias.size >= maxEvidencias

    // Este es un helper para que verifique el limite antes de lanzar o poner otra evidencia
    fun ejecutarSiExisteEspacio( accion:() -> Unit ){
        if(limiteAlcanzado){
            Toast.makeText(context, "Solo se puede aduntar un maximo de $maxEvidencias evidencias",
                Toast.LENGTH_SHORT).show()

        }else
        {
            accion()
        }
    }

    // Este es el Uri temporal para almacenar las fotos o videos de la camara
    var tempUriCamara by remember { mutableStateOf<Uri?>(null) }

    // Las siguiente implementaciones son los launchers para la seleccion de los archivos y multimedia
    // Este es para el selector de imagenes de la galeria
    val galeriaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
    )
    { uri ->
        uri?.let { viewModel.agregarEvidencia(it, "imagen", "galeria_imagen.jpg") }
    }

    // Este es para tomar las fotos con la camara
    val camaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { exito ->
        if (exito && tempUriCamara != null) {
            viewModel.agregarEvidencia(tempUriCamara!!, "imagen", "foto_camara.jpg")
        }
    }

    // Permiso de la camara
    val permisoCamaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) {
            val uri = crearUriTemporal(context, "jpg")
            tempUriCamara = uri
            camaraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Se requiere permiso de cámara", Toast.LENGTH_SHORT).show()
        }
    }

    // Este es para grabar video
    val videoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CaptureVideo()
    ) { exito ->
        if (exito && tempUriCamara != null) {
            viewModel.agregarEvidencia(tempUriCamara!!, "video", "video_camara.mp4")
        }
    }

    // Este es para adjntar el archivo de audio
    val archivoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { viewModel.agregarEvidencia(it, "archivo", "documento_adjunto") }
    }


    val colorMorado = Color(0xFF381DC3)
    val colorFondo = Color(0xFFEAF4FC)

    val colorFondoBotonSiguiente = Color(0xFFD3E4F4)
    val colorTextoBotonSiguiente = Color(0xFF3B719F)

    val colorFondoBotonAtras = Color(0xFFE2E2E2)
    val colorTextoBotonAtras = Color(0xFF5A5A5A)

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
            EncabezadoReporte(onGuardarBorrador = { viewModel.guardarBorrador()
            Toast.makeText(context, "Borrador guardado exitosamente", Toast.LENGTH_SHORT).show()})

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Evidencia",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = colorMorado,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            BarraDePasos(pasoActual = 3)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Si tienes alguna evidencia, puedes agregarla aquí.",
                fontSize = 16.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorMorado,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                TarjetaOpcionMedia(
                    icon = Icons.Default.PhotoCamera,
                    contentDescription = "Tomar foto",
                    modifier = Modifier.weight(1f),
                    deshabilitado = limiteAlcanzado,
                    onClick = {
                        ejecutarSiExisteEspacio {

                            solicitarPermisoYEjecutar(
                                context = context,
                                permiso = Manifest.permission.CAMERA,
                                launcher = permisoCamaraLauncher
                            )
                            {
                                val uri = crearUriTemporal(context, "jpg")
                                tempUriCamara = uri
                                camaraLauncher.launch(uri)
                            }
                        }
                    }
                )

                TarjetaOpcionMedia(
                    icon = Icons.Default.Image,
                    contentDescription = "Galería",
                    modifier = Modifier.weight(1f),
                    deshabilitado = limiteAlcanzado,
                    onClick = {
                        ejecutarSiExisteEspacio {
                        galeriaLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    }
                    }
                )
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                TarjetaOpcionMedia(
                    icon = Icons.Default.Videocam,
                    contentDescription = "Grabar video",
                    modifier = Modifier.weight(1f),
                    deshabilitado = limiteAlcanzado,
                    onClick = {
                        ejecutarSiExisteEspacio {
                            solicitarPermisoYEjecutar(
                                context = context,
                                permiso = Manifest.permission.CAMERA,
                                launcher = permisoCamaraLauncher
                            )
                            {
                                val uri = crearUriTemporal(context, "mp4")
                                tempUriCamara = uri
                                videoLauncher.launch(uri)
                            }
                        }

                    }
                )

                TarjetaOpcionMedia(
                    icon = Icons.Default.Mic,
                    contentDescription = "Grabar audio",
                    modifier = Modifier.weight(1f),
                    deshabilitado = limiteAlcanzado,
                        onClick = {
                            ejecutarSiExisteEspacio {
                            archivoLauncher.launch("audio/*")
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedCard(
                onClick = {
                    ejecutarSiExisteEspacio {

                    archivoLauncher.launch("*/*") } },
                shape = RoundedCornerShape(15.dp),
                border = BorderStroke(1.5.dp, colorMorado),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.AttachFile,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(32.dp)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = if (limiteAlcanzado) "Límite de evidencias alcanzado ($maxEvidencias/$maxEvidencias)" else "Adjuntar o subir archivo",                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.DarkGray,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            AnimatedVisibility(visible = state.evidencias.isNotEmpty()) {
                Column(modifier = modifier.fillMaxWidth()) {
                    Text(
                        text = "Archivos adjuntos (${state.evidencias.size}):",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorMorado,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(state.evidencias) { evidencia ->
                            ItemEvidenciaAdjunta(
                                evidencia = evidencia,
                                onEliminar = { viewModel.eliminarEvidencia(evidencia.uri) }
                            )
                        }
                    }
                }
            }

            Text(
                text = "Este paso es opcional. Puedes continuar sin adjuntar archivos.",
                fontSize = 14.sp,
                lineHeight = 18.sp,
                color = Color.DarkGray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = onAtras,
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
}

/**
 * Tarjeta interactiva para la selección de un tipo de medio de evidencia (Cámara, Galería, Video o Audio).
 *
 * @param icon Icono vectorial [ImageVector] correspondiente a la acción.
 * @param contentDescription Texto alternativo de descripción accesible para el icono.
 * @param modifier [Modifier] opcional para la tarjeta.
 * @param deshabilitado Flag que indica si la opción debe deshabilitarse por haber alcanzado el límite máximo de evidencias.
 * @param onClick Acción ejecutada al hacer clic sobre la tarjeta.
 */
@Composable
private fun TarjetaOpcionMedia(
    icon: ImageVector,
    contentDescription: String,
    modifier: Modifier = Modifier,
    deshabilitado: Boolean = false,
    onClick: () -> Unit


) {

    OutlinedCard(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.5.dp, if (deshabilitado) Color.LightGray else Color(0xFF381DC3)),
        modifier = modifier.height(100.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = Color.Gray,
                modifier = Modifier.size(42.dp)
            )
        }

    }
}

/**
 * Vista previa e ítem individual para listar cada evidencia adjunta en la interfaz.
 *
 * Muestra una miniatura cargada dinámicamente con Coil ([AsyncImage]) para imágenes o un icono descriptivo
 * para videos, audios y documentos, incorporando un botón superpuesto para su eliminación ([onEliminar]).
 *
 * @param evidencia Objeto [EvidenciaReporte] con la URI y tipo del archivo adjunto.
 * @param onEliminar Callback ejecutado al presionar el botón de eliminar.
 */
@Composable
private fun ItemEvidenciaAdjunta(
    evidencia: EvidenciaReporte,
    onEliminar: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
    ) {
        if (evidencia.tipo == "imagen") {
            AsyncImage(
                model = evidencia.uri,
                contentDescription = "Evidencia",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = when (evidencia.tipo) {
                        "video" -> Icons.Default.Videocam
                        "audio" -> Icons.Default.Mic
                        else -> Icons.AutoMirrored.Filled.InsertDriveFile
                    },
                    contentDescription = null,
                    tint = Color(0xFF381DC3),
                    modifier = Modifier.size(36.dp)
                )
            }
        }

        IconButton(
            onClick = onEliminar,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(24.dp)
                .background(Color.Red, CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Eliminar",
                tint = Color.White,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}


/**
 * Verifica si se posee el permiso especificado en tiempo de ejecución antes de lanzar la acción solicitada.
 *
 * Si el permiso ya se encuentra concedido ejecuta la acción de forma inmediata; caso contrario, solicita el permiso
 * utilizando el [launcher] proporcionado.
 *
 * @param context Contexto [Context] de Android.
 * @param permiso Nombre del permiso requerido en el manifiesto (ej. `Manifest.permission.CAMERA`).
 * @param launcher Launcher para la solicitud de permisos en tiempo de ejecución.
 * @param onConcedido Lambda que se invoca si el permiso ya está concedido.
 */
private fun solicitarPermisoYEjecutar(
    context: Context,
    permiso: String,
    launcher: ActivityResultLauncher<String>,
    onConcedido: () -> Unit
) {
    if (ContextCompat.checkSelfPermission(context, permiso) == PackageManager.PERMISSION_GRANTED) {
        onConcedido()
    } else {
        launcher.launch(permiso)
    }
}

/**
 * Crea un archivo temporal en el directorio de caché privado del dispositivo y retorna su [Uri] segura mediante [FileProvider].
 *
 * @param context Contexto [Context] para acceder al directorio de caché y proveedor de archivos del paquete.
 * @param extension Extensión del archivo temporal (ej. `"jpg"` o `"mp4"`).
 * @return [Uri] segura del archivo temporal creado.
 */
private fun crearUriTemporal(context: Context, extension: String): Uri {
    val tempFile = File.createTempFile("media_", ".$extension", context.cacheDir).apply {
        createNewFile()
        deleteOnExit()
    }
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        tempFile
    )
}

/**
 * Previsualización de la pantalla [Evidencia] en el entorno de desarrollo de Android Studio.
 */
@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
fun EvidenciaPreview() {
    SIPPINAAplicacionTheme {
        Evidencia()
    }
}
