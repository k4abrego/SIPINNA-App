package com.example.sippina_aplicacion.view.PantallasReporte

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
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
import java.io.File
import java.io.FileOutputStream
import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.sippina_aplicacion.view.colorMorado
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material.icons.filled.ErrorOutline


@Composable
fun Evidencia(
    viewModel: ReporteVM = viewModel(),
    modifier: Modifier = Modifier,
    onSiguiente: () -> Unit = { },
    onAtras: () -> Unit = { }
) {
    // Suscripcion al estado centralizado de viewModel
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Mensaje de error para que no pueda avanzar hasta que
    // adjunte una evidencia

    var mostrarError by remember { mutableStateOf(false ) }

    // Determina el color del borde segun el estado de la evidencia
    val colorBordeDinamico = when{
        state.evidenciaUri!= null -> Color.Green
        mostrarError -> Color.Red
        else -> colorMorado
    }

    //Colores usados para los botones
    val colorMorado = Color(0xFF381EBD)
    val colorFondoBotonSiguiente = Color(0xFFD3E4F4)
    val colorTextoBotonSiguiente = Color(0xFF3B719F)
    val colorFondoBotonAtras = Color(0xFFE2E2E2)
    val colorTextoBotonAtras = Color(0xFF5A5A5A)

    // Launchers y gestión de permisos

    // Abrir la galeria
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.actualizarEvidenciaUri(it)
            mostrarError = false
            Toast.makeText(context, "Imagen seleccionada", Toast.LENGTH_SHORT).show()
        }
    }

    // Tomar foto rápida cámara
    val cameraLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicturePreview()) {
        bitmap ->
        if(bitmap!= null){
            val uriTemp = guardarBitmapEnCache(context, bitmap)
            viewModel.actualizarEvidenciaUri(uriTemp)
            mostrarError = false
            Toast.makeText(context, "Foto tomada con éxito ", Toast.LENGTH_SHORT).show()
        }
    }
    // Este es para el audio
    val audioRecorderLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val audioUri: Uri? = result.data?.data
            if (audioUri != null) {
                viewModel.actualizarEvidenciaUri(audioUri)
                mostrarError = false
                Toast.makeText(context, "Audio guardado con éxito", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Permiso para la camara
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            cameraLauncher.launch(null)
        } else {
            Toast.makeText(context, "Permiso de camara denegado", Toast.LENGTH_SHORT).show()
        }
    }
    // Adjuntar un archivo en general (PDF, DOC, Imagenes)
    val documentLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.actualizarEvidenciaUri(it)
            mostrarError = false
            Toast.makeText(context, "Archivo adjuntado", Toast.LENGTH_SHORT).show()
        }
    }

    val audioPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            val intent = Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION)
            try {
                audioRecorderLauncher.launch(intent)
            } catch (e: Exception) {
                // Si el emulador/dispositivo no tiene una app de grabadora preinstalada
                Toast.makeText(
                    context,
                    "No se encontró una aplicación de grabadora de voz",
                    Toast.LENGTH_LONG
                ).show()
            }
        } else {
            Toast.makeText(context, "Permiso de micrófono denegado", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .verticalScroll(rememberScrollState())
            .imePadding()
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.imagensippina),
            contentDescription = "Logo Sippina",
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Evidencia Personal",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = colorMorado
        )
        Spacer(modifier = Modifier.height(14.dp))
        BarraDePasos(pasoActual = 3)
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TarjetaOpcionMedia(
                icon = Icons.Default.PhotoCamera,
                contentDescription = "Tomar Foto",
                modifier = Modifier.weight(1f),
                onClick = {cameraPermissionLauncher.launch(Manifest.permission.CAMERA)})

            TarjetaOpcionMedia(
                icon = Icons.Default.Image,
                contentDescription = "Galeria",
                modifier = Modifier.weight(1f),
                onClick = {galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))})

        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TarjetaOpcionMedia(
                icon = Icons.Default.Videocam,
                contentDescription = "Grabar Video",
                modifier = Modifier.weight(1f),
                onClick = {cameraPermissionLauncher.launch(Manifest.permission.CAMERA) })

            TarjetaOpcionMedia(
                icon = Icons.Default.Mic,
                contentDescription = "Grabar Audio",
                modifier = Modifier.weight(1f),
                onClick = {audioPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)})

        }

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedCard(
            onClick = {documentLauncher.launch("*/*")}, shape = RoundedCornerShape(15.dp),
            border = BorderStroke(1.5.dp, colorBordeDinamico),
            colors = CardDefaults.outlinedCardColors(containerColor = Color.LightGray),
            modifier = Modifier
                .fillMaxWidth()
                .height(105.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Icon(
                    imageVector = if (state.evidenciaUri != null) Icons.Default.CheckCircle else Icons.Default.AttachFile,
                    contentDescription = "Adjuntar archivo",
                    tint = if (state.evidenciaUri != null) Color(0xFF2E7D32) else Color.Gray,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = if (state.evidenciaUri != null) "¡Evidencia adjuntada!" else "Adjuntar o subir archivo",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (state.evidenciaUri != null) Color(0xFF2E7D32) else Color.Gray,
                    textAlign = TextAlign.Center
                )

            }


        }

        Spacer(modifier = Modifier.height(75.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp))
        {
            Button(
                onClick = { onAtras() },
                colors = ButtonDefaults.buttonColors(colorFondoBotonAtras, colorTextoBotonAtras),
                modifier = Modifier
                    .weight(1f)
                    .height(46.dp)
            ) {
                Text(text = "Atrás", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }
            Button(
                onClick = {
                    if (state.evidenciaUri != null) {
                        mostrarError = false
                        onSiguiente()
                    } else {
                        mostrarError = true
                        Toast.makeText(
                            context,
                            "Es necesario adjuntar una evidencia para continuar",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    colorFondoBotonSiguiente,
                    colorTextoBotonSiguiente
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(46.dp)
            ) {
                Text(text = "Siguiente ", fontSize = 15.sp, fontWeight = FontWeight.Bold)


            }
        }


    }


}

// Esta funcion es importante es el que sirve para guardar el bitmpa en cache osea que no se pierda la foto
private fun guardarBitmapEnCache(context: Context, bitmap: Bitmap): Uri {
    val file = File(context.cacheDir, "evidencia_camara_${System.currentTimeMillis()}.jpg")
    FileOutputStream(file).use { out ->
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
    }
    return Uri.fromFile(file)
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
        border = BorderStroke(1.5.dp, Color.Blue),
        colors = CardDefaults.outlinedCardColors(containerColor = Color.LightGray),
        modifier = modifier.height(115.dp)
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

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Evidencia Opcional"
)

@Composable
fun EvidenciaPreview() {
    SIPPINAAplicacionTheme {
        Evidencia()
    }
}
