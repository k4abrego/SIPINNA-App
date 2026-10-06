package com.example.sippina_aplicacion.view.PantallasReporte

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.compose.material.icons.filled.InsertDriveFile
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.sippina_aplicacion.viewmodel.EvidenciaReporte
import com.example.sippina_aplicacion.viewmodel.ReporteVM
import java.io.File

@Composable
fun Evidencia(
    viewModel: ReporteVM = viewModel(),
    modifier: Modifier = Modifier,
    onSiguiente: () -> Unit = {},
    onAtras: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    BackHandler { onAtras() }

    // Este es el Uri temporal para almacenar las fotos o videos de la camara
    var tempUriCamara by remember { mutableStateOf<Uri?>(null) }

    // Las siguiente implementaciones son los launchers para la seleccion de los archivos y multimedia
    // Este es para el selector de imagenes de la galeria
    var galeriaLauncher = rememberLauncherForActivityResult(
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
            Image(
                painter = painterResource(id = R.drawable.imagensippina),
                contentDescription = "Logo SIPINNA",
                modifier = Modifier.size(120.dp)
            )

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
                    onClick = {
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
                )

                TarjetaOpcionMedia(
                    icon = Icons.Default.Image,
                    contentDescription = "Galería",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        galeriaLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
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
                    onClick = {
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
                )

                TarjetaOpcionMedia(
                    icon = Icons.Default.Mic,
                    contentDescription = "Grabar audio",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        archivoLauncher.launch("audio/*")
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedCard(
                onClick = { archivoLauncher.launch("*/*") },
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
                        text = "Adjuntar o subir archivo",
                        fontSize = 15.sp,
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

@Composable
private fun TarjetaOpcionMedia(
    icon: ImageVector,
    contentDescription: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit


) {

    OutlinedCard(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            1.5.dp,
            Color(0xFF381DC3)
        ),
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

//Este implementacion es para la vista previa de coil
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


// Estos son permisos y uris temporales
private fun solicitarPermisoYEjecutar(
    context: Context,
    permiso: String,
    launcher: androidx.activity.result.ActivityResultLauncher<String>,
    onConcedido: () -> Unit
) {
    if (ContextCompat.checkSelfPermission(context, permiso) == PackageManager.PERMISSION_GRANTED) {
        onConcedido()
    } else {
        launcher.launch(permiso)
    }
}

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
